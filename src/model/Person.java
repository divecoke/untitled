package model;

import java.io.File;
import java.time.LocalDate;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.util.Callback;
import util.LocalDateAdapter;

import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

public class Person {
    private final StringProperty firstName;
    private final StringProperty lastName;
    private final StringProperty street;
    private final StringProperty email;
    private final IntegerProperty postalCode;
    private final StringProperty city;
    private final ObjectProperty<LocalDate> birthday;
    private final BooleanProperty admin;
    private final ObjectProperty<Image> image;
    private final ComboBox cbCombo;
    private final IntegerProperty sSlider;



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
        File file = new File("C:/Users/Minde/IdeaProjects/untitled/src/images/arrow-32-128.png"); // "/home/dive/IdeaProjects/untitled/src/images/arrow-32-128.png"
        Image image = new Image(file.toURI().toString());
        this.image = new SimpleObjectProperty<Image>(image);
        this.sSlider = new SimpleIntegerProperty(80);
        this.cbCombo = new ComboBox();
        this.cbCombo.setValue("Normal");

    }

    public Person() {
        this(null, null);
    }
    public boolean isAdmin() {
        return admin.get();
    }

    // SETTERS
    public void setAdmin(boolean admin) {
        this.admin.setValue(admin);
    }
    @XmlTransient  //excludes fields from JAXB for marshalling
    public void setImage(Image image) {
        this.image.set(image);
    }
    public void setsSlider(int sSlider) { this.sSlider.set(sSlider); }
    public void setEmail(String email) {
        this.email.set(email);
    }
    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }
    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }
    public void setPostalCode(int postalCode) {
        this.postalCode.set(postalCode);
    }
    public void setCity(String city) {
        this.city.set(city);
    }
    public void setBirthday(LocalDate birthday) {
        this.birthday.set(birthday);
    }
    public void setStreet(String street) {
        this.street.set(street);
    }

    // GETTERS
    public Image getImage() {
        return image.get();
    }
    public ComboBox getCbCombo() {
        return cbCombo;
    }
    public int getsSlider() {
        return sSlider.get();
    }
    public String getEmail() {
        return email.get();
    }
    public String getFirstName() {
        return firstName.get();
    }
    public String getLastName() {
        return lastName.get();
    }
    public String getStreet() {
        return street.get();
    }
    public int getPostalCode() {
        return postalCode.get();
    }
    public String getCity() {
        return city.get();
    }
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    public LocalDate getBirthday() {
        return birthday.getValue();
    }

    // PROPERTIES
    public BooleanProperty adminProperty() {
        return admin;
    }
    public StringProperty emailProperty() {
        return email;
    }
    public StringProperty firstNameProperty() {
        return firstName;
    }
    public ObjectProperty<Image> imageProperty() {
        return image;
    }
    public StringProperty lastNameProperty() {
        return lastName;
    }
    public StringProperty streetProperty() {
        return street;
    }
    public IntegerProperty postalCodeProperty() {
        return postalCode;
    }
    public StringProperty cityProperty() {
        return city;
    }
    public ObjectProperty<LocalDate> birthdayProperty() {
        return birthday;
    }





}