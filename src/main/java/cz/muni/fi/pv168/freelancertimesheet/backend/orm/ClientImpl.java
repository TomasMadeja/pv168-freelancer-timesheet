package cz.muni.fi.pv168.freelancertimesheet.backend.orm;

import cz.muni.fi.pv168.freelancertimesheet.backend.interfaces.Client;
import cz.muni.fi.pv168.freelancertimesheet.backend.interfaces.Entity;

import javax.persistence.Table;

@javax.persistence.Entity
@Table(name="clients")
public class ClientImpl extends EntityImpl implements Client {


    public ClientImpl(String name, String address, String ico, String dic) {
        super(name, address, ico, dic);
    }

    public ClientImpl() {
        super();
    }

    public static Entity createEntity(String name, String address, String ico, String dic) {
        return new ClientImpl(name, address, ico, dic);
    }
}
