package cz.muni.fi.pv168.freelancertimesheet.gui.actions.table;

import cz.muni.fi.pv168.freelancertimesheet.backend.PDFStorage;
import cz.muni.fi.pv168.freelancertimesheet.backend.exceptions.OpeningPDFReaderException;
import cz.muni.fi.pv168.freelancertimesheet.backend.interfaces.Invoice;
import cz.muni.fi.pv168.freelancertimesheet.gui.I18N;
import cz.muni.fi.pv168.freelancertimesheet.gui.models.TableModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;

public class ViewPDFAction extends AbstractAction{

    private static final I18N i18n = new I18N(ViewPDFAction.class);

    private final JTable table;
    private PDFStorage pdFStorage;

    public ViewPDFAction(JTable table, PDFStorage storage) {
        super(i18n.getString("view"));
        this.table = table;
        putValue(SHORT_DESCRIPTION, i18n.getString("shortDescription"));
        putValue(MNEMONIC_KEY, KeyEvent.VK_E);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl V"));
        pdFStorage = storage;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var tableModel = (TableModel) table.getModel();
        int index = table.getSelectedRow();
        if (index == -1) { // ignore action when no row is selected
            return;
        }
        Invoice invoice = (Invoice) tableModel.getRow(index);
        try {
            pdFStorage.openInDesktop(invoice.getPdfPath());
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        } catch (OpeningPDFReaderException openingPDFReaderException) {
            openingPDFReaderException.printStackTrace();
        }
    }
}
