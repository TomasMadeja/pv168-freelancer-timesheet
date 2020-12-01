package cz.muni.fi.pv168.freelancertimesheet.backend.orm;

import cz.muni.fi.pv168.freelancertimesheet.backend.interfaces.Entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class EntityImpl implements Entity {

    @Id
    @GeneratedValue
    @Column(name = "id")
    protected int id;

    @Column(name = "name", nullable=false)
    protected String name;

    @Column(name = "address", nullable=false)
    protected String address;

    @Column(name = "email", nullable=true)
    protected String email;

    @Column(name = "phone_number", nullable=true)
    protected String phoneNumber;

    @Column(name = "ico", nullable=false)
    protected String ico;

    @Column(name = "dic", nullable=false)
    protected String dic;

    public EntityImpl(String name, String address, String ico, String dic) {
        this.name = name;
        this.address = address;
        this.ico = ico;
        this.dic = dic;
    }

    public EntityImpl() {
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Entity setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public Entity validateName(String name) {
        return this;
    }

    @Override
    public String getAddress() {
        return address;
    }

    @Override
    public Entity setAddress(String address) {
        this.address = address;
        return this;
    }

    @Override
    public Entity validateAddress(String address) {
        return this;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public Entity setEmail(String email) {
        this.email = email;
        return this;
    }

    @Override
    public Entity validateEmail(String email) {
        return this;
    }

    @Override
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public Entity setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    @Override
    public Entity validatePhoneNumber(String phoneNumber) {
        return this;
    }

    @Override
    public String getICO() {
        return ico;
    }

    @Override
    public Entity setICO(String ico) {
        this.ico = ico;
        return this;
    }

    @Override
    public Entity validateICO(String ico) {
        return this;
    }

    @Override
    public String getDIC() {
        return dic;
    }

    @Override
    public Entity setDIC(String dic) {
        this.dic = dic;
        return this;
    }

    @Override
    public Entity validateDIC(String dic) {
        return this;
    }

    @Override
    public String toXML() {
        return null;
    }


    public static Entity createEntity(String name, String address, String ico, String dic) {
        return new EntityImpl(name, address, ico, dic);
    }
}
