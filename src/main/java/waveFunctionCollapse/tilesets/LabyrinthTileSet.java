package waveFunctionCollapse.tilesets;

import waveFunctionCollapse.algorithm.EdgeType;
import java.util.*;

public class LabyrinthTileSet extends TileSet {

    public LabyrinthTileSet() {
        // Put directoryPath here
        super("Labyrinth");
    }

    @Override
    protected final List<String> getFileNames() {
        // List of file names of the Tiles
        return new ArrayList<String>(Arrays.asList(
                "tile1.png", "tile2.png", "tile3.png",
                "tile4.png", "tile5.png", "tile6.png"));
    }

    @Override
    protected final List<Set<Integer>> getRotations() {
        // ArrayList of possible rotations per Tile
        return new ArrayList<Set<Integer>>(Arrays.asList(
                new HashSet<Integer>(List.of(0, 1, 2, 3)),    // tile1
                new HashSet<Integer>(List.of(0)),         // tile2
                new HashSet<Integer>(List.of(0, 1, 2, 3)),    // tile3
                new HashSet<Integer>(List.of(0, 1)),          // tile4
                new HashSet<Integer>(List.of(0)),         // tile5
                new HashSet<Integer>(List.of(0, 1, 2, 3))     // tile6
        ));
    }

    @Override
    protected final List<ArrayList<EdgeType>> getEdges() {
        // initialize all EdgeTypes of this TileSet
        EdgeType edge010 = new EdgeType("010");
        EdgeType edge000 = new EdgeType("000");

        return new ArrayList<ArrayList<EdgeType>>(Arrays.asList(
                new ArrayList<EdgeType>(List.of(edge010, edge010, edge000, edge000)),   // tile1
                new ArrayList<EdgeType>(List.of(edge010, edge010, edge010, edge010)),   // tile2
                new ArrayList<EdgeType>(List.of(edge010, edge000, edge010, edge010)),   // tile3
                new ArrayList<EdgeType>(List.of(edge010, edge000, edge010, edge000)),   // tile4
                new ArrayList<EdgeType>(List.of(edge000, edge000, edge000, edge000)),   // tile5
                new ArrayList<EdgeType>(List.of(edge010, edge000, edge000, edge000))    // tile6
        ));
    }
}
