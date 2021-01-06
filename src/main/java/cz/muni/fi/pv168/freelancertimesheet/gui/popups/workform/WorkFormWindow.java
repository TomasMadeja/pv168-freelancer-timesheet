package cz.muni.fi.pv168.freelancertimesheet.gui.popups.workform;

import cz.muni.fi.pv168.freelancertimesheet.backend.DBConnectionUtils;
import cz.muni.fi.pv168.freelancertimesheet.backend.interfaces.Work;
import cz.muni.fi.pv168.freelancertimesheet.gui.GenericElement;
import cz.muni.fi.pv168.freelancertimesheet.gui.I18N;

import javax.swing.*;
import java.awt.*;

public class WorkFormWindow extends JFrame implements GenericElement<WorkFormWindow> {

    private final I18N i18n = new I18N(getClass());

    private WorkForm workForm;

    public WorkFormWindow() {
        super();
    }


    @Override
    public WorkFormWindow setupLayout() {
        GridLayout layout = new GridLayout(1, 1);
        this.setLayout(layout);
        this.setTitle(i18n.getString("title"));
        return this;
    }

    @Override
    public WorkFormWindow setupVisuals() {
        return this;
    }

    @Override
    public WorkFormWindow setupNested() {
        workForm = WorkForm.setup();
        workForm.setupConfirmButtonAction(e -> confirmFilledForms());
        workForm.setCancelCallback(this::dispose);

        Dimension preferredSize = workForm.getPreferredSize();
        this.setPreferredSize(new Dimension(preferredSize.width * 2, preferredSize.height * 2));
        this.add(workForm);
        return this;
    }


    private void confirmFilledForms() {
        // TODO check if all needed fields have value
        pushDataToDatabase(workForm.prepareDataFromForms());
        this.dispose();
    }

    private void pushDataToDatabase(Work work) {
        var entityManager = DBConnectionUtils.getSessionFactory().createEntityManager();
        entityManager.getTransaction().begin();

        entityManager.persist(work);

        entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.clear();
    }


    public static WorkFormWindow setup() {
        var taskFormWindow = new WorkFormWindow()
                .setupLayout()
                .setupVisuals()
                .setupNested();
        taskFormWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        taskFormWindow.pack();
        taskFormWindow.setVisible(true);
        return taskFormWindow;

    }
}
