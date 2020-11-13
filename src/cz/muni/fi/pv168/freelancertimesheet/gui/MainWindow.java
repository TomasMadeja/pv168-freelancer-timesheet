package cz.muni.fi.pv168.freelancertimesheet.gui;

import javax.swing.*;
import java.awt.*;

/**
 * Main window
 */
public class MainWindow extends JFrame implements GenericElement{
    private JPanel rootPanel;


    public MainWindow() throws HeadlessException {
        super();
    }

    public MainWindow(GraphicsConfiguration gc) {
        super(gc);
    }

    public MainWindow(String title) throws HeadlessException {
        super(title);
    }

    public MainWindow(String title, GraphicsConfiguration gc) {
        super(title, gc);
    }

    public MainWindow setupLayout() // No need to
    {
        return this;
    }

    public MainWindow setupVisuals()
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

    public MainWindow setupNested() {
        rootPanel.add(Tabs.setup());
        return this;
    }

    public static MainWindow setup() {
        MainWindow window = new MainWindow("Dashboard");
        window.rootPanel = new JPanel(new BorderLayout());

        window
                .setupLayout()
                .setupVisuals()
                .setupNested();

        window.add(window.rootPanel);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        window.pack();
        window.setVisible(true);

//        var taskTablePopup = Popups.GetTaskTable();
//        taskTablePopup.setVisible(true);
//
//        var taskTabPopup = Popups.GetTaskTab();
//        taskTabPopup.setVisible(true);

        return window;
    }
}
