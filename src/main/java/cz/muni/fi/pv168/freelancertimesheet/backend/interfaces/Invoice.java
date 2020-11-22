package cz.muni.fi.pv168.freelancertimesheet.backend.interfaces;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

public interface Invoice {

    public Entity getClient();

    public Invoice setClient(Entity client);

    public Invoice validateClient(Entity client);

    public Entity getIssuer();

    public Invoice setIssuer(Entity issuer);

    public Invoice validateIssuer(Entity issuer);

    public ZonedDateTime getIssueDate();

    public Invoice setIssueDate(ZonedDateTime datetime);

    public Invoice validateIssueDate(ZonedDateTime datetime);

    public ZonedDateTime getDueDate();

    public Invoice setDueDate(ZonedDateTime datetime);

    public Invoice validateDueDate(ZonedDateTime datetime);

    public List<Work> getServices();

    public Invoice setServices(List<Work> works);

    public Invoice addService(Work work);

    public Invoice validateServices(List<Work> works);

    public Invoice validateService(Work work);

    public BigDecimal getTotalAmount();

    public Invoice createInvoice(
            Entity Client,
            Entity Issuer,
            ZonedDateTime issueDate,
            ZonedDateTime dueDate,
            List<Work> works
    );
}
