package cz.muni.fi.pv168.freelancertimesheet.backend.interfaces;

import java.time.ZonedDateTime;

public interface Service {

    public ZonedDateTime getStartTime();

    public Service setStartTime(ZonedDateTime datetime);

    public Service validateStartTime(ZonedDateTime datetime);

    public ZonedDateTime getEndTime();

    public Service setEndTime(ZonedDateTime datetime);

    public Service validateEndTime(ZonedDateTime datetime);

    public ServiceType getServiceType();

    public Service setServiceType(ServiceType serviceType);

    public Service validateServiceType(ServiceType serviceType);

    public Service createService(
            ZonedDateTime startTime,
            ZonedDateTime endTime,
            ServiceType serviceType
    );
}
