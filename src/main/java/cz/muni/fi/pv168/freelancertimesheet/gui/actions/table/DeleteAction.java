package cz.muni.fi.pv168.freelancertimesheet.gui.actions.table;

import cz.muni.fi.pv168.freelancertimesheet.gui.I18N;
import cz.muni.fi.pv168.freelancertimesheet.gui.models.TableModel;
import cz.muni.fi.pv168.freelancertimesheet.gui.models.WorkTypeTableModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.Comparator;

public class DeleteAction extends AbstractAction {

    private static final I18N i18n = new I18N(DeleteAction.class);

    private final JTable table;

    public DeleteAction(JTable table) {
        super(i18n.getString("delete"));
        this.table = table;
        putValue(SHORT_DESCRIPTION, i18n.getString("shortDescription"));
        putValue(MNEMONIC_KEY, KeyEvent.VK_D);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl D"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        TableModel tableModel = (TableModel) table.getModel();

        new SwingWorker<Void, Void>() {
            @Override
            public Void doInBackground() {
                int[] indexArray = Arrays.stream(table.getSelectedRows())
                        // view row index must be converted to model row index
                        .map(table::convertRowIndexToModel)
                        .toArray();
                tableModel.deleteRows(indexArray, false);
                return null;
            }

            @Override
            protected void done() {
                try {
                    tableModel.fireTableDataChanged();
                } catch (Exception ignore) {
                }
            }
        }.execute();
    }
}
