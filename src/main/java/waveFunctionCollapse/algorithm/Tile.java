package waveFunctionCollapse.algorithm;

import waveFunctionCollapse.gui.TileLabel;
import waveFunctionCollapse.tilesetdefinition.TileConfiguration;

import java.util.*;

/**
 * A Tile in a grid
 *
 * is displayed in a TileLabel object in the GUI.
 */
public class Tile {


    private final TileLabel tileLabel;
    private boolean collapsed;
    private final Position position;
    private int entropy;
    private Optional<TileConfiguration> tileConfiguration;

    /**
     * Creates a Tile object
     *
     * @param position the position (row, column) within the grid
     */
    public Tile(final Position position, final int tileSize, final int totalTiles) {
        super();
        this.tileLabel = new TileLabel(tileSize);
        this.collapsed = false;
        this.position = position;
        this.tileConfiguration = Optional.empty();
        this.entropy = totalTiles;
    }

    public final TileLabel getTileLabel() {
        return this.tileLabel;
    }

    public final boolean isCollapsed() {
        return this.collapsed;
    }

    public final Position getPosition() {
        return this.position;
    }

    public final int getEntropy() {
        return this.entropy;
    }

    public final void setEntropy(final int value) {
        this.entropy = value;
    }
    public final Optional<TileConfiguration> getTileImageConfiguration() {
        return this.tileConfiguration;
    }


    /**
     * displays the given TileConfiguration in the GUI and updates the state of this Tile
     *
     * @param configuration the TileConfiguration to be displayed
     */
    public void display(final TileConfiguration configuration) {
        this.tileLabel.showImageConfiguration(configuration);
        this.entropy = 0;
        this.collapsed = true;
        this.tileConfiguration = Optional.of(configuration);
    }


    @Override
    public String toString() {
        String collapsed = this.collapsed ? "TRUE" : "FALSE";
        return "[Position: " + this.position.toString() + ", collapsed: " + collapsed + ", entropy: " + String.valueOf(this.entropy) + ", TileLabel: " + this.tileLabel.toString() + "]";
    }
}
