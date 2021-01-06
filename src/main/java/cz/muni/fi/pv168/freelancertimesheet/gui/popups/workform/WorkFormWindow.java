package cz.muni.fi.pv168.freelancertimesheet.gui.popups.workform;

import cz.muni.fi.pv168.freelancertimesheet.backend.DBConnectionUtils;
import cz.muni.fi.pv168.freelancertimesheet.backend.interfaces.Work;
import cz.muni.fi.pv168.freelancertimesheet.gui.GenericElement;
import cz.muni.fi.pv168.freelancertimesheet.gui.actions.table.AddAction;
import cz.muni.fi.pv168.freelancertimesheet.gui.containers.WorkContainer;

import javax.swing.*;
import java.awt.*;

public class WorkFormWindow extends JFrame implements GenericElement<WorkFormWindow> {

    private WorkForm workForm;
    private final AddAction.Callback callback;
    private final WorkContainer container;

    public WorkFormWindow(AddAction.Callback callback, WorkContainer container) {
        super();
        this.callback = callback;
        this.container = container;
    }

    @Override
    public WorkFormWindow setupLayout() {
        GridLayout layout = new GridLayout(1, 1);
        this.setLayout(layout);
        this.setTitle("New Task");
        return this;
    }

    @Override
    public WorkFormWindow setupVisuals() {
        return this;
    }

    @Override
    public WorkFormWindow setupNested() {
        workForm = WorkForm.setup(container);
        workForm.setConfirmCallback(
                () -> {
                    if (callback != null) callback.call();
                    this.dispose();
                }
        );
        workForm.setCancelCallback(this::dispose);

        Dimension preferredSize = workForm.getPreferredSize();
        this.setPreferredSize(new Dimension(preferredSize.width * 2, preferredSize.height * 2));
        this.add(workForm);
        return this;
    }

    public static WorkFormWindow setup(AddAction.Callback callback, WorkContainer container) {
        var taskFormWindow = new WorkFormWindow(callback, container)
                .setupLayout()
                .setupVisuals()
                .setupNested();
        taskFormWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        taskFormWindow.pack();
        taskFormWindow.setVisible(true);
        return taskFormWindow;

    }
}
