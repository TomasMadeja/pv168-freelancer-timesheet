package cz.muni.fi.pv168.freelancertimesheet.gui.popups.worktype.table;

import cz.muni.fi.pv168.freelancertimesheet.gui.GenericElement;
import cz.muni.fi.pv168.freelancertimesheet.gui.popups.worktype.form.WorkTypeForm;

import javax.swing.*;
import java.awt.*;

public class WorkTypeTableWindow extends JFrame implements GenericElement<WorkTypeTableWindow> {
    public WorkTypeTableWindow() {
        super("Work Type");
    }

    @Override
    public WorkTypeTableWindow setupLayout() {
        GridLayout layout = new GridLayout(1, 1);
        this.setLayout(layout);
        return this;
    }

    @Override
    public WorkTypeTableWindow setupVisuals() {
        return this;
    }

    @Override
    public WorkTypeTableWindow setupNested() {
        this.add(WorkTypeTable.setup());
        return this;
    }

    public static WorkTypeTableWindow setup() {
        WorkTypeTableWindow workTypeTableWindow = new WorkTypeTableWindow();
        workTypeTableWindow
                .setupLayout()
                .setupVisuals()
                .setupNested();
        workTypeTableWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        workTypeTableWindow.pack();
        workTypeTableWindow.setVisible(true);
        return workTypeTableWindow;
    }

}
