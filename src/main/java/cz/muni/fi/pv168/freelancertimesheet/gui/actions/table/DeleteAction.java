package cz.muni.fi.pv168.freelancertimesheet.gui.actions.table;

import cz.muni.fi.pv168.freelancertimesheet.gui.models.TableModel;
import cz.muni.fi.pv168.freelancertimesheet.gui.models.WorkTypeTableModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.Comparator;

public class DeleteAction extends AbstractAction {

    private final JTable table;

    public DeleteAction(JTable table) {
        super("Delete");
        this.table = table;
        putValue(SHORT_DESCRIPTION, "Deletes selected employees");
        putValue(MNEMONIC_KEY, KeyEvent.VK_D);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl D"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        TableModel employeeTableModel = (TableModel) table.getModel();
        Arrays.stream(table.getSelectedRows())
                // view row index must be converted to model row index
                .map(table::convertRowIndexToModel)
                .boxed()
                // We need to delete rows in descending order to not change index of rows
                // which are not deleted yet
                .sorted(Comparator.reverseOrder())
                .forEach(employeeTableModel::deleteRow);
    }
}
