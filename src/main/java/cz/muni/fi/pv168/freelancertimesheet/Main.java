package cz.muni.fi.pv168.freelancertimesheet;

import cz.muni.fi.pv168.freelancertimesheet.backend.DBConnectionUtils;
import cz.muni.fi.pv168.freelancertimesheet.backend.PDFStorage;
import cz.muni.fi.pv168.freelancertimesheet.gui.MainWindow;
import cz.muni.fi.pv168.freelancertimesheet.gui.popups.ClientWindow;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;


public class Main {

    public static void main(String[] args) {
        DBConnectionUtils.init();
        PDFStorage pdfStorage = new PDFStorage(Path.of("pdfStorage/"));
        MainWindow dash = MainWindow.setup(pdfStorage);
    }
}
