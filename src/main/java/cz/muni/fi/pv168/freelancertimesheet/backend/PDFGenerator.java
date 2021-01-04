package cz.muni.fi.pv168.freelancertimesheet.backend;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import com.sun.xml.bind.v2.runtime.reflect.opt.Const;
import cz.muni.fi.pv168.freelancertimesheet.Main;
import cz.muni.fi.pv168.freelancertimesheet.backend.interfaces.Invoice;
import cz.muni.fi.pv168.freelancertimesheet.backend.interfaces.Work;
import cz.muni.fi.pv168.freelancertimesheet.backend.orm.InvoiceImpl;
import cz.muni.fi.pv168.freelancertimesheet.backend.orm.WorkImpl;
import org.jsoup.Jsoup;
import org.jsoup.helper.W3CDom;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;

public class PDFGenerator {

    public static final DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("YYYY-MM-DD");

    public static void savePDF(String data, String out) throws IOException {
        try (OutputStream os = new FileOutputStream(out)) {
            PdfRendererBuilder builder = new PdfRendererBuilder();

            builder.withW3cDocument(
                    (new W3CDom()).fromJsoup(Jsoup.parse(data)),
                    out
            );
            builder.toStream(os);
            builder.run();
        }
    }

    public static String generatePDF(Invoice invoice) throws URISyntaxException,FileNotFoundException, IOException {
        StringBuilder workListBuilder = new StringBuilder();
        for (Work work : invoice.getWorks()) {
            workListBuilder.append(work.toXML());
        }

        File file = Paths
                .get(
                        Main.class
                                .getClassLoader()
                                .getResource("invoice_template.html")
                                .toURI()
                ).toFile();
        try (
                BufferedInputStream br = new BufferedInputStream(new FileInputStream(file))
        )
        {
            String template = new String(br.readAllBytes(), StandardCharsets.UTF_8);
            String pdfHtml = String.format(
                    template,
                    invoice.getIssuer().toXML(),
                    invoice.getClient().toXML(),
                    invoice.getIssueDate().format(dateTimeFormat),
                    invoice.getDueDate().format(dateTimeFormat),
                    workListBuilder.toString(),
                    invoice.getTotalAmount().toString(), Constants.getCurrency(),
                    invoice.getTotalAmount().toString(), Constants.getCurrency()
            );
            return pdfHtml;
        }
    }

}
