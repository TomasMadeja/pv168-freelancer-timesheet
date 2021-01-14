package cz.muni.fi.pv168.freelancertimesheet.backend;

import cz.muni.fi.pv168.freelancertimesheet.Main;
import cz.muni.fi.pv168.freelancertimesheet.backend.interfaces.*;
import cz.muni.fi.pv168.freelancertimesheet.backend.orm.*;
import org.apache.commons.io.FileUtils;
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
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class InvoiceTests {
    private PDFStorage pdfStorage;
    private Path pdfStorageDir;

    @BeforeEach
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
                "School",
                "Gave up",
                new BigDecimal("20")
        ));
        collection.add(WorkTypeImpl.createWorkType(
                "Job",
                "Letting you down",
                new BigDecimal("30")
        ));
        collection.add(WorkTypeImpl.createWorkType(
                "Help",
                "Due date",
                new BigDecimal("40")
        ));
        return collection;
    }

    private List<WorkType> addWorkTypes() {
        var wt = prepareWorkTypes();
        for (var workType:
             wt) {
            PersistanceManager.persistWorkType(workType);
        }
        return wt;
    }

    private List<Work> prepareWork() {
        WorkType[] workTypes = addWorkTypes().toArray(WorkType[]::new);
        List<Work> collection = new ArrayList<>();
        collection.add(WorkImpl.createWork(
                "School today",
                "help us",
                ZonedDateTime.parse("2011-12-03T10:15:30+01:00"),
                ZonedDateTime.parse("2011-12-04T10:15:30+01:00"),
                workTypes[0]
        ));
        collection.add(WorkImpl.createWork(
                "School yesterday",
                "help yourself",
                ZonedDateTime.parse("2011-12-05T10:15:30+01:00"),
                ZonedDateTime.parse("2011-12-06T10:15:30+01:00"),
                workTypes[1]
        ));
        collection.add(WorkImpl.createWork(
                "Job assignment",
                "blow up",
                ZonedDateTime.parse("2011-12-07T10:15:30+01:00"),
                ZonedDateTime.parse("2011-12-08T10:15:30+01:00"),
                workTypes[2]
        ));
        collection.add(WorkImpl.createWork(
                "Sacrifice",
                "use the shovel",
                ZonedDateTime.parse("2011-12-07T10:15:30+01:00"),
                ZonedDateTime.parse("2011-12-08T10:15:30+01:00"),
                workTypes[0]
        ));
        return collection;
    }

    private List<Work> addWork() {
        var w = prepareWork();
        for (var work:
             w) {
            PersistanceManager.persistWork(work);
        }
        return w;
    }

    public List<InvoiceImpl> prepareInvoice() {
        List<Work> works = addWork();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm ZZ");
        ClientImpl client = ((ClientImpl) ClientImpl.createEntity("Client1",
                "Client Address, 00000 Place, Country",
                "3123123123",
                "CZ123123123",
                "",
                ""));
        ClientImpl client2 = ((ClientImpl) ClientImpl.createEntity("Client1",
                "Client Address, 00000 Place, Country",
                "42069",
                "123654789",
                "",
                ""));
        IssuerImpl issuer = ((IssuerImpl) IssuerImpl.createEntity(
                "Issuer1",
                "Issuer Address, 00000 Place, Country",
                "089876768",
                "CZ0988777797",
                "",
                ""));
        List<InvoiceImpl> collection = new ArrayList<>();
        collection.add((InvoiceImpl) InvoiceImpl.createInvoice(
                client,
                issuer,
                ZonedDateTime.parse("2000-04-08 08:30 +0000", formatter),
                ZonedDateTime.parse("2000-05-08 08:30 +0000", formatter),
                works
        ));
        collection.add((InvoiceImpl) InvoiceImpl.createInvoice(
                client2,
                issuer,
                ZonedDateTime.parse("2000-04-08 08:30 +0000", formatter),
                ZonedDateTime.parse("2000-05-08 08:30 +0000", formatter),
                works
        ));
        return collection;
    }

    @Test
    public void testInvoiceAdd() {
        var invoices = prepareInvoice();
        for (var invoice :
                invoices) {
            PersistanceManager.persistInvoice(invoice);
        }
        List<InvoiceImpl> result = PersistanceManager.getAllInvoice();
        Collections.sort(invoices);
        Collections.sort(result);
        Assertions.assertArrayEquals(invoices.toArray(),result.toArray());
    }

    @Test
    public void testInvoiceDelete() {
        var invoices = prepareInvoice();
        InvoiceImpl toDelete = invoices.get(0);
        PersistanceManager.persistInvoice(toDelete);
        PersistanceManager.removeEntity(toDelete);
        List<InvoiceImpl> result = (List<InvoiceImpl>)PersistanceManager.getAllInvoice(toDelete.getClient().getICO(), toDelete.getClient().getDIC());
        Assertions.assertEquals(0, result.size());
    }

    @Test
    public void testInvoiceGeneratedPdf() throws IOException, URISyntaxException {
        var invoices = prepareInvoice();
        InvoiceImpl generatedPdf = invoices.get(0);
        PDFStorage storage = new PDFStorage(Path.of("testPdfStorage/"));
        PersistanceManager.generateAndPersistInvoice(generatedPdf, storage);
        List<InvoiceImpl> result = (List<InvoiceImpl>)PersistanceManager.getAllInvoice();
        Assertions.assertEquals(1, result.size());
        Path pdfPath = storage.fetchFile(result.get(0));
        Assertions.assertTrue(pdfPath.toFile().exists());
    }

    @Test
    public void testSetClient() {
        var invoice = prepareInvoice().get(0);
        Client client = (Client)ClientImpl.createEntity(
                "Jozef",
                "Home",
                "123456",
                "789456",
                "09045697",
                "care@not.com"
        );
        invoice.setClient(client);
        PersistanceManager.persistInvoice(invoice);
        List<InvoiceImpl> result = (List<InvoiceImpl>)PersistanceManager.getAllInvoice();
        Assertions.assertTrue(result.contains(invoice));
    }

    @Test
    public void testSetIssuer() {
        var invoice = prepareInvoice().get(0);
        Issuer issuer = (Issuer)IssuerImpl.createEntity(
                "Jozef",
                "Home",
                "123456",
                "789456",
                "09045697",
                "care@not.com"
        );
        invoice.setIssuer(issuer);
        PersistanceManager.persistInvoice(invoice);
        List<InvoiceImpl> result = (List<InvoiceImpl>)PersistanceManager.getAllInvoice();
        Assertions.assertTrue(result.contains(invoice));
    }

    @Test
    public void testSetIssueData() {
        var invoice = prepareInvoice().get(0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm ZZ");
        invoice.setIssueDate(ZonedDateTime.parse("2020-05-08 08:30 +0000", formatter));
        PersistanceManager.persistInvoice(invoice);
        List<InvoiceImpl> result = (List<InvoiceImpl>)PersistanceManager.getAllInvoice();
        Assertions.assertTrue(result.contains(invoice));
    }

    @Test
    public void testSetDueDate() {
        var invoice = prepareInvoice().get(0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm ZZ");
        invoice.setDueDate(ZonedDateTime.parse("2020-06-08 08:30 +0000", formatter));
        PersistanceManager.persistInvoice(invoice);
        List<InvoiceImpl> result = (List<InvoiceImpl>)PersistanceManager.getAllInvoice();
        Assertions.assertTrue(result.contains(invoice));
    }

    @Test
    public void samplePDF() throws URISyntaxException, IOException {
        URL res = Main.class.getClassLoader().getResource("example.html");
        URI tmp = res.toURI();
        File file = Paths.get(tmp).toFile();
        try (
                BufferedInputStream br = new BufferedInputStream(new FileInputStream(file))
        ) {
            String contents = new String(br.readAllBytes(), StandardCharsets.UTF_8);
            Path newPath = pdfStorage.generateNewOutputPath();
            PDFGenerator.savePDF(contents, newPath.toAbsolutePath().toString());
            Assertions.assertTrue(newPath.toFile().exists(), "Output PDF file doesn't exist.");
        }
    }
}
