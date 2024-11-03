package waveFunctionCollapse.tilesetdefinition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public record TileType(String fileName, List<Integer> rotations, List<EdgeType> edges) implements Comparable<TileType> {

    public TileType {
        assert (edges.size() == 4) : "Not exactly 4 edges provided for tile type of file '" + fileName + "'.";
        assert (rotations.size() <= 4) : "Too many rotations provided for tile type of file '" + fileName + "'.";
    }

    public TileType(final String fileName, final int[] rotations, final EdgeType[] edges) {
        this(fileName, Arrays.stream(rotations).boxed().toList(), new ArrayList<>(Arrays.asList(edges)));
    }

    public TileType(final String fileName, final EdgeType[] edges) {
        this(fileName, TileType.getRotations(edges), edges);
    }

    private static int[] getRotations(final EdgeType[] edges) {
        List<Integer> rotations = new ArrayList<>(4);
        rotations.add(0);

        for (int i=1; i<4; i++) {
            List<EdgeType> edgesListCopy = new ArrayList<>(Arrays.asList(edges));
            Collections.rotate(edgesListCopy, i);

            for (int j=0; j<4; j++) {
                if (!edges[j].equals(edgesListCopy.get(j))) {
                    rotations.add(i);
                    break;
                }
            }
        }

        return rotations.stream()
                .mapToInt(Integer::valueOf)
                .toArray();
    }

    @Override
    public int compareTo(TileType o) {
        return this.fileName.compareTo(o.fileName);
    }
}
