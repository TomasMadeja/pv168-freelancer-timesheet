package cz.muni.fi.pv168.freelancertimesheet.backend;

import cz.muni.fi.pv168.freelancertimesheet.backend.interfaces.Client;
import cz.muni.fi.pv168.freelancertimesheet.backend.interfaces.Invoice;
import cz.muni.fi.pv168.freelancertimesheet.backend.interfaces.Issuer;
import cz.muni.fi.pv168.freelancertimesheet.backend.interfaces.Work;
import cz.muni.fi.pv168.freelancertimesheet.backend.interfaces.WorkType;
import cz.muni.fi.pv168.freelancertimesheet.backend.orm.ClientImpl;
import cz.muni.fi.pv168.freelancertimesheet.backend.orm.InvoiceImpl;
import cz.muni.fi.pv168.freelancertimesheet.backend.orm.IssuerImpl;
import cz.muni.fi.pv168.freelancertimesheet.backend.orm.WorkImpl;
import cz.muni.fi.pv168.freelancertimesheet.backend.orm.WorkTypeImpl;
import org.hibernate.ejb.HibernateEntityManager;

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

    public static List<WorkTypeImpl> getAllWorkType() {
        return getAllWorkType(null);
    }

    public static List<WorkTypeImpl> getAllWorkType(String workTypeName) {
        var entityManager = DBConnectionUtils.getSessionFactory().createEntityManager();
        entityManager.getTransaction().begin();
        if (workTypeName == null) {
            workTypeName = "%";
        }
        var query = entityManager.createQuery(
                "SELECT t FROM WorkTypeImpl t WHERE t.name like :workTypeName",
                WorkTypeImpl.class
        )
                .setParameter("workTypeName", "%" + workTypeName + "%");
        var results = query.getResultList();
        entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.clear();
        return results;
    }

    public static List<WorkImpl> getAllWork() {
        return getAllWork(null, null);
    }

    public static List<WorkImpl> getAllWork(String workName, String workTypeName) {
        var entityManager = DBConnectionUtils.getSessionFactory().createEntityManager();
        entityManager.getTransaction().begin();
        if (workTypeName == null) {
            workTypeName = "%";
        }
        if (workName == null) {
            workName = "%";
        }
        var query = entityManager.createQuery(
                "SELECT DISTINCT w FROM WorkImpl w INNER JOIN WorkTypeImpl t ON w.workType = t WHERE w.name like :workName AND t.name like :workTypeName",
                WorkImpl.class
        )
                .setParameter("workName", "%" + workName + "%")
                .setParameter("workTypeName", "%" + workTypeName + "%");
        var results = query.getResultList();
        entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.clear();
        return results;
    }

    public static List<ClientImpl> getAllClient() {
        var entityManager = DBConnectionUtils.getSessionFactory().createEntityManager();
        entityManager.getTransaction().begin();
        List<ClientImpl> results = entityManager.createNamedQuery("getAllClients", ClientImpl.class).getResultList();
        entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.clear();
        return results;
    }

    public static List<IssuerImpl> getAllIssuer() {
        var entityManager = DBConnectionUtils.getSessionFactory().createEntityManager();
        entityManager.getTransaction().begin();
        List<IssuerImpl> results = entityManager.createNamedQuery("getAllIssuers", IssuerImpl.class).getResultList();
        entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.clear();
        return results;
    }

    public static List<InvoiceImpl> getAllInvoice() {
        return getAllInvoice(null, null);
    }

    public static List<InvoiceImpl> getAllInvoice(String ico, String dic) {
        var entityManager = DBConnectionUtils.getSessionFactory().createEntityManager();
        entityManager.getTransaction().begin();
        if (ico == null) {
            ico = "%";
        }
        if (dic == null) {
            dic = "%";
        }
        var query = entityManager.createQuery(
                "SELECT i FROM InvoiceImpl i INNER JOIN ClientImpl c ON i.client = c WHERE c.ico like :ico AND c.dic like :dic",
                InvoiceImpl.class
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
        persistClient(invoice.getClient());
        if (invoice.getIssuer() != null)
            persistIssuer(invoice.getIssuer());
        persistEntity(invoice);
    }
//
//    public static void removeInvoice(Invoice invoice) {
//        removeEntity(invoice);
//    }
//
//    public static void removeInvoices(Collection<Invoice> invoice) {
//        removeCollection(invoice);
//    }

    public static void generateAndPersistInvoice(Invoice invoice, PDFStorage storage) throws IOException, URISyntaxException {
        String invoiceHtml = PDFGenerator.generatePDF(invoice);
        Path outputPath = storage.generateNewOutputPath();
        PDFGenerator.savePDF(invoiceHtml, outputPath.toAbsolutePath().toString());
        String relativePath = storage.toRelativePath(outputPath);
        invoice.setPdfPath(relativePath);
        persistInvoice(invoice);
    }

    public static void persistEntity(Object entity) {
        var session = DBConnectionUtils.getSessionFactory().openSession();
        try {
            session.getTransaction().begin();
            session.saveOrUpdate(entity);
            session.flush();
            session.getTransaction().commit();
            session.clear();
        }
        finally {
            session.close();
        }
    }

//    private static void persistEntity(Object entity) {
//        var entityManager = DBConnectionUtils.getSessionFactory().createEntityManager();
//        entityManager.getTransaction().begin();
//        try {
//            entityManager.persist(entity);
//        } catch (EntityExistsException e) {
//            var newEntity = entityManager.merge(entity);
//            entityManager.flush();
//            entityManager.merge(entity);
//            entityManager.flush();
//        } catch (javax.persistence.PersistenceException e) {
//            var newEntity = entityManager.merge(entity);
//            entityManager.flush();
//            entityManager.merge(entity);
//            entityManager.flush();
//        }
//        entityManager.flush();
//        entityManager.getTransaction().commit();
//        entityManager.clear();
//    }

    public static <T> void persistCollection(Collection<T> records) {
        var session = DBConnectionUtils.getSessionFactory().openSession();
        session.getTransaction().begin();
        for (T record : records) {
            session.saveOrUpdate(record);
        }
        session.flush();
        session.getTransaction().commit();
        session.clear();
    }

    public static void removeEntity(Object entity) {
        var entityManager = DBConnectionUtils.getSessionFactory().createEntityManager();
        entityManager.getTransaction().begin();
        try {
            entityManager.remove(entity);
            entityManager.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
        entityManager.getTransaction().commit();
        entityManager.clear();
    }

    public static <T> void removeCollection(Collection<T> records) {
        var entityManager = DBConnectionUtils.getSessionFactory().createEntityManager();
        entityManager.getTransaction().begin();
        for (T record : records) {
            try {
                entityManager.remove(record);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            entityManager.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
        entityManager.getTransaction().commit();
        entityManager.clear();
    }

}
