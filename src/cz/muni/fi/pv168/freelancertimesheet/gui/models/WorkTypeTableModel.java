package cz.muni.fi.pv168.freelancertimesheet.gui.models;

import cz.muni.fi.pv168.freelancertimesheet.gui.exampledata.WorkType;

import javax.swing.table.AbstractTableModel;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class WorkTypeTableModel extends AbstractTableModel {

    private List<WorkType> workTypes;

    public WorkTypeTableModel(List<WorkType> workTypes) throws NoSuchMethodException {
        this.workTypes = new ArrayList<>(workTypes);
    }

    private class Column {
        public String name;
        public Class<?> cClass;
        public Method valueGetter;

        public Column(String name, Class<?> cClass, Method valueGetter) {
            this.name = name;
            this.cClass = cClass;
            this.valueGetter = valueGetter;
        }
    }

    private Column[] columns = {
            new Column(
                    "Name",
                    String.class,
                    WorkType.class.getMethod("getName")
            ),
            new Column(
                    "Rate",
                    Double.class,
                    WorkType.class.getMethod("getRate")
            ),
            new Column(
                    "Description",
                    String.class,
                    WorkType.class.getMethod("getDescription")
            )
    };

    @Override
    public int getRowCount() {
        return workTypes.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        WorkType workType = workTypes.get(rowIndex);
        try {
            return columns[columnIndex].valueGetter.invoke(workType);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        return null; // unreachable
    }

    public void addRow(WorkType workType) {
        int newRowIndex = workTypes.size();
        workTypes.add(workType);
        fireTableRowsInserted(newRowIndex, newRowIndex);
    }

    public void deleteRow(int rowIndex) {
        workTypes.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }

    public WorkType getRow(int rowIndex){
        return workTypes.get(rowIndex);
    }
}
