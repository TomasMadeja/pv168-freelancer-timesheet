package cz.muni.fi.pv168.freelancertimesheet.gui.tabs.task;

import cz.muni.fi.pv168.freelancertimesheet.backend.DBConnectionUtils;
import cz.muni.fi.pv168.freelancertimesheet.backend.interfaces.Work;
import cz.muni.fi.pv168.freelancertimesheet.backend.orm.WorkImpl;
import cz.muni.fi.pv168.freelancertimesheet.gui.GenericElement;
import cz.muni.fi.pv168.freelancertimesheet.gui.actions.table.AddAction;
import cz.muni.fi.pv168.freelancertimesheet.gui.actions.table.DeleteAction;
import cz.muni.fi.pv168.freelancertimesheet.gui.containers.WorkContainer;
import cz.muni.fi.pv168.freelancertimesheet.gui.models.TableModel;
import cz.muni.fi.pv168.freelancertimesheet.gui.models.WorkTableModel;
import cz.muni.fi.pv168.freelancertimesheet.gui.popups.workform.WorkFormWindow;
import cz.muni.fi.pv168.freelancertimesheet.gui.popups.worktype.form.WorkTypeFormWindow;

import javax.persistence.EntityManager;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TaskTable extends JPanel implements GenericElement<TaskTable> {

    private JTable table;
    private final WorkContainer container;
    private final WorkTableModel model;

    public int GetSelectedTasksCount() {
        return table.getSelectedRows().length;
    }

    public TaskTable() {
        super();
        container = new WorkContainer();
        model = new WorkTableModel(container);
    }

    private JToolBar createTableButtonPanel() {
        JToolBar panel = new JToolBar();
        GridLayout layout = new GridLayout(1, 7);
        panel.setLayout(layout);

        var addButton = new AddAction(
                table,
                (JTable table, AddAction.Callback callback) -> WorkFormWindow.setup(callback, container),
                () -> {
                    ((TableModel) table.getModel()).fireTableDataChanged();
                }
        );
//        var editButton = new JButton("Edit"); // TODO
        var deleteButton = new DeleteAction(table); // TODO

        panel.add(addButton);
//        panel.add(editButton);
        panel.add(deleteButton);

        var searchLabel = new JLabel("Search:", SwingConstants.CENTER);
        panel.add(searchLabel);
        panel.add(new JTextField());

        panel.add(new JLabel());
        panel.add(new JLabel());

        return panel;
    }


    private JTable createTable() {
        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
//        loadDataFromDatabase((WorkTableModel) table.getModel());
        return table;
    }

    private void loadDataFromDatabase(TableModel<Work> table) {
        EntityManager entityManager = DBConnectionUtils.getSessionFactory().createEntityManager();
        List<WorkImpl> result = entityManager.createQuery("from WorkImpl").getResultList();
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
        this.add(createTableButtonPanel(), BorderLayout.BEFORE_FIRST_LINE);

        // table needs to be wrapped in JScrollPane in order to show column names
        this.add(new JScrollPane(createTable()), BorderLayout.CENTER);
        return this;
    }

    public static TaskTable setup() {
        return new TaskTable()
                .setupLayout()
                .setupVisuals()
                .setupNested();
    }
}
