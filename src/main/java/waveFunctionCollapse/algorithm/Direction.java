package waveFunctionCollapse.algorithm;

import java.util.*;

public enum Direction {
    ABOVE, RIGHT, BELOW, LEFT;

    final static List<Direction> ALL_DIRECTIONS = new ArrayList<>(List.of(
            Direction.ABOVE, Direction.RIGHT, Direction.BELOW, Direction.LEFT
    ));;
}
