package cz.muni.fi.pv168.freelancertimesheet;

import cz.muni.fi.pv168.freelancertimesheet.backend.DBConnectionUtils;
import cz.muni.fi.pv168.freelancertimesheet.backend.orm.WorkTypeImpl;
import cz.muni.fi.pv168.freelancertimesheet.gui.MainWindow;

public class Main {

    public static void main(String[] args) {
        DBConnectionUtils.init();
//        var factory = DBConnectionUtils.getSessionFactory();
//        var entityManager = factory.createEntityManager();
//        entityManager.getTransaction().begin();
////        entityManager.persist(WorkTypeImpl.createWorkType("a", "1"));
//        entityManager.flush();
//        entityManager.getTransaction().commit();
//        entityManager.clear();
//        System.out.println("Query output:");
//        System.out.println(entityManager.createQuery("SELECT a from WorkTypeImpl a", WorkTypeImpl.class).getResultList());
        MainWindow dash = MainWindow.setup();
    }
}
