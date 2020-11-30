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
        super.addColumn(new Column<Boolean, Work>(
                "Select",
                "test1",
                true,
                Boolean.class,
                Work.class,
                (Object object) -> isSelected((Work) object),
                (Object object, Object value) -> changeSelection((Work) object, (Boolean) value)
        ));
        super.addColumn(new Column<String, Work>(
                "Name",
                "test2",
                false,
                String.class,
                Work.class,
                (Object object) -> ((Work) object).getName(),
                null
        ));
        super.addColumn(new Column<String, Work>(
                "Description",
                "test2",
                false,
                String.class,
                Work.class,
                (Object object) -> ((Work) object).getDescription(),
                null
        ));
        super.addColumn(new Column<String, Work>(
                "Work Type",
                "test3",
                false,
                String.class,
                Work.class,
                (Object object) -> ((Work) object).getWorkType().getName(),
                null
        ));
        super.addColumn(new Column<ZonedDateTime, Work>(
                "Start Time",
                "test4",
                false,
                ZonedDateTime.class,
                Work.class,
                (Object object) -> ((Work) object).getStartTime(),
                null
        ));
        super.addColumn(new Column<ZonedDateTime, Work>(
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

    public void changeSelection(Work object, Boolean value) {
        selectedRows.put(object, value);
    }
}
