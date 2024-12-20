package waveFunctionCollapse.gui;

import waveFunctionCollapse.tilesetdefinition.TileConfiguration;

import javax.swing.*;
import java.awt.*;

/**
 * A Tile in the grid, represented as a JLabel child class
 */
public class TileLabel extends JLabel {

    private final int tileSize;
    /**
     * Creates a TileLabel object
     */
    public TileLabel(final int tileSize) {
        super();
        this.tileSize = tileSize;
        // not visible when tileSize is implemented correctly
        // kept for checking purposes
        this.setBackground(Color.GREEN);
    }

    /**
     * displays the given configuration in the GUI
     *
     * @param configuration the configuration to be displayed
     */
    public final void showImageConfiguration(final TileConfiguration configuration) {
        Image scaledImage = configuration.image().getScaledInstance(this.tileSize, this.tileSize, Image.SCALE_SMOOTH);
        ImageIcon imageIcon = new ImageIcon(scaledImage);

        this.setIcon(imageIcon);
        this.setVisible(true);
    }

    public void hideImageConfiguration() {
        this.setIcon(null);
        this.setVisible(false);
    }

    @Override
    public String toString() {
        return "[A TileLabel]";
    }
}
