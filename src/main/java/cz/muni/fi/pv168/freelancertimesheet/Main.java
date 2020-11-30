package cz.muni.fi.pv168.freelancertimesheet;

import cz.muni.fi.pv168.freelancertimesheet.backend.DBConnectionUtils;
import cz.muni.fi.pv168.freelancertimesheet.backend.PDFGenerator;
import cz.muni.fi.pv168.freelancertimesheet.backend.interfaces.Client;
import cz.muni.fi.pv168.freelancertimesheet.backend.interfaces.Invoice;
import cz.muni.fi.pv168.freelancertimesheet.backend.interfaces.Work;
import cz.muni.fi.pv168.freelancertimesheet.backend.interfaces.WorkType;
import cz.muni.fi.pv168.freelancertimesheet.backend.orm.ClientImpl;
import cz.muni.fi.pv168.freelancertimesheet.backend.orm.InvoiceImpl;
import cz.muni.fi.pv168.freelancertimesheet.backend.orm.IssuerImpl;
import cz.muni.fi.pv168.freelancertimesheet.backend.orm.WorkImpl;
import cz.muni.fi.pv168.freelancertimesheet.backend.orm.WorkTypeImpl;
import cz.muni.fi.pv168.freelancertimesheet.gui.MainWindow;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws URISyntaxException, IOException {
//        DBConnectionUtils.init();
//        samplePDF();
        sampleTemplatedPDF();
//        MainWindow dash = MainWindow.setup();
    }

    public static void samplePDF() throws URISyntaxException {
        URL res = Main.class.getClassLoader().getResource("example.html");
        URI tmp = res.toURI();
        File file = Paths.get(tmp).toFile();
        try (
                BufferedInputStream br = new BufferedInputStream(new FileInputStream(file))
        )
        {
            String contents = new String(br.readAllBytes(), StandardCharsets.UTF_8);
            PDFGenerator.savePDF(contents, "D:\\DDownloads\\example.pdf");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sampleTemplatedPDF() throws URISyntaxException, IOException {
        InvoiceImpl invoice = prepareInvoice();
        String htmlPdf = PDFGenerator.generatePDF(invoice);
        PDFGenerator.savePDF(htmlPdf, "D:\\DDownloads\\example.pdf");
    }

    public static InvoiceImpl prepareInvoice() {
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
