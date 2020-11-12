package cz.muni.fi.pv168.freelancertimesheet.gui.popups.worktype.table;

import cz.muni.fi.pv168.freelancertimesheet.gui.exampledata.WorkType;
import cz.muni.fi.pv168.freelancertimesheet.gui.models.WorkTypeTableModel;
import cz.muni.fi.pv168.freelancertimesheet.gui.popups.worktype.forms.WorkTypeForm;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class AddAction extends AbstractAction {
    private final JTable workTypeTable;

    public AddAction(JTable workTypeTable) {
        super("Add");
        this.workTypeTable = workTypeTable;
        putValue(SHORT_DESCRIPTION, "Add new work type");
        putValue(MNEMONIC_KEY, KeyEvent.VK_A);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl N"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var employeeTableModel = (WorkTypeTableModel) workTypeTable.getModel();
        WorkTypeForm.displayAdd();
        //employeeTableModel.addRow();
    }
}
