package cz.muni.fi.pv168.freelancertimesheet.gui.models;

import cz.muni.fi.pv168.freelancertimesheet.gui.GenericElement;
import cz.muni.fi.pv168.freelancertimesheet.gui.exampledata.WorkType;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

public class TableModel<T> extends AbstractTableModel {
    public TableModel(Column[] columns) {
        super();
        this.columns = columns;
        this.rows = new ArrayList<>();
    }

    protected final List<T> rows;

    protected static class Column {
        public Class<?> rowClass;
        public String columnName;
        public Class<?> columnClass;

        private String attributeName;

        private Function getter;

        public Column(String columnName, String attributeName, Class<?> columnClass, Class<?> rowClass, Function getter) {
            this.columnName = columnName;
            this.attributeName = attributeName;
            this.columnClass = columnClass;
            this.getter = getter;
            this.rowClass = rowClass;
        }

        public Object getValue(Object container) {
            if (!rowClass.isInstance(container)) {
                throw new RuntimeException();
            }
            return getter.apply(container);
        }
    }

    private Column[] columns;

    protected TableModel addColumn(Column column) {
        List<Column> columnList = Arrays.asList(columns.clone());
        columnList.add(column);
        columns = columnList.toArray(columns);
        return this;
    }

    @Override
    public int getRowCount() {
        return rows.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        if (columnIndex > columns.length || columnIndex < 0) {
            throw new IndexOutOfBoundsException("Invalid column index: " + columnIndex);
        }
        return columns[columnIndex].columnName;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex > columns.length || columnIndex < 0) {
            throw new IndexOutOfBoundsException("Invalid column index: " + columnIndex);
        }
        return columns[columnIndex].columnClass;
    }


    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (columnIndex > columns.length || columnIndex < 0) {
            throw new IndexOutOfBoundsException("Invalid column index: " + columnIndex);
        }
        if (rowIndex > 0 || rowIndex < 0) {
            throw new IndexOutOfBoundsException("Invalid column index: " + columnIndex);
        }
        return columns[columnIndex].getValue(new Date());
    }

    public void addRow(T rowData) {
        int newRowIndex = rows.size();
        rows.add(rowData);
        fireTableRowsInserted(newRowIndex, newRowIndex);
    }

    public void deleteRow(int rowIndex) {
        rows.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }

    public T getRow(int rowIndex){
        return rows.get(rowIndex);
    }
}
