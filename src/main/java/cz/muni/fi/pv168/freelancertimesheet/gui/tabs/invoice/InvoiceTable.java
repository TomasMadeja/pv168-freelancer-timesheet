package cz.muni.fi.pv168.freelancertimesheet.gui.tabs.invoice;

import cz.muni.fi.pv168.freelancertimesheet.backend.PDFStorage;
import cz.muni.fi.pv168.freelancertimesheet.backend.interfaces.Invoice;
import cz.muni.fi.pv168.freelancertimesheet.gui.GenericElement;
import cz.muni.fi.pv168.freelancertimesheet.gui.I18N;
import cz.muni.fi.pv168.freelancertimesheet.gui.actions.table.AddAction;
import cz.muni.fi.pv168.freelancertimesheet.gui.actions.table.DeleteAction;
import cz.muni.fi.pv168.freelancertimesheet.gui.actions.table.ViewPDFAction;
import cz.muni.fi.pv168.freelancertimesheet.gui.containers.InvoiceContainer;
import cz.muni.fi.pv168.freelancertimesheet.gui.elements.DateTimePickerFactory;
import cz.muni.fi.pv168.freelancertimesheet.gui.models.InvoiceTableModel;
import cz.muni.fi.pv168.freelancertimesheet.gui.models.TableModel;
import cz.muni.fi.pv168.freelancertimesheet.gui.popups.InvoiceWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URISyntaxException;

public class InvoiceTable extends JPanel implements GenericElement {

    private final I18N i18n = new I18N(getClass());

    private JTable table;
    private JToolBar toolbar;
    private JToolBar filterBar;
    private InvoiceContainer container;
    private final PDFStorage pdfStorage;

    private AbstractAction addButton;
    private AbstractAction deleteButton;
    private AbstractAction viewButton;

    private JTextField ico;
    private JTextField dic;

    private JButton filterButton;

//    private InvoiceForm form;

    public InvoiceTable(PDFStorage pdfStorage) {
        super();
        this.pdfStorage = pdfStorage;
        container = InvoiceContainer.getContainer();
//        this.form = form;
    }

    private void refresh() {
        container.refresh();
        table.repaint();
    }

    private InvoiceTable addFilter(String title, Component inputField) {
        JLabel label = new JLabel(title);
        label.setLabelFor(inputField);
        addFilter(label, inputField);
        return this;
    }

    private InvoiceTable addFilter(Component label, Component inputField) {
        filterBar.add(label);
        filterBar.add(inputField);
        return this;
    }

    private JScrollPane buildTable() {
        table = new JTable(new InvoiceTableModel(container));
        table.setAutoCreateRowSorter(true);
        table.getTableHeader().setReorderingAllowed(false);
        table.setRowHeight(20);
        return new JScrollPane(table);
    }

    private JToolBar buildToolBar() {
        toolbar = new JToolBar();
        toolbar.setFloatable(false);
        toolbar.setRollover(true);
        addButton = new AddAction(
                table,
                (JTable table, AddAction.Callback callback) -> InvoiceWindow.setup(callback, pdfStorage),
                () -> ((TableModel) table.getModel()).fireTableDataChanged());
        toolbar.add(
                addButton
        );
        viewButton = new ViewPDFAction(table, pdfStorage); // todo fix
        toolbar.add(viewButton);
        deleteButton = new DeleteAction(table);
        toolbar.add(deleteButton);
//        toolbar.add(new JButton("Delete"));
        toolbar.addSeparator();
        toolbar.add(Box.createHorizontalGlue());
        return toolbar;
    }

    private JToolBar buildFilterBar() {
        filterBar = new JToolBar();
        filterBar.setFloatable(false);
        filterBar.setRollover(true);
        filterButton = new JButton(i18n.getString("search"));
        filterButton.addActionListener(
                (ActionEvent e) -> {
                    filterButton.setEnabled(false);
                    String icoCurrent = ico.getText();
                    String dicCurrent = dic.getText();
                    new SwingWorker<Void, Void>() {
                        @Override
                        public Void doInBackground() {
                            container.refresh(icoCurrent, dicCurrent);
                            return null;
                        }

                        @Override
                        protected void done() {
                            try {
                                ((TableModel) table.getModel()).fireTableDataChanged();
                            } catch (Exception ignore) {
                            } finally {
                                filterButton.setEnabled(true);
                            }
                        }
                    }.execute();
                }
        );
        filterBar.add(filterButton);
        filterBar.addSeparator();
        ico = new JTextField(100);
        addFilter(i18n.getString("ICO"), ico);
        filterBar.addSeparator();
        dic = new JTextField(100);
        addFilter(i18n.getString("DIC"), dic);
        return filterBar;
    }

    private JPanel buildHeader() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1));
        panel.add(buildToolBar());
        panel.add(buildFilterBar());
        return panel;
    }

    @Override
    public InvoiceTable setupLayout() {
        return this;
    }

    @Override
    public InvoiceTable setupVisuals() {
        setLayout(new BorderLayout());
        return this;
    }

    @Override
    public InvoiceTable setupNested() {
        add(buildTable(), BorderLayout.CENTER);
        add(buildHeader(), BorderLayout.BEFORE_FIRST_LINE);

        return this;
    }

    public static InvoiceTable setup(PDFStorage pdfStorage) {
        InvoiceTable invoiceTable = new InvoiceTable(pdfStorage);
        invoiceTable
                .setupLayout()
                .setupVisuals()
                .setupNested();
        return invoiceTable;
    }
}
