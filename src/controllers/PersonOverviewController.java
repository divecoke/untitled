package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Person;
import sample.MainApp;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.util.List;
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
    @FXML
    private ImageView ivProfile;

    public CheckBox cbAdmin;
    @FXML
    private Pagination pPagination;

    @FXML
    private Label pagLabel;
    private MainApp mainApp;

    public PersonOverviewController() {

    }

    public int itemsPerPage() {
        return 8;
    }

    public int rowsPerPage() {
        return 2;
    }

    public VBox createPage(int pageIndex) {
        VBox box = new VBox(5);
        int page = pageIndex * itemsPerPage();
        for (int i = page; i < page + itemsPerPage(); i++) {
            VBox element = new VBox();
            Hyperlink link = new Hyperlink("Item " + (i+1));
            link.setVisited(true);
            pagLabel.setText(link.getText());
            //Label text = new Label("Search results\nfor "+ link.getText());
            element.getChildren().addAll(link, pagLabel);
            box.getChildren().add(element);
        }
        return box;
    }

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
        /*pPagination = new Pagination(28, 0);
        pPagination.setStyle("-fx-border-color:red;");
        pPagination.setPageFactory(new Callback<Integer, Node>() {
            @Override
            public Node call(Integer pageIndex) {
                return createPage(pageIndex);
            }
        });*/

    }

    public FilteredList<Person> filteredList;
    @FXML
    public void filterList() {
        System.out.println(mainApp.getPersonData());
        filteredList = new FilteredList<>(mainApp.getPersonData(), p -> true);

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
            ivProfile.setImage(person.getImage());

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
            ivProfile.setImage(null);

        }
    }

    // delete person
    @FXML
    private void handleDeletePerson() throws Exception{
        int selectedIndex = personTable.getSelectionModel().getSelectedIndex();
        // error handling if no user selected
        if (selectedIndex >= 0) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Look, a Confirmation Dialog");
            alert.setContentText("Are you sure you want to delete?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                // selects source index from main list
                int sourceIndex = filteredList.getSourceIndexFor(mainApp.getPersonData(), selectedIndex);
                mainApp.getPersonData().remove(sourceIndex);
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
        //System.out.println(mainApp.getPersonData());
        personTable.setItems(mainApp.getPersonData());
        filterList();

    }
}