package cz.muni.fi.pv168.freelancertimesheet.gui.models;

import cz.muni.fi.pv168.freelancertimesheet.gui.exampledata.WorkType;

import javax.swing.table.AbstractTableModel;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class WorkTypeTableModel extends AbstractTableModel {

    private final List<WorkType> workTypes;

    public WorkTypeTableModel(List<WorkType> workTypes) {
        this.workTypes = new ArrayList<>(workTypes);
        try {
            columns = new Column[]{
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
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    private static class Column {
        public final String name;
        public final Class<?> cClass;
        public final Method valueGetter;

        public Column(String name, Class<?> cClass, Method valueGetter) {
            this.name = name;
            this.cClass = cClass;
            this.valueGetter = valueGetter;
        }
    }

    private Column[] columns;

    @Override
    public int getRowCount() {
        return workTypes.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public String getColumnName(int column) {
        return columns[column].name;
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
