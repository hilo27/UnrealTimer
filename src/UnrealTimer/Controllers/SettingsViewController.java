package UnrealTimer.Controllers;
// Created by –ÛÒÎ‡Ì on 24.06.2019.

import UnrealTimer.Settings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Action controller for the settings window
 */
@SuppressWarnings("WeakerAccess")
public class SettingsViewController implements Initializable {
    private static final Logger log = LoggerFactory.getLogger(SettingsViewController.class);
    // main controller
    MainViewController mainController = null;

    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  <tt>null</tt> if the location is not known.
     * @param resources The resources used to localize the root object, or <tt>null</tt> if
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setMainController(MainViewController mainController) {
        this.mainController = mainController;
    }

    /**
     * «¿ –€“‹ Œ ÕŒ Õ¿—“–Œ≈ 
     * Assign the call this method in SceneBuilder.onAction
     * https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/ButtonBase.html#getOnAction--
     */
    @FXML
    private void saveButtonClick(ActionEvent event) {
        if (mainController != null) {
            mainController.settings = new Settings("Y",45, "U", 75).save();
            mainController.initComponents();
        }
        closeStage(event);
    }

    /**
     * «¿ –€“‹ Œ ÕŒ Õ¿—“–Œ≈ 
     * Assign the call this method in SceneBuilder.onAction
     * https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/ButtonBase.html#getOnAction--
     */
    @FXML
    private void cancelButtonClick(ActionEvent event) {
        closeStage(event);
    }

    private void closeStage(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
