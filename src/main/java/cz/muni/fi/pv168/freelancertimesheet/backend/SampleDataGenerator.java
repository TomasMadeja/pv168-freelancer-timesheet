package cz.muni.fi.pv168.freelancertimesheet.backend;

import cz.muni.fi.pv168.freelancertimesheet.backend.interfaces.Client;
import cz.muni.fi.pv168.freelancertimesheet.backend.interfaces.Work;
import cz.muni.fi.pv168.freelancertimesheet.backend.interfaces.WorkType;
import cz.muni.fi.pv168.freelancertimesheet.backend.orm.ClientImpl;
import cz.muni.fi.pv168.freelancertimesheet.backend.orm.WorkImpl;
import cz.muni.fi.pv168.freelancertimesheet.backend.orm.WorkTypeImpl;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class SampleDataGenerator {

    public static class GenerateWorkResult {
        public List<WorkType> workTypes;
        public List<Work> works;

        public GenerateWorkResult(List<WorkType> workTypes, List<Work> works) {
            this.workTypes = workTypes;
            this.works = works;
        }
    }

    public static List<WorkType> generateWorkType() {
        List<WorkType> collection = new ArrayList<>();
        collection.add(
                WorkTypeImpl.createWorkType(
                        "TestType1",
                        "Test description 1.",
                        "0.5"
                )
        );
        collection.add(
                WorkTypeImpl.createWorkType(
                        "TestType1",
                        "Test description 1.",
                        "250"
                )
        );
        collection.add(
                WorkTypeImpl.createWorkType(
                        "TestType1",
                        "Test description 1.",
                        "315.15"
                )
        );
        collection.stream().forEach(PersistanceManager::persistWorkType);
        return collection;
    }

    public static GenerateWorkResult generateWork() {
        List<WorkType> workTypes = generateWorkType();
        List<Work> collection = new ArrayList<>();
        collection.add(
                WorkImpl.createWork(
                        "Work11",
                        "Test work 11",
                        ZonedDateTime.parse("2011-12-05T10:07:30+01:00"),
                        ZonedDateTime.parse("2011-12-06T10:15:00+01:00"),
                        workTypes.get(0)
                )
        );
        collection.add(
                WorkImpl.createWork(
                        "Work12",
                        "Test work 12",
                        ZonedDateTime.parse("2011-12-07T10:07:30+01:00"),
                        ZonedDateTime.parse("2011-12-07T10:15:30+01:00"),
                        workTypes.get(0)
                )
        );
        collection.add(
                WorkImpl.createWork(
                        "Work21",
                        "Test work 21",
                        ZonedDateTime.parse("2011-12-08T10:07:30+01:00"),
                        ZonedDateTime.parse("2011-12-08T10:15:30+01:00"),
                        workTypes.get(1)
                )
        );
        collection.add(
                WorkImpl.createWork(
                        "Work22",
                        "Test work 22",
                        ZonedDateTime.parse("2011-12-09T10:07:30+01:00"),
                        ZonedDateTime.parse("2011-12-09T10:15:30+01:00"),
                        workTypes.get(1)
                )
        );
        collection.add(
                WorkImpl.createWork(
                        "Work31",
                        "Test work 31",
                        ZonedDateTime.parse("2011-12-10T10:07:30+01:00"),
                        ZonedDateTime.parse("2011-12-10T10:15:30+01:00"),
                        workTypes.get(2)
                )
        );
        collection.add(
                WorkImpl.createWork(
                        "Work32",
                        "Test work 32",
                        ZonedDateTime.parse("2011-12-11T10:07:30+01:00"),
                        ZonedDateTime.parse("2011-12-11T10:15:30+01:00"),
                        workTypes.get(2)
                )
        );
        collection.stream().forEach(PersistanceManager::persistWork);
        return new GenerateWorkResult(workTypes, collection);
    }


    public static List<Client> generateClients() {
        List<Client> collection = new ArrayList<>();
        collection.add(
                (Client) ClientImpl.createEntity(
                        "Name1",
                        "Address1",
                        "12345678",
                        "CZ12345678",
                        "+421090111222",
                        "mail1@mail.com"
                )
        );
        collection.add(
                (Client) ClientImpl.createEntity(
                        "Name2",
                        "Address2",
                        "22345678",
                        "CZ22345678",
                        "+421090111222",
                        "mail2@mail.com"
                )
        );
        collection.add(
                (Client) ClientImpl.createEntity(
                        "Name3",
                        "Address3",
                        "32345678",
                        "CZ32345678",
                        "+423090111222",
                        "mail3@mail.com"
                )
        );
        PersistanceManager.persistCollection(collection);
        return collection;
    }
}
