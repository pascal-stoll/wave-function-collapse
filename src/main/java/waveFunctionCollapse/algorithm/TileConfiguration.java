package waveFunctionCollapse.algorithm;

import waveFunctionCollapse.tilesets.TileSet;

import java.awt.*;
import java.util.List;

/**
 * A TileConfiguration is a specific Tile together with its rotation
 */
public record TileConfiguration(Image image, int rotation, List<EdgeType> edges) {


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
}
