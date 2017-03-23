package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Person;
import controllers.PersonEditController;
import controllers.PersonOverviewController;

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
        //primaryStage.setResizable(false);
        primaryStage.show();
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

    public static void main(String[] args) {
        launch(args);
    }
}