package cz.muni.fi.pv168.freelancertimesheet.gui;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel {
    public MainPanel(LayoutManager layout, boolean isDoubleBuffered) {
        super(layout, isDoubleBuffered);
    }

    public MainPanel(LayoutManager layout) {
        super(layout);
    }

    public MainPanel(boolean isDoubleBuffered) {
        super(isDoubleBuffered);
    }

    public MainPanel() {
        super();
    }

    public MainPanel setupVisuals()
    {
        setPreferredSize(
                new Dimension(500, 500)
        );
        setBackground(
                Color.ORANGE
        );
        return this;
    }

    public static JPanel setupPanel()
    {
        JPanel pp = new JPanel(new BorderLayout());
        pp.setBackground(Color.RED);
        MainPanel tab = new MainPanel(new GridBagLayout());
        tab.setupVisuals();
        return tab;
    }
}
