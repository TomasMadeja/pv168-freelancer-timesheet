package cz.muni.fi.pv168.freelancertimesheet.gui.popups.worktype.form;

import cz.muni.fi.pv168.freelancertimesheet.backend.PersistanceManager;
import cz.muni.fi.pv168.freelancertimesheet.backend.interfaces.Invoice;
import cz.muni.fi.pv168.freelancertimesheet.backend.interfaces.WorkType;
import cz.muni.fi.pv168.freelancertimesheet.backend.orm.WorkTypeImpl;
import cz.muni.fi.pv168.freelancertimesheet.gui.I18N;
import cz.muni.fi.pv168.freelancertimesheet.gui.containers.InvoiceContainer;
import cz.muni.fi.pv168.freelancertimesheet.gui.containers.WorkTypeContainer;
import cz.muni.fi.pv168.freelancertimesheet.gui.models.FormModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URISyntaxException;

public class WorkTypeForm extends FormModel {

    private final I18N i18n = new I18N(getClass());

    private final JTextField nameTextField = new JTextField();
    private final JTextField rateTextField = new JTextField();
    private final JTextArea descriptionTextArea = new JTextArea();
    private final JScrollPane descriptionScrollPane = new JScrollPane(descriptionTextArea);

    private WorkTypeContainer container;

    public WorkTypeForm(WorkTypeContainer container) {
        super();
        rateTextField.setText(Double.toString(0));
        this.container = container;
    }

    public static WorkTypeForm setup(WorkTypeContainer container) {
        WorkTypeForm form = new WorkTypeForm(container);
        form
                .setupLayout()
                .setupVisuals()
                .setupNested();
        return form;
    }

    @Override
    public WorkTypeForm setupNested() {
        addRow(new JLabel(i18n.getString("name")), nameTextField);
        addRow(new JLabel(i18n.getString("rate")), rateTextField);
        JLabel descriptionLabel = new JLabel(i18n.getString("description"));
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
            JOptionPane.showMessageDialog(this, i18n.getString("invHourlyRateMessage"), i18n.getString("invHourlyRateTitle"), JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    private WorkType validateData(WorkType workType) {
        if (workType == null) return null;
        if (workType.getName().isEmpty()) {
            JOptionPane.showMessageDialog(this, i18n.getString("emptyNameMessage"), i18n.getString("emptyNameTitle"), JOptionPane.ERROR_MESSAGE);
            return null;
        }
        return workType;
    }

    private void addDataToDatabase(WorkType workType) {
        workType = validateData(workType);
        if (workType == null) return;
        PersistanceManager.persistWorkType(workType);
    }

    private void makeConfirmAddData() {
        confirmButton.addActionListener(
                (ActionEvent e) -> {
                    confirmButton.setEnabled(false);
                    WorkType workType = getDataFromForm();
                    new SwingWorker<Void, Void>() {
                        @Override
                        public Void doInBackground() {
                            addDataToDatabase(workType);
                            if (container != null) container.refresh();
                            return null;
                        }

                        @Override
                        protected void done() {
                            try {
                                if (confirmCallback != null)
                                    confirmCallback.call();
                            } catch (Exception ignore) {
                            } finally {
                                confirmButton.setEnabled(true);
                            }
                        }
                    }.execute();
                }
        );
    }
}
