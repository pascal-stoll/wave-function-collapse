package waveFunctionCollapse.tilesets;

import java.util.*;

record TileType(String fileName, Set<Integer> rotations, ArrayList<EdgeType> edges) {

    TileType(final String fileName, final Integer[] rotations, final EdgeType[] edges) {
        this(fileName, Set.of(rotations), new ArrayList<>(List.of(edges)));
    }
}
