package cz.muni.fi.pv168.freelancertimesheet.backend.interfaces;

import java.time.ZonedDateTime;

public interface Work {

    public ZonedDateTime getStartTime();

    public Work setStartTime(ZonedDateTime datetime);

    public Work validateStartTime(ZonedDateTime datetime);

    public ZonedDateTime getEndTime();

    public Work setEndTime(ZonedDateTime datetime);

    public Work validateEndTime(ZonedDateTime datetime);

    public WorkType getWorkType();

    public Work setWorkType(WorkType workType);

    public Work validateWorkType(WorkType workType);


}
