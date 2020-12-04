package cz.muni.fi.pv168.freelancertimesheet.gui.popups.worktype.table;

import cz.muni.fi.pv168.freelancertimesheet.gui.GenericElement;
import cz.muni.fi.pv168.freelancertimesheet.gui.actions.table.AddAction;
import cz.muni.fi.pv168.freelancertimesheet.gui.containers.WorkTypeContainer;
import cz.muni.fi.pv168.freelancertimesheet.gui.models.TableModel;
import cz.muni.fi.pv168.freelancertimesheet.gui.models.WorkTypeTableModel;
import cz.muni.fi.pv168.freelancertimesheet.gui.popups.worktype.form.WorkTypeFormWindow;

import javax.swing.*;
import java.awt.*;

public class WorkTypeTable extends JPanel implements GenericElement<WorkTypeTable> {
    private final WorkTypeContainer container;
    private JTable workTypeTable;

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
        ((TableModel) workTypeTable.getModel()).fireTableDataChanged();
    }

    private JScrollPane buildWorkTypeTable() {
        workTypeTable = new JTable(new WorkTypeTableModel(container));
        workTypeTable.setAutoCreateRowSorter(true);
        workTypeTable.setRowHeight(20);
        return new JScrollPane(workTypeTable);
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
        var addAction = new AddAction(
                workTypeTable,
                (JTable table, AddAction.Callback callback) -> WorkTypeFormWindow.setup(callback),
                () -> {
                    refresh();
                }
        );
        add(buildToolbar(addAction), BorderLayout.BEFORE_FIRST_LINE);
        workTypeTable.setComponentPopupMenu(buildPopupMenu(addAction));
        return this;
    }
}