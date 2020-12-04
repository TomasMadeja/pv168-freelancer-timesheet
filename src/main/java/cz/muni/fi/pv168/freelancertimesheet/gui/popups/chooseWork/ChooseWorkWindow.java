package cz.muni.fi.pv168.freelancertimesheet.gui.popups.chooseWork;

import cz.muni.fi.pv168.freelancertimesheet.backend.DBConnectionUtils;
import cz.muni.fi.pv168.freelancertimesheet.backend.interfaces.Work;
import cz.muni.fi.pv168.freelancertimesheet.backend.interfaces.WorkType;
import cz.muni.fi.pv168.freelancertimesheet.backend.orm.WorkImpl;
import cz.muni.fi.pv168.freelancertimesheet.backend.orm.WorkTypeImpl;
import cz.muni.fi.pv168.freelancertimesheet.gui.GenericElement;
import cz.muni.fi.pv168.freelancertimesheet.gui.containers.WorkContainer;
import cz.muni.fi.pv168.freelancertimesheet.gui.models.ChooseWorkTableModel;
import cz.muni.fi.pv168.freelancertimesheet.gui.models.TableModel;

import javax.persistence.EntityManager;
import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

public class ChooseWorkWindow extends JFrame implements GenericElement<ChooseWorkWindow> {

    GridBagConstraints gbc;
    Function<List<Work>, Object> updateCallback;

    private final HashMap<Work, Boolean> selectedRows;

    public ChooseWorkWindow(Function<List<Work>, Object> updateCallback) {
        super("Choose Work");
        this.updateCallback = updateCallback;
        selectedRows = new HashMap<>();
    }


    private JTable createTable() {
        var model = new ChooseWorkTableModel(new WorkContainer(), selectedRows);
        JTable table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

//        createTmpDataInDb();
//        RandomDataGenerator.generateWorkData((ChooseWorkTableModel) table.getModel());

        loadDataFromDatabase((ChooseWorkTableModel) table.getModel());

        return table;
    }

    private void loadDataFromDatabase(TableModel<Work> table) {
        EntityManager entityManager = DBConnectionUtils.getSessionFactory().createEntityManager();
        List<WorkImpl> result = entityManager.createQuery("from WorkImpl").getResultList();
        result.forEach(table::addRow);
    }

    private JButton confirmSelectionButton(Function<List<Work>, Object> updateWorkSelection) {
        var button = new JButton();
        button.setText("Confirm Selection");

        // TODO modify so the invoice tab can get this info
        button.addActionListener(e -> {
            System.out.println("Printing selected rows (invoice choose work tab):");
            List<Work> selectedWorks = new ArrayList<>();
            selectedRows.forEach((workRow, isSelected) -> {
                if (isSelected) {
                    System.out.println(workRow);
                    selectedWorks.add(workRow);
                }
            });
            updateWorkSelection.apply(selectedWorks);
            this.dispose();
        });

        return button;
    }

    private GridBagConstraints getGbc(int x, int y, int height, int width, double weightY) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridheight = height;
        gbc.gridwidth = width;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = weightY;

        return gbc;
    }

    @Override
    public ChooseWorkWindow setupLayout() {
        GridBagLayout gbl = new GridBagLayout();
        this.setLayout(gbl);
        this.setSize(new Dimension(1000, 500)); // TODO
        return this;
    }

    @Override
    public ChooseWorkWindow setupVisuals() {
        return this;
    }


    @Override
    public ChooseWorkWindow setupNested() {
        var table = new JScrollPane(createTable());
        var confirmButton = confirmSelectionButton(updateCallback);

        gbc = getGbc(0, 0, 3, 5, 0.5);
        gbc.fill = GridBagConstraints.BOTH;
        this.add(table, gbc);
        gbc = getGbc(3, 4, 1, 1, 0.3);
        gbc.fill = GridBagConstraints.NONE;
        this.add(confirmButton, gbc);

        return this;
    }

    public static ChooseWorkWindow setup(Function<List<Work>, Object> updateCallback) {
        ChooseWorkWindow window = new ChooseWorkWindow(updateCallback)
                .setupLayout()
                .setupVisuals()
                .setupNested();
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        return window;
    }


    // create temporary data in database for testing
    private void createTmpDataInDb() {
        List<Work> records = prepareWork();
        var entityManager = DBConnectionUtils.getSessionFactory().createEntityManager();
        entityManager.getTransaction().begin();
        for (Work record : records) {
            entityManager.persist(record);
        }
        entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.clear();
    }

    private List<WorkType> prepareWorkTypes() {

        List<WorkType> collection = new ArrayList<>();
        collection.add(WorkTypeImpl.createWorkType(
                "TestType1",
                "Never gonna give you up",
                new BigDecimal("20")
        ));
        collection.add(WorkTypeImpl.createWorkType(
                "TestType2",
                "Never gonna let you down",
                new BigDecimal("30")
        ));
        collection.add(WorkTypeImpl.createWorkType(
                "TestType3",
                "Forgot the rest",
                new BigDecimal("40")
        ));
        return collection;
    }

    private List<Work> prepareWork() {
        WorkType[] workTypes = prepareWorkTypes().toArray(WorkType[]::new);
        List<Work> collection = new ArrayList<>();
        collection.add(WorkImpl.createWork(
                "TestWork1",
                "Beepboop",
                ZonedDateTime.parse("2011-12-03T10:15:30+01:00"),
                ZonedDateTime.parse("2011-12-04T10:15:30+01:00"),
                workTypes[0]
        ));
        collection.add(WorkImpl.createWork(
                "TestWork1",
                "Beepboop",
                ZonedDateTime.parse("2011-12-05T10:15:30+01:00"),
                ZonedDateTime.parse("2011-12-06T10:15:30+01:00"),
                workTypes[1]
        ));
        collection.add(WorkImpl.createWork(
                "TestWork1",
                "Beepboop",
                ZonedDateTime.parse("2011-12-07T10:15:30+01:00"),
                ZonedDateTime.parse("2011-12-08T10:15:30+01:00"),
                workTypes[2]
        ));
        return collection;
    }
}
