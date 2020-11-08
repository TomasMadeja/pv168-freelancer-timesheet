package cz.muni.fi.pv168.freelancertimesheet.gui;

import javax.swing.*;
import java.awt.*;

public class InvoicePanel extends JPanel {

    public InvoicePanel() {
        var java = new JCheckBox("Java");
        var is = new JCheckBox("Is");
        var awesome = new JCheckBox("Awesome :)");

        setLayout(new FlowLayout());
        add(java);
        add(is);
        add(awesome);
    }

}
