package cz.muni.fi.pv168.freelancertimesheet.gui.actions.table;

import cz.muni.fi.pv168.freelancertimesheet.gui.models.TableModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class AddAction extends AbstractAction {
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
        super("Add");
        this.table = table;
        this.form = form;
        this.successCallback = successCallback;
        putValue(SHORT_DESCRIPTION, "Add new work type");
        putValue(MNEMONIC_KEY, KeyEvent.VK_A);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl N"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        form.build(table, successCallback);
    }
}
