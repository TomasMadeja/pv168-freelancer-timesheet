package cz.muni.fi.pv168.freelancertimesheet.backend.interfaces;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

public interface IncomeCalculator {

    public BigDecimal calculateIncome(ZonedDateTime dateFrom, ZonedDateTime dateTo);
}
