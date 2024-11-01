package waveFunctionCollapse.algorithm;

import waveFunctionCollapse.gui.GridFrame;
import waveFunctionCollapse.parameters.AlgorithmParameters;
import waveFunctionCollapse.tilesetdefinition.EdgeType;
import waveFunctionCollapse.tilesetdefinition.TileConfiguration;
import waveFunctionCollapse.tilesetdefinition.TileSet;
import waveFunctionCollapse.tilesetdefinition.TileType;

import java.sql.Array;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * The algorithm that performs the WaveFunctionCollapse
 */
public class WaveFunctionCollapseAlgorithm {

    private final Set<TileConfiguration> tileConfigurations;
    private final Grid grid;
    private final int entropyRandomScore;

    /**
     * dependent on the given algorithmSpeed parameter, can reach from 0ms to 1000ms
     */
    private final int delayInMs;
    private final Map<TileType, Float> probabilityDistribution;
    private int tilesCollapsed;
    private static final Random random =  new Random();

    /**
     * Creates a new instance of the algorithm
     *
     * @param tileSet the TileSet the algorithm should work upon
     * @param parameters all parameters relevant to the algorithm
     */
    public WaveFunctionCollapseAlgorithm(final TileSet tileSet, final AlgorithmParameters parameters) {
        this.delayInMs = parameters.algorithmSpeed();
        this.probabilityDistribution = parameters.probabilityDistribution();

        // immediately filters out TileTypes with a probability value of 0
        this.tileConfigurations = tileSet.getAllTileConfigurations()
                .stream()
                .filter(config -> this.probabilityDistribution.get(config.tileType()) != 0)
                .collect(Collectors.toSet());
        this.entropyRandomScore = (int) Math.ceil(parameters.nonRandomFactor() * this.tileConfigurations.size());

        // Sets up Grid, GridFrame and Tiles
        this.grid = new Grid(parameters.tilesHorizontal(), parameters.tilesVertical(), parameters.borderEdge());
        GridFrame frame = new GridFrame(parameters.tilesHorizontal(), parameters.tilesVertical());


        // Initialize Tiles: Creates and initializes tiles for every spot
        int tilesHorizontal = parameters.tilesHorizontal();
        for (int i = 0; i<this.grid.totalTiles(); i++) {
            int row = i / tilesHorizontal;
            int col = i % tilesHorizontal;
            Position tilePosition = new Position(row, col);

            Tile tile = new Tile(tilePosition, parameters.tileSize());
            tile.setEntropy(this.grid.totalTiles());
            frame.add(tile.getTileLabel());
            this.grid.getTiles().put(tilePosition, tile);
        }

        // Start Configuration
        int startCollapses;
        switch (parameters.startConfiguration()) {
            case RANDOM:
                break;

            case MIDDLE:
                int middleHorizontal = parameters.tilesHorizontal() / 2;
                int middleVertical = parameters.tilesVertical() / 2;
                collapseAtPosition(new Position(middleVertical, middleHorizontal));
                break;

            case MULTISTART_RANDOM:
                startCollapses = (int) Math.ceil(Math.log10(parameters.tilesHorizontal()*parameters.tilesVertical())) * 2;
                Position randomPosition;
                for (int i=0; i<startCollapses; i++) {
                    int row = random.nextInt(parameters.tilesVertical() - 1);
                    int col = random.nextInt(parameters.tilesHorizontal() - 1);
                    randomPosition = new Position(row, col);
                    collapseAtPosition(randomPosition);
                }
                break;
        }
    }

    /**
     * Performs the algorithm iteratively until all tiles are collapsed.
     * Each loop, one tile is collapsed
     */
    public final void run() {
        while (!this.isCompleted()) {
            try {
                Collapse collapse = pickNextCollapse();
                collapse.collapse(this.tilesCollapsed);

                this.tilesCollapsed++;
                updateEntropiesOfAdjacent(collapse.tile());
                pause();
            }
            catch(NoValidCollapseException e) {
                pause();
            }
        }
        System.out.println("Done!");
    }

    /**
     * collapses a specific Tile
     *
     * used for implementing non-random StartConfigurations
     *
     * @param position the position of the tile to be collapsed
     */
    private void collapseAtPosition(final Position position) {
        Tile tile =  this.grid.getTiles().get(position);

        try {
            Collapse collapse = new Collapse(tile, pickTileConfiguration(tile));
            collapse.collapse(this.tilesCollapsed);

            this.tilesCollapsed++;
            updateEntropiesOfAdjacent(collapse.tile());
            pause();
        }
        catch (NoValidCollapseException e) {
            pause();
        }
    }

    /**
     * checks whether all tiles are collapsed and the algorithm is finished
     *
     * @return true if all tiles are collapsed, false otherwise
     */
    private boolean isCompleted() {
        return this.tilesCollapsed == this.grid.totalTiles();
    }

