package cz.muni.fi.pv168.freelancertimesheet.gui.models;

import cz.muni.fi.pv168.freelancertimesheet.backend.interfaces.Service;

import java.time.ZonedDateTime;
import java.util.Date;

public class ServiceTableModel extends TableModel<Service> {

    public ServiceTableModel() {
        super(new Column[] {
                new Column(
                        "Name",
                        "test2",
                        String.class,
                        Service.class,
                        (Object object) -> ((Service)object).getName()
                ),
                new Column(
                        "Description",
                        "test2",
                        String.class,
                        Service.class,
                        (Object object) -> ((Service)object).getDescription()
                ),
                new Column(
                        "Service Type",
                        "test3",
                        String.class,
                        Service.class,
                        (Object object) -> ((Service)object).getServiceType()
                ),
                new Column(
                        "Start Time",
                        "test4",
                        ZonedDateTime.class,
                        Service.class,
                        (Object object) -> ((Service)object).getStartTime()
                ),
                new Column(
                        "End Time",
                        "test5",
                        ZonedDateTime.class,
                        Service.class,
                        (Object object) -> ((Service)object).getEndTime()
                )
        });
    }
}
