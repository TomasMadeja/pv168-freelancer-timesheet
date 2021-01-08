package cz.muni.fi.pv168.freelancertimesheet.gui;

import cz.muni.fi.pv168.freelancertimesheet.backend.PDFStorage;
import cz.muni.fi.pv168.freelancertimesheet.backend.PersistanceManager;
import cz.muni.fi.pv168.freelancertimesheet.backend.interfaces.Issuer;
import cz.muni.fi.pv168.freelancertimesheet.gui.containers.WorkTypeContainer;
import cz.muni.fi.pv168.freelancertimesheet.gui.popups.InvoiceWindow;
import cz.muni.fi.pv168.freelancertimesheet.gui.popups.issuer.form.IssuerFormWindow;
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
    private static final I18N i18n = new I18N(MainWindow.class);



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

        var fileMenu = new JMenu(i18n.getString("file"));
        var taskMenu = new JMenu(i18n.getString("work"));
        var invoiceMenu = new JMenu(i18n.getString("invoice"));
        var settingsMenu = new JMenu(i18n.getString("settings"));

        var quitItem = new JMenuItem(i18n.getString("quit"));
        quitItem.addActionListener(e -> this.dispose());

        var newTaskItem = new JMenuItem(i18n.getString("newWork"));
        newTaskItem.addActionListener(e -> WorkFormWindow.setup(null, null));
        var newTaskTypeItem = new JMenuItem(i18n.getString("newWorkType"));
        newTaskTypeItem.addActionListener(e -> WorkTypeFormWindow.setup(null, null));
        var newInvoiceItem = new JMenuItem(i18n.getString("newInvoice"));
        newInvoiceItem.addActionListener(e -> InvoiceWindow.setup(null, pdfStorage));

        var issuerSettings = new JMenuItem(i18n.getString("issuerInfo"));
        issuerSettings.addActionListener(e -> {
                new SwingWorker<Issuer, Void>() {
                    @Override
                    public Issuer doInBackground() {
                        var issuers = PersistanceManager.getAllIssuer();
                        return issuers.size() > 0 ? issuers.get(0) : null;
                    }

                    @Override
                    protected void done() {
                        try {
                            var issuer = this.get();
                            IssuerFormWindow.setup(null, issuer);
                        } catch (Exception ignore) {
                        }
                    }
                }.execute();
            }
        );


        fileMenu.addSeparator();
        fileMenu.add(quitItem);

        taskMenu.add(newTaskItem);
        taskMenu.add(newTaskTypeItem);

        invoiceMenu.add(newInvoiceItem);

        settingsMenu.add(issuerSettings);

        menuBar.add(fileMenu);
        menuBar.add(taskMenu);
        menuBar.add(invoiceMenu);
        menuBar.add(settingsMenu);

        return menuBar;

    }

    public MainWindow setupNested() {
        this.setJMenuBar(createMenuBar());
        rootPanel.add(Tabs.setup(pdfStorage));
        return this;
    }

    public static MainWindow setup(PDFStorage pdfStorage) {
        MainWindow window = new MainWindow(i18n.getString("title"), pdfStorage);
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
