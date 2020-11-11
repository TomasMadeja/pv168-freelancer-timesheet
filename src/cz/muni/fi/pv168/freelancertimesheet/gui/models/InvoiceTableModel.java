package cz.muni.fi.pv168.freelancertimesheet.gui.models;

import javax.swing.table.AbstractTableModel;

public class InvoiceTableModel extends AbstractTableModel {

    public InvoiceTableModel() {
        super();
    }

    private class Column {
        public String columnName;
        public Class<?> columnClass;

        private String attributeName;

        public Column(String columnName, String attributeName, Class<?> columnClass) {
            this.columnName = columnName;
            this.attributeName = attributeName;
            this.columnClass = columnClass;
        }

        public Object getValue() {
            return null;
        }
    }

    private Column[] columns = {
            new Column(
                    "Test1",
                    "test1",
                    String.class
            ),
            new Column(
                    "Test2",
                    "test2",
                    String.class
            )
    };

    @Override
    public int getRowCount() {
        return 1;
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
        return columnIndex == 0 ? "A" : "B";
    }
}
