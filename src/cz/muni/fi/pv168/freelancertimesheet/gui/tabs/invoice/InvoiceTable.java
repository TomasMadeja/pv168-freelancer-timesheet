package cz.muni.fi.pv168.freelancertimesheet.gui.tabs.invoice;

import cz.muni.fi.pv168.freelancertimesheet.gui.GenericElement;
import cz.muni.fi.pv168.freelancertimesheet.gui.elements.DateTimePickerFactory;
import cz.muni.fi.pv168.freelancertimesheet.gui.models.InvoiceTableModel;
import cz.muni.fi.pv168.freelancertimesheet.gui.tabs.invoice.buttons.AddButton;

import javax.swing.*;
import java.awt.*;

public class InvoiceTable extends JPanel implements GenericElement {
    private JTable table;
    private JToolBar toolbar;
    private JToolBar filterBar;

    private InvoiceForm form;

    public InvoiceTable(InvoiceForm form) {
        super();
        this.form = form;
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
        table = new JTable(new InvoiceTableModel());
        table.setAutoCreateRowSorter(true);
        table.setRowHeight(20);
        return new JScrollPane(table);
    }

    private JToolBar buildToolBar() {
        toolbar = new JToolBar();
        toolbar.setFloatable(false);
        toolbar.setRollover(true);
        toolbar.add(new AddButton(form));
        toolbar.add(new JButton("View"));
        toolbar.add(new JButton("Delete"));
        toolbar.addSeparator();
        toolbar.add(Box.createHorizontalGlue());
        return toolbar;
    }

    private JToolBar buildFilterBar() {
        filterBar = new JToolBar();
        filterBar.setFloatable(false);
        filterBar.setRollover(true);
        String[] dateLabels = {"Invoice Date", "Due Date"};
        filterBar.add(new JComboBox<String>(dateLabels));
        filterBar.addSeparator();
        addFilter("From:", DateTimePickerFactory.createGenericDatePicker());
        filterBar.addSeparator();
        addFilter("To:", DateTimePickerFactory.createGenericDatePicker());
        filterBar.addSeparator();
        String[] textlabels = {"Name:", "ICO:", "DIC:"};
        addFilter(new JComboBox<String>(textlabels), new JTextField(100));
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

    public static InvoiceTable setup(InvoiceForm form) {
        InvoiceTable invoiceTable = new InvoiceTable(form);
        invoiceTable
                .setupLayout()
                .setupVisuals()
                .setupNested();
        return invoiceTable;
    }
}
