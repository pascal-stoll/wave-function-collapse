package waveFunctionCollapse.tilesets;

import waveFunctionCollapse.algorithm.EdgeType;

import java.util.*;

public class Labyrinth2TileSet extends TileSet {

    public Labyrinth2TileSet() {
        super("Labyrinth_2.0");
    }

    @Override
    protected final List<String> getFileNames() {
        return new ArrayList<>(Arrays.asList(
            "tile2.1.png", "tile2.2.png", "tile2.3.png", "tile2.4.png", "tile2.5.png", "tile2.6.png",
            "tile2.7.png", "tile2.8.png", "tile2.9.png", "tile2.10.png", "tile2.11.png"
        ));
    }

    @Override
    protected final List<Set<Integer>> getRotations() {
        return new ArrayList<>(Arrays.asList(
                new HashSet<>(List.of(0, 1, 2, 3)),
                new HashSet<>(List.of(0, 1, 2, 3)),
                new HashSet<>(List.of(0, 1)),
                new HashSet<>(List.of(0, 1)),
                new HashSet<>(List.of(0, 1)),
                new HashSet<>(List.of(0, 1, 2, 3)),
                new HashSet<>(List.of(0, 1, 2, 3)),
                new HashSet<>(List.of(0, 1, 2, 3)),
                new HashSet<>(List.of(0, 1, 2, 3)),
                new HashSet<>(List.of(0, 1)),
                new HashSet<>(List.of(0))
        ));
    }

    @Override
    protected final List<ArrayList<EdgeType>> getEdges() {
        EdgeType edge000 = new EdgeType("000");
        EdgeType edge101 = new EdgeType("101");

        return new ArrayList<>(Arrays.asList(
                new ArrayList<>(List.of(edge101, edge000, edge000, edge000)),
                new ArrayList<>(List.of(edge101, edge000, edge000, edge000)),
                new ArrayList<>(List.of(edge101, edge000, edge101, edge000)),
                new ArrayList<>(List.of(edge101, edge000, edge101, edge000)),
                new ArrayList<>(List.of(edge101, edge000, edge101, edge000)),
                new ArrayList<>(List.of(edge101, edge101, edge000, edge000)),
                new ArrayList<>(List.of(edge101, edge101, edge000, edge000)),
                new ArrayList<>(List.of(edge101, edge101, edge101, edge000)),
                new ArrayList<>(List.of(edge101, edge101, edge101, edge000)),
                new ArrayList<>(List.of(edge101, edge101, edge101, edge101)),
                new ArrayList<>(List.of(edge101, edge101, edge101, edge101))
        ));
    }
}
