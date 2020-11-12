package cz.muni.fi.pv168.freelancertimesheet.gui.popups.worktype.forms;

import cz.muni.fi.pv168.freelancertimesheet.gui.GenericElement;
import cz.muni.fi.pv168.freelancertimesheet.gui.exampledata.WorkType;

import javax.swing.*;
import java.awt.*;

public class WorkTypeForm extends JPanel implements GenericElement<WorkTypeForm> {

    private final String name;
    private final double rate;
    private final String description;
    private String buttonName;

    private Action action;

    public WorkTypeForm(){
        super();
        this.name = "";
        this.rate = 0;
        this.description = "";
    }

    public WorkTypeForm(String name, double rate, String description) {
        this.name = name;
        this.rate = rate;
        this.description = description;
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
        add(new JTextField(name), constraints);
        constraints.gridy = 1;
        add(new JTextField(Double.toString(rate)), constraints);
        constraints.gridy = 2;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weighty = 1;
        constraints.ipady = 40;
        JTextArea descriptionTextArea = new JTextArea(description);
        JScrollPane descriptionScrollPane = new JScrollPane(descriptionTextArea);
        add(descriptionScrollPane, constraints);

        constraints.gridy = 3;
        constraints.gridx = 1;
        constraints.anchor = GridBagConstraints.LAST_LINE_END;
        constraints.ipady = 10;
        constraints.fill = GridBagConstraints.NONE;
        JButton addButton = new JButton(buttonName);
        addButton.addActionListener(action);
        add(addButton, constraints);

        return this;
    }

    public static WorkTypeForm setupAdd(JFrame window) {
        WorkTypeForm panel = new WorkTypeForm();
        panel.action = new AddAction(window);
        panel.buttonName = "Add";
        panel
                .setupLayout()
                .setupVisuals()
                .setupNested();
        return panel;
    }

    public static void displayAdd() {
        JFrame frame = new JFrame("Add work type");
        WorkTypeForm test = WorkTypeForm.setupAdd(frame);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(test);
        frame.pack();
        frame.setVisible(true);
    }

    public static WorkTypeForm setupEdit(JFrame window, WorkType workType) {
        WorkTypeForm panel = new WorkTypeForm(workType.name, workType.rate, workType.description);
        panel.action = new EditAction(window);
        panel.buttonName = "Save";
        panel
                .setupLayout()
                .setupVisuals()
                .setupNested();
        return panel;
    }

    public static void displayEdit(WorkType workType) {
        JFrame frame = new JFrame("Edit work type");
        WorkTypeForm test = WorkTypeForm.setupEdit(frame, workType);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(test);
        frame.pack();
        frame.setVisible(true);
    }
}
