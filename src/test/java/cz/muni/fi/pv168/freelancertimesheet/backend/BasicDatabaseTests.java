package cz.muni.fi.pv168.freelancertimesheet.backend;

import cz.muni.fi.pv168.freelancertimesheet.Main;
import cz.muni.fi.pv168.freelancertimesheet.backend.interfaces.Work;
import cz.muni.fi.pv168.freelancertimesheet.backend.interfaces.WorkType;
import cz.muni.fi.pv168.freelancertimesheet.backend.orm.ClientImpl;
import cz.muni.fi.pv168.freelancertimesheet.backend.orm.InvoiceImpl;
import cz.muni.fi.pv168.freelancertimesheet.backend.orm.IssuerImpl;
import cz.muni.fi.pv168.freelancertimesheet.backend.orm.WorkImpl;
import cz.muni.fi.pv168.freelancertimesheet.backend.orm.WorkTypeImpl;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BasicDatabaseTests {

    private PDFStorage pdfStorage;
    private Path pdfStorageDir;

    @AfterEach
    public void cleanupTables() {
        SessionFactory factory = DBConnectionUtils.getSessionFactory();
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery("DELETE FROM invoices").executeUpdate();
            session.createSQLQuery("DELETE FROM issuers").executeUpdate();
            session.createSQLQuery("DELETE FROM clients").executeUpdate();
            session.createSQLQuery("DELETE FROM works").executeUpdate();
            session.createSQLQuery("DELETE FROM work_types").executeUpdate();
            session.getTransaction().commit();
        }
    }


    @BeforeEach
    public void prepareStorage() throws IOException {
        pdfStorageDir = Files.createTempDirectory(null);
        pdfStorage = new PDFStorage(pdfStorageDir);
    }

    @AfterEach
    public void destroyStorage() throws IOException {
        FileUtils.deleteDirectory(pdfStorageDir.toFile());
    }

    private List<WorkType> prepareWorkTypes() {

        List<WorkType> collection = new ArrayList<>();
        collection.add(WorkTypeImpl.createWorkType(
                "TestType1",
                "Never gonna give you up",
                new BigDecimal("20")
        ));
        collection.add(WorkTypeImpl.createWorkType(
                "TestType2",
                "Never gonna let you down",
                new BigDecimal("30")
        ));
        collection.add(WorkTypeImpl.createWorkType(
                "TestType3",
                "Forgot the rest",
                new BigDecimal("40")
        ));
        return collection;
    }

    private List<Work> prepareWork() {
        WorkType[] workTypes = prepareWorkTypes().toArray(WorkType[]::new);
        List<Work> collection = new ArrayList<>();
        collection.add(WorkImpl.createWork(
                "TestWork1",
                "Beepboop",
                ZonedDateTime.parse("2011-12-03T10:15:30+01:00"),
                ZonedDateTime.parse("2011-12-04T10:15:30+01:00"),
                workTypes[0]
        ));
        collection.add(WorkImpl.createWork(
                "TestWork1",
                "Beepboop",
                ZonedDateTime.parse("2011-12-05T10:15:30+01:00"),
                ZonedDateTime.parse("2011-12-06T10:15:30+01:00"),
                workTypes[1]
        ));
        collection.add(WorkImpl.createWork(
                "TestWork1",
                "Beepboop",
                ZonedDateTime.parse("2011-12-07T10:15:30+01:00"),
                ZonedDateTime.parse("2011-12-08T10:15:30+01:00"),
                workTypes[2]
        ));
        return collection;
    }

    private void addWorkTypes() {
        persistCollection(prepareWorkTypes());
    }

    private void addWork() {
        persistCollection(prepareWork());
    }

    private <T> void persistCollection(Collection<T> records) {
        var entityManager = DBConnectionUtils.getSessionFactory().createEntityManager();
        entityManager.getTransaction().begin();
        for (T record : records) {
            entityManager.persist(record);
        }
        entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.clear();
    }

    @Test
    public void testPersistWorkTypeImpl() {
        addWorkTypes();
        List<WorkType> reference = prepareWorkTypes();
        Collections.sort(reference);
        EntityManager entityManager = DBConnectionUtils.getSessionFactory().createEntityManager();
        List<WorkTypeImpl> result = entityManager.createQuery("from WorkTypeImpl").getResultList();
        entityManager.clear();
        Collections.sort(result);
        Assertions.assertEquals(reference,result);
    }

    @Test
    public void testPersistWorkImpl() {
        addWork();
        List<WorkType> reference = prepareWorkTypes();
        Collections.sort(reference);
        EntityManager entityManager = DBConnectionUtils.getSessionFactory().createEntityManager();
        List<WorkTypeImpl> result = entityManager.createQuery("from WorkTypeImpl").getResultList();
        entityManager.clear();
        Collections.sort(result);
        Assertions.assertEquals(reference,result);

        List<Work> referenceWork = prepareWork();
        List<WorkImpl> resultWork = entityManager.createQuery("from WorkImpl").getResultList();
        entityManager.clear();
        Collections.sort(resultWork);
        Assertions.assertEquals(referenceWork, resultWork);

    }

    @Test
    public void samplePDF() throws URISyntaxException, IOException {
        URL res = Main.class.getClassLoader().getResource("example.html");
        URI tmp = res.toURI();
        File file = Paths.get(tmp).toFile();
        try (
                BufferedInputStream br = new BufferedInputStream(new FileInputStream(file))
        )
        {
            String contents = new String(br.readAllBytes(), StandardCharsets.UTF_8);
            Path newPath = pdfStorage.generateNewOutputPath();
            PDFGenerator.savePDF(contents, newPath.toAbsolutePath().toString());
            Assertions.assertTrue(newPath.toFile().exists(), "Output PDF file doesn't exist.");
        }
    }

    @Test
    public void testInvoiceDB() throws IOException, URISyntaxException {
        InvoiceImpl invoice = sampleTemplatedPDF();

        var entityManager = DBConnectionUtils.getSessionFactory().createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(invoice);
        entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.clear();
    }

    @Test
    public InvoiceImpl sampleTemplatedPDF() throws URISyntaxException, IOException {
        InvoiceImpl invoice = prepareInvoice();
        String htmlPdf = PDFGenerator.generatePDF(invoice);
        Path newPath = pdfStorage.generateNewOutputPath();
        PDFGenerator.savePDF(htmlPdf, newPath.toAbsolutePath().toString());
        Assertions.assertTrue(newPath.toFile().exists(), "Output PDF file doesn't exist.");
        invoice.setPdfPath(pdfStorage.toRelativePath(newPath));
        return invoice;
    }

    @Test
    public InvoiceImpl prepareInvoice() {
        WorkType wt1 = WorkTypeImpl.createWorkType("TestType1", "Desc1", "10");
        WorkType wt2 = WorkTypeImpl.createWorkType("TestType2", "Desc2", "20");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm ZZ");
        Work w1 = WorkImpl.createWork("Work1", "Work Desc1", ZonedDateTime.parse("1986-04-08 08:30 +0000", formatter), ZonedDateTime.parse("1986-04-08 12:30 +0000", formatter), wt1);
        Work w2 = WorkImpl.createWork("Work1", "Work Desc1", ZonedDateTime.parse("1986-04-09 08:30 +0000", formatter), ZonedDateTime.parse("1986-04-09 16:00 +0000", formatter), wt2);
        ArrayList<Work> works = new ArrayList<>();
        works.add(w1);
        works.add(w2);
        ClientImpl client = ((ClientImpl) ClientImpl.createEntity("Client1", "Client Address, 00000 Place, Country", "3123123123", "CZ123123123"));
        IssuerImpl issuer = ((IssuerImpl) IssuerImpl.createEntity("Issuer1", "Issuer Address, 00000 Place, Country", "089876768", "CZ0988777797"));
        InvoiceImpl invoice = (InvoiceImpl) InvoiceImpl.createInvoice(client, issuer, ZonedDateTime.parse("2000-04-08 08:30 +0000", formatter), ZonedDateTime.parse("2000-05-08 08:30 +0000", formatter), works);
        return invoice;
    }

}
