package waveFunctionCollapse.tilesets;

import waveFunctionCollapse.algorithm.EdgeType;

import java.util.*;

public class CarcassoneTileSet extends TileSet {

    public CarcassoneTileSet() {
        super("Carcassonne");
    }

    @Override
    protected List<String> getFileNames() {
        // List of file names of the Tiles
        return new ArrayList<>(Arrays.asList(
                "2_city_stub.jpg",
                "3_city.jpg",
                "3_city_path.jpg",
                "chapel.jpg",
                "chapel_path.jpg",
                "city.jpg",
                "curved_path.jpg",
                "diagonal.jpg",
                "diagonal_path.jpg",
                "opposite_city_stub.jpg",
                "plus_joint.jpg",
                "single_stub.jpg",
                "single_stub_path.jpg",
                "single_stub_path_2.jpg",
                "straight_path.jpg",
                "stub_straight_path.jpg",
                "t_joint.jpg",
                "t_joint_stub.jpg",
                "tube.jpg"
                ));
    }

    @Override
    protected List<Set<Integer>> getRotations() {
        return new ArrayList<>(Arrays.asList(
                new HashSet<>(List.of(0, 1, 2, 3)),
                new HashSet<>(List.of(0, 1, 2, 3)),
                new HashSet<>(List.of(0, 1, 2, 3)),
                new HashSet<>(List.of(0)),
                new HashSet<>(List.of(0, 1, 2, 3)),
                new HashSet<>(List.of(0)),
                new HashSet<>(List.of(0, 1, 2, 3)),
                new HashSet<>(List.of(0, 1, 2, 3)),
                new HashSet<>(List.of(0, 1, 2, 3)),
                new HashSet<>(List.of(0, 1)),
                new HashSet<>(List.of(0, 1, 2, 3)),
                new HashSet<>(List.of(0, 1, 2, 3)),
                new HashSet<>(List.of(0, 1, 2, 3)),
                new HashSet<>(List.of(0, 1)),
                new HashSet<>(List.of(0, 1, 2, 3)),
                new HashSet<>(List.of(0, 1, 2, 3)),
                new HashSet<>(List.of(0, 1, 2, 3)),
                new HashSet<>(List.of(0, 1)),
                new HashSet<>(List.of(0))
                ));
    }

    @Override
    protected List<ArrayList<EdgeType>> getEdges() {
        // define edgeTypes
        EdgeType city = new EdgeType("city");
        EdgeType path = new EdgeType("path");
        EdgeType green = new EdgeType("green");

        return new ArrayList<>(List.of(
                new ArrayList<>(List.of(city, green, green, city)),
                new ArrayList<>(List.of(city, city, green, city)),
                new ArrayList<>(List.of(city, city, path, city)),
                new ArrayList<>(List.of(green, green, green, green)),
                new ArrayList<>(List.of(green, green, path, green)),
                new ArrayList<>(List.of(city, city, city, city)),
                new ArrayList<>(List.of(green, green, path, path)),
                new ArrayList<>(List.of(city, green, green, city)),
                new ArrayList<>(List.of(city, path, path, city)),
                new ArrayList<>(List.of(city, green, city, green)),
                new ArrayList<>(List.of(path, path, path, path)),
                new ArrayList<>(List.of(city, green, green, green)),
                new ArrayList<>(List.of(city, green, path, path)),
                new ArrayList<>(List.of(city, path, path, green)),
                new ArrayList<>(List.of(path, green, path, green)),
                new ArrayList<>(List.of(city, path, green, path)),
                new ArrayList<>(List.of(green, path, path, path)),
                new ArrayList<>(List.of(city, path, path, path)),
                new ArrayList<>(List.of(green, city, green, city))
        ));
    }
}
