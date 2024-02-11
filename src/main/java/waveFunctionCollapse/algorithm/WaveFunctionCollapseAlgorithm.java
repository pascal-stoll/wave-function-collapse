package waveFunctionCollapse.algorithm;

import waveFunctionCollapse.tilesets.TileSet;

import java.awt.*;
import java.util.Random;

/**
 * The algorithm that performs the WaveFunctionCollapse
 */
public final class WaveFunctionCollapseAlgorithm {

    private final TileSet tileSet;
    private final Dimension dimension;
    private final int tilesHorizontal;
    private final int tilesVertical;
    private final int tileSize;
    private final int algorithmSpeed;
    private final Grid grid;
    private final float nonRandomFactor;

    /**
     * Creates a new instance of the algorithm
     *
     * @param tileSet the TileSet the algorithm should work upon
     * @param dimension the size of the GUI window
     * @param tileSize the size of a single tiles (quadratic)
     */
    public WaveFunctionCollapseAlgorithm(final TileSet tileSet, final Dimension dimension, final int tileSize,
                                         final int algorithmSpeed, final StartConfiguration startConfiguration,
                                         final float nonRandomFactor) {
        this.tileSet = tileSet;
        this.dimension = dimension;
        this.tileSize = tileSize;
        this.tilesHorizontal = dimension.width / tileSize;
        this.tilesVertical = dimension.height / tileSize;
        this.algorithmSpeed = algorithmSpeed;
        this.nonRandomFactor = nonRandomFactor;

        // Sets up Grid and Tiles
        this.grid = new Grid(this);
        this.grid.initializeTiles();
        this.grid.getGridFrame().setVisible(true);

        // Start Configuration
        int startCollapses;
        switch (startConfiguration) {
            case RANDOM:
                break;
            case MIDDLE:
                int middleHorizontal = this.tilesHorizontal / 2;
                int middleVertical = this.tilesVertical / 2;
                this.grid.collapseCertainAt(new Position(middleVertical, middleHorizontal));
                break;
            case MULTISTART_RANDOM:
                startCollapses = (int) Math.ceil(Math.log10(tilesHorizontal*tilesVertical)) * 2;
                Position randomPosition;
                Random random = new Random();
                for (int i=0; i<startCollapses; i++) {
                    int row = random.nextInt(this.tilesVertical - 1);
                    int col = random.nextInt(this.tilesHorizontal - 1);
                    randomPosition = new Position(row, col);
                    this.grid.collapseCertainAt(randomPosition);
                }
                break;
        }
    }


    public final TileSet getTileSet() {
        return this.tileSet;
    }

    public final Dimension getDimension() {
        return this.dimension;
    }

    public final int getTilesHorizontal() {
        return this.tilesHorizontal;
    }

    public final int getTilesVertical() {
        return this.tilesVertical;
    }

    public final int getTileSize() {
        return this.tileSize;
    }

    public final int getAlgorithmSpeed() {
        return this.algorithmSpeed;
    }

    public final float getNonRandomFactor() {
        return this.nonRandomFactor;
    }

    /**
     * Performs the algorithm iteratively until all tiles are collapsed
     */
    public final void run() {
        do {
            collapseNext();
        }
        while (!this.isCompleted());
        System.out.println("Done!");
    }


    /**
     * One iterative algorithmic steps that collapses one tile
     *
     * Between two
     */
    public final void collapseNext() {
        this.grid.collapseNext();
        waitForMilliseconds();
    }

    /**
     * checks whether all tiles are collapsed and the algorithm is finished
     *
     * @return true if all tiles are collapsed, false otherwise
     */
    public final boolean isCompleted() {
        return this.grid.getCollapsedTiles() == getTilesHorizontal()*getTilesVertical();
    }

    /**
     * Puts the thread to sleep for algorithmSpeed milliseconds
     *
     * Implemented to see the progress of the algorithm visually
     */
    private final void waitForMilliseconds() {
        try {
            Thread.sleep(this.algorithmSpeed);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
