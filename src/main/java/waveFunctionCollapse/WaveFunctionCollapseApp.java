package waveFunctionCollapse;

import waveFunctionCollapse.algorithm.AlgorithmParameters;
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
        int algorithmSpeed = 5;
        float nonRandomFactor = 0.2f; // as percentage
        StartConfiguration startConfiguration = StartConfiguration.MULTISTART_RANDOM;

        AlgorithmParameters params = new AlgorithmParameters.Builder()
                .dimension(dimension)
                .tileSize(tileSize)
                .algorithmSpeed(algorithmSpeed)
                .startConfiguration(startConfiguration)
                .nonRandomFactor(nonRandomFactor)
                .build();

        // TileSet for the algorithm
        TileSet tileSet = new LabyrinthTileSet();

        // Initialize
        WaveFunctionCollapseAlgorithm algorithm =
                new WaveFunctionCollapseAlgorithm(tileSet, params); // dimension, tileSize, algorithmSpeed, startConfiguration, nonRandomFactor);

        // Run algorithm
        algorithm.run();
    }
}
