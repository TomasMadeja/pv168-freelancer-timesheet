package cz.muni.fi.pv168.freelancertimesheet.gui.models;

import cz.muni.fi.pv168.freelancertimesheet.backend.interfaces.Work;

import java.time.ZonedDateTime;

public class ChooseWorkTableModel extends TableModel<Work> {

    public ChooseWorkTableModel() {
        super(new Column[]{
                new Column(
                        "Select",
                        "test1",
                        Boolean.class,
                        Work.class,
                        (Object object) -> false
                ),
                new Column(
                        "Name",
                        "test2",
                        String.class,
                        Work.class,
                        (Object object) -> ((Work) object).getName()
                ),
                new Column(
                        "Description",
                        "test2",
                        String.class,
                        Work.class,
                        (Object object) -> ((Work) object).getDescription()
                ),
                new Column(
                        "Work Type",
                        "test3",
                        String.class,
                        Work.class,
                        (Object object) -> ((Work) object).getWorkType().getName()
                ),
                new Column(
                        "Start Time",
                        "test4",
                        ZonedDateTime.class,
                        Work.class,
                        (Object object) -> ((Work) object).getStartTime()
                ),
                new Column(
                        "End Time",
                        "test5",
                        ZonedDateTime.class,
                        Work.class,
                        (Object object) -> ((Work) object).getEndTime()
                )
        });
    }
}
