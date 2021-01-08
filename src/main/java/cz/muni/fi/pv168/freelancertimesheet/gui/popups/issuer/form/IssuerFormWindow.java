package cz.muni.fi.pv168.freelancertimesheet.gui.popups.issuer.form;

import cz.muni.fi.pv168.freelancertimesheet.backend.interfaces.Issuer;
import cz.muni.fi.pv168.freelancertimesheet.gui.GenericElement;
import cz.muni.fi.pv168.freelancertimesheet.gui.I18N;
import cz.muni.fi.pv168.freelancertimesheet.gui.actions.table.AddAction;
import cz.muni.fi.pv168.freelancertimesheet.gui.containers.WorkTypeContainer;

import javax.swing.*;
import java.awt.*;

public class IssuerFormWindow extends JFrame implements GenericElement<IssuerFormWindow> {

    private static final I18N i18n = new I18N(IssuerFormWindow.class);

    private final AddAction.Callback callback;
    private final Issuer issuer;

    public IssuerFormWindow(AddAction.Callback callback, Issuer issuer) {
        super(i18n.getString("title"));
        this.callback = callback;
        this.issuer = issuer;
    }

    @Override
    public IssuerFormWindow setupLayout() {
        GridLayout layout = new GridLayout(1, 1);
        this.setLayout(layout);
        return this;
    }

    @Override
    public IssuerFormWindow setupVisuals() {
        return this;
    }

    @Override
    public IssuerFormWindow setupNested() {
        IssuerForm issuerForm = IssuerForm.setup(issuer);
        issuerForm.setConfirmCallback(
                () -> {
                    if (callback != null) callback.call();
                    this.dispose();
                }
        );
        issuerForm.setCancelCallback(
                () -> {
                    this.dispose();
                }
        );
        Dimension preferredSize = issuerForm.getPreferredSize();
        this.setPreferredSize(new Dimension(preferredSize.width*2, preferredSize.height*2));
        this.add(issuerForm);
        return this;
    }

    public static IssuerFormWindow setup(AddAction.Callback callback, Issuer issuer) {
        IssuerFormWindow issuerFormWindow = new IssuerFormWindow(callback, issuer);
        issuerFormWindow
                .setupLayout()
                .setupVisuals()
                .setupNested();
        issuerFormWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        issuerFormWindow.pack();
        issuerFormWindow.setVisible(true);
        return issuerFormWindow;
    }
}
