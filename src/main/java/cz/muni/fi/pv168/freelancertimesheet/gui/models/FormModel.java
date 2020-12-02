package cz.muni.fi.pv168.freelancertimesheet.gui.models;

import cz.muni.fi.pv168.freelancertimesheet.gui.GenericElement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public abstract class FormModel extends JPanel implements GenericElement<FormModel>{
    protected GridBagLayout layout;
    protected GridBagConstraints layoutConstraints;
    protected List<Component> inputFields;

    private JButton confirmButton;
    private JButton cancelButton;

    public FormModel() {
        super();
        inputFields = new ArrayList<Component>();
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

    protected FormModel addConfirmButton() {
        JPanel panel = new JPanel(new BorderLayout());
        confirmButton = new JButton("Confirm");
        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener((ActionEvent e) -> disableForm());
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
        return this;
    }
}
