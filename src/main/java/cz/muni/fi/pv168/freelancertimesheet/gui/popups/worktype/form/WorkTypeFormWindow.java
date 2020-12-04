package cz.muni.fi.pv168.freelancertimesheet.gui.popups.worktype.form;

import cz.muni.fi.pv168.freelancertimesheet.gui.GenericElement;
import cz.muni.fi.pv168.freelancertimesheet.gui.actions.table.AddAction;

import javax.swing.*;
import java.awt.*;

public class WorkTypeFormWindow extends JFrame implements GenericElement<WorkTypeFormWindow> {
    private final AddAction.Callback callback;

    public WorkTypeFormWindow(AddAction.Callback callback) {
        super("WorkType");
        this.callback = callback;
    }

    @Override
    public WorkTypeFormWindow setupLayout() {
        GridLayout layout = new GridLayout(1, 1);
        this.setLayout(layout);
        return this;
    }

    @Override
    public WorkTypeFormWindow setupVisuals() {
        return this;
    }

    @Override
    public WorkTypeFormWindow setupNested() {
        WorkTypeForm workTypeForm = WorkTypeForm.setup(this);
        workTypeForm.setConfirmCallback(
                () -> {
                    if (callback != null) callback.call();
                }
        );
        workTypeForm.setCancelCallback(
                () -> {
                    this.dispose();
                }
        );
        Dimension preferredSize = workTypeForm.getPreferredSize();
        this.setPreferredSize(new Dimension(preferredSize.width*2, preferredSize.height*2));
        this.add(workTypeForm);
        return this;
    }

    public static WorkTypeFormWindow setup(AddAction.Callback callback) {
        WorkTypeFormWindow workTypeFormWindow = new WorkTypeFormWindow(callback);
        workTypeFormWindow
                .setupLayout()
                .setupVisuals()
                .setupNested();
        workTypeFormWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        workTypeFormWindow.pack();
        workTypeFormWindow.setVisible(true);
        return workTypeFormWindow;
    }
}
