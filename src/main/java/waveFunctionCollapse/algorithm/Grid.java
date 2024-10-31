package waveFunctionCollapse.algorithm;

import waveFunctionCollapse.tilesetdefinition.EdgeType;
import waveFunctionCollapse.tilesetdefinition.TileConfiguration;

import java.util.HashMap;
import java.util.Optional;

class Grid {

    private final HashMap<Position, Tile> tiles;
    private final int tilesHorizontal;
    private final int tilesVertical;
    private final Optional<EdgeType> borderEdge;

    Grid(final int tilesHorizontal, final int tilesVertical, final Optional<EdgeType> borderEdge) {
        this.tilesHorizontal = tilesHorizontal;
        this.tilesVertical = tilesVertical;
        this.tiles = new HashMap<>();
        this.borderEdge = borderEdge;
    }

    public HashMap<Position, Tile> getTiles() {
        return tiles;
    }

    public int totalTiles() {
        return this.tilesHorizontal * this.tilesVertical;
    }

    /**
     * returns the EdgeType that is adjacent to this tile in the given direction
     *
     * @param direction the direction of adjacency
     * @return the adjacent EdgeType or Optional.empty() if there is none
     */
    public final Optional<EdgeType> getAdjacentEdge(final Tile tile, final Direction direction) {
        Optional<Tile> adjacentTile = getAdjacentTile(tile, direction);

        if (adjacentTile.isEmpty()) return borderEdge;
        if (adjacentTile.get().getTileConfiguration().isEmpty()) return Optional.empty();

        TileConfiguration adjacentConfiguration = adjacentTile.get().getTileConfiguration().get();
        int directionAsInt = TileConfiguration.directionToInt(direction);
        EdgeType adjacentEdge = adjacentConfiguration.edges().get((directionAsInt+2)%4);

        return Optional.of(adjacentEdge);
    }

    /**
     * returns the adjacent Tile to the given Tile in the given direction
     *
     * @param tile the given Tile
     * @param direction the direction of adjacency
     * @return the tile that is adjacent or Optional.empty() if there is none
     */
    public final Optional<Tile> getAdjacentTile(final Tile tile, final Direction direction) {
        final int lastRow = this.tilesVertical - 1;
        final int lastCol = this.tilesHorizontal- 1;
        final int row = tile.getPosition().row();
        final int col = tile.getPosition().col();

        switch (direction) {
            case ABOVE:
                if (row != 0) return Optional.of(this.tiles.get(new Position(row-1, col)));
                break;
            case RIGHT:
                if (col != lastCol) return Optional.of(this.tiles.get(new Position(row, col+1)));
                break;
            case BELOW:
                if (row != lastRow) return Optional.of(this.tiles.get(new Position(row+1, col)));
                break;
            case LEFT:
                if (col != 0) return Optional.of(this.tiles.get(new Position(row, col-1)));
                break;
        }
        return Optional.empty();
    }
}
