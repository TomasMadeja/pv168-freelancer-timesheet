package cz.muni.fi.pv168.freelancertimesheet.backend.interfaces;

import java.math.BigDecimal;

public interface WorkType {

    public String getDescription();

    public WorkType setDescription(String description);

    public WorkType validateDescription(String description);

    public BigDecimal getHourlyRate();

    public WorkType setHourlyRate(BigDecimal hourlyRate);

    public WorkType setHourlyRate(String hourlyRate);

    public WorkType validateHourlyRate(BigDecimal hourlyRate);

    public WorkType validateHourlyRate(String hourlyRate);

    public WorkType createServiceType(
            String description,
            BigDecimal hourlyRate
    );

    public WorkType createServiceType(
            String description,
            String hourlyRate
    );
}
