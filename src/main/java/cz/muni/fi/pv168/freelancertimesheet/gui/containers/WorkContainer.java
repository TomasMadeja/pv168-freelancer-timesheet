package cz.muni.fi.pv168.freelancertimesheet.gui.containers;

import cz.muni.fi.pv168.freelancertimesheet.backend.PersistanceManager;
import cz.muni.fi.pv168.freelancertimesheet.backend.interfaces.Invoice;
import cz.muni.fi.pv168.freelancertimesheet.backend.interfaces.Work;

import java.util.List;

public class WorkContainer implements GenericContainer {

    private List<? extends Work> rows;

    public WorkContainer() {
        refresh();
    }

    @Override
    public GenericContainer refresh() {
        rows = PersistanceManager.getAllWork();
        return this;
    }

    @Override
    public Object get(int i) {
        return rows.get(i);
    }

    @Override
    public int size() {
        return rows.size();
    }
}
