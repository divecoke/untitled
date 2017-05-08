package view;

import controllers.PersonOverviewController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Person;
import sample.MainApp;

import java.io.File;
import java.util.Optional;

/**
 * Created by Minde on 5/3/2017.
 */
public class RootLayoutController {
    private MainApp mainApp;
    private PersonOverviewController personOverviewController;
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private void handleNew() {
        mainApp.getPersonData().clear();
        mainApp.setPersonFilePath(null);
    }

    @FXML
    private void handleOpen() {
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show open file dialog
        File file = fileChooser.showOpenDialog(new Stage());

        if (file != null) {
            mainApp.loadPersonDataFromFile(file);
        }
    }

    @FXML
    private void handleSave() {
        File personFile = mainApp.getPersonFilePath();
        if (personFile != null) {
            mainApp.savePersonDataToFile(personFile);
        } else {
            handleSaveAs();
        }
    }

    @FXML
    private void handleSaveAs() {
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show save file dialog
        File file = fileChooser.showSaveDialog(new Stage());

        if (file != null) {
            // Make sure it has the correct extension
            if (!file.getPath().endsWith(".xml")) {
                file = new File(file.getPath() + ".xml");
            }
            mainApp.savePersonDataToFile(file);
        }
    }

    @FXML
    private void handleAbout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText("About");
        alert.setContentText("Mindaugas Poškus\nI11-2 grupė 2017 metai\nKlaipėdos valstybinė kolegija\n------------------------------------\nJava versija 1.8.0");

        alert.showAndWait();
    }
    @FXML
    private void handleFaq() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText("How to use?");
        alert.setContentText("If you want to create a new user click New(CTRL+N)\nIf you want to edit user," +
                " select user and click Edit\nIf you want to delete user, select user and click Del..\n" +
                "If you want to create a new file click File>New (CTRL+1)\n" +
                "If you want to save a current file click File>Save(CTRL+3)\n" +
                "If you want to save to a new file click File>Save As\n" +
                "If you want to open a new file click File>Open(CTRL+2)\n" +
                "If you watn to exit click File>Exit(CTRL+Q)");
        alert.showAndWait();
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
    private void handleExit() {
        System.exit(0);
    }

}
