package cz.muni.fi.pv168.freelancertimesheet.backend.interfaces;

public interface Entity extends ORMEntity {
    public String getName();

    public Entity setName(String name);

    public Entity validateName(String name);

    public String getAddress();

    public Entity setAddress(String address);

    public Entity validateAddress(String address);

    public String getEmail();

    public Entity setEmail(String email);

    public Entity validateEmail(String email);

    public String getPhoneNumber();

    public Entity setPhoneNumber(String phoneNumber);

    public Entity validatePhoneNumber(String phoneNumber);

    public String getICO();

    public Entity setICO(String ico);

    public Entity validateICO(String ico);

    public String getDIC();

    public Entity setDIC(String dic);

    public Entity validateDIC(String dic);

    public static Entity createEntity(
            String name,
            String address
    ) {
        return null;
    }

    public String toXML();
}
