package cz.muni.fi.pv168.freelancertimesheet;

import cz.muni.fi.pv168.freelancertimesheet.gui.popups.worktype.forms.WorkTypeForm;
import cz.muni.fi.pv168.freelancertimesheet.gui.popups.worktype.table.WorkTypeTableWindow;

public class Main {

    public static void main(String[] args) throws NoSuchMethodException {
        //MainWindow dash = MainWindow.setup();
        new WorkTypeTableWindow().show();
    }
}
