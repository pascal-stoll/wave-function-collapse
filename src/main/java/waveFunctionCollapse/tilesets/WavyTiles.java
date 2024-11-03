package waveFunctionCollapse.tilesets;

import waveFunctionCollapse.tilesetdefinition.EdgeType;
import waveFunctionCollapse.tilesetdefinition.TileSet;
import waveFunctionCollapse.tilesetdefinition.TileType;

import java.util.List;

public class WavyTiles extends TileSet {

    public WavyTiles() {
        super("WavyTiles");
    }

    @Override
    protected final List<TileType> defineTiles() {
        EdgeType edge000 = new EdgeType("000");
        EdgeType edge101 = new EdgeType("101");

        return List.of(
              new TileType("tile2.1.png",   new int[]{0, 1, 2, 3},  new EdgeType[]{edge101, edge000, edge000, edge000}),
              new TileType("tile2.2.png",   new int[]{0, 1, 2, 3},  new EdgeType[]{edge101, edge000, edge000, edge000}),
              new TileType("tile2.3.png",   new int[]{0, 1},        new EdgeType[]{edge101, edge000, edge101, edge000}),
              new TileType("tile2.4.png",   new int[]{0, 1},        new EdgeType[]{edge101, edge000, edge101, edge000}),
              new TileType("tile2.5.png",   new int[]{0, 1},        new EdgeType[]{edge101, edge000, edge101, edge000}),
              new TileType("tile2.6.png",   new int[]{0, 1, 2, 3},  new EdgeType[]{edge101, edge101, edge000, edge000}),
              new TileType("tile2.7.png",   new int[]{0, 1, 2, 3},  new EdgeType[]{edge101, edge101, edge000, edge000}),
              new TileType("tile2.8.png",   new int[]{0, 1, 2, 3},  new EdgeType[]{edge101, edge101, edge101, edge000}),
              new TileType("tile2.9.png",   new int[]{0, 1, 2, 3},  new EdgeType[]{edge101, edge101, edge101, edge000}),
              new TileType("tile2.10.png",  new int[]{0, 1},        new EdgeType[]{edge101, edge101, edge101, edge101}),
              new TileType("tile2.11.png",  new int[]{0},           new EdgeType[]{edge101, edge101, edge101, edge101})
        );
    }
}
