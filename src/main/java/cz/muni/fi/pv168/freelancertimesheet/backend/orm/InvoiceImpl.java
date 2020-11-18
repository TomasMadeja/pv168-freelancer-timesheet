package cz.muni.fi.pv168.freelancertimesheet.backend.orm;

import cz.muni.fi.pv168.freelancertimesheet.backend.interfaces.Entity;
import cz.muni.fi.pv168.freelancertimesheet.backend.interfaces.Invoice;
import cz.muni.fi.pv168.freelancertimesheet.backend.interfaces.Work;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

@javax.persistence.Entity
@Table(name="invoices")
public class InvoiceImpl implements Invoice {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Column(name = "issue_date", nullable=false)
    private ZonedDateTime issueDate;

    @Column(name = "due_date", nullable=false)
    private ZonedDateTime dueDate;

//    @OneToMany
//    @JoinTable(
//            name="invoice_works",
//            joinColumns = {@JoinColumn( name="invoice_id")},
//            inverseJoinColumns = {@JoinColumn( name="work_id")}
//    )
    @Transient
    private List<WorkImpl> works;

    @Transient
    private Entity issuer;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable=false)
    private ClientImpl client;

    @Column(name = "total_amount", nullable=false)
    private BigDecimal totalAmount;

    @Override
    public Entity getClient() {
        return client;
    }

    @Override
    public Invoice setClient(Entity client) {
        this.client = (ClientImpl) client;
        return this;
    }

    @Override
    public Invoice validateClient(Entity client) {
        return this;
    }

    @Override
    public Entity getIssuer() {
        return issuer;
    }

    @Override
    public Invoice setIssuer(Entity issuer) {
        this.issuer = issuer;
        return this;
    }

    @Override
    public Invoice validateIssuer(Entity issuer) {
        return this;
    }

    @Override
    public ZonedDateTime getIssueDate() {
        return issueDate;
    }

    @Override
    public Invoice setIssueDate(ZonedDateTime datetime) {
        this.issueDate = datetime;
        return this;
    }

    @Override
    public Invoice validateIssueDate(ZonedDateTime datetime) {
        return this;
    }

    @Override
    public ZonedDateTime getDueDate() {
        return dueDate;
    }

    @Override
    public Invoice setDueDate(ZonedDateTime datetime) {
        this.dueDate = datetime;
        return this;
    }

    @Override
    public Invoice validateDueDate(ZonedDateTime datetime) {
        return this;
    }

    @Override
    public List<? extends Work> getWorks() {
        return works;
    }

    @Override
    public Invoice setWorks(List<? extends Work> works) {
        for (var work: works) {
            addWork(work);
        }
        return this;
    }

    @Override
    public Invoice addWork(Work work) {
        this.works.add((WorkImpl) work);
        return this;
    }

    @Override
    public Invoice validateWorks(List<? extends Work> works) {
        return this;
    }

    @Override
    public Invoice validateWork(Work work) {
        return this;
    }

    @Override
    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    @Override
    public Invoice createInvoice(Entity Client, Entity Issuer, ZonedDateTime issueDate, ZonedDateTime dueDate, List<Work> works) {
        return null;
    }
}
