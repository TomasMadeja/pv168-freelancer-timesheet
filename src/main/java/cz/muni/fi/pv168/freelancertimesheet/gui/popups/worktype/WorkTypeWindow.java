package cz.muni.fi.pv168.freelancertimesheet.gui.popups.worktype;

import cz.muni.fi.pv168.freelancertimesheet.gui.GenericElement;
import cz.muni.fi.pv168.freelancertimesheet.gui.popups.worktype.forms.WorkTypeForm;
import cz.muni.fi.pv168.freelancertimesheet.gui.popups.worktype.table.*;

import javax.swing.*;
import java.awt.*;

public class WorkTypeWindow extends JFrame implements GenericElement<WorkTypeWindow> {
    public WorkTypeWindow() {
        super("Work Type");
    }

    @Override
    public WorkTypeWindow setupLayout() {
        GridLayout layout = new GridLayout(2, 1);
        this.setLayout(layout);
        return this;
    }

    @Override
    public WorkTypeWindow setupVisuals() {
        return this;
    }

    @Override
    public WorkTypeWindow setupNested() {
        WorkTypeForm workTypeForm = WorkTypeForm.setup();
        Dimension preferredSize = workTypeForm.getPreferredSize();
        this.setPreferredSize(new Dimension(preferredSize.width, preferredSize.height * 4));
        this.add(workTypeForm);
        this.add(WorkTypeTable.setup(workTypeForm, this));

        return this;
    }

    public static WorkTypeWindow setup() {
        WorkTypeWindow workTypeWindow = new WorkTypeWindow();
        workTypeWindow
                .setupLayout()
                .setupVisuals()
                .setupNested();
        workTypeWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        workTypeWindow.pack();
        workTypeWindow.setVisible(true);
        return workTypeWindow;
    }

}
