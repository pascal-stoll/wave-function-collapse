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

        // Parameters for the algorithm
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int tileSize = 60;
        int algorithmSpeed = 1;
        float nonRandomFactor = 0.2f; // as percentage
        StartConfiguration startConfiguration = StartConfiguration.RANDOM;

        // TileSet for the algorithm
        TileSet tileSet = new CarcassoneTileSet();

        // Initialize
        WaveFunctionCollapseAlgorithm algorithm =
                new WaveFunctionCollapseAlgorithm(tileSet, dimension, tileSize, algorithmSpeed, startConfiguration, nonRandomFactor);

        // Run algorithm
        algorithm.run();
    }
}
