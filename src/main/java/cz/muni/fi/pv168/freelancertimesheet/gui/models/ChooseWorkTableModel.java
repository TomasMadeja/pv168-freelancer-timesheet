package cz.muni.fi.pv168.freelancertimesheet.gui.models;

import cz.muni.fi.pv168.freelancertimesheet.backend.interfaces.Work;

import java.time.ZonedDateTime;
import java.util.HashMap;

public class ChooseWorkTableModel extends TableModel<Work> {

    private HashMap<Work, Boolean> selectedRows;

    public ChooseWorkTableModel() {
        super();
        selectedRows = new HashMap<>();
        createColumns();
    }

    private void createColumns() {
        super.addColumn(new Column<>(
                "Select",
                "test1",
                true,
                Boolean.class,
                Work.class,
                (Object object) -> isSelected((Work) object),
                (Object object) -> changeSelection((Work) object)
        ));
        super.addColumn(new Column<>(
                "Name",
                "test2",
                false,
                String.class,
                Work.class,
                (Object object) -> ((Work) object).getName(),
                null
        ));
        super.addColumn(new Column<>(
                "Description",
                "test2",
                false,
                String.class,
                Work.class,
                (Object object) -> ((Work) object).getDescription(),
                null
        ));
        super.addColumn(new Column<>(
                "Work Type",
                "test3",
                false,
                String.class,
                Work.class,
                (Object object) -> ((Work) object).getWorkType().getName(),
                null
        ));
        super.addColumn(new Column<>(
                "Start Time",
                "test4",
                false,
                ZonedDateTime.class,
                Work.class,
                (Object object) -> ((Work) object).getStartTime(),
                null
        ));
        super.addColumn(new Column<>(
                "End Time",
                "test5",
                false,
                ZonedDateTime.class,
                Work.class,
                (Object object) -> ((Work) object).getEndTime(),
                null
        ));
    }

    public boolean isSelected(Work object) {
        return selectedRows.getOrDefault(object, false);
    }

    public void changeSelection(Work object) {
        // toggle boolean value
        selectedRows.put(object, !selectedRows.get(object));
    }
}
