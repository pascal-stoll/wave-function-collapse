package waveFunctionCollapse.gui;

import waveFunctionCollapse.algorithm.Grid;

import javax.swing.JFrame;
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

        int tilesHorizontal = grid.getAlgorithm().getTilesHorizontal();
        int tilesVertical = grid.getAlgorithm().getTilesVertical();

        // apply default settings
        this.setTitle("Wave Function Collapse");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        this.setResizable(false);

        // apply grid layout
        this.setLayout(new GridLayout(tilesVertical, tilesHorizontal, 0, 0));
        this.getContentPane().setBackground(Color.WHITE);
    }

}
