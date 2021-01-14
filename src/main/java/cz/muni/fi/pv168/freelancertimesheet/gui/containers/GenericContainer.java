package cz.muni.fi.pv168.freelancertimesheet.gui.containers;

import cz.muni.fi.pv168.freelancertimesheet.backend.PersistanceManager;
import cz.muni.fi.pv168.freelancertimesheet.backend.interfaces.Invoice;

public interface GenericContainer<T> {
    public GenericContainer refresh();

    public T get(int i);

    public int size();

    public void remove(int i);

    public void removeList(int[] indices);
}
