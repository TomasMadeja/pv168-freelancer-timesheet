package cz.muni.fi.pv168.freelancertimesheet.backend.interfaces;

import java.time.ZonedDateTime;

public interface Work {

    public ZonedDateTime getStartTime();

    public Work setStartTime(ZonedDateTime datetime);

    public Work validateStartTime(ZonedDateTime datetime);

    public ZonedDateTime getEndTime();

    public Work setEndTime(ZonedDateTime datetime);

    public Work validateEndTime(ZonedDateTime datetime);

    public WorkType getServiceType();

    public Work setServiceType(WorkType workType);

    public Work validateServiceType(WorkType workType);

    public Work createService(
            ZonedDateTime startTime,
            ZonedDateTime endTime,
            WorkType workType
    );
}
