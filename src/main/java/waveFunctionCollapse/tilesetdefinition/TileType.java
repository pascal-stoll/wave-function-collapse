package waveFunctionCollapse.tilesetdefinition;

import java.util.*;

public record TileType(String fileName, Set<Integer> rotations, ArrayList<EdgeType> edges) {

    public TileType(final String fileName, final Integer[] rotations, final EdgeType[] edges) {
        this(fileName, Set.of(rotations), new ArrayList<>(List.of(edges)));
    }
}
