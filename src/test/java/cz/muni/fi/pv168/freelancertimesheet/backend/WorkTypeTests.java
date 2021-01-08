package cz.muni.fi.pv168.freelancertimesheet.backend;

import cz.muni.fi.pv168.freelancertimesheet.backend.interfaces.WorkType;
import cz.muni.fi.pv168.freelancertimesheet.backend.orm.WorkTypeImpl;
import org.hibernate.PropertyValueException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

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

    @Test
    public void testWorkTypeImplPersist() {
        var workType = WorkTypeImpl.createWorkType("test", "test", "102.02");
        PersistanceManager.persistWorkType(workType);
        var result = PersistanceManager.getAllWorkType();
        Assertions.assertEquals(result, List.of(workType));
    }

    @Test
    public void testWorkTypeImplPersistMultiple() {
        List<WorkType> workTypes = new ArrayList<WorkType>();
        workTypes.add(WorkTypeImpl.createWorkType("test1","test2","42"));
        workTypes.add(WorkTypeImpl.createWorkType("test2","test2","69"));
        for (var workType:
             workTypes) {
            PersistanceManager.persistWorkType(workType);
        }
        var result = PersistanceManager.getAllWorkType();
        Collections.sort(workTypes);
        Collections.sort(result);
        Assertions.assertEquals(result, workTypes);
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
        var workTypes = new ArrayList<WorkType>();
        workTypes.add(WorkTypeImpl.createWorkType("test1", "test2", "42"));
        workTypes.add(WorkTypeImpl.createWorkType("test2", "test2", "69"));
        for (var workType:
                workTypes) {
            PersistanceManager.persistWorkType(workType);
        }
        PersistanceManager.removeEntity(workTypeToRemove);
        var result = PersistanceManager.getAllWorkType();
        Collections.sort(workTypes);
        Collections.sort(result);
        Assertions.assertEquals(workTypes, result);
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
        var workTypes = new ArrayList<WorkType>();
        workTypes.add(WorkTypeImpl.createWorkType("test3", "test3", "424"));
        workTypes.add(WorkTypeImpl.createWorkType("test4", "test4", "694"));
        for (var workType:
                workTypes) {
            PersistanceManager.persistWorkType(workType);
        }
        PersistanceManager.removeCollection(workTypesToRemove);
        var result = PersistanceManager.getAllWorkType();
        Collections.sort(workTypes);
        Collections.sort(result);
        Assertions.assertEquals(workTypes, result);
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
        var copyOfWorkType = makeCopyOfWorkType(workType);
        PersistanceManager.persistWorkType(workType);
        Assertions.assertDoesNotThrow(() -> PersistanceManager.persistWorkType(copyOfWorkType));
        var workTypes = new ArrayList<WorkType>();
        workTypes.add(workType);
        workTypes.add(copyOfWorkType);
        Collections.sort(workTypes);
        var result = PersistanceManager.getAllWorkType();
        Collections.sort(result);
        Assertions.assertEquals(workTypes, result);
    }

    @Test
    public void testWorkTypeEdit() {
        var workType = WorkTypeImpl.createWorkType("test", "test", "102.02");
        PersistanceManager.persistWorkType(workType);
        workType.setName("test2");
        workType.setDescription("test3");
        workType.setHourlyRate("69");
        PersistanceManager.persistWorkType(workType);
        Assertions.assertEquals(List.of(workType), PersistanceManager.getAllWorkType());
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

    @Test
    public void testWorkTypeCompareToSame() {
        var workType1 = WorkTypeImpl.createWorkType("test", "test1", "42");
        var workType2 = WorkTypeImpl.createWorkType("test", "test1", "42");
        Assertions.assertEquals(workType1.compareTo(workType2), 0);
    }

    @Test
    public void testWorkTypeCompareToDifferent() {
        var workType1 = WorkTypeImpl.createWorkType("test", "test1", "42");
        var workType2 = WorkTypeImpl.createWorkType("test2", "test1", "42");
        Assertions.assertTrue(workType1.compareTo(workType2) != 0);
    }

    @Test
    public void testWorkTypeHashCodeSame() {
        var workType1 = WorkTypeImpl.createWorkType("test", "test1", "42");
        var workType2 = WorkTypeImpl.createWorkType("test", "test1", "42");
        Assertions.assertEquals(workType1.hashCode(), workType2.hashCode());
    }

    @Test
    public void testWorkTypeHashCodeDifferent() {
        var workType1 = WorkTypeImpl.createWorkType("test", "test1", "42");
        var workType2 = WorkTypeImpl.createWorkType("test1", "test1", "42");
        Assertions.assertTrue(workType1.hashCode() != workType2.hashCode());
    }

    @Test
    public void testWorkTypeToString() {
        var workType1 = WorkTypeImpl.createWorkType("test", "test1", "42");
        Assertions.assertEquals("WorkTypeImpl{id=0, description='test1', hourlyRate=42}", workType1.toString());
    }

    @Test
    public void testWorkTypeValidateBigDecimalWithBigDecimal() {
        var workType = new WorkTypeImpl();
        Assertions.assertDoesNotThrow(() -> workType.validateHourlyRate(new BigDecimal("42")));
    }
}
