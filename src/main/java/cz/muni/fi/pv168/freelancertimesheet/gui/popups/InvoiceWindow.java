package cz.muni.fi.pv168.freelancertimesheet.gui.popups;

import cz.muni.fi.pv168.freelancertimesheet.backend.PDFStorage;
import cz.muni.fi.pv168.freelancertimesheet.gui.GenericElement;
import cz.muni.fi.pv168.freelancertimesheet.gui.I18N;
import cz.muni.fi.pv168.freelancertimesheet.gui.actions.table.AddAction;
import cz.muni.fi.pv168.freelancertimesheet.gui.tabs.invoice.InvoiceForm;

import javax.swing.*;
import java.awt.*;

public class InvoiceWindow extends JFrame implements GenericElement<InvoiceWindow> {
    private static final I18N i18n = new I18N(InvoiceWindow.class);

    private PDFStorage pdfStorage;
    private final AddAction.Callback callback;

    public InvoiceWindow(AddAction.Callback callback, PDFStorage pdfStorage) {
        super(i18n.getString("title"));
        this.callback = callback;
        this.pdfStorage = pdfStorage;
    }

    @Override
    public InvoiceWindow setupLayout() {
        GridLayout layout = new GridLayout(1, 1);
        this.setLayout(layout);
        return this;
    }

    @Override
    public InvoiceWindow setupVisuals() {
        return this;
    }

    @Override
    public InvoiceWindow setupNested() {
        InvoiceForm invoiceForm = InvoiceForm.setup(pdfStorage);
        invoiceForm.setConfirmCallback(
                () -> {
                    if (callback != null) callback.call();
                    this.dispose();
                }
        );
        invoiceForm.setCancelCallback(
                () -> {
                    this.dispose();
                }
        );
        Dimension preferredSize = invoiceForm.getPreferredSize();
        this.setPreferredSize(new Dimension(preferredSize.width*2, preferredSize.height*2));
        this.add(invoiceForm);
        return this;
    }

    public static InvoiceWindow setup(AddAction.Callback callback, PDFStorage pdfStorage) {
        InvoiceWindow invoiceWindow = new InvoiceWindow(callback, pdfStorage);
        invoiceWindow
                .setupLayout()
                .setupVisuals()
                .setupNested();
        invoiceWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        invoiceWindow.pack();
        invoiceWindow.setVisible(true);
        return invoiceWindow;
    }
}
