package cz.muni.fi.pv168.freelancertimesheet.gui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MainMenu extends JPanel {

    public MainMenu(LayoutManager layout, boolean isDoubleBuffered) {
        super(layout, isDoubleBuffered);
    }

    public MainMenu(LayoutManager layout) {
        super(layout);
    }

    public MainMenu(boolean isDoubleBuffered) {
        super(isDoubleBuffered);
    }

    public MainMenu() {
        super();
    }

    public MainMenu setupVisuals()
    {
        setPreferredSize(
                new Dimension(100, 500)
        );
        setBackground(
                Color.YELLOW
        );
        return this;
    }

    private MainMenu setupNested()
    {
        JButton buttons[] = {
                new JButton("test1"),
                new JButton("test2"),
                new JButton("test3"),
        };
        GridBagConstraints coord = new GridBagConstraints();
        coord.fill = GridBagConstraints.BOTH;
        coord.gridx = 10;
        coord.gridy = 0;
        for (JButton button : buttons)
        {
            add(button, coord);
            coord.gridy++;
        }
        coord.weighty = 1;
        JPanel padding = new JPanel();
        padding.setOpaque(false);
        add(padding, coord);

        return this;
    }

    public static JPanel setupPanel()
    {
        MainMenu menu = new MainMenu(new GridBagLayout());
        menu.setupVisuals();
        menu.setupNested();
        return menu;
    }

}
