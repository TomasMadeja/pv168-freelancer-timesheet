package cz.muni.fi.pv168.freelancertimesheet.backend.interfaces;

import java.math.BigDecimal;

public interface ServiceType {

    public String getName();

    public String setName();

    public String getDescription();

    public ServiceType setDescription(String description);

    public ServiceType validateDescription(String description);

    public BigDecimal getHourlyRate();

    public ServiceType setHourlyRate(BigDecimal hourlyRate);

    public ServiceType setHourlyRate(String hourlyRate);

    public ServiceType validateHourlyRate(BigDecimal hourlyRate);

    public ServiceType validateHourlyRate(String hourlyRate);

    public ServiceType createServiceType(
            String name,
            String description,
            BigDecimal hourlyRate
    );

    public ServiceType createServiceType(
            String name,
            String description,
            String hourlyRate
    );
}
