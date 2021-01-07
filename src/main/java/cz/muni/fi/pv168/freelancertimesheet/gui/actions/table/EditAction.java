package cz.muni.fi.pv168.freelancertimesheet.gui.actions.table;

import cz.muni.fi.pv168.freelancertimesheet.gui.I18N;
import cz.muni.fi.pv168.freelancertimesheet.gui.models.FormModel;
import cz.muni.fi.pv168.freelancertimesheet.gui.models.TableModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class EditAction extends AbstractAction {

    private static final I18N i18n = new I18N(EditAction.class);

    private final JTable table;
    private final FormBuilder form;
    private final AddAction.Callback successCallback;

    public EditAction(JTable table, FormBuilder form, AddAction.Callback successCallback) {
        super(i18n.getString("edit"));
        this.table = table;
        this.form = form;
        this.successCallback = successCallback;
        putValue(SHORT_DESCRIPTION, i18n.getString("shortDescription"));
        putValue(MNEMONIC_KEY, KeyEvent.VK_E);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl E"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var tableModel = (TableModel) table.getModel();
        int index = table.getSelectedRow();
        form.build(table, successCallback);
    }
}
