package cz.muni.fi.pv168.freelancertimesheet.gui.containers;

import cz.muni.fi.pv168.freelancertimesheet.backend.PersistanceManager;
import cz.muni.fi.pv168.freelancertimesheet.backend.interfaces.WorkType;

import java.util.List;

public class WorkTypeContainer implements GenericContainer {

    private List<? extends WorkType> rows;

    public WorkTypeContainer() {
        refresh();
    }

    @Override
    public GenericContainer refresh() {
        rows = PersistanceManager.getAllWorkType();
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