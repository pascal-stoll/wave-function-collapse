package waveFunctionCollapse.algorithm;

import waveFunctionCollapse.gui.GridFrame;
import waveFunctionCollapse.tilesets.TileSet;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The algorithm that performs the WaveFunctionCollapse
 */
public class WaveFunctionCollapseAlgorithm {

    private final TileSet tileSet;
    private final AlgorithmParameters parameters;
    private final HashMap<Position, Tile> tiles = new HashMap<>();
    private final int entropyRandomScore;
    private int tilesCollapsed = 0;

    /**
     * Creates a new instance of the algorithm
     *
     * @param tileSet the TileSet the algorithm should work upon
     * @param parameters all parameters relevant to the algorithm
     */
    public WaveFunctionCollapseAlgorithm(final TileSet tileSet, final AlgorithmParameters parameters) {
        this.tileSet = tileSet;
        this.parameters = parameters;

        // Sets up GridFrame and Tiles
        GridFrame frame = new GridFrame(this.parameters.tilesHorizontal(), this.parameters.tilesVertical());
        this.entropyRandomScore = (int) Math.ceil(this.parameters.nonRandomFactor() * this.tileSet.getNumberOfTileConfigurations());

        // Initialize Tiles: Creates and initializes tiles for every spot
        int tilesHorizontal = this.parameters.tilesHorizontal();
        for (int i = 0; i<this.parameters.totalTiles(); i++) {
            int row = i / tilesHorizontal;
            int col = i % tilesHorizontal;
            Position tilePosition = new Position(row, col);

            Tile tile = new Tile(tilePosition, this.parameters.tileSize(), this.parameters.totalTiles());
            frame.add(tile.getTileLabel());
            this.tiles.put(tilePosition, tile);
        }

        // Start Configuration
        int startCollapses;
        switch (parameters.startConfiguration()) {
            case RANDOM:
                break;

            case MIDDLE:
                int middleHorizontal = this.parameters.tilesHorizontal() / 2;
                int middleVertical = this.parameters.tilesVertical() / 2;
                collapseAtPosition(new Position(middleVertical, middleHorizontal));
                break;

            case MULTISTART_RANDOM:
                startCollapses = (int) Math.ceil(Math.log10(this.parameters.tilesHorizontal()*this.parameters.tilesVertical())) * 2;
                Position randomPosition;
                Random random = new Random();
                for (int i=0; i<startCollapses; i++) {
                    int row = random.nextInt(this.parameters.tilesVertical() - 1);
                    int col = random.nextInt(this.parameters.tilesHorizontal() - 1);
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
            Tile nextCollapsed = nextTileToBeCollapsed();
            collapseTile(nextCollapsed);

            waitForMilliseconds();
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
        collapseTile(this.tiles.get(position));
    }

    /**
     * checks whether all tiles are collapsed and the algorithm is finished
     *
     * @return true if all tiles are collapsed, false otherwise
     */
    private boolean isCompleted() {
        return this.tilesCollapsed == this.parameters.totalTiles();
    }

    /**
     * Puts the thread to sleep for algorithmSpeed milliseconds
     *
     * Implemented to see the progress of the algorithm visually
     */
    private void waitForMilliseconds() {
        try {
            Thread.sleep(this.parameters.algorithmSpeed());
        }
        catch (InterruptedException e) {

        }
    }

    /**
     * Picks a Tile which is collapsed next
     *
     * @return the Tile to be collapsed next
     */
    private Tile nextTileToBeCollapsed() {
        int minimalEntropy = this.tiles.values().stream()
                .filter(Predicate.not(Tile::isCollapsed))
                .map(Tile::getEntropy)
                .min(Integer::compareTo).get()
                + this.entropyRandomScore;

        // TODO: Change logic to always extract candidate with entropy=1, throw error on entropy=0
        List<Tile> collapseCandidates = this.tiles.values()
                .stream()
                .filter(Predicate.not(Tile::isCollapsed))
                .filter(tile -> tile.getEntropy() <= minimalEntropy)
                .collect(Collectors.toCollection(ArrayList::new));

        if (collapseCandidates.size() == 1) {
            return collapseCandidates.get(0);
        }
        else if (collapseCandidates.isEmpty()) {
            throw new RuntimeException("No Tile can be collapsed.");
        }

        Random random = new Random();
        int randIndex = random.nextInt(collapseCandidates.size());

        return collapseCandidates.get(randIndex);
    }

    /**
     * Collapses a Tile by picking a TileConfiguration and showing it
     * @param nextCollapsed the Tile to be collapsed
     */
    private void collapseTile(Tile nextCollapsed) {
        // pick one configuration at random
        TileConfiguration collapseConfiguration = getTileConfiguration(nextCollapsed);

        // display ImageConfiguration in GUI
        nextCollapsed.display(collapseConfiguration);

        // Update algorithm state
        this.tilesCollapsed++;
        updateEntropiesOfAdjacent(nextCollapsed);
    }

    /**
     * picks one TileConfiguration of the given Tile to collapse it
     *
     * @param nextCollapsed the Tile to be collapsed next
     * @return one randomly picked configuration
     * @throws RuntimeException if no configuration is possible
     */
    private TileConfiguration getTileConfiguration(final Tile nextCollapsed) {
        List<TileConfiguration> possibleConfigurations = getPossibleImageConfigurations(nextCollapsed);

        int numberOfCandidates = possibleConfigurations.size();
        ArrayList<TileConfiguration> possibleConfigurationsAsArray = new ArrayList<>(possibleConfigurations);

        if (numberOfCandidates == 1) {
            return possibleConfigurations.iterator().next();
        }
        else if (numberOfCandidates == 0) {
            throw new RuntimeException("Error. No valid collapse was found.");
        }

        Random random = new Random();
        int randomIndex = random.nextInt(numberOfCandidates);
        return possibleConfigurationsAsArray.get(randomIndex);
    }

    private Set<Tile> getAdjacentTiles(final Tile tile) {
        int lastRow = this.parameters.tilesVertical() - 1;
        int lastCol = this.parameters.tilesHorizontal() - 1;
        Position position = tile.getPosition();
        int row = position.row();
        int col = position.col();

        Set<Tile> adjacentTiles = new HashSet<Tile>();

        // Check all 4 directions
        if (row != 0) {
            adjacentTiles.add(this.tiles.get(new Position(row - 1, col)));
        }
        if (row != lastRow) {
            adjacentTiles.add(this.tiles.get(new Position(row + 1, col)));
        }
        if (col != 0) {
            adjacentTiles.add(this.tiles.get(new Position(row, col - 1)));
        }
        if (col != lastCol) {
            adjacentTiles.add(this.tiles.get(new Position(row, col + 1)));
        }

        return adjacentTiles;
    }

    /**
     * Updates the entropies of the Tiles adjacent to the gieven Tile
     *
     * @param collapsed the Tile whose neighbours should be updated
     */
    private void updateEntropiesOfAdjacent(final Tile collapsed) {
        getAdjacentTiles(collapsed)
                .stream()
                .filter(Predicate.not(Tile::isCollapsed))
                .forEach(tile -> tile.setEntropy(getPossibleImageConfigurations(tile).size()));
    }

    /**
     * Returns all possible imageConfiguration for the given Tile
     * by checking all adjacent edges
     *
     * @return the List of possible configurations
     */
    private List<TileConfiguration> getPossibleImageConfigurations(final Tile tile) {

        Optional<EdgeType> adjacentEdge;
        Stream<TileConfiguration> possibleConfigurations = this.tileSet.getAllTileImageConfigurations().stream();

        for (int i = 0; i < 4; i++) {
            adjacentEdge = getAdjacentEdge(tile, Direction.ALL_DIRECTIONS.get(i));
            if (adjacentEdge.isPresent()) {
                // Create final variables to use in Stream
                int finalI = i;
                Optional<EdgeType> finalAdjacentEdge = adjacentEdge;

                possibleConfigurations = possibleConfigurations
                        .filter(x -> x.edges().get(finalI) == finalAdjacentEdge.get());
            }
        }

        return possibleConfigurations.collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * returns the EdgeType that is adjacent to this tile in the given direction
     *
     * @param direction the direction of adjacency
     * @return the adjacent EdgeType or Optional.empty() if there is none
     */
    private Optional<EdgeType> getAdjacentEdge(final Tile tile, final Direction direction) {
        Optional<Tile> adjacentTile = getAdjacentTile(tile, direction);

        if (adjacentTile.isEmpty()) {
            return Optional.empty();
        }

        Optional<TileConfiguration> adjacentTileImageConfiguration = adjacentTile.get().getTileImageConfiguration();

        if (adjacentTileImageConfiguration.isEmpty()) {
            return Optional.empty();
        }

        TileConfiguration adjacentTIC = adjacentTileImageConfiguration.get();
        int directionAsInt = adjacentTIC.directionToInt(direction);
        EdgeType adjacentEdge = adjacentTIC.edges().get((directionAsInt+2)%4);

        return Optional.of(adjacentEdge);
    }

    /**
     * returns the adjacent Tile to the given Tile in the given direction
     *
     * @param tile the given Tile
     * @param direction the direction of adjacency
     * @return the tile that is adjacent or Optional.empty() if there is none
     */
    private Optional<Tile> getAdjacentTile(final Tile tile, final Direction direction) {
        final int lastRow = this.parameters.tilesVertical() - 1;
        final int lastCol = this.parameters.tilesHorizontal() - 1;
        Position position = tile.getPosition();
        int row = position.row();
        int col = position.col();

        switch (direction) {
            case ABOVE:
                if (row != 0) {
                    return Optional.of(this.tiles.get(new Position(row-1, col)));
                }
                break;
            case RIGHT:
                if (col != lastCol) {
                    return Optional.of(this.tiles.get(new Position(row, col+1)));
                }
                break;
            case BELOW:
                if (row != lastRow) {
                    return Optional.of(this.tiles.get(new Position(row+1, col)));
                }
                break;
            case LEFT:
                if (col != 0) {
                    return Optional.of(this.tiles.get(new Position(row, col-1)));
                }
                break;
        }
        return Optional.empty();
    }
}
