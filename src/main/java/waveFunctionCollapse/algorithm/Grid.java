package waveFunctionCollapse.algorithm;

import waveFunctionCollapse.gui.GridFrame;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * A Grid keeps track of its tiles and their status.
 */
public class Grid {

    final WaveFunctionCollapseAlgorithm algorithm;
    private final GridFrame gridFrame;
    private int collapsedTiles;
    private final int entropyRandomScore;

    private Map<Position, Tile> tiles = new HashMap<>();

    private ArrayList<Integer> entropies = new ArrayList<>();

    /**
     * Creates a Grid object
     */
    public Grid(final WaveFunctionCollapseAlgorithm algorithm) {
        super();
        this.algorithm = algorithm;
        this.gridFrame = new GridFrame(this);
        this.collapsedTiles = 0;
        this.entropyRandomScore = (int) Math.ceil(this.algorithm.getNonRandomFactor() * this.algorithm.getTileSet().getNumberOfTileConfigurations());
    }

    public final WaveFunctionCollapseAlgorithm getAlgorithm() {
        return this.algorithm;
    }

    public final GridFrame getGridFrame() {
        return this.gridFrame;
    }

    public final Map<Position, Tile> getTiles() {
        return this.tiles;
    }

    public final int getCollapsedTiles() {
        return this.collapsedTiles;
    }


    /**
     * Creates and initializes a Tile for every spot
     */
    public final void initializeTiles() {
        int tilesHorizontal = this.algorithm.getTilesHorizontal();
        int tilesVertical = this.algorithm.getTilesVertical();
        Tile tile;
        Position tilePosition;

        for (int i = 0; i<tilesHorizontal*tilesVertical; i++) {
            int row = i / tilesHorizontal;
            int col = i % tilesHorizontal;
            tilePosition = new Position(row, col);

            tile = new Tile(tilePosition, this);
            this.gridFrame.add(tile.getTileLabel());
            this.tiles.put(tilePosition, tile);
        }
    }


    /**
     * Collapses the next tile the algorithm chooses
     */
    public final void collapseNext() {
        // find next Tile to be collapsed
        Tile nextCollapsed = nextTileToBeCollapsed();

        // collapse Tile
        collapseTile(nextCollapsed);
    }

    /**
     * collapses a specific Tile
     *
     * used for implementing non-random StartConfigurations
     *
     * @param position the position of the tile to be collapsed
     */
    public final void collapseCertainAt(final Position position) {
        // Get certain Tile
        Tile nextCollapsed = this.getTiles().get(position);

        // collapse Tile
        collapseTile(nextCollapsed);
    }

    private final void collapseTile(Tile nextCollapsed) {
        // Collapse Tile
        nextCollapsed.collapseTile();

        // Update status in this
        this.collapsedTiles++;

        // Update entropies
        Set<Tile> adjacentTiles = getAdjacentTiles(nextCollapsed);
        adjacentTiles = adjacentTiles.stream()
                .filter(Predicate.not(Tile::isCollapsed))
                .collect(Collectors.toCollection(HashSet::new));

        updateEntropies(adjacentTiles);
    }

    /**
     * Returns the non-collapsed tiles that are adjacent to the given tile
     *
     * @param tile the respective Tile
     * @return all adjacent Tiles
     */
    protected final Set<Tile> getAdjacentTiles(final Tile tile) {
        int lastRow = this.algorithm.getTilesVertical() - 1;
        int lastCol = this.algorithm.getTilesHorizontal() - 1;
        Position position = tile.getPosition();
        int row = position.row();
        int col = position.col();

        Set<Tile> adjacentTiles = new HashSet<Tile>();

        // Check all 4 directions
        if (row != 0) {
            adjacentTiles.add(this.tiles.get(new Position(row - 1, col)));
        }
        if (row != lastRow) {
            adjacentTiles.add(this.tiles.get(new Position(row + 1, col)));
        }
        if (col != 0) {
            adjacentTiles.add(this.tiles.get(new Position(row, col - 1)));
        }
        if (col != lastCol) {
            adjacentTiles.add(this.tiles.get(new Position(row, col + 1)));
        }

        return adjacentTiles;
    }

    private final Tile nextTileToBeCollapsed() {
        int minimalEntropy = this.tiles.values().stream()
                .filter(Predicate.not(Tile::isCollapsed))
                .map(Tile::getEntropy)
                .min(Integer::compareTo).get()
                + this.entropyRandomScore;

        Set<Tile> collapseCandidates = this.tiles.values()
                .stream()
                .filter(Predicate.not(Tile::isCollapsed))
                .filter(tile -> tile.getEntropy() <= minimalEntropy)
                .collect(Collectors.toCollection(HashSet::new));

        ArrayList<Tile> collapseCandidatesArray = new ArrayList<Tile>(collapseCandidates);

        if (collapseCandidates.size() == 1) {
            return collapseCandidatesArray.get(0);
        }

        Random random = new Random();
        int randIndex = random.nextInt(collapseCandidatesArray.size());

        return collapseCandidatesArray.get(randIndex);
    }

    /**
     * Updates the entropies of the given Tiles
     *
     * @param tiles the tiles to be updated
     */
    private final void updateEntropies(final Set<Tile> tiles) {
        for (Tile tile : tiles) {
            tile.updateEntropy();
        }
    }
}
