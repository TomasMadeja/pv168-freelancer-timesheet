package cz.muni.fi.pv168.freelancertimesheet.backend;

import cz.muni.fi.pv168.freelancertimesheet.backend.interfaces.Client;
import cz.muni.fi.pv168.freelancertimesheet.backend.interfaces.Invoice;
import cz.muni.fi.pv168.freelancertimesheet.backend.interfaces.Issuer;
import cz.muni.fi.pv168.freelancertimesheet.backend.interfaces.Work;
import cz.muni.fi.pv168.freelancertimesheet.backend.interfaces.WorkType;
import cz.muni.fi.pv168.freelancertimesheet.backend.orm.InvoiceImpl;

import javax.persistence.EntityExistsException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class PersistanceManager {

    public static List<? extends WorkType> getAllWorkType() {
        var entityManager = DBConnectionUtils.getSessionFactory().createEntityManager();
        entityManager.getTransaction().begin();
        List<? extends WorkType> results = entityManager.createNamedQuery("getAllWorkTypes").getResultList();
        entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.clear();
        entityManager.close();
        return results;
    }

    public static List<? extends Work> getAllWork() {
        var entityManager = DBConnectionUtils.getSessionFactory().createEntityManager();
        entityManager.getTransaction().begin();
        List<? extends Work> results = entityManager.createNamedQuery("getAllWorks").getResultList();
        entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.clear();
        return results;
    }

    public static List<? extends Client> getAllClient() {
        var entityManager = DBConnectionUtils.getSessionFactory().createEntityManager();
        entityManager.getTransaction().begin();
        List<? extends Client> results = entityManager.createNamedQuery("getAllClients").getResultList();
        entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.clear();
        return results;
    }

    public static List<? extends Issuer> getAllIssuer() {
        var entityManager = DBConnectionUtils.getSessionFactory().createEntityManager();
        entityManager.getTransaction().begin();
        List<? extends Issuer> results = entityManager.createNamedQuery("getAllIssuers").getResultList();
        entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.clear();
        return results;
    }

    public static List<? extends Invoice> getAllInvoice() {
        var entityManager = DBConnectionUtils.getSessionFactory().createEntityManager();
        entityManager.getTransaction().begin();
        List<? extends Invoice> results = entityManager.createNamedQuery("getAllInvoices").getResultList();
        entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.clear();
        return results;
    }

    public static List<? extends Invoice> getAllInvoice(String ico, String dic) {
        var entityManager = DBConnectionUtils.getSessionFactory().createEntityManager();
        entityManager.getTransaction().begin();
        if (ico == null) {
            ico = "%";
        }
        if (dic == null) {
            dic = "%";
        }
        var query = entityManager.createQuery(
                "SELECT i FROM InvoiceImpl i INNER JOIN ClientImpl c ON i.client = c WHERE c.ico like :ico AND c.dic like :dic"
        )
                .setParameter("ico", "%" + ico + "%")
                .setParameter("dic", "%" + dic + "%");
        var results = query.getResultList();
        entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.clear();
        return results;
    }

    public static void persistWorkType(WorkType workType) {
        workType.validateAttributes();
        persistEntity(workType);
    }

    public static void persistWork(Work work) {
        work.validateAttributes();
        persistEntity(work);
    }

    public static void persistClient(Client client) {
        client.validateAttributes();
        persistEntity(client);
    }

    public static void persistIssuer(Issuer issuer) {
        issuer.validateAttributes();
        persistEntity(issuer);
    }

    public static void persistInvoice(Invoice invoice) {
        invoice.validateAttributes();
        persistEntity(invoice);
    }

    public static void generateAndPersistInvoice(Invoice invoice, PDFStorage storage) throws IOException, URISyntaxException {
        String invoiceHtml = PDFGenerator.generatePDF(invoice);
        Path outputPath = storage.generateNewOutputPath();
        PDFGenerator.savePDF(invoiceHtml, outputPath.toAbsolutePath().toString());
        String relativePath = storage.toRelativePath(outputPath);
        invoice.setPdfPath(relativePath);
        persistInvoice(invoice);
    }

    private static void persistEntity(Object entity) {
        var entityManager = DBConnectionUtils.getSessionFactory().createEntityManager();
        entityManager.getTransaction().begin();
        try {
            entityManager.persist(entity);
        } catch (EntityExistsException e) {
            entityManager.merge(entity);
        }
        entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.clear();
    }

    private static <T> void persistCollection(Collection<T> records) {
        var entityManager = DBConnectionUtils.getSessionFactory().createEntityManager();
        entityManager.getTransaction().begin();
        for (T record : records) {
            entityManager.persist(record);
        }
        entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.clear();
    }
}
