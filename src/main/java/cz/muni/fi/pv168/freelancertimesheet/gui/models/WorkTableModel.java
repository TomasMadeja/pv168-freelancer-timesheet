package cz.muni.fi.pv168.freelancertimesheet.gui.models;

import cz.muni.fi.pv168.freelancertimesheet.backend.interfaces.Work;
import cz.muni.fi.pv168.freelancertimesheet.gui.containers.WorkContainer;

import java.time.ZonedDateTime;

public class WorkTableModel extends TableModel<Work> {

    public WorkTableModel(WorkContainer container) {
        super(container);
        createColumns();
    }

    private void createColumns() {
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
}
