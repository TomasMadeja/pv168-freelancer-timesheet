package cz.muni.fi.pv168.freelancertimesheet.gui.models;

import cz.muni.fi.pv168.freelancertimesheet.backend.interfaces.WorkType;
import cz.muni.fi.pv168.freelancertimesheet.gui.I18N;
import cz.muni.fi.pv168.freelancertimesheet.gui.containers.WorkTypeContainer;

import java.math.BigDecimal;

public class WorkTypeTableModel extends TableModel<WorkType> {

    private final I18N i18n = new I18N(getClass());

    public WorkTypeTableModel(WorkTypeContainer container) {
        super(container);
        createColumns();
    }

    private void createColumns() {
        super.addColumn(new Column<String, WorkType>(
                i18n.getString("name"),
                "test1",
                false,
                String.class,
                WorkType.class,
                (Object object) -> ((WorkType) object).getName(),
                null
        ));
        super.addColumn(new Column<BigDecimal, WorkType>(
                i18n.getString("rate"),
                "test2",
                false,
                BigDecimal.class,
                WorkType.class,
                (Object object) -> ((WorkType) object).getHourlyRate(),
                null
        ));
        super.addColumn(new Column<String, WorkType>(
                i18n.getString("description"),
                "test3",
                false,
                String.class,
                WorkType.class,
                (Object object) -> ((WorkType) object).getDescription(),
                null
        ));
    }

}
