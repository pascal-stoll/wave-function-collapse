package waveFunctionCollapse.tilesets;

import waveFunctionCollapse.tilesetdefinition.EdgeType;
import waveFunctionCollapse.tilesetdefinition.TileSet;
import waveFunctionCollapse.tilesetdefinition.TileType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CarcassoneTileSet extends TileSet {

    public CarcassoneTileSet() {
        super("Carcassonne");
    }

    @Override
    protected List<TileType> defineTiles() {
        // define edgeTypes
        EdgeType city = new EdgeType("city");
        EdgeType path = new EdgeType("path");
        EdgeType green = new EdgeType("green");

        return List.of(
                new TileType("2_city_stub.jpg",         new int[]{0, 1, 2, 3},  new EdgeType[]{city, green, green, city}),
                new TileType("3_city.jpg",              new int[]{0, 1, 2, 3},  new EdgeType[]{city, city, green, city}),
                new TileType("3_city_path.jpg",         new int[]{0, 1, 2, 3},  new EdgeType[]{city, city, path, city}),
                new TileType("chapel.jpg",              new int[]{0},           new EdgeType[]{green, green, green, green}),
                new TileType("chapel_path.jpg",         new int[]{0, 1, 2, 3},  new EdgeType[]{green, green, path, green}),
                new TileType("city.jpg",                new int[]{0},           new EdgeType[]{city, city, city, city}),
                new TileType("curved_path.jpg",         new int[]{0, 1, 2, 3},  new EdgeType[]{green, green, path, path}),
                new TileType("diagonal.jpg",            new int[]{0, 1, 2, 3},  new EdgeType[]{city, green, green, city}),
                new TileType("diagonal_path.jpg",       new int[]{0, 1, 2, 3},  new EdgeType[]{city, path, path, city}),
                new TileType("opposite_city_stub.jpg",  new int[]{0, 1},        new EdgeType[]{city, green, city, green}),
                new TileType("plus_joint.jpg",          new int[]{0, 1, 2, 3},  new EdgeType[]{path, path, path, path}),
                new TileType("single_stub.jpg",         new int[]{0, 1, 2, 3},  new EdgeType[]{city, green, green, green}),
                new TileType("single_stub_path.jpg",    new int[]{0, 1, 2, 3},  new EdgeType[]{city, green, path, path}),
                new TileType("single_stub_path_2.jpg",  new int[]{0, 1},        new EdgeType[]{city, path, path, green}),
                new TileType("straight_path.jpg",       new int[]{0, 1, 2, 3},  new EdgeType[]{path, green, path, green}),
                new TileType("stub_straight_path.jpg",  new int[]{0, 1, 2, 3},  new EdgeType[]{city, path, green, path}),
                new TileType("t_joint.jpg",             new int[]{0, 1, 2, 3},  new EdgeType[]{green, path, path, path}),
                new TileType("t_joint_stub.jpg",        new int[]{0, 1},        new EdgeType[]{city, path, path, path}),
                new TileType("tube.jpg",                new int[]{0},           new EdgeType[]{green, city, green, city})
        );
    }

    public Map<TileType, Float> moreCities() {
        Map<TileType, Float> map = new HashMap<>();
        List<Float> values = List.of(
                2f, 3f, 3f, .5f, .5f, 5f, 1f, 2f, 2f, 2f, 2f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f
        );
        int i=0;
        for (TileType type : defineTiles()) {
            map.put(type, values.get(i));
            i++;
        }

        return map;
    }

    public Map<TileType, Float> moreGreen() {
        Map<TileType, Float> map = new HashMap<>();
        List<Float> values = List.of(
                2f, 1f, 1f, 4f, 4f, .5f, 2f, 1f, 1f, 1f, 1.5f, 1f, 3f, 3f, 3f, 3f, 2f, 1f, .5f
        );
        int i=0;
        for (TileType type : defineTiles()) {
            map.put(type, values.get(i));
            i++;
        }

        return map;
    }
}
