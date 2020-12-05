package cz.muni.fi.pv168.freelancertimesheet.gui.popups.worktype.table;

import cz.muni.fi.pv168.freelancertimesheet.backend.interfaces.WorkType;
import cz.muni.fi.pv168.freelancertimesheet.gui.GenericElement;

import javax.swing.*;
import java.awt.*;

public class ChooseWorkTypeWindow extends JFrame implements GenericElement<ChooseWorkTypeWindow> {
    private final WorkType workType;

    public ChooseWorkTypeWindow(WorkType workType) {
        super("Choose Work Type");
        this.workType = workType;
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
        this.add(ChooseWorkType.setup(workType));

        return this;
    }

    public static ChooseWorkTypeWindow setup(WorkType workType) {
        ChooseWorkTypeWindow chooseWorkTypeWindow = new ChooseWorkTypeWindow(workType);
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
