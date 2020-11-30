package cz.muni.fi.pv168.freelancertimesheet.gui.models;

import cz.muni.fi.pv168.freelancertimesheet.backend.interfaces.Work;

import java.time.ZonedDateTime;

public class WorkTableModel extends TableModel<Work> {

    public WorkTableModel() {
        super();
        createColumns();
    }

    private void createColumns() {
        super.addColumn(new Column<>(
                "Name",
                "test2",
                String.class,
                Work.class,
                (Object object) -> ((Work) object).getName(),
                null
        ));
        super.addColumn(new Column<>(
                "Description",
                "test2",
                String.class,
                Work.class,
                (Object object) -> ((Work) object).getDescription(),
                null
        ));
        super.addColumn(new Column<>(
                "Work Type",
                "test3",
                String.class,
                Work.class,
                (Object object) -> ((Work) object).getWorkType().getName(),
                null
        ));
        super.addColumn(new Column<>(
                "Start Time",
                "test4",
                ZonedDateTime.class,
                Work.class,
                (Object object) -> ((Work) object).getStartTime(),
                null
        ));
        super.addColumn(new Column<>(
                "End Time",
                "test5",
                ZonedDateTime.class,
                Work.class,
                (Object object) -> ((Work) object).getEndTime(),
                null
        ));
    }
}
