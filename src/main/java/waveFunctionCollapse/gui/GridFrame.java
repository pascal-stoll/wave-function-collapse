package waveFunctionCollapse.gui;

import waveFunctionCollapse.algorithm.Grid;
import waveFunctionCollapse.algorithm.WaveFunctionCollapseAlgorithm;

import javax.swing.JFrame;
import javax.swing.ImageIcon;
import java.awt.*;

/**
 * The GUI window where all actions take place visually.
 *
 * Compared to the JFrame class, some settings are directly
 * initialized in the construcor
 */
public class GridFrame extends JFrame {

    /**
     * Creates a new GridFrame object and thus GUI window
     *
     */
    public GridFrame(final Grid grid) {
        super();

        Dimension dimension = grid.getAlgorithm().getDimension();
        int tilesHorizontal = grid.getAlgorithm().getTilesHorizontal();
        int tilesVertical = grid.getAlgorithm().getTilesVertical();

        // apply default settings
        this.setTitle("Wave Function Collapse");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(dimension);
        this.setResizable(false);

        // apply grid layout
        this.setLayout(new GridLayout(tilesVertical, tilesHorizontal, 0, 0));
        this.getContentPane().setBackground(Color.WHITE);

        // TODO use other icon image file (path)
        ImageIcon icon = new ImageIcon("C:/Users/pas02/IdeaProjects/WaveFunctionCollapse/src/main/resources/Tiles/tile1.png");
        this.setIconImage(icon.getImage());
    }

}
