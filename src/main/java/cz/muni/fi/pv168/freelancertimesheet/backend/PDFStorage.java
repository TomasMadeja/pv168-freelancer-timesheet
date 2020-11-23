package cz.muni.fi.pv168.freelancertimesheet.backend;

import cz.muni.fi.pv168.freelancertimesheet.backend.interfaces.Invoice;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class PDFStorage {
    private Path storageRoot;

    public PDFStorage(Path storageRoot) {
        this.storageRoot = storageRoot.toAbsolutePath();
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
}
