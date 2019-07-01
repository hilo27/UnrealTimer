package UnrealTimer.Controllers;
// Created by –ÛÒÎ‡Ì on 24.06.2019.

import UnrealTimer.Settings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Action controller for the settings window
 */
@SuppressWarnings("WeakerAccess")
public class SettingsViewController {
    private static final Logger log = LoggerFactory.getLogger(SettingsViewController.class);
    // main controller
    MainViewController mainController = null;

    /**
     * Set main view controller, so that later to be able to call his methods
     * Also initialize value for the form
     *
     * @param mainController - <code>{@link MainViewController}</code>
     */
    public void setMainController(MainViewController mainController) {
        initData(mainController);
    }

    private void initData(MainViewController mainController) {
        if (mainController != null) {
            this.mainController = mainController;
            System.out.println("init");
        }
    }

    /**
     * «¿ –€“‹ Œ ÕŒ Õ¿—“–Œ≈ 
     * Assign the call this method in SceneBuilder.onAction
     * https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/ButtonBase.html#getOnAction--
     */
    @FXML
    private void saveButtonClick(ActionEvent event) {
        if (mainController != null) {
            mainController.settings = new Settings("Y", 45, "U", 75).save();
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
