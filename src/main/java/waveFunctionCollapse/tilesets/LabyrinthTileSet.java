package waveFunctionCollapse.tilesets;

import waveFunctionCollapse.tilesetdefinition.EdgeType;
import waveFunctionCollapse.tilesetdefinition.TileSet;
import waveFunctionCollapse.tilesetdefinition.TileType;

import java.util.*;

public class LabyrinthTileSet extends TileSet {

    public LabyrinthTileSet() {
        super("Labyrinth");
    }

    @Override
    protected final List<TileType> defineTiles() {
        EdgeType edge010 = new EdgeType("010");
        EdgeType edge000 = new EdgeType("000");

        return List.of(
                new TileType("tile1.png",   new int[]{0, 1, 2, 3},    new EdgeType[]{edge010, edge010, edge000, edge000}),
                new TileType("tile2.png",   new int[]{0},             new EdgeType[]{edge010, edge010, edge010, edge010}),
                new TileType("tile3.png",   new int[]{0, 1, 2, 3},    new EdgeType[]{edge010, edge000, edge010, edge010}),
                new TileType("tile4.png",   new int[]{0, 1},          new EdgeType[]{edge010, edge000, edge010, edge000}),
                new TileType("tile5.png",   new int[]{0},             new EdgeType[]{edge000, edge000, edge000, edge000}),
                new TileType("tile6.png",   new int[]{0, 1, 2, 3},    new EdgeType[]{edge010, edge000, edge000, edge000})
        );
    }

    public Map<TileType, Float> probabilityDistribution1() {
        Map<TileType, Float> map = new HashMap<>();
        float[] values = {1f, .2f, .2f, 1f, 5f, .2f};
        int i = 0;
        for (TileType type : defineTiles()) {
            map.put(type, values[i]);
            i++;
        }

        return map;
    }
}
