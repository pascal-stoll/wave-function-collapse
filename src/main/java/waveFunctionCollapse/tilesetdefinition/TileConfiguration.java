package waveFunctionCollapse.tilesetdefinition;

import waveFunctionCollapse.algorithm.Direction;

import java.awt.*;
import java.util.List;

/**
 * A TileConfiguration is a specific TileType together with its rotation
 */
public record TileConfiguration(TileType tileType, Image image, int rotation, List<EdgeType> edges) implements Comparable<TileConfiguration> {

    public TileConfiguration {    }

    /**
     * Converts a Direction into an int, compatible for calculating with rotations
     *
     * @param direction the direction
     * @return direction as int
     */
    public static int directionToInt(Direction direction) {
        return switch (direction) {
            case ABOVE -> 0;
            case RIGHT -> 1;
            case BELOW -> 2;
            case LEFT -> 3;
        };
    }

    @Override
    public String toString() {
        return "[" + this.image + ", rotation: " + this.rotation + "]";
    }

    @Override
    public int compareTo(TileConfiguration o) {
        return this.tileType.compareTo(o.tileType());
    }
}
