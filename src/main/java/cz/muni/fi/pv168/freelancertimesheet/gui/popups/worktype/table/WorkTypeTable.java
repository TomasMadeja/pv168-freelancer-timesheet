package cz.muni.fi.pv168.freelancertimesheet.gui.popups.worktype.table;

import cz.muni.fi.pv168.freelancertimesheet.gui.exampledata.WorkType;
import cz.muni.fi.pv168.freelancertimesheet.gui.models.ServiceTypeTableModel;
import cz.muni.fi.pv168.freelancertimesheet.gui.popups.worktype.forms.WorkTypeForm;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.util.List;

public class WorkTypeTable {
    private final JPanel panel;
    private final JFrame frame;

    private final Action addAction;
    private final Action deleteAction;
    private final Action editAction;
    private final Action selectAction;

    public WorkTypeTable(WorkTypeForm workTypeForm, JFrame frame) {
        this.frame = frame;
        panel = createPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        JTable workTypeTable = createWorkTypeTable(WorkType.getExampleData());
        addAction = new AddAction(workTypeTable, workTypeForm);
        deleteAction = new DeleteAction(workTypeTable);
        editAction = new EditAction(workTypeTable, workTypeForm);
        selectAction = new SelectAction(frame);
        updateUsabilityOfButtons(0);
        workTypeTable.setComponentPopupMenu(createEmployeeTablePopupMenu());
        panel.add(createToolbar());
        panel.add(new JScrollPane(workTypeTable));
    }

    public void show() {
        frame.setVisible(true);
    }

    private JPanel createPanel() {
        return new JPanel();
    }

    private JTable createWorkTypeTable(List<WorkType> workTypes) {
        var model = new ServiceTypeTableModel(workTypes);
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
        menu.add(deleteAction);
        menu.add(editAction);
        return menu;
    }

    private JMenuBar createMenuBar() {
        var menuBar = new JMenuBar();
        var editMenu = new JMenu("Edit");
        editMenu.setMnemonic('e');
        editMenu.add(selectAction);
        editMenu.addSeparator();
        editMenu.add(addAction);
        editMenu.add(editAction);
        editMenu.add(deleteAction);
        menuBar.add(editMenu);
        return menuBar;
    }

    private JToolBar createToolbar() {
        var toolbar = new JToolBar();
        toolbar.setFloatable(false);
        toolbar.add(selectAction);
        toolbar.addSeparator();
        toolbar.add(addAction);
        toolbar.add(editAction);
        toolbar.add(deleteAction);
        return toolbar;
    }

    private void rowSelectionChanged(ListSelectionEvent listSelectionEvent) {
        var selectionModel = (ListSelectionModel) listSelectionEvent.getSource();
        int rows = selectionModel.getSelectedItemsCount();
        updateUsabilityOfButtons(rows);
    }

    private void updateUsabilityOfButtons(int rows) {
        editAction.setEnabled(rows == 1);
        selectAction.setEnabled(rows == 1);
        deleteAction.setEnabled(rows >= 1);
    }

    public static JPanel setup(WorkTypeForm workTypeForm, JFrame frame) {
        return new WorkTypeTable(workTypeForm, frame).panel;
    }
}