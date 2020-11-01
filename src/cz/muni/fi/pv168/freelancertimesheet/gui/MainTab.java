package cz.muni.fi.pv168.freelancertimesheet.gui;

import javax.swing.*;
import java.awt.*;

public class MainTab extends JPanel {
    public MainTab(LayoutManager layout, boolean isDoubleBuffered) {
        super(layout, isDoubleBuffered);
    }

    public MainTab(LayoutManager layout) {
        super(layout);
    }

    public MainTab(boolean isDoubleBuffered) {
        super(isDoubleBuffered);
    }

    public MainTab() {
        super();
    }

    public MainTab setupVisuals()
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
        MainTab tab = new MainTab(new GridBagLayout());
        tab.setupVisuals();
        return tab;
    }
}
