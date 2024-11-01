package waveFunctionCollapse.algorithm;

import waveFunctionCollapse.gui.TileLabel;
import waveFunctionCollapse.tilesetdefinition.TileConfiguration;

import java.util.*;

/**
 * A Tile in a grid
 *
 * is displayed in a TileLabel object in the GUI.
 */
public class Tile implements Comparable<Tile> {

    private final TileLabel tileLabel;
    private boolean collapsed;
    private int collapseTime;
    private final Position position;
    private int entropy;
    private Optional<TileConfiguration> tileConfiguration;
    private int previousUncollapses;

    /**
     * Creates a Tile object
     *
     * @param position the position (row, column) within the grid
     */
    public Tile(final Position position, final int tileSize) {
        super();
        this.tileLabel = new TileLabel(tileSize);
        this.collapsed = false;
        this.position = position;
        this.tileConfiguration = Optional.empty();
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

    public final int getCollapseTime() {
        return this.collapseTime;
    }

    public final void setEntropy(final int value) {
        this.entropy = value;
    }
    public final int getPreviousUncollapses() {
        return this.previousUncollapses;
    }

    public final Optional<TileConfiguration> getTileConfiguration() {
        return this.tileConfiguration;
    }

    /**
     * displays the given TileConfiguration in the GUI and updates the state of this Tile
     *
     * @param configuration the TileConfiguration to be displayed
     */
    public void display(final TileConfiguration configuration, int time) {
        this.tileLabel.showImageConfiguration(configuration);
        this.entropy = 0;
        this.collapsed = true;
        this.collapseTime = time;
        this.tileConfiguration = Optional.of(configuration);
    }

    /**
     * Hides the ImageConfiguration of a Tile.
     * Used in error case when no valid collapse is possible to undo collapses
     */
    public void hide() {
        this.tileLabel.hideImageConfiguration();
        this.collapsed = false;
        this.collapseTime = 0;
        this.tileConfiguration = Optional.empty();
        this.previousUncollapses++;
    }

    @Override
    public int compareTo(Tile other) {
        int prevCollapses = this.previousUncollapses - other.previousUncollapses;
        if (prevCollapses != 0) return prevCollapses;
        return this.collapseTime - other.collapseTime;
    }

    @Override
    public String toString() {
        String collapsed = this.collapsed ? "TRUE" : "FALSE";
        return "[Position: " + this.position.toString() + ", collapsed: " + collapsed + ", entropy: " + String.valueOf(this.entropy) + ", TileLabel: " + this.tileLabel.toString() + "]";
    }
}
