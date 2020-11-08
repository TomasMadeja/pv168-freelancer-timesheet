package cz.muni.fi.pv168.freelancertimesheet.gui;

import javax.swing.*;
import java.awt.*;

/**
 * Main window
 */
public class Window extends JFrame implements GenericElement{
    private JPanel rootPanel;


    public Window() throws HeadlessException {
        super();
    }

    public Window(GraphicsConfiguration gc) {
        super(gc);
    }

    public Window(String title) throws HeadlessException {
        super(title);
    }

    public Window(String title, GraphicsConfiguration gc) {
        super(title, gc);
    }

    public Window setupLayout() // No need to
    {
        return this;
    }

    public Window setupVisuals()
    {
        rootPanel.setPreferredSize(
                new Dimension(1000, 500)
        );
        setMinimumSize(
                new Dimension(600, 500)
        );
        rootPanel.setBackground(Color.BLUE);
        rootPanel.setOpaque(false);
        return this;
    }

    public Window setupNested() {
        rootPanel.add(TabbedPane.setupPanel());
        return this;
    }

    public static Window setupFrame() {
        Window dashboard = new Window("Dashboard");
        dashboard.rootPanel = new JPanel(new BorderLayout());

        dashboard.setupLayout();
        dashboard.setupVisuals();
        dashboard.setupNested();

        dashboard.add(dashboard.rootPanel);
        dashboard.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        dashboard.pack();
        dashboard.setVisible(true);

        return dashboard;
    }
}
