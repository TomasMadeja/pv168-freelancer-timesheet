package cz.muni.fi.pv168.freelancertimesheet.backend.orm;

import cz.muni.fi.pv168.freelancertimesheet.backend.interfaces.Entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.Objects;

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

    public EntityImpl(String name, String address, String ico, String dic, String phoneNumber, String email) {
        this.name = name;
        this.address = address;
        this.ico = ico;
        this.dic = dic;
        this.phoneNumber = phoneNumber;
        this.email = email;
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


//    public static Entity createEntity(String name, String address, String ico, String dic, String phoneNumber, String email) {
//        return new EntityImpl(name, address, ico, dic, phoneNumber, email);
//    }

    @Override
    public void validateAttributes() {
        validateName(name);
        validateAddress(address);
        validateDIC(dic);
        validateICO(ico);
        validatePhoneNumber(phoneNumber);
        validateEmail(email);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityImpl entity = (EntityImpl) o;
        return id == entity.id &&
                Objects.equals(name, entity.name) &&
                Objects.equals(address, entity.address) &&
                Objects.equals(email, entity.email) &&
                Objects.equals(phoneNumber, entity.phoneNumber) &&
                Objects.equals(ico, entity.ico) &&
                Objects.equals(dic, entity.dic);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, address, email, phoneNumber, ico, dic);
    }
}
