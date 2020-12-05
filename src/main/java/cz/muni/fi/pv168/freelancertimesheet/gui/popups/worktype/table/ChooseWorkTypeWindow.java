package cz.muni.fi.pv168.freelancertimesheet.gui.popups.worktype.table;

import cz.muni.fi.pv168.freelancertimesheet.gui.GenericElement;
import cz.muni.fi.pv168.freelancertimesheet.gui.popups.workform.iWorkTypeSetter;

import javax.swing.*;
import java.awt.*;

public class ChooseWorkTypeWindow extends JFrame implements GenericElement<ChooseWorkTypeWindow> {

    private final iWorkTypeSetter workTypeSetter;

    public ChooseWorkTypeWindow(iWorkTypeSetter workTypeSetter) {
        super("Choose Work Type");
        this.workTypeSetter = workTypeSetter;
    }

    @Override
    public ChooseWorkTypeWindow setupLayout() {
        GridLayout layout = new GridLayout(1, 1);
        this.setLayout(layout);
        return this;
    }

    @Override
    public ChooseWorkTypeWindow setupVisuals() {
        return this;
    }

    @Override
    public ChooseWorkTypeWindow setupNested() {
        var chooseWorkType = ChooseWorkType.setup(workTypeSetter);
        chooseWorkType.setConfirmCallback(this::dispose);
        this.add(chooseWorkType);

        return this;
    }

    public static ChooseWorkTypeWindow setup(iWorkTypeSetter workTypeSetter) {
        ChooseWorkTypeWindow chooseWorkTypeWindow = new ChooseWorkTypeWindow(workTypeSetter);
        chooseWorkTypeWindow
                .setupLayout()
                .setupVisuals()
                .setupNested();
        chooseWorkTypeWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        chooseWorkTypeWindow.pack();
        chooseWorkTypeWindow.setVisible(true);
        return chooseWorkTypeWindow;
    }
}
