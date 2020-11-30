package cz.muni.fi.pv168.freelancertimesheet.gui.models;

import cz.muni.fi.pv168.freelancertimesheet.backend.interfaces.WorkType;

import java.math.BigDecimal;

public class WorkTypeTableModel extends TableModel<WorkType> {

    public WorkTypeTableModel() {
        super();
        createColumns();
    }

    private void createColumns() {
        super.addColumn(new Column<>(
                "Name",
                "test1",
                String.class,
                WorkType.class,
                (Object object) -> ((WorkType) object).getDescription(),
                null
        ));
        super.addColumn(new Column<>(
                "Rate",
                "test2",
                BigDecimal.class,
                WorkType.class,
                (Object object) -> ((WorkType) object).getHourlyRate(),
                null
        ));
        super.addColumn(new Column<>(
                "Description",
                "test3",
                String.class,
                WorkType.class,
                (Object object) -> ((WorkType) object).getDescription(),
                null
        ));
    }

}
