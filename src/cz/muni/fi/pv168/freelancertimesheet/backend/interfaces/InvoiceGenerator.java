package cz.muni.fi.pv168.freelancertimesheet.backend.interfaces;

import java.nio.file.Path;

public interface InvoiceGenerator {

    public void generateInvoice(Invoice invoice, Path storePath);

    // public void generateSignedInvoice(Invoice invoice, Path storePath);
}
