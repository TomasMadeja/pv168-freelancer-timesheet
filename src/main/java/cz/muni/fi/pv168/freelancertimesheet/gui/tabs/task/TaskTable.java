package cz.muni.fi.pv168.freelancertimesheet.gui.tabs.task;

import cz.muni.fi.pv168.freelancertimesheet.backend.DBConnectionUtils;
import cz.muni.fi.pv168.freelancertimesheet.backend.interfaces.Work;
import cz.muni.fi.pv168.freelancertimesheet.backend.orm.WorkImpl;
import cz.muni.fi.pv168.freelancertimesheet.gui.GenericElement;
import cz.muni.fi.pv168.freelancertimesheet.gui.containers.WorkContainer;
import cz.muni.fi.pv168.freelancertimesheet.gui.models.TableModel;
import cz.muni.fi.pv168.freelancertimesheet.gui.models.WorkTableModel;

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

    private JPanel createTableButtonPanel() {
        JPanel panel = new JPanel();
        GridLayout layout = new GridLayout(1, 7);
        panel.setLayout(layout);

        var editButton = new JButton("Edit"); // TODO
        var deleteButton = new JButton("Delete"); // TODO

        panel.add(editButton);
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
        loadDataFromDatabase((WorkTableModel) table.getModel());
        return table;
    }

    private void loadDataFromDatabase(TableModel<Work> table) {
        EntityManager entityManager = DBConnectionUtils.getSessionFactory().createEntityManager();
        List<WorkImpl> result = entityManager.createQuery("from WorkImpl").getResultList();
        result.forEach(table::addRow);
    }

    @Override
    public TaskTable setupLayout() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        return this;
    }

    @Override
    public TaskTable setupVisuals() {
        return this;
    }

    @Override
    public TaskTable setupNested() {
        this.add(createTableButtonPanel());

        // table needs to be wrapped in JScrollPane in order to show column names
        this.add(new JScrollPane(createTable()));

        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return this;
    }

    public static TaskTable setup() {
        return new TaskTable()
                .setupLayout()
                .setupVisuals()
                .setupNested();
    }
}
