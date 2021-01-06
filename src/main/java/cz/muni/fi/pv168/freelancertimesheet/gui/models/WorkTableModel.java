package cz.muni.fi.pv168.freelancertimesheet.gui.models;

import cz.muni.fi.pv168.freelancertimesheet.backend.interfaces.Work;
import cz.muni.fi.pv168.freelancertimesheet.gui.I18N;
import cz.muni.fi.pv168.freelancertimesheet.gui.containers.WorkContainer;

import java.time.ZonedDateTime;

public class WorkTableModel extends TableModel<Work> {

    private final I18N i18n = new I18N(getClass());

    public WorkTableModel(WorkContainer container) {
        super(container);
        createColumns();
    }

    private void createColumns() {
        super.addColumn(new Column<String, Work>(
                i18n.getString("name"),
                "test2",
                false,
                String.class,
                Work.class,
                (Object object) -> ((Work) object).getName(),
                null
        ));
        super.addColumn(new Column<String, Work>(
                i18n.getString("description"),
                "test2",
                false,
                String.class,
                Work.class,
                (Object object) -> ((Work) object).getDescription(),
                null
        ));
        super.addColumn(new Column<String, Work>(
                i18n.getString("workType"),
                "test3",
                false,
                String.class,
                Work.class,
                (Object object) -> ((Work) object).getWorkType().getName(),
                null
        ));
        super.addColumn(new Column<ZonedDateTime, Work>(
                i18n.getString("startTime"),
                "test4",
                false,
                ZonedDateTime.class,
                Work.class,
                (Object object) -> ((Work) object).getStartTime(),
                null
        ));
        super.addColumn(new Column<ZonedDateTime, Work>(
                i18n.getString("endTime"),
                "test5",
                false,
                ZonedDateTime.class,
                Work.class,
                (Object object) -> ((Work) object).getEndTime(),
                null
        ));
    }
}
