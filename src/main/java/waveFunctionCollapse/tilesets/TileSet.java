package waveFunctionCollapse.tilesets;

import waveFunctionCollapse.algorithm.EdgeType;
import waveFunctionCollapse.algorithm.TileConfiguration;

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

    private final int numberOfTileConfigurations;
    private final Set<TileConfiguration> allTileConfigurations;

    public TileSet(final String directoryPath) {
        final String projectPath = "C:/Users/pas02/IdeaProjects/WaveFunctionCollapse/src/main/resources/";

        List<String> fileNames = getFileNames();
        List<Set<Integer>> rotations = getRotations();
        List<ArrayList<EdgeType>> edges = getEdges();

        this.numberOfTileConfigurations = rotations.stream().map(Set::size).reduce(0, Integer::sum);

        int numberOfTiles = new File(projectPath + directoryPath + "/").list().length;

        Set<TileConfiguration> temporaryTileConfigurations = new HashSet<>();
        for (int i = 0; i < numberOfTiles; i++) {
            String fullImagePath = projectPath + directoryPath + "/" + fileNames.get(i);
            BufferedImage image = initializeImage(fullImagePath);

            for (int rotation : rotations.get(i)) {
                Image finalImage = rotateImage(image, rotation);
                List<EdgeType> rotatedEdges = rotateEdges(edges.get(i), rotation);
                temporaryTileConfigurations.add(new TileConfiguration(this, finalImage, rotation, rotatedEdges));
            }
        }

        this.allTileConfigurations = temporaryTileConfigurations;
    }


    /**
     * creates a BufferedImage from the given file path
     *
     * @param imagePath the path to the image
     * @return the BufferedImage of the given file
     * @throws IOException if the image is not found
     */
    private BufferedImage initializeImage(final String imagePath) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }


    /**
     * rotates the given image by as many 90-degree steps as given in rotation
     *
     * @param image the image to be rotated
     * @param rotation the number of 90 deg. turn
     * @return the rotated image
     */
    private final Image rotateImage(final BufferedImage image, final int rotation) {
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

    public final int getNumberOfTileConfigurations() {
        return this.numberOfTileConfigurations;
    }

    public final Set<TileConfiguration> getAllTileImageConfigurations() {
        return this.allTileConfigurations;
    }

    protected abstract List<String> getFileNames();
    protected abstract List<Set<Integer>> getRotations();
    protected abstract List<ArrayList<EdgeType>> getEdges();


    /**
     * rotates the elements in ArrayList of EdgeTypes so that in the
     * TileConfiguration the edges are in correct order
     *
     * @param edges the current List of edges
     * @param rotation the rotation
     * @return the new List of EdgeType
     */
    private final List<EdgeType> rotateEdges(final ArrayList<EdgeType> edges, final int rotation) {
        ArrayList<EdgeType> edgesCopy = new ArrayList<>(edges);
        Collections.rotate(edgesCopy, rotation);

        return edgesCopy;
    }
}
