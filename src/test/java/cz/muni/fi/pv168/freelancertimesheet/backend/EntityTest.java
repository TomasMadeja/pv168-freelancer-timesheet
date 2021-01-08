package cz.muni.fi.pv168.freelancertimesheet.backend;

import cz.muni.fi.pv168.freelancertimesheet.backend.interfaces.Client;
import cz.muni.fi.pv168.freelancertimesheet.backend.interfaces.Issuer;
import cz.muni.fi.pv168.freelancertimesheet.backend.orm.ClientImpl;
import cz.muni.fi.pv168.freelancertimesheet.backend.orm.EntityImpl;
import cz.muni.fi.pv168.freelancertimesheet.backend.orm.IssuerImpl;
import cz.muni.fi.pv168.freelancertimesheet.backend.orm.WorkImpl;
import cz.muni.fi.pv168.freelancertimesheet.backend.orm.WorkTypeImpl;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;

public class EntityTest {

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
    public void testPersistClient() {
        var records = SampleDataGenerator.generateClients();
        var databaseRecords = PersistanceManager.getAllClient();
        Assertions.assertEquals(records.size(), databaseRecords.size());
        for (int i = 0; i < databaseRecords.size(); i++) {
            Assertions.assertEquals(records.get(i), databaseRecords.get(i));
        }
    }

    @Test
    public void testEditClientName() {
        var records = SampleDataGenerator.generateClients();
        records.get(0).setName("EditedName");
        PersistanceManager.persistClient(records.get(0));
        var databaseRecords = PersistanceManager.getAllClient();
        Assertions.assertEquals(records.get(0), databaseRecords.get(0));
    }

    @Test
    public void testEditClientAddress() {
        var records = SampleDataGenerator.generateClients();
        records.get(0).setAddress("EditedAddress");
        PersistanceManager.persistClient(records.get(0));
        var databaseRecords = PersistanceManager.getAllClient();
        Assertions.assertEquals(records.get(0), databaseRecords.get(0));
    }

    @Test
    public void testEditClientEmail() {
        var records = SampleDataGenerator.generateClients();
        records.get(0).setEmail("email@mail.com");
        PersistanceManager.persistClient(records.get(0));
        var databaseRecords = PersistanceManager.getAllClient();
        Assertions.assertEquals(records.get(0), databaseRecords.get(0));
    }

    @Test
    public void testEditClientPhoneNumber() {
        var records = SampleDataGenerator.generateClients();
        records.get(0).setPhoneNumber("111222333");
        PersistanceManager.persistClient(records.get(0));
        var databaseRecords = PersistanceManager.getAllClient();
        Assertions.assertEquals(records.get(0), databaseRecords.get(0));
    }

    @Test
    public void testEditClientICO() {
        var records = SampleDataGenerator.generateClients();
        records.get(0).setICO("111222333");
        PersistanceManager.persistClient(records.get(0));
        var databaseRecords = PersistanceManager.getAllClient();
        Assertions.assertEquals(records.get(0), databaseRecords.get(0));
    }

    @Test
    public void testEditClientDIC() {
        var records = SampleDataGenerator.generateClients();
        records.get(0).setDIC("CZ111222333");
        PersistanceManager.persistClient(records.get(0));
        var databaseRecords = PersistanceManager.getAllClient();
        Assertions.assertEquals(records.get(0), databaseRecords.get(0));
    }

    @Test
    public void testEqualHash() {
        var records = SampleDataGenerator.generateClients();
        var databaseRecords = PersistanceManager.getAllClient();
        Assertions.assertEquals(databaseRecords.size(), records.size());
        for (int i = 0; i < records.size(); i++) {
            Assertions.assertEquals(
                    records.get(i).hashCode(),
                    databaseRecords.get(i).hashCode()
            );
        }
    }

    @Test
    public void testCreateEntity() {
        String name = "WorkName";
        String address = "Address1";
        String ico = "12345678";
        String dic = "CZ12345678";
        String phoneNumber = "+421090111222";
        String email = "mail1@mail.com";
        var client = (Client) ClientImpl.createEntity(
                name,
                address,
                ico,
                dic,
                phoneNumber,
                email
        );
        PersistanceManager.persistClient(client);
        var clientCollection = PersistanceManager.getAllClient();
        Assertions.assertEquals(clientCollection.size(), 1);
        var record = clientCollection.get(0);
        Assertions.assertEquals(record, client);
        Assertions.assertNotSame(record, client);
        Assertions.assertEquals(record.getName(), name);
        Assertions.assertEquals(record.getAddress(), address);
        Assertions.assertEquals(record.getICO(), ico);
        Assertions.assertEquals(record.getDIC(), dic);
        Assertions.assertEquals(record.getPhoneNumber(), phoneNumber);
        Assertions.assertEquals(record.getEmail(), email);
    }

    @Test
    public void testEntityToXML() {
        var entity = new EntityImpl();
        Assertions.assertNull(entity.toXML());
    }

    @Test
    public void testClienttoXML() {
        String name = "WorkName";
        String address = "Address1";
        String ico = "12345678";
        String dic = "CZ12345678";
        String phoneNumber = "+421090111222";
        String email = "mail1@mail.com";
        var client = (Client) ClientImpl.createEntity(
                name,
                address,
                ico,
                dic,
                phoneNumber,
                email
        );
        Assertions.assertNotNull(client.toXML());
        Assertions.assertEquals(
                "                <p class=\"name\">WorkName</p>\n" +
                        "                <p>Address1</p>\n" +
                        "                <p><b>ICO:</b> 12345678</p>\n" +
                        "                <p><b>DIC:</b> CZ12345678</p>\n",
                client.toXML()
        );
    }

    @Test
    public void testIssuertoXML() {
        String name = "WorkName";
        String address = "Address1";
        String ico = "12345678";
        String dic = "CZ12345678";
        String phoneNumber = "+421090111222";
        String email = "mail1@mail.com";
        var issuer = (Issuer) IssuerImpl.createEntity(
                name,
                address,
                ico,
                dic,
                phoneNumber,
                email
        );
        Assertions.assertNotNull(issuer.toXML());
        Assertions.assertEquals(
                "            <h2 class=\"title\">WorkName</h2>\n" +
                        "            <p>\n" +
                        "                Address1\n" +
                        "            </p>\n" +
                        "            <p><b>ICO:</b> 12345678</p>\n" +
                        "            <p><b>DIC:</b> CZ12345678</p>\n",
                issuer.toXML()
        );
    }

    @Test
    public void testStoreIssuer() {
        String name = "WorkName";
        String address = "Address1";
        String ico = "12345678";
        String dic = "CZ12345678";
        String phoneNumber = "+421090111222";
        String email = "mail1@mail.com";
        var issuer = (Issuer) IssuerImpl.createEntity(
                name,
                address,
                ico,
                dic,
                phoneNumber,
                email
        );
        Assertions.assertDoesNotThrow(() -> PersistanceManager.persistIssuer(issuer));
        var records = PersistanceManager.getAllIssuer();
        Assertions.assertEquals(records.size(), 1);
        Assertions.assertEquals(records.get(0), issuer);
    }
}
