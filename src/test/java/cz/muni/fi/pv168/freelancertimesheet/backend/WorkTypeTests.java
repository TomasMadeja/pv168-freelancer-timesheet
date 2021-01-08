package cz.muni.fi.pv168.freelancertimesheet.backend;

import cz.muni.fi.pv168.freelancertimesheet.backend.interfaces.WorkType;
import cz.muni.fi.pv168.freelancertimesheet.backend.orm.WorkTypeImpl;
import org.hibernate.PropertyValueException;
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
            session.clear();
        }
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
        var workType = WorkTypeImpl.createWorkType("test", "test", "102.02");
        var referenceWorkType = WorkTypeImpl.createWorkType("test", "test", "102.02");
        PersistanceManager.persistWorkType(workType);
        var result = PersistanceManager.getAllWorkType();
        Assertions.assertEquals(result, List.of(referenceWorkType));
    }

    @Test
    public void testWorkTypeImplPersistMultiple() {
        var workTypes = List.of(
                WorkTypeImpl.createWorkType("test1", "test2", "42"),
                WorkTypeImpl.createWorkType("test2", "test2", "69")
        );
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
        var workType = WorkTypeImpl.createWorkType("test", "test", "102.02");
        PersistanceManager.persistWorkType(workType);
        PersistanceManager.removeEntity(workType);
        var result = PersistanceManager.getAllWorkType();
        Assertions.assertTrue(result.size() == 0);
    }

    @Test
    public void testWorkTypeImplRemoveOneOutOfMany() {
        var workTypeToRemove = WorkTypeImpl.createWorkType("test", "test", "102.02");
        PersistanceManager.persistWorkType(workTypeToRemove);
        var workTypes = List.of(
                WorkTypeImpl.createWorkType("test1", "test2", "42"),
                WorkTypeImpl.createWorkType("test2", "test2", "69")
        );
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

    @Test
    public void testWorkTypeImplRemoveMany() {
        var workTypesToRemove = List.of(
                WorkTypeImpl.createWorkType("test1", "test2", "42"),
                WorkTypeImpl.createWorkType("test2", "test2", "69")
        );;
        for (var workTypeToRemove:
             workTypesToRemove) {
            PersistanceManager.persistWorkType(workTypeToRemove);
        }
        var workTypes = List.of(
                WorkTypeImpl.createWorkType("test3", "test3", "424"),
                WorkTypeImpl.createWorkType("test4", "test4", "694")
        );;
        var referenceWorkTypes = makeCopyOfWorkTypes(workTypes);
        for (var workType:
                workTypes) {
            PersistanceManager.persistWorkType(workType);
        }
        PersistanceManager.removeCollection(workTypesToRemove);
        var result = PersistanceManager.getAllWorkType();
        Collections.sort(referenceWorkTypes);
        Collections.sort(result);
        Assertions.assertEquals(referenceWorkTypes, result);
    }

    @Test
    public void testWorkTypeRemoveNotPersisted() {
        var workType = WorkTypeImpl.createWorkType("test", "test", "102.02");
        Assertions.assertEquals(PersistanceManager.getAllWorkType().size(), 0);
        Assertions.assertDoesNotThrow(() -> PersistanceManager.removeEntity(workType));
    }

    @Test
    public void testWorkTypePersistsTwoIdentical() {
        var workType = WorkTypeImpl.createWorkType("test", "test", "102.02");
        var referenceWorkType = WorkTypeImpl.createWorkType("test", "test", "102.02");
        var copyOfWorkType = makeCopyOfWorkType(workType);
        PersistanceManager.persistWorkType(workType);
        Assertions.assertDoesNotThrow(() -> PersistanceManager.persistWorkType(copyOfWorkType));
        Assertions.assertEquals(List.of(referenceWorkType, referenceWorkType), PersistanceManager.getAllWorkType());
    }

    @Test
    public void testWorkTypeEdit() {
        var workType = WorkTypeImpl.createWorkType("test", "test", "102.02");
        PersistanceManager.persistWorkType(workType);
        var workTypeEdits = WorkTypeImpl.createWorkType("test2", "test2", "42");
        workType.setName(workTypeEdits.getName());
        workType.setDescription(workTypeEdits.getDescription());
        workType.setHourlyRate(workTypeEdits.getHourlyRate());
        PersistanceManager.persistWorkType(workType);
        Assertions.assertEquals(List.of(workTypeEdits), PersistanceManager.getAllWorkType());
    }

    @Test
    public void testWorkTypePersistEmptyName() {
        var workType = new WorkTypeImpl();
        workType.setDescription("test");
        workType.setHourlyRate("1");
        Assertions.assertThrows(PropertyValueException.class, () -> PersistanceManager.persistWorkType(workType));
    }

    @Test
    public void testWorkTypePersistEmptyDescription() {
        var workType = new WorkTypeImpl();
        workType.setName("test");
        workType.setHourlyRate("1");
        Assertions.assertThrows(PropertyValueException.class, () -> PersistanceManager.persistWorkType(workType));
    }

    @Test
    public void testWorkTypePersistEmptyBigDecimal() {
        var workType = new WorkTypeImpl();
        workType.setDescription("test");
        workType.setName("testy");
        Assertions.assertThrows(PropertyValueException.class, () -> PersistanceManager.persistWorkType(workType));
    }

}