    /**
     * Puts the thread to sleep for algorithmSpeed milliseconds
     *
     * Implemented to see the progress of the algorithm visually
     */
    private void pause() {
        try {
            Thread.sleep(this.delayInMs);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    /**
     * Picks a Tile which is collapsed next
     *
     * @return the Tile to be collapsed next
     */
    private Collapse pickNextCollapse() throws NoValidCollapseException {
        int minimalEntropy = this.grid.getTiles().values().stream()
                .filter(Predicate.not(Tile::isCollapsed))
                .map(Tile::getEntropy)
                .min(Integer::compareTo).get()
                + this.entropyRandomScore;

        List<Tile> collapseCandidates = this.grid.getTiles().values()
                .stream()
                .filter(Predicate.not(Tile::isCollapsed))
                .filter(tile -> tile.getEntropy() <= minimalEntropy)
                .collect(Collectors.toList());

        Tile collapseTile = WaveFunctionCollapseAlgorithm.getRandomFromList(collapseCandidates);
        TileConfiguration collapseConfiguration = pickTileConfiguration(collapseTile);

        return new Collapse(collapseTile, collapseConfiguration);
    }


    /**
     * picks one TileConfiguration of the given Tile to collapse it
     * picks Tile according the the given probabilityDistribution, choice is random if not specified
     *
     * @param nextCollapsed the Tile to be collapsed next
     * @return one randomly picked configuration
     * @throws RuntimeException if no configuration is possible
     */
    private TileConfiguration pickTileConfiguration(final Tile nextCollapsed) throws NoValidCollapseException {
        List<TileConfiguration> configurations = getValidTileConfigurations(nextCollapsed);
        List<TileType> types = configurations.stream()
                .map(TileConfiguration::tileType)
                .toList();

        // TODO Handle error case better
        if (configurations.isEmpty()) {
            System.out.println("No valid collapse found. Starting to un-Collapse a Tile.");

            int maxTime = 0;
            List<Tile> uncollapseOptions = new ArrayList<>(4);
            for (Direction dir : Direction.ALL_DIRECTIONS) {
                Optional<Tile> adjacent = this.grid.getAdjacentTile(nextCollapsed, dir);
                if (adjacent.isEmpty()) continue;

                uncollapseOptions.add(adjacent.get());
            }

            Optional<Tile> minOption = uncollapseOptions.stream().min(Tile::compareTo);
            if (minOption.isEmpty()) throw new RuntimeException("This can never occur. (Only if there are no possible Configurations");

            Tile target = minOption.get();
            target.hide();
            target.setEntropy(getValidTileConfigurations(target).size());
            updateEntropiesOfAdjacent(target);
            this.tilesCollapsed--;

            throw new NoValidCollapseException();
        }

        // Random value between 0.0 and the sum of all probabilities of all possible TileTypes
        float value = random.nextFloat() *
                types.stream()
                .map(this.probabilityDistribution::get)
                .reduce(0f, Float::sum);

        int i;
        float currentSum = 0;
        for(i=0; currentSum<value; i++) {
            currentSum += this.probabilityDistribution.get(types.get(i));
        }

        TileType chosenType = types.get(i-1);
        List<TileConfiguration> configs = configurations.stream().filter(config -> chosenType.equals(config.tileType())).toList();

        return WaveFunctionCollapseAlgorithm.getRandomFromList(configs);
    }

    /**
     * Returns all possible TileConfigurations of a given Tile
     * by checking all adjacent edges
     *
     * @return a List of valid TileConfigurations
     */
    private List<TileConfiguration> getValidTileConfigurations(final Tile tile) {
        // returns true if a configuration matches all four adjacent edges
        Predicate<TileConfiguration> matchingEdgesCondition = config -> {
            for (int i=0; i<4; i++) {
                Optional<EdgeType> adjacent = this.grid.getAdjacentEdge(tile, Direction.ALL_DIRECTIONS.get(i));
                if (adjacent.isEmpty()) continue;
                if (!adjacent.get().equals(config.edges().get(i))) return false;
            }
            return true;
        };

        return this.tileConfigurations
                .stream()
                .filter(matchingEdgesCondition)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Updates the entropies of the Tiles adjacent to the given Tile
     *
     * @param collapsed the Tile whose neighbours' entropies are updated
     */
    private void updateEntropiesOfAdjacent(final Tile collapsed) {
        Direction.ALL_DIRECTIONS
                .stream()
                .map(dir -> this.grid.getAdjacentTile(collapsed, dir))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter(Predicate.not(Tile::isCollapsed))
                .forEach(tile -> tile.setEntropy(getValidTileConfigurations(tile).size()));
    }

    /**
     * selects a random element from a List and returns it
     *
     * @param list the given List
     * @return a random element the List
     * @param <T> the type parameter of the List
     */
    private static <T extends Object> T getRandomFromList(final List<T> list) {
        int i = random.nextInt(list.size());
        return list.get(i);
    }
}
