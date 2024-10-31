package waveFunctionCollapse;

import waveFunctionCollapse.algorithm.Tile;
import waveFunctionCollapse.parameters.AlgorithmParameters;
import waveFunctionCollapse.parameters.StartConfiguration;
import waveFunctionCollapse.algorithm.WaveFunctionCollapseAlgorithm;
import waveFunctionCollapse.tilesetdefinition.EdgeType;
import waveFunctionCollapse.tilesetdefinition.TileSet;
import waveFunctionCollapse.tilesets.*;

import java.awt.*;
import java.util.Map;

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
        int tileSize = 60;          // in pixels
        short algorithmSpeed = 2;   // delay in ms between two collapses
        float nonRandomFactor = 0.2f;   // as percentage

        TileSet tileSet = new LabyrinthTileSet();

        AlgorithmParameters params = new AlgorithmParameters.Builder()
                .dimension(dimension)
                .tileSize(tileSize)
                .algorithmSpeed(algorithmSpeed)
                .startConfiguration(StartConfiguration.MIDDLE)
                .nonRandomFactor(nonRandomFactor)
                //.borderEdge(new EdgeType("green")
                .probabilityDistribution(tileSet.testProbDistribution())
                .build();

        WaveFunctionCollapseAlgorithm algorithm = new WaveFunctionCollapseAlgorithm(tileSet, params);
        algorithm.run();
    }
}
