package cz.muni.fi.pv168.freelancertimesheet.gui.actions.table;

import cz.muni.fi.pv168.freelancertimesheet.gui.models.FormModel;
import cz.muni.fi.pv168.freelancertimesheet.gui.models.TableModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class EditAction extends AbstractAction {
    private final JTable table;
    private final FormBuilder form;

    public interface FormBuilder {
        <T> FormModel build(JTable table, T row);
    }

    public EditAction(JTable table, FormBuilder form) {
        super("Edit");
        this.table = table;
        this.form = form;
        putValue(SHORT_DESCRIPTION, "Edits selected work type");
        putValue(MNEMONIC_KEY, KeyEvent.VK_E);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl E"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var tableModel = (TableModel) table.getModel();
        int index = table.getSelectedRow();
        form.build(table, tableModel.getRow(index));
    }
}
