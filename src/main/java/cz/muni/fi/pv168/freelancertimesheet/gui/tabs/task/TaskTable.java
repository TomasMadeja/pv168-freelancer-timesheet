package cz.muni.fi.pv168.freelancertimesheet.gui.tabs.task;

import cz.muni.fi.pv168.freelancertimesheet.backend.DBConnectionUtils;
import cz.muni.fi.pv168.freelancertimesheet.backend.PDFStorage;
import cz.muni.fi.pv168.freelancertimesheet.backend.interfaces.Work;
import cz.muni.fi.pv168.freelancertimesheet.backend.orm.WorkImpl;
import cz.muni.fi.pv168.freelancertimesheet.gui.GenericElement;
import cz.muni.fi.pv168.freelancertimesheet.gui.I18N;
import cz.muni.fi.pv168.freelancertimesheet.gui.actions.table.AddAction;
import cz.muni.fi.pv168.freelancertimesheet.gui.actions.table.DeleteAction;
import cz.muni.fi.pv168.freelancertimesheet.gui.containers.WorkContainer;
import cz.muni.fi.pv168.freelancertimesheet.gui.models.TableModel;
import cz.muni.fi.pv168.freelancertimesheet.gui.models.WorkTableModel;
import cz.muni.fi.pv168.freelancertimesheet.gui.popups.InvoiceWindow;
import cz.muni.fi.pv168.freelancertimesheet.gui.popups.workform.WorkFormWindow;
import cz.muni.fi.pv168.freelancertimesheet.gui.popups.worktype.form.WorkTypeFormWindow;
import cz.muni.fi.pv168.freelancertimesheet.gui.tabs.invoice.InvoiceTable;

import javax.persistence.EntityManager;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class TaskTable extends JPanel implements GenericElement<TaskTable> {

    private final I18N i18n = new I18N(getClass());

    private JTable table;
    private final WorkContainer container;
    private final WorkTableModel model;

    private JToolBar filterBar;
    private JButton filterButton;
    private JTextField workName;
    private JTextField workTypeName;

    private final PDFStorage pdfStorage;

    public int GetSelectedTasksCount() {
        return table.getSelectedRows().length;
    }

    public TaskTable(PDFStorage pdfStorage) {
        super();
        container = new WorkContainer();
        model = new WorkTableModel(container);
        this.pdfStorage = pdfStorage;
    }

    private TaskTable addFilter(String title, Component inputField) {
        JLabel label = new JLabel(title);
        label.setLabelFor(inputField);
        filterBar.add(label);
        filterBar.add(inputField);
        return this;
    }

    private JToolBar buildFilterBar() {
        filterBar = new JToolBar();
        filterBar.setFloatable(false);
        filterBar.setRollover(true);
        filterButton = new JButton(i18n.getString("search"));
        filterButton.addActionListener(
                (ActionEvent e) -> {
                    filterButton.setEnabled(false);
                    String workNameCurrent = workName.getText();
                    String workTypeNameCurrent = workTypeName.getText();
                    new SwingWorker<Void, Void>() {
                        @Override
                        public Void doInBackground() {
                            container.refresh(workNameCurrent, workTypeNameCurrent);
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
        workName = new JTextField(100);
        addFilter(i18n.getString("workName"), workName);
        filterBar.addSeparator();
        workTypeName = new JTextField(100);
        addFilter(i18n.getString("workTypeName"), workTypeName);
        return filterBar;
    }

    private JToolBar buildToolBar() {
        JToolBar toolbar = new JToolBar();
        toolbar.setFloatable(false);
        toolbar.setRollover(true);

        var addButton = new AddAction(
                table,
                (JTable table, AddAction.Callback callback) -> WorkFormWindow.setup(callback, container),
                () -> {
                    ((TableModel) table.getModel()).fireTableDataChanged();
                }
        );
//        var editButton = new JButton("Edit"); // TODO
        var deleteButton = new DeleteAction(table); // TODO

        toolbar.add(addButton);
//        panel.add(editButton);
        toolbar.add(deleteButton);

        toolbar.addSeparator();
        toolbar.add(Box.createHorizontalGlue());
        var addInvoiceButton = new AddAction(
                i18n.getString("addInvoiceButton"),
                table,
                (JTable table, AddAction.Callback callback) -> {
                    List<Work> selected = new ArrayList<>();
                    for (int i : table.getSelectedRows()) {
                        selected.add((WorkImpl) container.get(i));
                    }
                    InvoiceWindow.setup(callback, pdfStorage, selected);
                    },
                () -> {
                    ((TableModel) table.getModel()).fireTableDataChanged();
                }
        );
        toolbar.add(addInvoiceButton);

        return toolbar;
    }

    private JPanel createTableButtonPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1));
        panel.add(buildToolBar());
        panel.add(buildFilterBar());
        return panel;
    }


    private JTable createTable() {
        table = new JTable(model);
        table.getTableHeader().setReorderingAllowed(false);
        table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
//        loadDataFromDatabase((WorkTableModel) table.getModel());
        return table;
    }

    private void loadDataFromDatabase(TableModel<Work> table) {
        EntityManager entityManager = DBConnectionUtils.getSessionFactory().createEntityManager();
        List<WorkImpl> result = entityManager.createQuery("from WorkImpl", WorkImpl.class).getResultList();
//        result.forEach(table::addRow);
    }

    private void refresh() {
        container.refresh();
        table.repaint();
        loadDataFromDatabase((WorkTableModel) table.getModel());
    }

    @Override
    public TaskTable setupLayout() {
        this.setLayout(new BorderLayout());
        return this;
    }

    @Override
    public TaskTable setupVisuals() {
        return this;
    }

    @Override
    public TaskTable setupNested() {
        this.add(new JScrollPane(createTable()), BorderLayout.CENTER);
        this.add(createTableButtonPanel(), BorderLayout.BEFORE_FIRST_LINE);

        // table needs to be wrapped in JScrollPane in order to show column names
        return this;
    }

    public static TaskTable setup(PDFStorage pdfStorage) {
        return new TaskTable(pdfStorage)
                .setupLayout()
                .setupVisuals()
                .setupNested();
    }
}
