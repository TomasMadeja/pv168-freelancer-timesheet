package cz.muni.fi.pv168.freelancertimesheet.gui.elements;

import javax.swing.*;

public class TextFieldFactory {

    public static class CustomWrappedClass extends JScrollPane {
        JTextArea textArea;

        public CustomWrappedClass(JTextArea textArea) {
            super(textArea);
            this.textArea = textArea;
            textArea.setEditable(true);
        }

        @Override
        public void setEnabled(boolean enabled) {
            super.setEnabled(enabled);
            textArea.setEnabled(enabled);
            textArea.setEditable(enabled);
        }
    }

    public static CustomWrappedClass createWrappedTextField() {
        JTextArea textField = new JTextArea();
        CustomWrappedClass scrollPane = new CustomWrappedClass(textField);
        textField.setLineWrap(true);
        textField.setWrapStyleWord(true);

        return scrollPane;
    }

}
