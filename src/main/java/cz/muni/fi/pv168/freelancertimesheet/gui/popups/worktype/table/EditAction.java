package cz.muni.fi.pv168.freelancertimesheet.gui.popups.worktype.table;

import cz.muni.fi.pv168.freelancertimesheet.backend.interfaces.ServiceType;
import cz.muni.fi.pv168.freelancertimesheet.gui.models.ServiceTypeTableModel;
import cz.muni.fi.pv168.freelancertimesheet.gui.popups.worktype.forms.WorkTypeForm;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class EditAction extends AbstractAction {
    private final JTable workTypeTable;
    private final WorkTypeForm serviceTypeForm;

    public EditAction(JTable workTypeTable, WorkTypeForm workTypeForm) {
        super("Edit");
        this.workTypeTable = workTypeTable;
        this.serviceTypeForm = workTypeForm;
        putValue(SHORT_DESCRIPTION, "Edits selected work type");
        putValue(MNEMONIC_KEY, KeyEvent.VK_E);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl E"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var employeeTableModel = (ServiceTypeTableModel) workTypeTable.getModel();
        int index = workTypeTable.getSelectedRow();
        ServiceType serviceType = employeeTableModel.getRow(index);
        serviceTypeForm.fillForm(serviceType);
    }
}
