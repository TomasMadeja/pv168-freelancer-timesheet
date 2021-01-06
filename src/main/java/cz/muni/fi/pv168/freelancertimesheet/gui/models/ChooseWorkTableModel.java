package cz.muni.fi.pv168.freelancertimesheet.gui.models;

import cz.muni.fi.pv168.freelancertimesheet.backend.interfaces.Work;
import cz.muni.fi.pv168.freelancertimesheet.gui.I18N;
import cz.muni.fi.pv168.freelancertimesheet.gui.containers.GenericContainer;

import java.time.ZonedDateTime;
import java.util.HashMap;

public class ChooseWorkTableModel extends TableModel<Work> {

    private final I18N i18n = new I18N(getClass());

    private final HashMap<Work, Boolean> selectedRows;

    public ChooseWorkTableModel(GenericContainer container, HashMap<Work, Boolean> selectedRows) {
        super(container);
        this.selectedRows = selectedRows;
        createColumns();
    }

    private void createColumns() {
        super.addColumn(new Column<Boolean, Work>(
                i18n.getString("select"),
                "test1",
                true,
                Boolean.class,
                Work.class,
                (Object object) -> isSelected((Work) object),
                (Object object, Object value) -> changeSelection((Work) object, (Boolean) value)
        ));
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

    public boolean isSelected(Work object) {
        return selectedRows.getOrDefault(object, false);
    }

    public void changeSelection(Work object, Boolean value) {
        selectedRows.put(object, value);
    }
}
