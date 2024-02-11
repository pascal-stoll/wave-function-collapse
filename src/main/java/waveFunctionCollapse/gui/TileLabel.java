package waveFunctionCollapse.gui;

import waveFunctionCollapse.algorithm.Tile;
import waveFunctionCollapse.algorithm.TileConfiguration;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * A Tile in the grid, represented as a JLabel child class
 */
public class TileLabel extends JLabel {

    private final Tile tile;
    /**
     * Creates a TileLabel object
     */
    public TileLabel(final Tile tile) {
        super();
        this.tile = tile;
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
        Image image = configuration.image();
        int tileSize = this.tile.getGrid().getAlgorithm().getTileSize();

        Image scaledImage = image.getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH);

        ImageIcon imageIcon = new ImageIcon(scaledImage);
        this.setIcon(imageIcon);
        this.setVisible(true);
    }

    @Override
    public String toString() {
        return "[A TileLabel]";
    }
}
