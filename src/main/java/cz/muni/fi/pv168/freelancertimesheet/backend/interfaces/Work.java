package cz.muni.fi.pv168.freelancertimesheet.backend.interfaces;

import java.time.ZonedDateTime;

public interface Work {

    public String getName();

    public Work setName();

    public Work validateName();

    public String getDescription();

    public Work setDescription();

    public Work validateDescription();

    public ZonedDateTime getStartTime();

    public Work setStartTime(ZonedDateTime datetime);

    public Work validateStartTime(ZonedDateTime datetime);

    public ZonedDateTime getEndTime();

    public Work setEndTime(ZonedDateTime datetime);

    public Work validateEndTime(ZonedDateTime datetime);

    public WorkType getWorkType();

    public Work setWorkType(WorkType workType);

    public Work validateWorkType(WorkType workType);

    public Work createService(
            String name,
            String description,
            ZonedDateTime startTime,
            ZonedDateTime endTime,
            WorkType workType
    );
}
