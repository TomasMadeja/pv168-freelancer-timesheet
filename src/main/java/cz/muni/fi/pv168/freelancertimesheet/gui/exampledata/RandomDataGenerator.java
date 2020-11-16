package cz.muni.fi.pv168.freelancertimesheet.gui.exampledata;

import cz.muni.fi.pv168.freelancertimesheet.backend.interfaces.Service;
import cz.muni.fi.pv168.freelancertimesheet.backend.interfaces.ServiceType;
import cz.muni.fi.pv168.freelancertimesheet.gui.models.ServiceTableModel;

import java.time.ZonedDateTime;

public class RandomDataGenerator {
    public static class ServiceImpl implements Service {

        private String name;
        private String description;
        private ZonedDateTime startTime;
        private ZonedDateTime endTime;
        private ServiceType serviceType;

        @Override
        public String getName() {
            return name;
        }

        @Override
        public Service setName() {
            return null;
        }

        @Override
        public Service validateName9() {
            return null;
        }

        @Override
        public String getDescription() {
            return description;
        }

        @Override
        public Service setDescription() {
            return null;
        }

        @Override
        public Service validateDescription() {
            return null;
        }

        @Override
        public ZonedDateTime getStartTime() {
            return startTime;
        }

        @Override
        public Service setStartTime(ZonedDateTime datetime) {
            return null;
        }

        @Override
        public Service validateStartTime(ZonedDateTime datetime) {
            return null;
        }

        @Override
        public ZonedDateTime getEndTime() {
            return endTime;
        }

        @Override
        public Service setEndTime(ZonedDateTime datetime) {
            return null;
        }

        @Override
        public Service validateEndTime(ZonedDateTime datetime) {
            return null;
        }

        @Override
        public ServiceType getServiceType() {
            return serviceType;
        }

        @Override
        public Service setServiceType(ServiceType serviceType) {
            return null;
        }

        @Override
        public Service validateServiceType(ServiceType serviceType) {
            return null;
        }

        @Override
        public Service createService(String name, String description, ZonedDateTime startTime, ZonedDateTime endTime, ServiceType serviceType) {
            this.name = name;
            this.description = description;
            this.startTime = startTime;
            this.endTime = endTime;
            this.serviceType = serviceType;
            return this;
        }
    }

    public static void GenerateServiceData (ServiceTableModel table) {
        table.addRow(new ServiceImpl().createService(
                "Funny name",
                "This is a funny description",
                ZonedDateTime.now(),
                ZonedDateTime.now(),
                null
                ));
    }
}
