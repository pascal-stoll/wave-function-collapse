package waveFunctionCollapse.algorithm;

import waveFunctionCollapse.gui.GridFrame;
import waveFunctionCollapse.tilesets.EdgeType;
import waveFunctionCollapse.tilesets.TileConfiguration;
import waveFunctionCollapse.tilesets.TileSet;

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
    private int tilesCollapsed = 0;

    /**
     * Creates a new instance of the algorithm
     *
     * @param tileSet the TileSet the algorithm should work upon
     * @param parameters all parameters relevant to the algorithm
     */
    public WaveFunctionCollapseAlgorithm(final TileSet tileSet, final AlgorithmParameters parameters) {
        this.tileConfigurations = tileSet.getAllTileConfigurations();
        this.entropyRandomScore = (int) Math.ceil(parameters.nonRandomFactor() * this.tileConfigurations.size());
        this.delayInMs = parameters.algorithmSpeed();

        // Sets up Grid, GridFrame and Tiles
        this.grid = new Grid(parameters.tilesHorizontal(), parameters.tilesVertical());
        GridFrame frame = new GridFrame(parameters.tilesHorizontal(), parameters.tilesVertical());


        // Initialize Tiles: Creates and initializes tiles for every spot
        int tilesHorizontal = parameters.tilesHorizontal();
        for (int i = 0; i<this.grid.totalTiles(); i++) {
            int row = i / tilesHorizontal;
            int col = i % tilesHorizontal;
            Position tilePosition = new Position(row, col);

            Tile tile = new Tile(tilePosition, parameters.tileSize(), this.grid.totalTiles());
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
                Random random = new Random();
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
            Collapse collapse = pickNextCollapse();
            collapse.collapse();

            // Update state of algorithm
            this.tilesCollapsed++;
            updateEntropiesOfAdjacent(collapse.tile());
            pause();
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
        Collapse collapse = new Collapse(tile, pickTileConfiguration(tile));
        collapse.collapse();

        this.tilesCollapsed++;
        updateEntropiesOfAdjacent(collapse.tile());
        pause();
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
            System.out.println(e);
        }
    }


    /**
     * Picks a Tile which is collapsed next
     *
     * @return the Tile to be collapsed next
     */
    private Collapse pickNextCollapse() {
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
     *
     * @param nextCollapsed the Tile to be collapsed next
     * @return one randomly picked configuration
     * @throws RuntimeException if no configuration is possible
     */
    private TileConfiguration pickTileConfiguration(final Tile nextCollapsed) {
        List<TileConfiguration> configurations = getValidTileConfigurations(nextCollapsed);

        // TODO Handle error case better
        if (configurations.isEmpty()) throw new RuntimeException("Error. No valid collapse was found.");

        return WaveFunctionCollapseAlgorithm.getRandomFromList(configurations);
    }

    /**
     * Returns all possible TileConfigurations of a given Tile
     * by checking all adjacent edges
     *
     * @return a List of valid TileConfigurations
     */
    private List<TileConfiguration> getValidTileConfigurations(final Tile tile) {
        // returns true if a configuration matches all four adjacent edges
        Predicate<TileConfiguration> cond = config -> {
            for (int i=0; i<4; i++) {
                Optional<EdgeType> adjacent = this.grid.getAdjacentEdge(tile, Direction.ALL_DIRECTIONS.get(i));
                if (adjacent.isEmpty()) continue;
                if (adjacent.get() != config.edges().get(i)) return false;
            }
            return true;
        };

        return this.tileConfigurations
                .stream()
                .filter(cond)
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
        int i = new Random().nextInt(list.size());
        return list.get(i);
    }
}
