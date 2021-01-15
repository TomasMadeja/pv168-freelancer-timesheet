package cz.muni.fi.pv168.freelancertimesheet.gui.elements;

import javax.swing.*;

public class TextFieldFactory {

    public static class CustomWrappedClass extends JScrollPane {
        JTextField textField;

        public CustomWrappedClass(JTextField textField) {
            super(textField);
            this.textField = textField;
            textField.setEditable(true);
        }

        @Override
        public void setEnabled(boolean enabled) {
            super.setEnabled(enabled);
            textField.setEnabled(enabled);
            textField.setEditable(enabled);
        }

        public CustomWrappedClass setText(String text) {
            textField.setText(text);
            return this;
        }

        public String getText() {
            return textField.getText();
        }
    }

    public static CustomWrappedClass createWrappedTextField() {
        JTextField textField = new JTextField();
        CustomWrappedClass scrollPane = new CustomWrappedClass(textField);
//        textField.setLineWrap(true);
//        textField.setWrapStyleWord(true);

        return scrollPane;
    }

}
