package cz.muni.fi.pv168.freelancertimesheet.gui.popups.worktype.form;

import cz.muni.fi.pv168.freelancertimesheet.backend.PersistanceManager;
import cz.muni.fi.pv168.freelancertimesheet.backend.interfaces.WorkType;
import cz.muni.fi.pv168.freelancertimesheet.backend.orm.WorkTypeImpl;
import cz.muni.fi.pv168.freelancertimesheet.gui.models.FormModel;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class WorkTypeForm extends FormModel {

    private final JTextField nameTextField = new JTextField();
    private final JTextField rateTextField = new JTextField();
    private final JTextArea descriptionTextArea = new JTextArea();
    private final JScrollPane descriptionScrollPane = new JScrollPane(descriptionTextArea);

    private final JFrame parentFrame;

    public WorkTypeForm(JFrame parentFrame){
        super();
        this.parentFrame = parentFrame;
        rateTextField.setText(Double.toString(0));
    }

    public static WorkTypeForm setup(JFrame parentFrame) {
        WorkTypeForm form = new WorkTypeForm(parentFrame);
        form
                .setupLayout()
                .setupVisuals()
                .setupNested();
        return form;
    }

    @Override
    public WorkTypeForm setupNested() {
        addRow(new JLabel("Name:"), nameTextField);
        addRow(new JLabel("Hourly rate:"), rateTextField);
        JLabel descriptionLabel = new JLabel("Description:");
        addRow(descriptionLabel, descriptionScrollPane);
        addConfirmButton();
        makeConfirmAddData();
        return this;
    }

    private WorkType getDataFromForm() {
        try {
            return WorkTypeImpl.createWorkType(
                    nameTextField.getText(),
                    descriptionTextArea.getText(),
                    rateTextField.getText()
            );
        }
        catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid hourly rate, valid are only numbers and ',' for decimal numbers!", "Invalid hourly rate", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    private WorkType validateData(WorkType workType) {
        if (workType == null) return null;
        if (workType.getName().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Field name cannot be empty!", "Invalid name!", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        return workType;
    }

    private void addDataToDatabase() {
        WorkType workType = getDataFromForm();
        workType = validateData(workType);
        if (workType == null) return;
        PersistanceManager.persistWorkType(workType);
        parentFrame.dispose();
    }

    private void makeConfirmAddData() {
        confirmButton.addActionListener((ActionEvent e) -> addDataToDatabase());
    }
}
