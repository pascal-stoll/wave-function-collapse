package waveFunctionCollapse.tilesets;

import java.util.*;

public class LabyrinthTileSet extends TileSet {

    public LabyrinthTileSet() {
        // Put directoryPath here
        super("Labyrinth");
    }

    @Override
    protected final List<TileType> defineTiles() {
        EdgeType edge010 = new EdgeType("010");
        EdgeType edge000 = new EdgeType("000");

        return List.of(
                new TileType("tile1.png", new Integer[]{0, 1, 2, 3}, new EdgeType[]{edge010, edge010, edge000, edge000}),
                new TileType("tile2.png", new Integer[]{0}, new EdgeType[]{edge010, edge010, edge010, edge010}),
                new TileType("tile3.png", new Integer[]{0, 1, 2, 3}, new EdgeType[]{edge010, edge000, edge010, edge010}),
                new TileType("tile4.png", new Integer[]{0, 1}, new EdgeType[]{edge010, edge000, edge010, edge000}),
                new TileType("tile5.png", new Integer[]{0}, new EdgeType[]{edge000, edge000, edge000, edge000}),
                new TileType("tile6.png", new Integer[]{0, 1, 2, 3}, new EdgeType[]{edge010, edge000, edge000, edge000})
        );
    }
}
