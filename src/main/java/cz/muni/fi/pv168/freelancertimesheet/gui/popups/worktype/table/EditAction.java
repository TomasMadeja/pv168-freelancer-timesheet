package cz.muni.fi.pv168.freelancertimesheet.gui.popups.worktype.table;

import cz.muni.fi.pv168.freelancertimesheet.backend.interfaces.WorkType;
import cz.muni.fi.pv168.freelancertimesheet.gui.models.WorkTypeTableModel;
import cz.muni.fi.pv168.freelancertimesheet.gui.popups.worktype.forms.WorkTypeForm;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class EditAction extends AbstractAction {
    private final JTable workTypeTable;
    private final WorkTypeForm workTypeForm;

    public EditAction(JTable workTypeTable, WorkTypeForm workTypeForm) {
        super("Edit");
        this.workTypeTable = workTypeTable;
        this.workTypeForm = workTypeForm;
        putValue(SHORT_DESCRIPTION, "Edits selected work type");
        putValue(MNEMONIC_KEY, KeyEvent.VK_E);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl E"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var employeeTableModel = (WorkTypeTableModel) workTypeTable.getModel();
        int index = workTypeTable.getSelectedRow();
        WorkType workType = employeeTableModel.getRow(index);
        workTypeForm.fillForm(workType);
    }
}
