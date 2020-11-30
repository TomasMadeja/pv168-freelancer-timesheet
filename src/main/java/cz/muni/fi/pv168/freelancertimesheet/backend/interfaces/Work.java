package cz.muni.fi.pv168.freelancertimesheet.backend.interfaces;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

public interface Work extends Comparable {

    public String getName();

    public Work setName(String name);

    public Work validateName(String name);

    public String getDescription();

    public Work setDescription(String description);

    public Work validateDescription(String description);

    public ZonedDateTime getStartTime();

    public Work setStartTime(ZonedDateTime datetime);

    public Work validateStartTime(ZonedDateTime datetime);

    public ZonedDateTime getEndTime();

    public Work setEndTime(ZonedDateTime datetime);

    public Work validateEndTime(ZonedDateTime datetime);

    public WorkType getWorkType();

    public Work setWorkType(WorkType workType);

    public Work validateWorkType(WorkType workType);

    public String toXML();

    public BigDecimal getCost();

    public BigDecimal getHours();
}
