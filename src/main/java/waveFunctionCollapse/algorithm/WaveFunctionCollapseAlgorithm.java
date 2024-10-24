package waveFunctionCollapse.algorithm;

import waveFunctionCollapse.tilesets.TileSet;

import java.awt.*;
import java.util.Random;

/**
 * The algorithm that performs the WaveFunctionCollapse
 */
public class WaveFunctionCollapseAlgorithm {

    private final TileSet tileSet;
    private final AlgorithmParameters parameters;
    private final Grid grid;
    private final int totalTiles;

    /**
     * Creates a new instance of the algorithm
     *
     * @param tileSet the TileSet the algorithm should work upon
     * @param parameters all parameters relevant to the algorithm
     */
    public WaveFunctionCollapseAlgorithm(final TileSet tileSet, final AlgorithmParameters parameters) {
        this.tileSet = tileSet;
        this.parameters = parameters;
        this.totalTiles = this.parameters.tilesHorizontal()*this.parameters.tilesVertical();

        // Sets up Grid and Tiles
        this.grid = new Grid(this.parameters);
        this.grid.initializeTiles();
        this.grid.getGridFrame().setVisible(true);

        // Start Configuration
        int startCollapses;
        switch (parameters.startConfiguration()) {
            case RANDOM:
                break;

            case MIDDLE:
                int middleHorizontal = this.parameters.tilesHorizontal() / 2;
                int middleVertical = this.parameters.tilesVertical() / 2;
                this.grid.collapseCertainAt(new Position(middleVertical, middleHorizontal));
                break;

            case MULTISTART_RANDOM:
                startCollapses = (int) Math.ceil(Math.log10(this.parameters.tilesHorizontal()*this.parameters.tilesVertical())) * 2;
                Position randomPosition;
                Random random = new Random();
                for (int i=0; i<startCollapses; i++) {
                    int row = random.nextInt(this.parameters.tilesVertical() - 1);
                    int col = random.nextInt(this.parameters.tilesHorizontal() - 1);
                    randomPosition = new Position(row, col);
                    this.grid.collapseCertainAt(randomPosition);
                }
                break;
        }
    }


    public final TileSet getTileSet() {
        return this.tileSet;
    }

    public final int getTilesHorizontal() {
        return this.parameters.tilesHorizontal();
    }

    public final int getTilesVertical() {
        return this.parameters.tilesVertical();
    }

    public final int getTileSize() {
        return this.parameters.tileSize();
    }

    /**
     * Performs the algorithm iteratively until all tiles are collapsed.
     * Each loop, one tile is collapsed
     */
    public final void run() {
        while (!this.isCompleted()) {
            this.grid.collapseNext();
            waitForMilliseconds();
        }
        System.out.println("Done!");
    }

    /**
     * checks whether all tiles are collapsed and the algorithm is finished
     *
     * @return true if all tiles are collapsed, false otherwise
     */
    private boolean isCompleted() {
        return this.grid.getCollapsedTiles() == this.totalTiles;
    }

    /**
     * Puts the thread to sleep for algorithmSpeed milliseconds
     *
     * Implemented to see the progress of the algorithm visually
     */
    private void waitForMilliseconds() {
        try {
            Thread.sleep(this.parameters.algorithmSpeed());
        }
        catch (InterruptedException e) {

        }
    }
}
