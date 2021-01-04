package cz.muni.fi.pv168.freelancertimesheet.gui.actions.table;

import javax.swing.*;

public interface FormBuilder {
    void build(JTable table, AddAction.Callback callback);
}
