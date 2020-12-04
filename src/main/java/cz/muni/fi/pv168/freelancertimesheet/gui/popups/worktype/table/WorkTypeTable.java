package cz.muni.fi.pv168.freelancertimesheet.gui.popups.worktype.table;

import cz.muni.fi.pv168.freelancertimesheet.gui.actions.table.AddAction;
import cz.muni.fi.pv168.freelancertimesheet.gui.containers.WorkTypeContainer;
import cz.muni.fi.pv168.freelancertimesheet.gui.models.TableModel;
import cz.muni.fi.pv168.freelancertimesheet.gui.models.WorkTypeTableModel;
import cz.muni.fi.pv168.freelancertimesheet.gui.popups.worktype.form.WorkTypeFormWindow;
import cz.muni.fi.pv168.freelancertimesheet.gui.popups.worktype.form.WorkTypeForm;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;

public class WorkTypeTable {
    private final JPanel panel;
    private final JFrame frame;

    private final Action addAction;
    private final Action selectAction;

    private JTable workTypeTable;

    private final WorkTypeContainer container;

    public WorkTypeTable(WorkTypeForm workTypeForm, JFrame frame) {
        container = new WorkTypeContainer();
        this.frame = frame;
        panel = createPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        workTypeTable = createWorkTypeTable();
        addAction = new AddAction(
                workTypeTable,
                (JTable table, AddAction.Callback callback) -> WorkTypeFormWindow.setup(callback),
                () -> {
                    refresh();
                }
        );
        selectAction = new SelectAction(frame);
        updateUsabilityOfButtons(0);
        workTypeTable.setComponentPopupMenu(createEmployeeTablePopupMenu());
        panel.add(createToolbar());
        panel.add(new JScrollPane(workTypeTable));
    }

    public void refresh() {
        container.refresh();
        ((TableModel) workTypeTable.getModel()).fireTableDataChanged();
    }

    public void show() {
        frame.setVisible(true);
    }

    private JPanel createPanel() {
        return new JPanel();
    }

    private JTable createWorkTypeTable() {
        var model = new WorkTypeTableModel(container);
        var table = new JTable(model);
        table.setAutoCreateRowSorter(true);
        table.getSelectionModel().addListSelectionListener(this::rowSelectionChanged);
        table.setRowHeight(20);
        return table;
    }

    private JPopupMenu createEmployeeTablePopupMenu() {
        var menu = new JPopupMenu();
        menu.add(selectAction);
        menu.addSeparator();
        return menu;
    }

    private JMenuBar createMenuBar() {
        var menuBar = new JMenuBar();
        var editMenu = new JMenu("Edit");
        editMenu.setMnemonic('e');
        editMenu.add(selectAction);
        editMenu.addSeparator();
        editMenu.add(addAction);
        menuBar.add(editMenu);
        return menuBar;
    }

    private JToolBar createToolbar() {
        var toolbar = new JToolBar();
        toolbar.setFloatable(false);
        toolbar.add(selectAction);
        toolbar.addSeparator();
        toolbar.add(addAction);
        return toolbar;
    }

    private void rowSelectionChanged(ListSelectionEvent listSelectionEvent) {
        var selectionModel = (ListSelectionModel) listSelectionEvent.getSource();
        int rows = selectionModel.getSelectedItemsCount();
        updateUsabilityOfButtons(rows);
    }

    private void updateUsabilityOfButtons(int rows) {
        selectAction.setEnabled(rows == 1);
    }

    public static JPanel setup(WorkTypeForm workTypeForm, JFrame frame) {
        return new WorkTypeTable(workTypeForm, frame).panel;
    }
}