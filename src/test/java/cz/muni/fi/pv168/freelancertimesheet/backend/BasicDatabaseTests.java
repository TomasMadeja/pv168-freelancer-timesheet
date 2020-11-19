package cz.muni.fi.pv168.freelancertimesheet.backend;

import cz.muni.fi.pv168.freelancertimesheet.backend.interfaces.Work;
import cz.muni.fi.pv168.freelancertimesheet.backend.interfaces.WorkType;
import cz.muni.fi.pv168.freelancertimesheet.backend.orm.WorkImpl;
import cz.muni.fi.pv168.freelancertimesheet.backend.orm.WorkTypeImpl;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BasicDatabaseTests {

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



    private List<WorkType> prepareWorkTypes() {

        List<WorkType> collection = new ArrayList<>();
        collection.add(WorkTypeImpl.createWorkType(
                "TestType1",
                "Never gonna give you up",
                new BigDecimal("20")
        ));
        collection.add(WorkTypeImpl.createWorkType(
                "TestType2",
                "Never gonna let you down",
                new BigDecimal("30")
        ));
        collection.add(WorkTypeImpl.createWorkType(
                "TestType3",
                "Forgot the rest",
                new BigDecimal("40")
        ));
        return collection;
    }

    private void addWorkTypes() {
        persistCollection(prepareWorkTypes());
    }

    private <T> void persistCollection(Collection<T> records) {
        var entityManager = DBConnectionUtils.getSessionFactory().createEntityManager();
        entityManager.getTransaction().begin();
        for (T record : records) {
            entityManager.persist(record);
        }
        entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.clear();
    }

    @Test
    public void testPersistWorkImpl() {
        addWorkTypes();
        List<WorkType> reference = prepareWorkTypes();
        Collections.sort(reference);
        EntityManager entityManager = DBConnectionUtils.getSessionFactory().createEntityManager();
        List<WorkTypeImpl> result = entityManager.createQuery("from WorkTypeImpl").getResultList();
        entityManager.clear();
        Collections.sort(result);
        Assertions.assertEquals(reference,result);
    }

}
