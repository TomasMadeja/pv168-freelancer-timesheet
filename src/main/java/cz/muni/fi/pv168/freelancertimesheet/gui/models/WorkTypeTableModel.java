package cz.muni.fi.pv168.freelancertimesheet.gui.models;

import cz.muni.fi.pv168.freelancertimesheet.backend.interfaces.WorkType;

import java.math.BigDecimal;

public class WorkTypeTableModel extends TableModel<WorkType> {

    public WorkTypeTableModel() {
        super();
        createColumns();
    }

    private void createColumns() {
        super.addColumn(new Column<String, WorkType>(
                "Name",
                "test1",
                false,
                String.class,
                WorkType.class,
                (Object object) -> ((WorkType) object).getDescription(),
                null
        ));
        super.addColumn(new Column<BigDecimal, WorkType>(
                "Rate",
                "test2",
                false,
                BigDecimal.class,
                WorkType.class,
                (Object object) -> ((WorkType) object).getHourlyRate(),
                null
        ));
        super.addColumn(new Column<String, WorkType>(
                "Description",
                "test3",
                false,
                String.class,
                WorkType.class,
                (Object object) -> ((WorkType) object).getDescription(),
                null
        ));
    }

}
