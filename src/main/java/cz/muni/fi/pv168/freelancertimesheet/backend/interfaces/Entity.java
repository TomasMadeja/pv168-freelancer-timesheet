package cz.muni.fi.pv168.freelancertimesheet.backend.interfaces;

public interface Entity {
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

    public Entity createEntity(
            String name,
            String address
    );
}
