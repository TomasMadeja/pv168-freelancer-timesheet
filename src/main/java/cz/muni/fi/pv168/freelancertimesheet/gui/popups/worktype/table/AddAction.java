package cz.muni.fi.pv168.freelancertimesheet.gui.popups.worktype.table;

import cz.muni.fi.pv168.freelancertimesheet.gui.models.ServiceTypeTableModel;
import cz.muni.fi.pv168.freelancertimesheet.gui.popups.worktype.forms.WorkTypeForm;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class AddAction extends AbstractAction {
    private final JTable workTypeTable;
    private final WorkTypeForm workTypeForm;

    public AddAction(JTable workTypeTable, WorkTypeForm workTypeForm) {
        super("Add");
        this.workTypeTable = workTypeTable;
        this.workTypeForm = workTypeForm;
        putValue(SHORT_DESCRIPTION, "Add new work type");
        putValue(MNEMONIC_KEY, KeyEvent.VK_A);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl N"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var employeeTableModel = (ServiceTypeTableModel) workTypeTable.getModel();
        workTypeForm.resetForm();
    }
}
