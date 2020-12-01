package cz.muni.fi.pv168.freelancertimesheet.gui;

import javax.swing.*;
import java.awt.*;

/**
 * Main window
 */
public class MainWindow extends JFrame implements GenericElement<MainWindow> {
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
                new Dimension(600, 600)
        );
        rootPanel.setBackground(Color.BLUE);
        rootPanel.setOpaque(false);
        return this;
    }

    // TODO use specific actions instead of temporary items
    private JMenuBar createMenuBar() {
        var menuBar = new JMenuBar();

        var fileMenu = new JMenu("File");
        var taskMenu = new JMenu("Tasks");
        var invoiceMenu = new JMenu("Invoice");

        var quitItem = new JMenuItem("Quit");
        quitItem.addActionListener(e -> this.dispose());

        var newTaskItem = new JMenuItem("New Task");
        var newTaskTypeItem = new JMenuItem("New Task Type");
        var newInvoiceItem = new JMenuItem("New Invoice");

        fileMenu.addSeparator();
        fileMenu.add(quitItem);

        taskMenu.add(newTaskItem);
        taskMenu.add(newTaskTypeItem);

        invoiceMenu.add(newInvoiceItem);

        menuBar.add(fileMenu);
        menuBar.add(taskMenu);
        menuBar.add(invoiceMenu);

        return menuBar;

    }

    public MainWindow setupNested() {
        this.setJMenuBar(createMenuBar());
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
