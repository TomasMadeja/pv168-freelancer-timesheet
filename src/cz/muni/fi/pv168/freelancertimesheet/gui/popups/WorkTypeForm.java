package cz.muni.fi.pv168.freelancertimesheet.gui.popups;

import cz.muni.fi.pv168.freelancertimesheet.gui.GenericElement;

import javax.swing.*;
import java.awt.*;

public class WorkTypeForm extends JPanel implements GenericElement<WorkTypeForm> {

    public WorkTypeForm() {
        super();
    }

    @Override
    public WorkTypeForm setupLayout() {
        GridBagLayout layout = new GridBagLayout();
        setLayout(layout);
        return this;
    }

    @Override
    public WorkTypeForm setupVisuals() {
        return this;
    }

    @Override
    public WorkTypeForm setupNested() {
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.LINE_START;
        constraints.insets = new Insets(10, 10, 10, 10);

        constraints.weighty = 0;
        constraints.weightx = 0;

        constraints.gridx = 0;
        constraints.gridy = 0;
        add(new JLabel("Name:"), constraints);
        constraints.gridy = 1;
        add(new JLabel(("Rate:")), constraints);
        constraints.gridy = 2;
        constraints.weighty = 1;
        constraints.fill = GridBagConstraints.BOTH;
        JLabel descriptionLabel = new JLabel("Description:");
        descriptionLabel.setVerticalAlignment(JLabel.TOP);
        add(descriptionLabel, constraints);

        constraints.weighty = 0;
        constraints.weightx = 1;

        constraints.gridx = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.ipadx = 100;

        constraints.gridy = 0;
        add(new JTextField(), constraints);
        constraints.gridy = 1;
        add(new JTextField(), constraints);
        constraints.gridy = 2;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weighty = 1;
        constraints.ipady = 40;
        add(new JTextField(), constraints);

        return this;
    }

    public static WorkTypeForm setup() {
        WorkTypeForm panel = new WorkTypeForm();
        panel
                .setupLayout()
                .setupVisuals()
                .setupNested();
        return panel;
    }

    //test method
    public static void test() {
        WorkTypeForm test = WorkTypeForm.setup();
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(test);
        frame.pack();
        frame.setVisible(true);
    }
}
