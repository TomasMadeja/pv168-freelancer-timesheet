package cz.muni.fi.pv168.freelancertimesheet.gui.exampledata;

import cz.muni.fi.pv168.freelancertimesheet.backend.interfaces.Work;
import cz.muni.fi.pv168.freelancertimesheet.backend.interfaces.WorkType;
import cz.muni.fi.pv168.freelancertimesheet.gui.models.WorkTableModel;

import java.time.ZonedDateTime;

public class RandomDataGenerator {
    public static class WorkImpl implements Work {

        private String name;
        private String description;
        private ZonedDateTime startTime;
        private ZonedDateTime endTime;
        private WorkType workType;

        @Override
        public String getName() {
            return name;
        }

        @Override
        public Work setName() {
            return null;
        }

        @Override
        public Work validateName() {
            return null;
        }

        @Override
        public String getDescription() {
            return description;
        }

        @Override
        public Work setDescription() {
            return null;
        }

        @Override
        public Work validateDescription() {
            return null;
        }

        @Override
        public ZonedDateTime getStartTime() {
            return startTime;
        }

        @Override
        public Work setStartTime(ZonedDateTime datetime) {
            return null;
        }

        @Override
        public Work validateStartTime(ZonedDateTime datetime) {
            return null;
        }

        @Override
        public ZonedDateTime getEndTime() {
            return endTime;
        }

        @Override
        public Work setEndTime(ZonedDateTime datetime) {
            return null;
        }

        @Override
        public Work validateEndTime(ZonedDateTime datetime) {
            return null;
        }

        @Override
        public WorkType getWorkType() {
            return workType;
        }

        @Override
        public Work setWorkType(WorkType workType) {
            return null;
        }

        @Override
        public Work validateWorkType(WorkType workType) {
            return null;
        }

        @Override
        public Work createService(String name, String description, ZonedDateTime startTime, ZonedDateTime endTime, WorkType workType) {
            this.name = name;
            this.description = description;
            this.startTime = startTime;
            this.endTime = endTime;
            this.workType = workType;
            return this;
        }
    }

    public static void GenerateServiceData (WorkTableModel table) {
        table.addRow(new WorkImpl().createService(
                "Funny name",
                "This is a funny description",
                ZonedDateTime.now(),
                ZonedDateTime.now(),
                null
                ));
    }
}
