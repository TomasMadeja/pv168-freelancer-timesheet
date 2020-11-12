package cz.muni.fi.pv168.freelancertimesheet.gui.popups.worktype.forms;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class AddAction extends AbstractAction {

    private final JFrame window;

    public AddAction(JFrame window) {
        this.window = window;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        window.dispose();
    }
}
