package waveFunctionCollapse.algorithm;

import waveFunctionCollapse.gui.TileLabel;

import java.util.*;
import java.util.stream.Collectors;

/**
 * A Tile in a grid
 *
 * is displayed in a TileLabel object in the GUI.
 */
public final class Tile {

    private final Grid grid;
    private final TileLabel tileLabel;
    private boolean collapsed;
    private final Position position;
    private int entropy;
    private Optional<TileConfiguration> tileImageConfiguration;

    /**
     * Creates a Tile object
     *
     * @param position the position (row, column) within the grid
     */
    public Tile(final Position position, Grid grid) {
        super();
        this.grid = grid;
        this.tileLabel = new TileLabel(this);
        this.collapsed = false;
        this.position = position;
        this.entropy = this.grid.getAlgorithm().getTileSet().getNumberOfTileConfigurations();
        this.tileImageConfiguration = Optional.empty();
    }

    public final Grid getGrid() {
        return this.grid;
    }

    public final TileLabel getTileLabel() {
        return this.tileLabel;
    }

    public final boolean isCollapsed() {
        return this.collapsed;
    }

    public final Position getPosition() {
        return this.position;
    }

    public final int getEntropy() {
        return this.entropy;
    }

    public final Optional<TileConfiguration> getTileImageConfiguration() {
        return this.tileImageConfiguration;
    }


    /**
     * recalculates the entropy and saves it in object variable entropy
     */
    public void updateEntropy() {
        this.entropy = getPossibleImageConfigurations().size();
    }


    /**
     * Collapses the given Tile to one of the possible options
     *
     * Chooses one option randomly, if more than one is available
     */
    public final void collapseTile() {

        // get all possible configurations
        Set<TileConfiguration> possibleConfigurations = getPossibleImageConfigurations();

        // pick one configuration at random
        TileConfiguration collapseConfiguration = pickRandomTIConfiguration(possibleConfigurations);

        // display ImageConfiguration in GUI
        this.getTileLabel().showImageConfiguration(collapseConfiguration);

        // update Tile status
        this.entropy = 0;
        this.collapsed = true;
        this.tileImageConfiguration = Optional.of(collapseConfiguration);
    }

    /**
     * returns the EdgeType that is adjacent to this tile in the given direction
     *
     * @param direction the direction of adjacency
     * @return the adjacent EdgeType or Optional.empty() if there is none
     */
    private final Optional<EdgeType> getAdjacentEdge(Direction direction) {
        Optional<Tile> adjacentTile = getAdjacentTile(direction);

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
     * returns the tile adjacent to this in the given direction
     *
     * @param direction the direction of adjacency
     * @return the tile that is adjacent or Optional.empty() if there is none
     */
    private final Optional<Tile> getAdjacentTile(Direction direction) {
        final int lastRow = this.grid.getAlgorithm().getTilesVertical() - 1;
        final int lastCol = this.grid.getAlgorithm().getTilesHorizontal() - 1;
        final Map<Position, Tile> tiles = this.grid.getTiles();
        Position position = this.position;
        int row = position.row();
        int col = position.col();

        switch (direction) {
            case ABOVE:
                if (row != 0) {
                    return Optional.of(tiles.get(new Position(row-1, col)));
                }
                break;
            case RIGHT:
                if (col != lastCol) {
                    return Optional.of(tiles.get(new Position(row, col+1)));
                }
                break;
            case BELOW:
                if (row != lastRow) {
                    return Optional.of(tiles.get(new Position(row+1, col)));
                }
                break;
            case LEFT:
                if (col != 0) {
                    return Optional.of(tiles.get(new Position(row, col-1)));
                }
                break;
        }
        return Optional.empty();
    }

    /**
     * Returns all possible imageConfiguration for the given Tile
     * by checking all adjacent edges
     *
     * @return the set of possible configurations
     */
    private final Set<TileConfiguration> getPossibleImageConfigurations() {
        final ArrayList<Direction> allDirections = new ArrayList<>(Arrays.asList(
                Direction.ABOVE, Direction.RIGHT, Direction.BELOW, Direction.LEFT
        ));

        Optional<EdgeType> adjacentEdge;
        Set<TileConfiguration> possibleConfigurations = this.grid.getAlgorithm().getTileSet().getAllTileImageConfigurations();

        for (int i=0; i<4; i++) {
            adjacentEdge = (getAdjacentEdge(allDirections.get(i)));
            if (adjacentEdge.isPresent()) {
                int finalI = i;
                Optional<EdgeType> finalAdjacentEdge = adjacentEdge;

                possibleConfigurations = possibleConfigurations.stream()
                        .filter(x -> x.edges().get(finalI) == finalAdjacentEdge.get())
                        .collect(Collectors.toCollection(HashSet::new));
            }
        }

        return possibleConfigurations;
    }


    /**
     * picks one configuration of the set randomly
     *
     * @param possibleConfigurations the Set of possible configurations
     * @return the randomly picked configuration
     * @throws RuntimeException if no configuration is in the given Set
     */
    private final TileConfiguration pickRandomTIConfiguration(Set<TileConfiguration> possibleConfigurations) {
        int numberOfCandidates = possibleConfigurations.size();
        ArrayList<TileConfiguration> possibleConfigurationsAsArray = new ArrayList<TileConfiguration>(possibleConfigurations);

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

    @Override
    public String toString() {
        String collapsed = this.collapsed ? "TRUE" : "FALSE";
        return "[Position: " + this.position.toString() + ", collapsed: " + collapsed + ", entropy: " + String.valueOf(this.entropy) + ", TileLabel: " + this.tileLabel.toString() + "]";
    }
}
