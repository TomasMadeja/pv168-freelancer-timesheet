package cz.muni.fi.pv168.freelancertimesheet.gui;

import cz.muni.fi.pv168.freelancertimesheet.backend.PDFStorage;
import cz.muni.fi.pv168.freelancertimesheet.gui.containers.WorkTypeContainer;
import cz.muni.fi.pv168.freelancertimesheet.gui.popups.InvoiceWindow;
import cz.muni.fi.pv168.freelancertimesheet.gui.popups.workform.WorkFormWindow;
import cz.muni.fi.pv168.freelancertimesheet.gui.popups.worktype.form.WorkTypeFormWindow;

import javax.swing.*;
import java.awt.*;

/**
 * Main window
 */
public class MainWindow extends JFrame implements GenericElement<MainWindow> {
    private JPanel rootPanel;
    private PDFStorage pdfStorage;


    public MainWindow(String title, PDFStorage pdfStorage) throws HeadlessException {
        super(title);
        this.pdfStorage = pdfStorage;
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
        newTaskItem.addActionListener(e -> WorkFormWindow.setup(null, null));
        var newTaskTypeItem = new JMenuItem("New Task Type");
        newTaskTypeItem.addActionListener(e -> WorkTypeFormWindow.setup(null, null));
        var newInvoiceItem = new JMenuItem("New Invoice");
        newInvoiceItem.addActionListener(e -> InvoiceWindow.setup(null, pdfStorage));


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
        rootPanel.add(Tabs.setup(pdfStorage));
        return this;
    }

    public static MainWindow setup(PDFStorage pdfStorage) {
        MainWindow window = new MainWindow("Dashboard", pdfStorage);
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
