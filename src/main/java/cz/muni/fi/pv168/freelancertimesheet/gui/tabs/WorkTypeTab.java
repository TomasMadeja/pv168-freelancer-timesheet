package cz.muni.fi.pv168.freelancertimesheet.gui.tabs;

import cz.muni.fi.pv168.freelancertimesheet.gui.GenericElement;
import cz.muni.fi.pv168.freelancertimesheet.gui.popups.worktype.table.WorkTypeTable;
import cz.muni.fi.pv168.freelancertimesheet.gui.tabs.task.TaskTable;

import javax.swing.*;
import java.awt.*;

public class WorkTypeTab extends JPanel implements GenericElement<WorkTypeTab> {
    public WorkTypeTab() {
        super();
    }

    @Override
    public WorkTypeTab setupLayout() {
        GridLayout layout = new GridLayout(1, 1);
        this.setLayout(layout);
        return this;
    }

    @Override
    public WorkTypeTab setupVisuals() {
        return this;
    }

    @Override
    public WorkTypeTab setupNested() {
        add(WorkTypeTable.setup());
        return this;
    }

    public static WorkTypeTab setup() {
        return new WorkTypeTab()
                .setupLayout()
                .setupVisuals()
                .setupNested();
    }
}
