package cz.muni.fi.pv168.freelancertimesheet;

import cz.muni.fi.pv168.freelancertimesheet.backend.DBConnectionUtils;
import cz.muni.fi.pv168.freelancertimesheet.backend.PDFGenerator;
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

public class Main {

    public static void main(String[] args) throws URISyntaxException {
        DBConnectionUtils.init();
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
        MainWindow dash = MainWindow.setup();
    }
}
