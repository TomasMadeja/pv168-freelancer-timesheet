package cz.muni.fi.pv168.freelancertimesheet.gui.popups.worktype.form;

import cz.muni.fi.pv168.freelancertimesheet.gui.GenericElement;
import cz.muni.fi.pv168.freelancertimesheet.gui.I18N;
import cz.muni.fi.pv168.freelancertimesheet.gui.actions.table.AddAction;
import cz.muni.fi.pv168.freelancertimesheet.gui.containers.WorkTypeContainer;

import javax.swing.*;
import java.awt.*;

public class WorkTypeFormWindow extends JFrame implements GenericElement<WorkTypeFormWindow> {

    private static final I18N i18n = new I18N(WorkTypeFormWindow.class);

    private final AddAction.Callback callback;
    private WorkTypeContainer container;

    public WorkTypeFormWindow(AddAction.Callback callback, WorkTypeContainer container) {
        super(i18n.getString("title"));
        this.callback = callback;
        this.container = container;
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
        WorkTypeForm workTypeForm = WorkTypeForm.setup(container);
        workTypeForm.setConfirmCallback(
                () -> {
                    if (callback != null) callback.call();
                    this.dispose();
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

    public static WorkTypeFormWindow setup(AddAction.Callback callback, WorkTypeContainer container) {
        WorkTypeFormWindow workTypeFormWindow = new WorkTypeFormWindow(callback, container);
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
