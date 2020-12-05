package cz.muni.fi.pv168.freelancertimesheet.gui.popups.worktype.table;

import cz.muni.fi.pv168.freelancertimesheet.gui.GenericElement;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Method;

public class ChooseWorkTypeWindow extends JFrame implements GenericElement<ChooseWorkTypeWindow> {
    private final Method getSelectedWorkType;

    public ChooseWorkTypeWindow(Method getSelectedWorkType) {
        super("Choose Work Type");
        this.getSelectedWorkType = getSelectedWorkType;
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
        this.add(ChooseWorkType.setup(getSelectedWorkType));

        return this;
    }

    public static ChooseWorkTypeWindow setup(Method getSelectedWorkType) {
        ChooseWorkTypeWindow chooseWorkTypeWindow = new ChooseWorkTypeWindow(getSelectedWorkType);
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
