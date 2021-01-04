package cz.muni.fi.pv168.freelancertimesheet.gui.actions.table;

import cz.muni.fi.pv168.freelancertimesheet.backend.PDFStorage;
import cz.muni.fi.pv168.freelancertimesheet.backend.exceptions.OpeningPDFReaderException;
import cz.muni.fi.pv168.freelancertimesheet.backend.interfaces.Invoice;
import cz.muni.fi.pv168.freelancertimesheet.gui.models.FormModel;
import cz.muni.fi.pv168.freelancertimesheet.gui.models.TableModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;

public class ViewPDFAction extends AbstractAction{
    private final JTable table;
    private PDFStorage pdFStorage;

    public ViewPDFAction(JTable table, PDFStorage storage) {
        super("View");
        this.table = table;
        putValue(SHORT_DESCRIPTION, "Edits selected work type");
        putValue(MNEMONIC_KEY, KeyEvent.VK_E);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl E"));
        pdFStorage = storage;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var tableModel = (TableModel) table.getModel();
        int index = table.getSelectedRow();
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
