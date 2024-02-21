package waveFunctionCollapse;

import waveFunctionCollapse.algorithm.StartConfiguration;
import waveFunctionCollapse.algorithm.WaveFunctionCollapseAlgorithm;
import waveFunctionCollapse.tilesets.*;

import java.awt.*;

/**
 * Entry point to the program.
 */
public class WaveFunctionCollapseApp {

    public static void main(String[] args) {

        Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();

        // Parameters for the algorithm
        Dimension dimension = screenDimension;
        int tileSize = 15;
        int algorithmSpeed = 2;
        float nonRandomFactor = 0.2f; // as percentage
        StartConfiguration startConfiguration = StartConfiguration.MIDDLE;

        // TileSet for the algorithm
        TileSet tileSet = new LabyrinthTileSet();

        // Initialize
        WaveFunctionCollapseAlgorithm algorithm =
                new WaveFunctionCollapseAlgorithm(tileSet, dimension, tileSize, algorithmSpeed, startConfiguration, nonRandomFactor);

        // Run algorithm
        algorithm.run();
    }
}
