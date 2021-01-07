package cz.muni.fi.pv168.freelancertimesheet.gui.popups;

import cz.muni.fi.pv168.freelancertimesheet.gui.GenericElement;
import cz.muni.fi.pv168.freelancertimesheet.gui.I18N;
import cz.muni.fi.pv168.freelancertimesheet.gui.actions.table.AddAction;
import cz.muni.fi.pv168.freelancertimesheet.gui.tabs.client.ClientForm;

import javax.swing.*;
import java.awt.*;

public class ClientWindow extends JFrame implements GenericElement<ClientWindow> {

    private static final I18N i18n = new I18N(ClientWindow.class);

    private final AddAction.Callback callbackAdd;

    public ClientWindow(AddAction.Callback callbackAdd) {
        super(i18n.getString("title"));
        this.callbackAdd = callbackAdd;
    }

    @Override
    public ClientWindow setupLayout() {
        GridLayout layout = new GridLayout(1, 1);
        this.setLayout(layout);
        return this;
    }

    @Override
    public ClientWindow setupVisuals() {
        return this;
    }

    @Override
    public ClientWindow setupNested() {
        ClientForm clientForm = ClientForm.setup();
        clientForm.setConfirmCallback(
                () -> {
                    if (callbackAdd != null) callbackAdd.call();
                    this.dispose();
                }
        );
        clientForm.setCancelCallback(
                () -> {
                    this.dispose();
                }
        );
        Dimension preferredSize = clientForm.getPreferredSize();
        this.setPreferredSize(new Dimension(preferredSize.width*2, preferredSize.height*2));
        this.add(clientForm);
        return this;
    }

    public static ClientWindow setup(AddAction.Callback callback) {
        ClientWindow clientWindow = new ClientWindow(callback);
        clientWindow
                .setupLayout()
                .setupVisuals()
                .setupNested();
        clientWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        clientWindow.pack();
        clientWindow.setVisible(true);
        return clientWindow;
    }
}
