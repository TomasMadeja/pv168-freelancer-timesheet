package cz.muni.fi.pv168.freelancertimesheet.gui;

import javax.swing.*;
import java.awt.*;

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
//        getContentPane().Op;.setBackground(Color.BLUE);
//        setOpacity(0);
        return this;
    }

    public Window setupNested()
    {
        rootPanel.add(MainMenuBar.setup(), BorderLayout.NORTH);
        rootPanel.add(MainPanel.setupPanel(), BorderLayout.CENTER);
        return this;
    }

    public static JFrame setup()
    {
        Window window = new Window("Freelancer Timesheet");
        window.rootPanel = new JPanel(new BorderLayout());

        window.setupLayout();
        window.setupVisuals();
        window.setupNested();

        window.add(window.rootPanel);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        return window;
    }

    public static void main(String[] args) {
        Window dash = (Window) setup();
        dash.pack();
        dash.setVisible(true);
    }
}
