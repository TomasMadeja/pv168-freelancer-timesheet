package cz.muni.fi.pv168.freelancertimesheet.gui.actions.table;

import cz.muni.fi.pv168.freelancertimesheet.backend.PDFStorage;
import cz.muni.fi.pv168.freelancertimesheet.backend.interfaces.Invoice;
import cz.muni.fi.pv168.freelancertimesheet.gui.models.FormModel;
import cz.muni.fi.pv168.freelancertimesheet.gui.models.TableModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class ViewPDFAction extends AbstractAction{
    private final JTable table;

    public interface FormBuilder {
        <T> FormModel build(JTable table, T row);
    }

    public ViewPDFAction(JTable table, PDFStorage storage) {
        super("Edit");
        this.table = table;
        putValue(SHORT_DESCRIPTION, "Edits selected work type");
        putValue(MNEMONIC_KEY, KeyEvent.VK_E);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl E"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var tableModel = (TableModel) table.getModel();
        int index = table.getSelectedRow();
        Invoice invoice = (Invoice) tableModel.getRow(index);
        // TODO open PDF via implemented class
    }
}
