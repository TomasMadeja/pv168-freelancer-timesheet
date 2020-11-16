package cz.muni.fi.pv168.freelancertimesheet.gui.popups.worktype.forms;

import cz.muni.fi.pv168.freelancertimesheet.backend.interfaces.ServiceType;
import cz.muni.fi.pv168.freelancertimesheet.gui.GenericElement;
import cz.muni.fi.pv168.freelancertimesheet.gui.exampledata.WorkType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

public class WorkTypeForm extends JPanel implements GenericElement<WorkTypeForm> {

    private final JTextField nameTextField = new JTextField();
    private final JTextField rateTextField = new JTextField();
    private final JTextArea descriptionTextArea = new JTextArea();
    private final JScrollPane descriptionScrollPane = new JScrollPane(descriptionTextArea);

    public WorkTypeForm(){
        super();
        rateTextField.setText(Double.toString(0));
    }

    public static WorkTypeForm setup() {
        WorkTypeForm form = new WorkTypeForm();
        form
                .setupLayout()
                .setupVisuals()
                .setupNested();
        return form;
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
        add(nameTextField, constraints);
        constraints.gridy = 1;
        add(rateTextField, constraints);
        constraints.gridy = 2;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weighty = 1;
        constraints.ipady = 40;
        add(descriptionScrollPane, constraints);

        constraints.gridy = 3;
        constraints.gridx = 1;
        constraints.anchor = GridBagConstraints.LAST_LINE_START;
        constraints.ipady = 10;
        constraints.fill = GridBagConstraints.NONE;
        JButton addButton = new JButton("confirm");
        addButton.addActionListener(e -> {
            resetForm();
            disableFields();
        });
        add(addButton, constraints);

        disableFields();

        return this;
    }

    public void fillForm(ServiceType workType) {
        nameTextField.setText(workType.getName());
        rateTextField.setText(workType.getHourlyRate().toString());
        descriptionTextArea.setText(workType.getDescription());
        enableFields();
    }

    public void resetForm() {
        nameTextField.setText("");
        rateTextField.setText(Double.toString(0));
        descriptionTextArea.setText("");
        enableFields();
    }

    public void enableFields()  {
        nameTextField.setEnabled(true);
        rateTextField.setEnabled(true);
        descriptionTextArea.setEnabled(true);
    }

    public void disableFields() {
        nameTextField.setEnabled(false);
        rateTextField.setEnabled(false);
        descriptionTextArea.setEnabled(false);
    }
}