package cz.muni.fi.pv168.freelancertimesheet.gui.models;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class TableModel<T> extends AbstractTableModel {

    protected final List<T> rows;
    private final List<Column<?, T>> columns;

    public TableModel() {
        super();
        this.columns = new ArrayList<>();
        this.rows = new ArrayList<>();
    }


    protected TableModel<T> addColumn(Column<?, T> column) {
        columns.add(column);
        return this;
    }

    @Override
    public int getRowCount() {
        return rows.size();
    }

    @Override
    public int getColumnCount() {
        return columns.size();
    }

    @Override
    public String getColumnName(int columnIndex) {
        checkColumnIndex(columnIndex);
        return columns.get(columnIndex).columnName;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        checkColumnIndex(columnIndex);
        return columns.get(columnIndex).columnClass;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        checkColumnIndex(columnIndex);
        checkRowIndex(rowIndex);
        columns.get(columnIndex).setValue(rows.get(rowIndex), aValue);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        checkColumnIndex(columnIndex);
        checkRowIndex(rowIndex);
        return columns.get(columnIndex).getValue(rows.get(rowIndex));
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columns.get(columnIndex).isEditable();
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

    public T getRow(int rowIndex) {
        return rows.get(rowIndex);
    }

    protected class Column<ColT, RowT> {
        public Class<RowT> rowClass;
        public String columnName;
        public Class<ColT> columnClass;

        private String attributeName;
        private final boolean editable;

        private final Function<Object, ColT> getter;
        private final BiConsumer<Object, Object> setter;

        public Column(String columnName, String attributeName, boolean editable, Class<ColT> columnClass, Class<RowT> rowClass, Function<Object, ColT> getter, BiConsumer<Object, Object> setter) {
            this.columnName = columnName;
            this.attributeName = attributeName;
            this.editable = editable;
            this.columnClass = columnClass;
            this.rowClass = rowClass;
            this.getter = getter;
            this.setter = setter;
        }

        public ColT getValue(Object container) {
            // TODO check type
            return getter.apply(container);
        }

        public void setValue(Object object, Object value) {
            // TODO check type
            setter.accept(object, value);
        }

        public boolean isEditable() {
            return editable;
        }

    }


    private void checkRowIndex(int rowIndex) {
        if (rowIndex > rows.size() || rowIndex < 0) {
            throw new IndexOutOfBoundsException("Invalid row index: " + rowIndex);
        }
    }

    private void checkColumnIndex(int columnIndex) {
        if (columnIndex > columns.size() || columnIndex < 0) {
            throw new IndexOutOfBoundsException("Invalid column index: " + columnIndex);
        }
    }

}
