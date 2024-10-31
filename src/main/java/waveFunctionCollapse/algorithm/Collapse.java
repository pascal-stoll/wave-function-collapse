package waveFunctionCollapse.algorithm;

import waveFunctionCollapse.tilesetdefinition.TileConfiguration;

/**
 * Encapsulates a Tile and its configuration that are being collapsed in one step of the algorithm
 */
public record Collapse (Tile tile, TileConfiguration configuration) {
    /**
     * triggers the collapse
     */
    public void collapse(final int collapseTime) {
        this.tile.display(this.configuration, collapseTime);
    }
}
