package cz.muni.fi.pv168.freelancertimesheet.gui.popups.issuer.form;

import cz.muni.fi.pv168.freelancertimesheet.backend.PersistanceManager;
import cz.muni.fi.pv168.freelancertimesheet.backend.interfaces.Issuer;
import cz.muni.fi.pv168.freelancertimesheet.backend.interfaces.WorkType;
import cz.muni.fi.pv168.freelancertimesheet.backend.orm.IssuerImpl;
import cz.muni.fi.pv168.freelancertimesheet.backend.orm.WorkTypeImpl;
import cz.muni.fi.pv168.freelancertimesheet.gui.I18N;
import cz.muni.fi.pv168.freelancertimesheet.gui.containers.WorkTypeContainer;
import cz.muni.fi.pv168.freelancertimesheet.gui.models.FormModel;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class IssuerForm extends FormModel {

    private final I18N i18n = new I18N(getClass());

    private final JTextField nameField = new JTextField();
    private final JTextField addressField = new JTextField();
    private final JTextField icoField = new JTextField();
    private final JTextField dicField = new JTextField();
    private final JTextField phoneNumberField = new JTextField();
    private final JTextField emailField = new JTextField();


    public IssuerForm() {
        super();
    }

    public IssuerForm(Issuer issuer) {
        super();
        nameField.setText(issuer.getName());
        addressField.setText(issuer.getAddress());
        icoField.setText(issuer.getICO());
        dicField.setText(issuer.getDIC());
        emailField.setText(issuer.getEmail());
        phoneNumberField.setText(issuer.getPhoneNumber());
    }

    public static IssuerForm setup(Issuer issuer) {
        IssuerForm form = issuer != null ? new IssuerForm(issuer): new IssuerForm();
        form
                .setupLayout()
                .setupVisuals()
                .setupNested();
        return form;
    }

    @Override
    public IssuerForm setupNested() {
        addRow(new JLabel(i18n.getString("name")), nameField);
        addRow(new JLabel(i18n.getString("address")), addressField);
        addRow(new JLabel(i18n.getString("ico")), icoField);
        addRow(new JLabel(i18n.getString("dic")), dicField);
        addRow(new JLabel(i18n.getString("phoneNumber")), phoneNumberField);
        addRow(new JLabel(i18n.getString("email")), emailField);

        addConfirmButton();
        makeConfirmAddData();
        return this;
    }

    private Issuer getDataFromForm() {
        return (Issuer) IssuerImpl.createEntity(
                nameField.getText(),
                addressField.getText(),
                icoField.getText(),
                dicField.getText(),
                phoneNumberField.getText(),
                emailField.getText()
        );
    }

    private void addDataToDatabase(Issuer issuer) {
        if (issuer == null) return;
        var issuers = PersistanceManager.getAllIssuer();
        if (issuers.size() >= 1) { // if larger, just in case of a bug where more then 1 is allowed
            var i = issuers.get(0);
            i.setName(issuer.getName());
            i.setAddress(issuer.getAddress());
            i.setICO(issuer.getICO());
            i.setDIC(issuer.getDIC());
            i.setEmail(issuer.getEmail());
            i.setPhoneNumber(issuer.getPhoneNumber());
            issuer = i;
        }
        PersistanceManager.persistIssuer(issuer);
    }

    private void makeConfirmAddData() {
        confirmButton.addActionListener(
                (ActionEvent e) -> {
                    confirmButton.setEnabled(false);
                    Issuer issuer = getDataFromForm();
                    new SwingWorker<Void, Void>() {
                        @Override
                        public Void doInBackground() {
                            addDataToDatabase(issuer);
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
