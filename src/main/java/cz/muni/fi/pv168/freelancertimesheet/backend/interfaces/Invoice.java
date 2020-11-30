package cz.muni.fi.pv168.freelancertimesheet.backend.interfaces;

import cz.muni.fi.pv168.freelancertimesheet.backend.orm.IssuerImpl;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

public interface Invoice {

    public Client getClient();

    public Invoice setClient(Client client);

    public Invoice validateClient(Client client);

    public Issuer getIssuer();

    public Invoice setIssuer(Issuer issuer);

    public Invoice validateIssuer(Entity issuer);

    public ZonedDateTime getIssueDate();

    public Invoice setIssueDate(ZonedDateTime datetime);

    public Invoice validateIssueDate(ZonedDateTime datetime);

    public ZonedDateTime getDueDate();

    public Invoice setDueDate(ZonedDateTime datetime);

    public Invoice validateDueDate(ZonedDateTime datetime);

    public List<? extends Work> getWorks();

    public Invoice setWorks(List<? extends Work> works);

    public Invoice addWork(Work work);

    public Invoice validateWorks(List<? extends Work> works);

    public Invoice validateWork(Work work);

    public BigDecimal getTotalAmount();

    public String getPdfPath();

    public Invoice setPdfPath(String pdfPath);

    public static Invoice createInvoice(
            Entity Client,
            Entity Issuer,
            ZonedDateTime issueDate,
            ZonedDateTime dueDate,
            List<Work> works
    ) {
        return null;
    }
}
