package cz.muni.fi.pv168.freelancertimesheet.gui.models;

import javax.swing.table.AbstractTableModel;
import java.util.Date;
import java.util.function.Function;

public class InvoiceTableModel extends AbstractTableModel {

    public InvoiceTableModel() {
        super();
    }

    private static class Column {
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

//    private interface AttributePicker {
//
//    }

    private Column[] columns = {
            new Column(
                    "Invoice Date",
                    "test1",
                    Date.class,
                    Date.class, // TODO replace with actual object
                    (Object object) -> new Date()
            ),
            new Column(
                    "Due Date",
                    "test2",
                    Date.class,
                    Date.class, // TODO replace with actual object
                    (Object object) -> new Date()
            ),
            new Column(
                    "ICO",
                    "test3",
                    String.class,
                    Date.class, // TODO replace with actual object
                    (Object object) -> "25596641"
            ),
            new Column(
                    "DIC",
                    "test3",
                    String.class,
                    Date.class, // TODO replace with actual object
                    (Object object) -> "CZ1234567890"
            ),
            new Column(
                    "Customer Name",
                    "test3",
                    String.class,
                    Date.class, // TODO replace with actual object
                    (Object object) -> "Test Customer"
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
        return columns[columnIndex].getValue(new Date());
    }
}
