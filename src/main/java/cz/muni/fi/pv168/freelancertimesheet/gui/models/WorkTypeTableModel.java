package cz.muni.fi.pv168.freelancertimesheet.gui.models;

import cz.muni.fi.pv168.freelancertimesheet.backend.interfaces.WorkType;

public class WorkTypeTableModel extends TableModel<WorkType> {

    public WorkTypeTableModel() {
        super(new Column[]{
                new Column(
                        "Name",
                        "test1",
                        String.class,
                        WorkType.class,
                        (Object object) -> ((WorkType) object).getDescription(),
                        null
                ),
                new Column(
                        "Rate",
                        "test2",
                        Double.class,
                        WorkType.class,
                        (Object object) -> ((WorkType) object).getHourlyRate(),
                        null
                ),
                new Column(
                        "Description",
                        "test3",
                        String.class,
                        WorkType.class,
                        (Object object) -> ((WorkType) object).getDescription(),
                        null
                )
        });
    }
}
