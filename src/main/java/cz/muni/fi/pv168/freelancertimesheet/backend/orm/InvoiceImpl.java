package cz.muni.fi.pv168.freelancertimesheet.backend.orm;

import cz.muni.fi.pv168.freelancertimesheet.backend.interfaces.Client;
import cz.muni.fi.pv168.freelancertimesheet.backend.interfaces.Entity;
import cz.muni.fi.pv168.freelancertimesheet.backend.interfaces.Invoice;
import cz.muni.fi.pv168.freelancertimesheet.backend.interfaces.Issuer;
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
import java.util.ArrayList;
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
    private IssuerImpl issuer;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable=false)
    private ClientImpl client;

    @Column(name = "total_amount", nullable=false)
    private BigDecimal totalAmount;

    @Column(name = "pdf_path", nullable=true)
    private String pdfPath;

    public InvoiceImpl(ZonedDateTime issueDate, ZonedDateTime dueDate, List<Work> works, IssuerImpl issuer, ClientImpl client) {
        this.issueDate = issueDate;
        this.dueDate = dueDate;
        this.works = new ArrayList<>();
        this.issuer = issuer;
        this.client = client;

        this.totalAmount = BigDecimal.ZERO;
        setWorks(works);
    }

    public InvoiceImpl() {
        this.issueDate = null;
        this.dueDate = null;
        this.works = new ArrayList<>();
        this.issuer = null;
        this.client = null;

        this.totalAmount = BigDecimal.ZERO;
    }

    @Override
    public Client getClient() {
        return client;
    }

    @Override
    public Invoice setClient(Client client) {
        this.client = (ClientImpl) client;
        return this;
    }

    @Override
    public Invoice validateClient(Client client) {
        return this;
    }

    @Override
    public Issuer getIssuer() {
        return issuer;
    }

    @Override
    public Invoice setIssuer(Issuer issuer) {
        this.issuer = (IssuerImpl) issuer;
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
        this.totalAmount = this.totalAmount.add(work.getCost());
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
    public String getPdfPath() {
        return pdfPath;
    }

    @Override
    public Invoice setPdfPath(String pdfPath) {
        this.pdfPath = pdfPath;
        return this;
    }

    public static Invoice createInvoice(Client client, Issuer issuer, ZonedDateTime issueDate, ZonedDateTime dueDate, List<Work> works) {
        InvoiceImpl invoice = new InvoiceImpl(issueDate, dueDate, works, (IssuerImpl) issuer, (ClientImpl) client);
        return invoice;
    }
}
