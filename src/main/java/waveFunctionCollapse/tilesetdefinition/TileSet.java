package waveFunctionCollapse.tilesetdefinition;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.*;

/**
 * A set of tiles that belong together and that the algorithm works on
 *
 * Every child must implement getFileNames(), getRotations(), and getEdges()
 * to provide all information about the TileSet
 */
public abstract class TileSet {

    private final Set<TileConfiguration> allTileConfigurations;

    public TileSet(final String directoryPath) {
        final String projectPath = "src/main/resources/";
        final List<TileType> tileTypes = defineTiles();

        this.allTileConfigurations = new HashSet<>();
        for (TileType tileType : tileTypes) {
            try {
                final String fullImagePath = projectPath + directoryPath + "/" + tileType.fileName();
                final BufferedImage image = initializeImage(fullImagePath);

                for (int rotation : tileType.rotations()) {
                    Image finalImage = rotateImage(image, rotation);
                    List<EdgeType> rotatedEdges = rotateEdges(tileType.edges(), rotation);
                    this.allTileConfigurations.add(new TileConfiguration(tileType, finalImage, rotation, rotatedEdges));
                }
            }
            catch (IOException e) {
                throw new RuntimeException("No tileType found under the given fileName " + tileType.fileName());
            }
        }
    }

    /**
     * returns all TileConfigurations of the TileSet as a Set
     *
     * @return all TileConfigurations as a Set
     */
    public final Set<TileConfiguration> getAllTileConfigurations() {
        return new HashSet<>(this.allTileConfigurations);
    }

    /**
     * Returns the default distribution how often different tiles occur
     * @return a Map mapping each Tile to 1, that is equal weight of all Tiles
     */
    public Map<TileType, Float> defaultProbabilityDistribution() {
        Map<TileType, Float> distribution = new HashMap<>();
        for (TileType tileType : defineTiles()) {
            distribution.put(tileType, 1f);
        }
        return distribution;
    }

    /**
     * defines the Tiles of the TileSet by returning a List of Tiles.
     * Method is overwritten in child classes to define the respective TileSet
     *
     * @return a List of all Tiles in this TileSet
     */
    protected abstract List<TileType> defineTiles();

    public abstract Map<TileType, Float> testProbDistribution();


    /**
     * creates a BufferedImage from the given file path
     *
     * @param imagePath the path to the image
     * @return the BufferedImage of the given file
     * @throws IOException if the image is not found
     */
    private BufferedImage initializeImage(final String imagePath) throws IOException {
        return ImageIO.read(new File(imagePath));
    }

    /**
     * rotates the given image by as many 90-degree steps as given in rotation
     *
     * @param image the image to be rotated
     * @param rotation the number of 90 deg. turn
     * @return the rotated image
     */
    private Image rotateImage(final BufferedImage image, final int rotation) {
        double angle = rotation*(Math.PI/2);
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage rotatedImage = new BufferedImage(width, height, image.getType());
        Graphics2D g2d = rotatedImage.createGraphics();

        // Rotate the image
        g2d.rotate(angle, width / 2d, height / 2d);
        g2d.drawImage(image, null, 0, 0);
        g2d.dispose();

        return rotatedImage;
    }


    /**
     * rotates the List of edges, so they are correct order given the rotation
     *
     * @param edges the current List of edges
     * @param rotation the rotation
     * @return the new List of EdgeType
     */
    private List<EdgeType> rotateEdges(final ArrayList<EdgeType> edges, final int rotation) {
        ArrayList<EdgeType> edgesCopy = new ArrayList<>(edges);
        Collections.rotate(edgesCopy, rotation);

        return edgesCopy;
    }
}
