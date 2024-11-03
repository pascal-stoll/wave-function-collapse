package waveFunctionCollapse;

import waveFunctionCollapse.algorithm.WaveFunctionCollapseAlgorithm;
import waveFunctionCollapse.parameters.AlgorithmParameters;
import waveFunctionCollapse.parameters.StartConfiguration;
import waveFunctionCollapse.tilesetdefinition.EdgeType;
import waveFunctionCollapse.tilesets.*;

import java.awt.*;

/**
 * Entry point to the program.
 */
public class WaveFunctionCollapseApp {

    public static void main(String[] args) {
        // Parameters for the algorithm
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int tileSize = 50;          // in pixels
        short algorithmSpeed = 15;   // delay in ms between two collapses
        float nonRandomFactor = 0.2f;   // as percentage

        var tileSet = new LabyrinthTileSet();

        AlgorithmParameters params = new AlgorithmParameters.Builder()
                .dimension(dimension)
                .tileSize(tileSize)
                .algorithmSpeed(algorithmSpeed)
                .startConfiguration(StartConfiguration.MIDDLE)
                .nonRandomFactor(nonRandomFactor)
                .borderEdge(new EdgeType("000"))
                .probabilityDistribution(tileSet.probabilityDistribution1())
                .build();

        WaveFunctionCollapseAlgorithm algorithm = new WaveFunctionCollapseAlgorithm(tileSet, params);
        algorithm.run();
    }
}
