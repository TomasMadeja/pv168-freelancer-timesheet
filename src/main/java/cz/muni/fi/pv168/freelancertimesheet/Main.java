package cz.muni.fi.pv168.freelancertimesheet;

import cz.muni.fi.pv168.freelancertimesheet.backend.DBConnectionUtils;
import cz.muni.fi.pv168.freelancertimesheet.gui.MainWindow;


public class Main {

    public static void main(String[] args) {
        DBConnectionUtils.init();
        MainWindow dash = MainWindow.setup();
    }
}
