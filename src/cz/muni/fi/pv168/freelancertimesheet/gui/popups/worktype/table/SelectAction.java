package cz.muni.fi.pv168.freelancertimesheet.gui.popups.worktype.table;

import cz.muni.fi.pv168.freelancertimesheet.gui.models.WorkTypeTableModel;
import cz.muni.fi.pv168.freelancertimesheet.gui.popups.worktype.forms.WorkTypeForm;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class SelectAction extends AbstractAction {
    private final JFrame frame;

    public SelectAction(JFrame frame) {
        super("Select");
        this.frame = frame;
        putValue(SHORT_DESCRIPTION, "Select work type");
        putValue(MNEMONIC_KEY, KeyEvent.VK_A);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl S"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        frame.dispose();
    }
}
