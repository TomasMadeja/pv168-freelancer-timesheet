package cz.muni.fi.pv168.freelancertimesheet.backend;
import cz.muni.fi.pv168.freelancertimesheet.backend.interfaces.Work;
import cz.muni.fi.pv168.freelancertimesheet.backend.interfaces.WorkType;
import cz.muni.fi.pv168.freelancertimesheet.backend.orm.WorkImpl;
import cz.muni.fi.pv168.freelancertimesheet.backend.orm.WorkTypeImpl;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.*;

public class WorkTests {
    private Random rand = new Random();

    @BeforeEach
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

    @Test
    public void testPersistWork() {
        var records = SampleDataGenerator.generateWork();
        var databaseRecords = PersistanceManager.getAllWork();
        Assertions.assertEquals(records.works.size(), databaseRecords.size());
        for (int i = 0; i < databaseRecords.size(); i++) {
            Assertions.assertEquals(records.works.get(i), databaseRecords.get(i));
        }
    }

    @Test
    public void testEditWorkName() {
        var records = SampleDataGenerator.generateWork();
        records.works.get(0).setName("EditedName");
        PersistanceManager.persistWork(records.works.get(0));
        var databaseRecords = PersistanceManager.getAllWork("Work11", null);
        Assertions.assertEquals(databaseRecords.size(), 0);
        databaseRecords = PersistanceManager.getAllWork("EditedName", null);
        Assertions.assertEquals(databaseRecords.size(), 1);
        Assertions.assertEquals(records.works.get(0), databaseRecords.get(0));
    }

    @Test
    public void testEditWorkDescription() {
        var records = SampleDataGenerator.generateWork();
        records.works.get(0).setDescription("EditedDesc");
        PersistanceManager.persistWork(records.works.get(0));
        var databaseRecords = PersistanceManager.getAllWork("Work11", null);
        Assertions.assertEquals(databaseRecords.size(), 1);
        Assertions.assertEquals(records.works.get(0), databaseRecords.get(0));
    }

    @Test
    public void testEditWorkStartTime() {
        var records = SampleDataGenerator.generateWork();
        records.works.get(0).setStartTime(ZonedDateTime.parse("2010-12-05T10:07:30+01:00"));
        PersistanceManager.persistWork(records.works.get(0));
        var databaseRecords = PersistanceManager.getAllWork("Work11", null);
        Assertions.assertEquals(databaseRecords.size(), 1);
        Assertions.assertEquals(records.works.get(0), databaseRecords.get(0));
    }

    @Test
    public void testEditWorkEndTime() {
        var records = SampleDataGenerator.generateWork();
        records.works.get(0).setEndTime(ZonedDateTime.parse("2012-12-05T10:07:30+01:00"));
        PersistanceManager.persistWork(records.works.get(0));
        var databaseRecords = PersistanceManager.getAllWork("Work11", null);
        Assertions.assertEquals(databaseRecords.size(), 1);
        Assertions.assertEquals(records.works.get(0), databaseRecords.get(0));
    }

    @Test
    public void testEditWorkWorkType() {
        var records = SampleDataGenerator.generateWork();
        records.works.get(0).setWorkType(records.workTypes.get(2));
        PersistanceManager.persistWork(records.works.get(0));
        var databaseRecords = PersistanceManager.getAllWork("Work11", null);
        Assertions.assertEquals(databaseRecords.size(), 1);
        Assertions.assertEquals(records.works.get(0), databaseRecords.get(0));
    }

    @Test
    public void testDeleteWork() {
        var records = SampleDataGenerator.generateWork();
        for (int i = 0; i < 3; i++) {
            PersistanceManager.removeEntity(records.works.get(i));
        }
        var databaseRecords = PersistanceManager.getAllWork(null, null);
        Assertions.assertEquals(databaseRecords.size(), records.works.size() - 3);
        for (int i = 3; i < databaseRecords.size(); i++) {
            Assertions.assertEquals(records.works.get(i), databaseRecords.get(i));
        }
    }

    @Test
    public void testDuplicatePersistWork() {
        var records = SampleDataGenerator.generateWork();
        var duplicateRecords = SampleDataGenerator.generateWork();
        var databaseRecords = PersistanceManager.getAllWork(null, null);
        Assertions.assertEquals(databaseRecords.size(), records.works.size() + duplicateRecords.works.size());
        for (int i = 0; i < records.works.size(); i++) {
            Assertions.assertEquals(records.works.get(i), databaseRecords.get(i));
        }
        for (int i = 0; i < duplicateRecords.works.size(); i++) {
            Assertions.assertEquals(duplicateRecords.works.get(i), databaseRecords.get(i + records.works.size()));
        }
    }

    @Test
    public void testDuplicateRemove() {
        var records = SampleDataGenerator.generateWork();
        PersistanceManager.removeCollection(records.works);
        Assertions.assertEquals(PersistanceManager.getAllWork().size(), 0);
        Assertions.assertDoesNotThrow(() -> PersistanceManager.removeCollection(records.works));
    }


    @Test
    public void testEqualHash() {
        var records = SampleDataGenerator.generateWork();
        var databaseRecords = PersistanceManager.getAllWork();
        Assertions.assertEquals(databaseRecords.size(), records.works.size());
        for (int i = 0; i < records.works.size(); i++) {
            Assertions.assertEquals(
                    records.works.get(i).getWorkType().hashCode(),
                    databaseRecords.get(i).getWorkType().hashCode(),
                    records.works.get(i).getWorkType().toString() + "\n" + databaseRecords.get(i).getWorkType().toString()
            );
            Assertions.assertEquals(
                    records.works.get(i).hashCode(),
                    databaseRecords.get(i).hashCode(),
                    records.works.get(i).toString() + "\n" + databaseRecords.get(i).toString()
            );
        }
    }

    @Test
    public void testCreateWork() {
        String name = "WorkName";
        String description = "WorkDesc";
        ZonedDateTime startTime = ZonedDateTime.parse("2011-11-07T10:07:30+01:00");
        ZonedDateTime endTime = ZonedDateTime.parse("2011-11-07T10:07:30+01:00");
        var workType = WorkTypeImpl.createWorkType(
            "TestType1",
            "Test description 1.",
            "0.5"
        );
        var work = WorkImpl.createWork(name, description, startTime, endTime, workType);
        PersistanceManager.persistWorkType(workType);
        PersistanceManager.persistWork(work);
        var workCollection = PersistanceManager.getAllWork();
        Assertions.assertEquals(workCollection.size(), 1);
        var record = workCollection.get(0);
        Assertions.assertEquals(record, work);
        Assertions.assertNotSame(record, work);
        Assertions.assertEquals(record.compareTo(work), 0);
        Assertions.assertEquals(record.getName(), name);
        Assertions.assertEquals(record.getDescription(), description);
        Assertions.assertEquals(record.getStartTime().toInstant(), startTime.toInstant());
        Assertions.assertEquals(record.getEndTime().toInstant(), endTime.toInstant());
        Assertions.assertNotEquals(record.getWorkType(), workType);
    }
}
