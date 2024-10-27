package waveFunctionCollapse;

import waveFunctionCollapse.parameters.AlgorithmParameters;
import waveFunctionCollapse.parameters.StartConfiguration;
import waveFunctionCollapse.algorithm.WaveFunctionCollapseAlgorithm;
import waveFunctionCollapse.tilesetdefinition.EdgeType;
import waveFunctionCollapse.tilesetdefinition.TileSet;
import waveFunctionCollapse.tilesets.*;

import java.awt.*;

/**
 * Entry point to the program.
 */
public class WaveFunctionCollapseApp {

    // TODO:
    // - probabilities of tiles
    // - backtrack & error recovering
    public static void main(String[] args) {
        // Parameters for the algorithm
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int tileSize = 40;
        short algorithmSpeed = 2; // delay in ms between two collapses
        float nonRandomFactor = 0.2f; // as percentage
        StartConfiguration startConfiguration = StartConfiguration.MIDDLE;
        EdgeType borderEdge = new EdgeType("000");

        AlgorithmParameters params = new AlgorithmParameters.Builder()
                .dimension(dimension)
                .tileSize(tileSize)
                .algorithmSpeed(algorithmSpeed)
                .startConfiguration(startConfiguration)
                .nonRandomFactor(nonRandomFactor)
                .borderEdge(borderEdge)
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
