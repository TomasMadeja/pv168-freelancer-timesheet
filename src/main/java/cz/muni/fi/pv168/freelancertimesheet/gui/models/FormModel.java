package cz.muni.fi.pv168.freelancertimesheet.gui.models;

import cz.muni.fi.pv168.freelancertimesheet.gui.GenericElement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class FormModel extends JPanel implements GenericElement<FormModel>{
    protected GridBagLayout layout;
    protected GridBagConstraints layoutConstraints;
    protected List<Component> inputFields;

    protected JButton confirmButton;
    protected JButton cancelButton;

    protected Callback confirmCallback;
    protected Callback cancelCallback;

    public interface Callback {
        void call();
    }

    public FormModel() {
        super();
        inputFields = new ArrayList<>();
    }

    protected FormModel addRow(Component left, Component right) {
        layoutConstraints.gridwidth = GridBagConstraints.RELATIVE;
        layoutConstraints.weightx = 0;
        add(left, layoutConstraints);
        layoutConstraints.gridwidth = GridBagConstraints.REMAINDER;
        layoutConstraints.gridx = 1;
        layoutConstraints.weightx = 1;
        add(right, layoutConstraints);
        inputFields.add(right);
        layoutConstraints.gridy = layoutConstraints.gridy + 1;
        layoutConstraints.gridx = 0;
        return this;
    }

    protected JButton buildConfirmButton() {
        confirmButton = new JButton("Confirm");
        confirmButton.addActionListener((ActionEvent e) -> triggerConfirmCallback());
        return confirmButton;
    }

    protected JButton buildCancelButton() {
        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener((ActionEvent e) -> triggerCancelCallback());
        return cancelButton;
    }

    private void triggerConfirmCallback() {
        if (confirmCallback != null) {
            confirmCallback.call();
        }
    }

    private void triggerCancelCallback() {
        if (cancelCallback != null) {
            cancelCallback.call();
        }
    }

    public FormModel setConfirmCallback(Callback callback) {
        confirmCallback = callback;
        return this;
    }

    public FormModel setCancelCallback(Callback callback) {
        cancelCallback = callback;
        return this;
    }

    protected FormModel addConfirmButton() {
        JPanel panel = new JPanel(new BorderLayout());
        buildConfirmButton();
        buildCancelButton();
        JPanel subPanel = new JPanel(new GridLayout(1, 2));
        subPanel.add(cancelButton);
        subPanel.add(confirmButton);
        panel.add(subPanel, BorderLayout.EAST);
        layoutConstraints.gridx = 1;
        layoutConstraints.gridwidth = GridBagConstraints.RELATIVE;
        layoutConstraints.weightx = 0;
        add(panel, layoutConstraints);
        layoutConstraints.gridy = layoutConstraints.gridy + 1;
        layoutConstraints.gridx = 0;
        return this;
    }

    public FormModel disableForm() {
        enableForm(false);
        return this;
    }

    public FormModel enableForm() {
        enableForm(true);
        return this;
    }

    public FormModel enableForm(boolean enabled) {
        confirmButton.setEnabled(enabled);
        cancelButton.setEnabled(enabled);
        for (Component c: inputFields) {
            c.setEnabled(enabled);
        }
        return this;
    }

    @Override
    public FormModel setupLayout() {
        layout = new GridBagLayout();
        layoutConstraints = new GridBagConstraints();
        layoutConstraints.weighty = 1;
        layoutConstraints.fill = GridBagConstraints.BOTH;
        layoutConstraints.gridx = 0;
        layoutConstraints.gridy = 0;
        setLayout(layout);
        return this;
    }

    @Override
    public FormModel setupVisuals() {
        setBorder(
                BorderFactory.createEmptyBorder(10,10,10,10)
        );
        return this;
    }

    public static <T> FormModel setup() {
        return null;
    }

    public static <T> FormModel setup(T object) {
        return null;
    }

    public static <T> FormModel setup(Collection<T> object) {
        return null;
    }
}
