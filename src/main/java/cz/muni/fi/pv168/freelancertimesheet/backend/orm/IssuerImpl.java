package cz.muni.fi.pv168.freelancertimesheet.backend.orm;

import cz.muni.fi.pv168.freelancertimesheet.backend.interfaces.Entity;
import cz.muni.fi.pv168.freelancertimesheet.backend.interfaces.Issuer;

import javax.persistence.Table;

@javax.persistence.Entity
@Table(name="issuers")
public class IssuerImpl extends EntityImpl implements Issuer {

    public IssuerImpl(String name, String address, String ico, String dic) {
        super(name, address, ico, dic);
    }

    public IssuerImpl() {
        super();
    }

    public static Entity createEntity(String name, String address, String ico, String dic) {
        return new IssuerImpl(name, address, ico, dic);
    }
}
