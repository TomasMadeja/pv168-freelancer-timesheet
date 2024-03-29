package cz.muni.fi.pv168.freelancertimesheet.gui.containers;

import cz.muni.fi.pv168.freelancertimesheet.backend.PersistanceManager;
import cz.muni.fi.pv168.freelancertimesheet.backend.interfaces.Invoice;
import cz.muni.fi.pv168.freelancertimesheet.backend.interfaces.Work;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class WorkContainer implements GenericContainer<Work> {

    private List<? extends Work> rows;

    public WorkContainer() {
        refresh();
    }

    @Override
    public synchronized GenericContainer refresh() {
        rows = PersistanceManager.getAllWork();
        return this;
    }

    public synchronized GenericContainer refresh(String workName, String workTypeName) {
        rows = PersistanceManager.getAllWork(workName, workTypeName);
        return this;
    }

    @Override
    public synchronized Work get(int i) {
        return rows.get(i);
    }

    @Override
    public synchronized int size() {
        return rows.size();
    }

    @Override
    public synchronized void remove(int i) {
        PersistanceManager.removeEntity(rows.get(i));
        rows.remove(i);
    }

    public synchronized void removeList(int[] indices) {
        List<Work> mappedEntities = new ArrayList<>();
        int[] reverseIndices = Arrays.stream(indices)
                .boxed()
                .sorted(Collections.reverseOrder())
                .mapToInt(Integer::intValue)
                .toArray();
        Arrays.stream(reverseIndices)
                .forEach((i) -> mappedEntities.add(rows.get(i)));
        PersistanceManager.removeCollection(mappedEntities);
        Arrays.stream(reverseIndices)
                .forEach((i) -> rows.remove(i));
    }
}
