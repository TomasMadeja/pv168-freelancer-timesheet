package cz.muni.fi.pv168.freelancertimesheet.gui.popups.worktype.table;

import cz.muni.fi.pv168.freelancertimesheet.gui.exampledata.WorkType;
import cz.muni.fi.pv168.freelancertimesheet.gui.models.WorkTypeTableModel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;
import java.util.List;

public class WorkTypeTableWindow {
    private JFrame frame;

    private Action addAction;
    private Action deleteAction;
    private Action editAction;
    private Action selectAction;

    public WorkTypeTableWindow() throws NoSuchMethodException {
        frame = createFrame();
        JTable workTypeTable = createWorkTypeTable(WorkType.getExampleData());
        addAction = new AddAction(workTypeTable);
        deleteAction = new DeleteAction(workTypeTable);
        editAction = new EditAction(workTypeTable);
        selectAction = new SelectAction(frame);
        updateUsabilityOfButtons(0);
        workTypeTable.setComponentPopupMenu(createEmployeeTablePopupMenu());
        frame.add(new JScrollPane(workTypeTable), BorderLayout.CENTER);
        frame.add(createToolbar(), BorderLayout.BEFORE_FIRST_LINE);
        frame.setJMenuBar(createMenuBar());
        frame.pack();
    }

    public void show() {
        frame.setVisible(true);
    }

    private JFrame createFrame() {
        var frame = new JFrame("Employee evidence");
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        return frame;
    }

    private JTable createWorkTypeTable(List<WorkType> workTypes) throws NoSuchMethodException {
        var model = new WorkTypeTableModel(workTypes);
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
}