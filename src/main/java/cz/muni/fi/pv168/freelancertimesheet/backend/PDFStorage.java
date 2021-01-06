package cz.muni.fi.pv168.freelancertimesheet.backend;

import cz.muni.fi.pv168.freelancertimesheet.backend.exceptions.OpeningPDFReaderException;
import cz.muni.fi.pv168.freelancertimesheet.backend.interfaces.Invoice;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class PDFStorage {
    private Path storageRoot;

    public PDFStorage(Path storageRoot) {
        this.storageRoot = storageRoot.toAbsolutePath();
        this.storageRoot.toFile().mkdirs();
    }

    public Path fetchFile(Invoice invoice) throws IOException {
        String subpath = invoice.getPdfPath();
        return Paths.get(storageRoot.toString(), subpath).toRealPath();
    }

    public Path generateNewOutputPath() {
        String fileName = UUID.randomUUID().toString();
        return Paths.get(storageRoot.toString(), fileName).toAbsolutePath();
    }

    public String toRelativePath(Path path) {
        return storageRoot.relativize(path).toString();
    }

    public boolean openInDesktop(String path) throws FileNotFoundException, OpeningPDFReaderException {
        Path properPath = Paths.get(storageRoot.toString(), path);;
        return openInDesktop(properPath);
    }

    public boolean openInDesktop(Path path) throws FileNotFoundException, OpeningPDFReaderException {
        if (!path.toFile().exists()) {
            throw new FileNotFoundException(path.toString());
        }
        if (!Desktop.isDesktopSupported()) {
            return false;
        }
        try {
            File myFile = new File(path.toString());
            Desktop.getDesktop().open(myFile);
        } catch (IOException ex) {
            throw new OpeningPDFReaderException(
                    "Failed to pdf file (No default reader found or error encountered on startup): " +
                            path.toString()
            );
        }
        return true;
    }
}
