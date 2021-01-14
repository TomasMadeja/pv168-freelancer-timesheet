package cz.muni.fi.pv168.freelancertimesheet.gui.popups.worktype.table;

import cz.muni.fi.pv168.freelancertimesheet.backend.interfaces.WorkType;
import cz.muni.fi.pv168.freelancertimesheet.gui.GenericElement;
import cz.muni.fi.pv168.freelancertimesheet.gui.I18N;
import cz.muni.fi.pv168.freelancertimesheet.gui.containers.WorkTypeContainer;
import cz.muni.fi.pv168.freelancertimesheet.gui.models.FormModel;
import cz.muni.fi.pv168.freelancertimesheet.gui.models.TableModel;
import cz.muni.fi.pv168.freelancertimesheet.gui.popups.workform.iWorkTypeSetter;

import javax.swing.*;
import java.awt.*;

public class ChooseWorkType extends JPanel implements GenericElement<ChooseWorkType> {

    private final I18N i18n = new I18N(getClass());

    private final iWorkTypeSetter workTypeSetter;
    private final WorkTypeContainer container;
    private JTable table;
    private final JPanel workTypeTable;
    private WorkTypeTableModel tableModel;
    protected FormModel.Callback confirmCallback;

    private final JButton confirm = new JButton(i18n.getString("select"));

    public ChooseWorkType(iWorkTypeSetter workTypeSetter) {
        this.workTypeSetter = workTypeSetter;
        container = new WorkTypeContainer();
        selectionChanged(0);
        workTypeTable = WorkTypeTable.setup();
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

        add(workTypeTable, constraints);

        table = getTable(workTypeTable);
        confirm.setEnabled(true);
        confirm.addActionListener(e -> {
                    if (table != null and tableModel != null) {
                        var selectedRow = table.getSelectedRow();
                        if (selectedRow != -1) {
                            var selectedWorkType = tableModel.getRow(selectedRow);
                            workTypeSetter.setWorkType(selectedWorkType);
                            triggerConfirmCallback();
                        }
                    }
                }
        );

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 0;
        constraints.gridheight = 0;
        this.add(confirm, constraints);
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

    // Find JTable component recursively
    private JTable getTable(Component container) {
        Container component = (Container) container;
        for (Component child : component.getComponents()) {
            if (child instanceof JTable) {
                return (JTable) child;
            }
            var output = getTable(child);
            if (output != null) {
                return output;
            }
        }
        return null;
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
