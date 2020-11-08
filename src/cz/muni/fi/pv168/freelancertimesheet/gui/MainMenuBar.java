package cz.muni.fi.pv168.freelancertimesheet.gui;

import javax.swing.*;
import java.awt.*;

public class MainMenuBar extends JPanel implements GenericElement {

    public MainMenuBar(LayoutManager layout, boolean isDoubleBuffered) {
        super(layout, isDoubleBuffered);
    }

    public MainMenuBar(LayoutManager layout) {
        super(layout);
    }

    public MainMenuBar(boolean isDoubleBuffered) {
        super(isDoubleBuffered);
    }

    public MainMenuBar() {
        super();
    }

    @Override
    public GenericElement setupLayout() {
        return this;
    }

    @Override
    public MainMenuBar setupVisuals()
    {
        setPreferredSize(
                new Dimension(1000, 30)
        );
        setBackground(
                Color.YELLOW
        );
        return this;
    }

    @Override
    public MainMenuBar setupNested()
    {
        JButton buttons[] = {
                new JButton("test1"),
                new JButton("test2"),
                new JButton("test3"),
        };
        GridBagConstraints coord = new GridBagConstraints();
        coord.fill = GridBagConstraints.BOTH;
        coord.gridx = 0;
        coord.gridy = 0;
        for (JButton button : buttons)
        {
            add(button, coord);
            coord.gridx++;
        }
        coord.weighty = 1;
        coord.weightx = 1;
        JPanel padding = new JPanel();
        padding.setOpaque(false);
        add(padding, coord);

        return this;
    }

    public static MainMenuBar setup()
    {
        MainMenuBar menu = new MainMenuBar(new GridBagLayout());
        menu.setupVisuals();
        menu.setupNested();
        return menu;
    }

}
