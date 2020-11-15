package cz.muni.fi.pv168.freelancertimesheet.gui.models;

import cz.muni.fi.pv168.freelancertimesheet.backend.interfaces.ServiceType;
import cz.muni.fi.pv168.freelancertimesheet.gui.exampledata.WorkType;

import java.util.List;

public class ServiceTypeTableModel extends TableModel<ServiceType> {

    public ServiceTypeTableModel(List<WorkType> workTypes) {
        super(new Column[]{
                new Column(
                        "Name",
                        "test1",
                        String.class,
                        ServiceType.class,
                        (Object object) -> ((ServiceType)object).getDescription()
                ),
                new Column(
                        "Rate",
                        "test2",
                        Double.class,
                        ServiceType.class,
                        (Object object) -> ((ServiceType)object).getHourlyRate()
                ),
                new Column(
                        "Description",
                        "test3",
                        String.class,
                        ServiceType.class,
                        (Object object) -> ((ServiceType)object).getDescription()
                )
        });
    }
}
