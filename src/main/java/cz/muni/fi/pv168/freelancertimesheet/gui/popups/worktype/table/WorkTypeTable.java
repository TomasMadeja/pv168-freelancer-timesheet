package cz.muni.fi.pv168.freelancertimesheet.gui.popups.worktype.table;

import cz.muni.fi.pv168.freelancertimesheet.gui.GenericElement;
import cz.muni.fi.pv168.freelancertimesheet.gui.I18N;
import cz.muni.fi.pv168.freelancertimesheet.gui.actions.table.AddAction;
import cz.muni.fi.pv168.freelancertimesheet.gui.actions.table.DeleteAction;
import cz.muni.fi.pv168.freelancertimesheet.gui.actions.table.ViewPDFAction;
import cz.muni.fi.pv168.freelancertimesheet.gui.containers.WorkTypeContainer;
import cz.muni.fi.pv168.freelancertimesheet.gui.models.TableModel;
import cz.muni.fi.pv168.freelancertimesheet.gui.models.WorkTypeTableModel;
import cz.muni.fi.pv168.freelancertimesheet.gui.popups.InvoiceWindow;
import cz.muni.fi.pv168.freelancertimesheet.gui.popups.worktype.form.WorkTypeFormWindow;
import cz.muni.fi.pv168.freelancertimesheet.gui.tabs.invoice.InvoiceTable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class WorkTypeTable extends JPanel implements GenericElement<WorkTypeTable> {

    private final I18N i18n = new I18N(getClass());

    private final WorkTypeContainer container;
    private JTable table;

    private JToolBar toolbar;
    private AddAction addButton;
    private DeleteAction deleteButton;

    private JToolBar filterBar;
    private JButton filterButton;
    private JTextField workTypeName;

    public WorkTypeTable() {
        container = new WorkTypeContainer();
    }

    public static JPanel setup() {
        WorkTypeTable workTypeTable = new WorkTypeTable();
        workTypeTable
                .setupVisuals()
                .setupLayout()
                .setupNested();
        return workTypeTable;
    }

    public void refresh() {
        container.refresh();
        ((TableModel) table.getModel()).fireTableDataChanged();
    }

    private JScrollPane buildWorkTypeTable() {
        table = new JTable(new WorkTypeTableModel(container));
        table.setAutoCreateRowSorter(true);
        table.getTableHeader().setReorderingAllowed(false);
        table.setRowHeight(20);
        return new JScrollPane(table);
    }

    private JPopupMenu buildPopupMenu(AbstractAction addAction) {
        var menu = new JPopupMenu();
        menu.add(addAction);
        return menu;
    }

    private JToolBar buildToolbar(AbstractAction addAction) {
        var toolbar = new JToolBar();
        toolbar.setFloatable(false);
        toolbar.add(addAction);
        return toolbar;
    }

    private WorkTypeTable addFilter(String title, Component inputField) {
        JLabel label = new JLabel(title);
        label.setLabelFor(inputField);
        filterBar.add(label);
        filterBar.add(inputField);
        return this;
    }

    private JToolBar buildToolBar() {
        toolbar = new JToolBar();
        toolbar.setFloatable(false);
        toolbar.setRollover(true);
        addButton = new AddAction(
                table,
                (JTable table, AddAction.Callback callback) -> WorkTypeFormWindow.setup(callback, container),
                () -> ((TableModel) table.getModel()).fireTableDataChanged());
        toolbar.add(
                addButton
        );
        deleteButton = new DeleteAction(table);
        toolbar.add(deleteButton);
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
                    String workTypeNameCurrent = workTypeName.getText();
                    new SwingWorker<Void, Void>() {
                        @Override
                        public Void doInBackground() {
                            container.refresh(workTypeNameCurrent);
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
        workTypeName = new JTextField(100);
        addFilter(i18n.getString("workTypeName"), workTypeName);
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
    public WorkTypeTable setupLayout() {
        return this;
    }

    @Override
    public WorkTypeTable setupVisuals() {
        setLayout(new BorderLayout());
        return this;
    }

    @Override
    public WorkTypeTable setupNested() {
        add(buildWorkTypeTable(), BorderLayout.CENTER);
        add(buildHeader(), BorderLayout.BEFORE_FIRST_LINE);
        return this;
    }
}