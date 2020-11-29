package cz.muni.fi.pv168.freelancertimesheet.backend;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.jsoup.Jsoup;
import org.jsoup.helper.W3CDom;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class PDFGenerator {

    public static void savePDF(String data, String out)
    {
        try (OutputStream os = new FileOutputStream(out))
        {
            try {
                PdfRendererBuilder builder = new PdfRendererBuilder();

                builder.withW3cDocument(
                        (new W3CDom()).fromJsoup(Jsoup.parse(data)),
                        out
                );
                builder.toStream(os);
                builder.run();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
