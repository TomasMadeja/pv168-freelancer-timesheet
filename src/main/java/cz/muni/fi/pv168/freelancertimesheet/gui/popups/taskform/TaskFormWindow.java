package cz.muni.fi.pv168.freelancertimesheet.gui.popups.taskform;

import cz.muni.fi.pv168.freelancertimesheet.backend.DBConnectionUtils;
import cz.muni.fi.pv168.freelancertimesheet.backend.interfaces.Work;
import cz.muni.fi.pv168.freelancertimesheet.gui.GenericElement;

import javax.swing.*;
import java.awt.*;

public class TaskFormWindow extends JFrame implements GenericElement<TaskFormWindow> {

    private TaskForm taskForm;

    public TaskFormWindow() {
        super();
    }


    @Override
    public TaskFormWindow setupLayout() {
        GridLayout layout = new GridLayout(1, 1);
        this.setLayout(layout);
        return this;
    }

    @Override
    public TaskFormWindow setupVisuals() {
        return this;
    }

    @Override
    public TaskFormWindow setupNested() {
        taskForm = TaskForm.setup();
        taskForm.setupConfirmButtonAction(e -> confirmFilledForms());
//        taskForm.setConfirmCallback(
//                () -> {
//                    if (callback != null) callback.call();
//                    this.dispose();
//                }
//        );
//        taskForm.setCancelCallback(this::dispose);

        Dimension preferredSize = taskForm.getPreferredSize();
        this.setPreferredSize(new Dimension(preferredSize.width * 2, preferredSize.height * 2));
        this.add(taskForm);
        return this;
    }


    private void confirmFilledForms() {
        // TODO check if all needed fields have value
        pushDataToDatabase(taskForm.prepareDataFromForms());
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


    public static TaskFormWindow setup() {
        var taskFormWindow = new TaskFormWindow()
                .setupLayout()
                .setupVisuals()
                .setupNested();
        taskFormWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        taskFormWindow.pack();
        taskFormWindow.setVisible(true);
        return taskFormWindow;

    }
}
