package model;

import java.time.LocalDate;

import javafx.beans.property.*;

public class Person {

    private final StringProperty firstName;
    private final StringProperty lastName;
    private final StringProperty street;



    private final StringProperty email;
    private final IntegerProperty postalCode;
    private final StringProperty city;
    private final ObjectProperty<LocalDate> birthday;
    private final BooleanProperty admin;

    public Person() {
        this(null, null);
    }

    public boolean isAdmin() {
        return admin.get();
    }

    public BooleanProperty adminProperty() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin.setValue(admin);
    }

    public Person(String firstName, String lastName) {
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);

        // dummy
        this.email = new SimpleStringProperty("lakis22@gmail.com");
        this.street = new SimpleStringProperty("avenue st. 32-24");
        this.postalCode = new SimpleIntegerProperty(94118);
        this.city = new SimpleStringProperty("Klaipeda");
        this.birthday = new SimpleObjectProperty<LocalDate>(LocalDate.of(1997, 2, 7));
        this.admin = new SimpleBooleanProperty(false);
    }


    public String getEmail() {
        return email.get();
    }

    public StringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getFirstName() {
        return firstName.get();
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }

    public String getLastName() {
        return lastName.get();
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    public String getStreet() {
        return street.get();
    }

    public void setStreet(String street) {
        this.street.set(street);
    }

    public StringProperty streetProperty() {
        return street;
    }

    public int getPostalCode() {
        return postalCode.get();
    }

    public void setPostalCode(int postalCode) {
        this.postalCode.set(postalCode);
    }

    public IntegerProperty postalCodeProperty() {
        return postalCode;
    }

    public String getCity() {
        return city.get();
    }

    public void setCity(String city) {
        this.city.set(city);
    }

    public StringProperty cityProperty() {
        return city;
    }

    public LocalDate getBirthday() {
        return birthday.getValue();
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday.set(birthday);
    }

    public ObjectProperty<LocalDate> birthdayProperty() {
        return birthday;
    }
}