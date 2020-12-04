package cz.muni.fi.pv168.freelancertimesheet.gui.popups.worktype.table;

import cz.muni.fi.pv168.freelancertimesheet.backend.interfaces.WorkType;
import cz.muni.fi.pv168.freelancertimesheet.gui.GenericElement;
import cz.muni.fi.pv168.freelancertimesheet.gui.containers.WorkTypeContainer;
import cz.muni.fi.pv168.freelancertimesheet.gui.models.TableModel;
import cz.muni.fi.pv168.freelancertimesheet.gui.models.WorkTypeTableModel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ChooseWorkType extends JPanel implements GenericElement<ChooseWorkType> {
    private final Method getSelectedWorkType;
    private final WorkTypeContainer container;
    private JTable table;

    private final JButton confirm = new JButton("Select Work Type");

    public ChooseWorkType(Method getSelectedWorkType) {
        this.getSelectedWorkType = getSelectedWorkType;
        container = new WorkTypeContainer();
        selectionChanged(0);
    }

    @Override
    public ChooseWorkType setupLayout() {
        setLayout(new GridLayout(2, 1));
        return this;
    }

    @Override
    public ChooseWorkType setupVisuals() {
        return this;
    }

    @Override
    public ChooseWorkType setupNested() {
        add(buildTable());
        confirm.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                var wt = table.getSelectedRow();
                var s = (WorkType)((TableModel) table.getModel()).getDataFromContainer(wt);
                try {
                    getSelectedWorkType.invoke(s);
                } catch (IllegalAccessException | InvocationTargetException illegalAccessException) {
                    illegalAccessException.printStackTrace();
                }
            }
        });
        add(confirm);
        return this;
    }

    private Component buildTable() {
        var tableModel = new WorkTypeTableModel(container);
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getSelectionModel().addListSelectionListener(e -> selectionChanged(table.getSelectedRows().length));
        return new JScrollPane(table);
    }

    public static ChooseWorkType setup(Method getSelectedWorkType) {
        ChooseWorkType chooseWorkType = new ChooseWorkType(getSelectedWorkType);
        chooseWorkType
                .setupVisuals()
                .setupLayout()
                .setupNested();
        return chooseWorkType;
    }

    public void selectionChanged(int nor) {
        confirm.setEnabled(nor == 1);
    }
}
