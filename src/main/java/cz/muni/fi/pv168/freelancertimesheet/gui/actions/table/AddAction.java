package cz.muni.fi.pv168.freelancertimesheet.gui.actions.table;

import cz.muni.fi.pv168.freelancertimesheet.gui.I18N;
import cz.muni.fi.pv168.freelancertimesheet.gui.models.TableModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class AddAction extends AbstractAction {

    private static final I18N i18n = new I18N(AddAction.class);

    private final JTable table;
    private final FormBuilder form;
    private final Callback successCallback;

    public interface FormBuilder {
        void build(JTable table, Callback callback);
    }

    public interface Callback {
        void call();
    }

    public AddAction(JTable table, FormBuilder form, Callback successCallback) {
        super(i18n.getString("add"));
        this.table = table;
        this.form = form;
        this.successCallback = successCallback;
        putValue(SHORT_DESCRIPTION, i18n.getString("shortDescription"));
        putValue(MNEMONIC_KEY, KeyEvent.VK_A);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl N"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var employeeTableModel = (TableModel) table.getModel();
        form.build(table, successCallback);
    }
}
