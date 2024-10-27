package waveFunctionCollapse.tilesets;

import java.util.*;

public class CarcassoneTileSet extends TileSet {

    public CarcassoneTileSet() {
        super("Carcassonne");
    }

    protected List<String> getFileNames() {
        // List of file names of the Tiles
        return new ArrayList<>(Arrays.asList(

                ));
    }


    protected List<Set<Integer>> getRotations() {
        return new ArrayList<>(Arrays.asList(

                ));
    }

    protected List<ArrayList<EdgeType>> getEdges() {


        return new ArrayList<>(List.of(

        ));
    }

    @Override
    protected List<TileType> defineTiles() {
        // define edgeTypes
        EdgeType city = new EdgeType("city");
        EdgeType path = new EdgeType("path");
        EdgeType green = new EdgeType("green");

        return List.of(
                new TileType("2_city_stub.jpg", new Integer[]{0, 1, 2, 3}, new EdgeType[]{city, green, green, city}),
                new TileType("3_city.jpg", new Integer[]{0, 1, 2, 3}, new EdgeType[]{city, city, green, city}),
                new TileType("3_city_path.jpg", new Integer[]{0, 1, 2, 3}, new EdgeType[]{city, city, path, city}),
                new TileType("chapel.jpg", new Integer[]{0}, new EdgeType[]{green, green, green, green}),
                new TileType("chapel_path.jpg", new Integer[]{0, 1, 2, 3}, new EdgeType[]{green, green, path, green}),
                new TileType("city.jpg", new Integer[]{0}, new EdgeType[]{city, city, city, city}),
                new TileType("curved_path.jpg", new Integer[]{0, 1, 2, 3}, new EdgeType[]{green, green, path, path}),
                new TileType("diagonal.jpg", new Integer[]{0, 1, 2, 3}, new EdgeType[]{city, green, green, city}),
                new TileType("diagonal_path.jpg", new Integer[]{0, 1, 2, 3}, new EdgeType[]{city, path, path, city}),
                new TileType("opposite_city_stub.jpg", new Integer[]{0, 1}, new EdgeType[]{city, green, city, green}),
                new TileType("plus_joint.jpg", new Integer[]{0, 1, 2, 3}, new EdgeType[]{path, path, path, path}),
                new TileType("single_stub.jpg", new Integer[]{0, 1, 2, 3}, new EdgeType[]{city, green, green, green}),
                new TileType("single_stub_path.jpg", new Integer[]{0, 1, 2, 3}, new EdgeType[]{city, green, path, path}),
                new TileType("single_stub_path_2.jpg", new Integer[]{0, 1}, new EdgeType[]{city, path, path, green}),
                new TileType("straight_path.jpg", new Integer[]{0, 1, 2, 3}, new EdgeType[]{path, green, path, green}),
                new TileType("stub_straight_path.jpg", new Integer[]{0, 1, 2, 3}, new EdgeType[]{city, path, green, path}),
                new TileType("t_joint.jpg", new Integer[]{0, 1, 2, 3}, new EdgeType[]{green, path, path, path}),
                new TileType("t_joint_stub.jpg", new Integer[]{0, 1}, new EdgeType[]{city, path, path, path}),
                new TileType("tube.jpg", new Integer[]{0}, new EdgeType[]{green, city, green, city})
        );
    }
}
