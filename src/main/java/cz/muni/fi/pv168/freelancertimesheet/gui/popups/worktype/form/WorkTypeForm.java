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
    public WorkTypeForm setupNested() {
        addRow(new JLabel("Name:"), nameTextField);
        addRow(new JLabel("Hourly rate:"), rateTextField);
        JLabel descriptionLabel = new JLabel("Description:");
        addRow(descriptionLabel, descriptionScrollPane);
        addConfirmButton();
        makeConfirmAddData();
        return this;
    }

    public void fillForm(WorkType workType) {
        nameTextField.setText(workType.getName());
        rateTextField.setText(workType.getHourlyRate().toString());
        descriptionTextArea.setText(workType.getDescription());
        enableForm();
    }

    private WorkType getDataFromForm() {
        return WorkTypeImpl.createWorkType(
                nameTextField.getText(),
                descriptionTextArea.getText(),
                rateTextField.getText()
        );
    }

    private void validateData(WorkType workType) {
        workType.validateAttributes();
    }

    private void addDataToDatabase() {
        WorkType workType = getDataFromForm();
        validateData(workType);
        PersistanceManager.persistWorkType(workType);
    }

    private void makeConfirmAddData() {
        confirmButton.addActionListener((ActionEvent e) -> addDataToDatabase());
    }
}
