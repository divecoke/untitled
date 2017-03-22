package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Person;
import sample.MainApp;

import java.util.Optional;

public class PersonOverviewController {
    @FXML
    private TableView<Person> personTable;
    @FXML
    private TableColumn<Person, String> firstNameColumn;
    @FXML
    private TableColumn<Person, String> lastNameColumn;
    @FXML
    private TableColumn<Person, String> emailColumn;
    @FXML
    private TableColumn<Person, String> streetColumn;
    @FXML
    private TableColumn<Person, String> cityColumn;
    @FXML
    private Label firstNameLabel;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label streetLabel;
    @FXML
    private Label postalCodeLabel;
    @FXML
    private Label cityLabel;
    @FXML
    private Label birthdayLabel;
    @FXML
    private TextField filterField;

    public CheckBox cbAdmin;


    private MainApp mainApp;

    public PersonOverviewController() {
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        emailColumn.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
        streetColumn.setCellValueFactory(cellData -> cellData.getValue().streetProperty());
        cityColumn.setCellValueFactory(cellData -> cellData.getValue().cityProperty());


        // listener for LIVE changes
        personTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showPersonDetails(newValue));
        //filterList();
    }

    @FXML
    public void filterList() {
        //System.out.println(mainApp.getPersonData());
        FilteredList<Person> filteredList = new FilteredList<>(mainApp.getPersonData(), p -> true);

        filterField.textProperty().addListener(((observable, oldValue, newValue) -> {
            filteredList.setPredicate(person -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (person.getFirstName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (person.getLastName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        }));
        SortedList<Person> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(personTable.comparatorProperty());
        personTable.setItems(sortedList);
    }

    private void showPersonDetails(Person person) {
        if (person != null) {
            firstNameLabel.setText(person.getFirstName());
            lastNameLabel.setText(person.getLastName());
            emailLabel.setText(person.getEmail());
            streetLabel.setText(person.getStreet());
            postalCodeLabel.setText(Integer.toString(person.getPostalCode()));
            cityLabel.setText(person.getCity());
            birthdayLabel.setText(person.getBirthday().toString());
            cbAdmin.setSelected(person.isAdmin());
            cbAdmin.setDisable(true);
        } else {
            firstNameLabel.setText("");
            lastNameLabel.setText("");
            emailLabel.setText("");
            streetLabel.setText("");
            postalCodeLabel.setText("");
            cityLabel.setText("");
            birthdayLabel.setText("");
            cbAdmin.setDisable(true);
            cbAdmin.setSelected(false);

        }
    }

    // delete person
    @FXML
    private void handleDeletePerson() {
        int selectedIndex = personTable.getSelectionModel().getSelectedIndex();
        // error handling if no user selected
        if (selectedIndex >= 0) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Look, a Confirmation Dialog");
            alert.setContentText("Are you sure you want to delete?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                personTable.getItems().remove(selectedIndex);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setContentText("Please select a person in the table.");
            alert.showAndWait();
        }

    }

    @FXML
    private void handleNewPerson() throws Exception {
        Person tempPerson = new Person();
        boolean clicked = mainApp.showPersonEdit(tempPerson);
        if (clicked) {
            mainApp.getPersonData().add(tempPerson);
        }
    }

    @FXML
    private void handleEditPerson() throws Exception {
        Person selectedPerson = personTable.getSelectionModel().getSelectedItem();
        if (selectedPerson != null) {
            boolean okClicked = mainApp.showPersonEdit(selectedPerson);
            if (okClicked) {
                showPersonDetails(selectedPerson);
            }

        } else {
            // Nothing selected.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Person Selected");
            alert.setContentText("Please select a person in the table.");

            alert.showAndWait();
        }
    }



    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        // add list to table
        personTable.setItems(mainApp.getPersonData());
    }
}