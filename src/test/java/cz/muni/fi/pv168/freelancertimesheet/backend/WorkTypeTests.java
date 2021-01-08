package cz.muni.fi.pv168.freelancertimesheet.backend;

import cz.muni.fi.pv168.freelancertimesheet.backend.interfaces.WorkType;
import cz.muni.fi.pv168.freelancertimesheet.backend.orm.WorkTypeImpl;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.*;

public class WorkTypeTests {

    private Random rand = new Random();

    @AfterEach
    public void cleanupTables() {
        SessionFactory factory = DBConnectionUtils.getSessionFactory();
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery("DELETE FROM invoices").executeUpdate();
            session.createSQLQuery("DELETE FROM issuers").executeUpdate();
            session.createSQLQuery("DELETE FROM clients").executeUpdate();
            session.createSQLQuery("DELETE FROM works").executeUpdate();
            session.createSQLQuery("DELETE FROM work_types").executeUpdate();
            session.getTransaction().commit();
        }
    }

    private WorkType generateWorkType() {
        var names = new String[]{"Programming", "Writing", "Graphical Design", "Sleeping", "Playing video games", "Eating"};
        var places = new String[]{"at home", "at work", "at park", "in bed", "on remote island", "under a bridge"};
        int num1 = rand.nextInt(6);
        int num2 = rand.nextInt(6);
        double num3 = rand.nextInt(10000) / 100.0;
        var workType= new WorkTypeImpl();
        workType.setName(names[num1]).setDescription(names[num1]+ " " + places[num2]).setHourlyRate(BigDecimal.valueOf(num3));
        return workType;
    }

    private List<WorkType> generateWorkTypes(int n) {
        var workTypes = new ArrayList<WorkType>();
        for (int i = 0; i < n; i++) {
            workTypes.add(generateWorkType());
        }
        return workTypes;
    }

    private WorkType makeCopyOfWorkType(WorkType orig) {
        var copy = new WorkTypeImpl();
        copy.setName(orig.getName());
        copy.setDescription(orig.getDescription());
        copy.setHourlyRate(new BigDecimal(orig.getHourlyRate().toString()));
        return copy;
    }

    private List<WorkType> makeCopyOfWorkTypes(List<WorkType> orig) {
        var  copy = new ArrayList<WorkType>();
        for (int i = 0; i < orig.size(); i++) {
            copy.add(makeCopyOfWorkType(orig.get(i)));
        }
        return copy;
    }

    @Test
    public void testWorkTypeImplPersist() {
        var workType = generateWorkType();
        var referenceWorkType = makeCopyOfWorkType(workType);
        PersistanceManager.persistWorkType(workType);
        var result = PersistanceManager.getAllWorkType();
        Assertions.assertEquals(result, List.of(referenceWorkType));
    }

    @Test
    public void testWorkTypeImplPersistMultiple() {
        var workTypes = generateWorkTypes(10);
        var referenceWorkTypes = makeCopyOfWorkTypes(workTypes);
        for (var workType:
             workTypes) {
            PersistanceManager.persistWorkType(workType);
        }
        var result = PersistanceManager.getAllWorkType();
        Collections.sort(referenceWorkTypes);
        Collections.sort(result);
        Assertions.assertEquals(result, referenceWorkTypes);
    }

    @Test
    public void testWorkTypeImplRemove() {
        var workType = generateWorkType();
        PersistanceManager.persistWorkType(workType);
        PersistanceManager.removeEntity(workType);
        var result = PersistanceManager.getAllWorkType();
        Assertions.assertTrue(result.size() == 0);
    }

    @Test
    public void testWorkTypeImplRemoveOneOutOfMany() {
        var workTypeToRemove = generateWorkType();
        PersistanceManager.persistWorkType(workTypeToRemove);
        var workTypes = generateWorkTypes(10);
        var referenceWorkTypes = makeCopyOfWorkTypes(workTypes);
        for (var workType:
                workTypes) {
            PersistanceManager.persistWorkType(workType);
        }
        PersistanceManager.removeEntity(workTypeToRemove);
        var result = PersistanceManager.getAllWorkType();
        Collections.sort(referenceWorkTypes);
        Collections.sort(result);
        Assertions.assertEquals(referenceWorkTypes, result);
    }


}
