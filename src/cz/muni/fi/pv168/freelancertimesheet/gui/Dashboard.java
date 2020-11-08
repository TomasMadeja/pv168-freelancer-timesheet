package cz.muni.fi.pv168.freelancertimesheet.gui;

import javax.swing.*;
import java.awt.*;

/**
 * Main window
 */
public class Dashboard extends JFrame {

    private JPanel rootPanel;


    public Dashboard() throws HeadlessException {
        super();
    }

    public Dashboard(GraphicsConfiguration gc) {
        super(gc);
    }

    public Dashboard(String title) throws HeadlessException {
        super(title);
    }

    public Dashboard(String title, GraphicsConfiguration gc) {
        super(title, gc);
    }

    private Dashboard setupLayout() // No need to
    {
        return this;
    }

    private Dashboard setupFrameVisuals() {
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

    private Dashboard setupNested() {
        rootPanel.add(TabbedPane.setupPanel());

//        rootPanel.add(MainMenu.setupPanel(), BorderLayout.WEST);
//        rootPanel.add(MainTab.setupPanel(), BorderLayout.CENTER);
        return this;
    }

    public static Dashboard setupFrame() {

        Dashboard dashboard = new Dashboard("Dashboard");
        dashboard.rootPanel = new JPanel(new BorderLayout());

        dashboard.setupLayout();
        dashboard.setupFrameVisuals();
        dashboard.setupNested();

        dashboard.add(dashboard.rootPanel);
        dashboard.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        dashboard.pack();
        dashboard.setVisible(true);

        return dashboard;
    }


}
