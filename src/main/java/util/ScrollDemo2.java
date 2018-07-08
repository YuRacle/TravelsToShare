package util;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

/**
 * This application aims to dynamically change the client's size in a scroll
 * pane.
 *
 * @author HAN
 *
 */
@SuppressWarnings("serial")
public class ScrollDemo2 extends JPanel {
    /**
     * We can also use Vector to store a variable array. But the overhead will
     * be more than ArrayList. So the difference between them is thread-safe or
     * not.
     */
    private ArrayList<Shape> circles;

    private final int CIRCLE_DIAMETER = 100;

    private Dimension area;// indicate area taken up by graphics.

    private Color[] colors = { Color.BLACK, Color.BLUE, Color.CYAN,
            Color.DARK_GRAY, Color.GRAY, Color.GREEN, Color.LIGHT_GRAY,
            Color.MAGENTA, Color.ORANGE, Color.PINK, Color.RED, Color.YELLOW };

    ScrollDemo2() {
        // The constructor serves also as a content pane. This is a common
        // trick: invoke the constructor with parameter from a constructor
        // without parameter.
        super(new BorderLayout());

        // Allocate memory for member variables if necessary.
        circles = new ArrayList<Shape>();
        area = new Dimension(0, 0);

        // Create "labelPanel".
        JPanel labelPanel = new JPanel(new GridLayout(0, 1));
        labelPanel
                .add(new JLabel("Click left mouse button to place a circle."));
        labelPanel.add(new JLabel(
                "Click right mouse button to clear drawing area."));

        // Create "drawingPane".
        final JPanel drawingPane = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                // Paint first the default behavior of the JPanel painting,
                // which includes fill the whole area with a background color we
                // set. This arrives to the effect that "clear the panel", which
                // can also be realized by setColor followed by fillRect.
                super.paintComponent(g);
                // g.setColor(getBackground());
                // g.fillRect(0, 0, getWidth(), getHeight());

                Graphics2D g2 = (Graphics2D) g;

                // For showing always all drawn circles even if you resize the
                // window, we are forced to draw all circles in the "current"
                // painting.
                for (int i = 0; i < circles.size(); i++) {
                    g2.setColor(colors[i % colors.length]);
                    g2.fill(circles.get(i));
                }
            }
        };

        drawingPane.setOpaque(true);

        // Set the background property, which you can get by getBackground.
        drawingPane.setBackground(Color.WHITE);

        drawingPane.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                boolean changed = false;
                int x = e.getX() - CIRCLE_DIAMETER / 2;
                int y = e.getY() - CIRCLE_DIAMETER / 2;
                if (x < 0)
                    x = 0;
                if (y < 0)
                    y = 0;

                if (SwingUtilities.isLeftMouseButton(e)) {
                    circles.add(new Ellipse2D.Double(x, y, CIRCLE_DIAMETER,
                            CIRCLE_DIAMETER));

                    // Here we can use two repaint methods: the one with
                    // parameter and the other without.
                    // repaint(clip) will repaint updates only in the desired
                    // region (as clip), which can have a more efficient
                    // performance than use directly repaint().
                    drawingPane.repaint(x, y, CIRCLE_DIAMETER, CIRCLE_DIAMETER);
                    // drawingPane.repaint();

                    // Update the area taken up by graphics.
                    int thisWidth = x + CIRCLE_DIAMETER;
                    if (thisWidth > area.width) {
                        area.width = thisWidth;
                        changed = true;
                    }
                    int thisHeight = y + CIRCLE_DIAMETER;
                    if (thisHeight > area.height) {
                        area.height = thisHeight;
                        changed = true;
                    }
                } else if (SwingUtilities.isRightMouseButton(e)) {
                    circles.clear();

                    // Because in this case we want to change the whole painting
                    // area, use the repaint() directly.
                    drawingPane.repaint();

                    area.width = 0;
                    area.height = 0;
                    changed = true;
                }

                if (changed) {
                    // The setPreferredSize method is to be used in a
                    // combination way with the revalidate() method.
                    drawingPane.setPreferredSize(area);

                    // Let the scroll pane know to update itself especially its
                    // scroll bars.
                    drawingPane.revalidate();
                }
            }
        });

        // Put the drawingPane to a scroll pane.
        JScrollPane scrollPane = new JScrollPane(drawingPane);
        // setPreferredSize for the first "pack()" use.
        scrollPane.setPreferredSize(new Dimension(200, 200));


        // Lay out the content pane.
        add(labelPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * For thread safety, this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        // Create and set up the window.
        JFrame frame = new JFrame("ScrollDemo2");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create and set up the content pane.
        JPanel contentPane = new ScrollDemo2();
        contentPane.setOpaque(true);// Content pane must be opaque.
        frame.setContentPane(contentPane);

        // Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        // Schedule a job for the event-dispatching thread:
        // creating and showing this application's GUI.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
