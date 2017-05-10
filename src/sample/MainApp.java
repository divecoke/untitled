package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Person;
import controllers.PersonEditController;
import controllers.PersonOverviewController;
import model.PersonListWrapper;
import view.RootLayoutController;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.prefs.Preferences;


public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    private BorderPane personOverview;

    // list
    public ObservableList<Person> personData = FXCollections.observableArrayList();

    public MainApp() {
        personData.add(new Person("John", "Lyn"));
        personData.add(new Person("John1", "Lyn1"));
        personData.add(new Person("John2", "Lyn2"));
        personData.add(new Person("John3", "Lyn3"));
        personData.add(new Person("John4", "Lyn4"));
        personData.add(new Person("John5", "Lyn5"));
        personData.add(new Person("John6", "Lyn6"));
    }

    public ObservableList<Person> getPersonData() {
        return personData;
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("User creation");

        this.primaryStage.setResizable(false);
        // icon
        this.primaryStage.getIcons().add(new Image("/images/arrow-32-128.png"));
        // root layoutas
        initRootLayout();
        // show persons
        showPersonOverview();

    }


    public void initRootLayout() throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("../view/RootLayout.fxml"));
        rootLayout = (BorderPane) loader.load();

        Scene scene = new Scene(rootLayout);
        scene.getStylesheets().add(MainApp.class.getResource("/bootstrap.css").toExternalForm());
        primaryStage.setScene(scene);

        RootLayoutController controller = loader.getController();
        controller.setMainApp(this);
        //primaryStage.setResizable(false);
        primaryStage.show();

        // Try to load last opened person file.
        File file = getPersonFilePath();
        if (file != null) {
            loadPersonDataFromFile(file);
        }
    }

    public void showPersonOverview() throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("../view/PersonOverview.fxml"));
        AnchorPane personOverview = (AnchorPane) loader.load();


        rootLayout.setCenter(personOverview);

        PersonOverviewController controller = loader.getController();
        controller.setMainApp(this);

    }




    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public boolean showPersonEdit(Person person) throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("../view/PersonEdit.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        Stage dialogStage = new Stage();
        dialogStage.setTitle("Edit Person");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(primaryStage);
        Scene scene = new Scene(page);
        //primaryStage.setResizable(false);
        dialogStage.setScene(scene);

        PersonEditController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setPerson(person);

        dialogStage.showAndWait();

        return controller.isClicked();
    }

    public File getPersonFilePath() {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        String filePath = prefs.get("filePath", null);
        if (filePath != null) {
            return new File(filePath);
        } else {
            return null;
        }
    }

    public void setPersonFilePath(File file) {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        if (file != null) {
            prefs.put("filePath", file.getPath());

            // Update the stage title.
            primaryStage.setTitle("AddressApp - " + file.getName());
        } else {
            prefs.remove("filePath");

            // Update the stage title.
            primaryStage.setTitle("AddressApp");
        }
    }

    public void loadPersonDataFromFile(File file) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(PersonListWrapper.class);
            Unmarshaller um = context.createUnmarshaller();

            // Reading XML from the file and unmarshalling.
            PersonListWrapper wrapper = (PersonListWrapper) um.unmarshal(file);

            personData.clear();
            personData.addAll(wrapper.getPersons());

            // Save the file path to the registry.
            setPersonFilePath(file);

        } catch (Exception e) { // catches ANY exception
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not load data");
            alert.setContentText("Could not load data from file:\n" + file.getPath());

            alert.showAndWait();
        }
    }
    public void savePersonDataToFile(File file) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(PersonListWrapper.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // Wrapping our person data.
            PersonListWrapper wrapper = new PersonListWrapper();
            wrapper.setPersons(personData);

            // Marshalling and saving XML to the file.
            m.marshal(wrapper, file);

            // Save the file path to the registry.
            setPersonFilePath(file);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText("Successful");
            alert.setContentText("Successfully Saved!");
            alert.showAndWait();
        } catch (Exception e) { // catches ANY exception
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not save data");
            alert.setContentText("Could not save data to file:\n" + file.getPath());

            alert.showAndWait();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}