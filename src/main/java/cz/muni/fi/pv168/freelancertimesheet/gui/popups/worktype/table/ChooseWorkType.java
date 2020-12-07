package cz.muni.fi.pv168.freelancertimesheet.gui.popups.worktype.table;

import cz.muni.fi.pv168.freelancertimesheet.backend.interfaces.WorkType;
import cz.muni.fi.pv168.freelancertimesheet.gui.GenericElement;
import cz.muni.fi.pv168.freelancertimesheet.gui.containers.WorkTypeContainer;
import cz.muni.fi.pv168.freelancertimesheet.gui.models.FormModel;
import cz.muni.fi.pv168.freelancertimesheet.gui.models.TableModel;
import cz.muni.fi.pv168.freelancertimesheet.gui.models.WorkTypeTableModel;
import cz.muni.fi.pv168.freelancertimesheet.gui.popups.workform.iWorkTypeSetter;

import javax.swing.*;
import java.awt.*;

public class ChooseWorkType extends JPanel implements GenericElement<ChooseWorkType> {

    private final iWorkTypeSetter workTypeSetter;
    private final WorkTypeContainer container;
    private JTable table;

    protected FormModel.Callback confirmCallback;

    private final JButton confirm = new JButton("Select Work Type");

    public ChooseWorkType(iWorkTypeSetter workTypeSetter) {
        this.workTypeSetter = workTypeSetter;
        container = new WorkTypeContainer();
        selectionChanged(0);
    }

    @Override
    public ChooseWorkType setupLayout() {
        setLayout(new GridBagLayout());
        return this;
    }

    @Override
    public ChooseWorkType setupVisuals() {
        return this;
    }

    @Override
    public ChooseWorkType setupNested() {
        var constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        add(buildTable(), constraints);
        confirm.addActionListener(e -> {
                    var selectedWorkType = (WorkType) ((TableModel<WorkType>) table.getModel()).getDataFromContainer(table.getSelectedRow());
                    workTypeSetter.setWorkType(selectedWorkType);
                    triggerConfirmCallback();
                }
        );
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 0;
        constraints.gridheight = 0;
        add(confirm, constraints);
        return this;
    }

    private void triggerConfirmCallback() {
        if (confirmCallback != null) {
            confirmCallback.call();
        }
    }

    public void setConfirmCallback(FormModel.Callback callback) {
        confirmCallback = callback;
    }

    private Component buildTable() {
        var tableModel = new WorkTypeTableModel(container);
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getSelectionModel().addListSelectionListener(e -> selectionChanged(table.getSelectedRows().length));
        return new JScrollPane(table);
    }

    public static ChooseWorkType setup(iWorkTypeSetter workTypeSetter) {
        ChooseWorkType chooseWorkType = new ChooseWorkType(workTypeSetter);
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
