package UnrealTimer.Controllers;
// Created by –ÛÒÎ‡Ì on 24.06.2019.

import UnrealTimer.Settings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.event.KeyEvent;

/**
 * Action controller for the settings window
 */
@SuppressWarnings("WeakerAccess")
public class SettingsViewController {
    private static final Logger log = LoggerFactory.getLogger(SettingsViewController.class);
    // main controller object
    private MainViewController mainController = null;
    private Settings currentSettings;

    // SHIELD FIELDS
    @FXML
    TextField inputShieldButton = new TextField();
    @FXML
    TextField inputShieldInterval = new TextField();

    // DOUBLE DAMAGE FIELDS
    @FXML
    TextField inputDDButton = new TextField();
    @FXML
    TextField inputDDInterval = new TextField();

    /**
     * Set main view controller, and put current config in form fields
     * Also initialize value for the form
     *
     * @param mainController - <code>{@link MainViewController}</code>
     */
    public void initData(MainViewController mainController) {
        if (mainController != null) {
            this.mainController = mainController;
            this.currentSettings = mainController.settings;
            populateForm();
        }
    }

    /**
     * Filling the settings window with the current configuration
     */
    private void populateForm() {
        // set key
        inputShieldButton.setText(KeyEvent.getKeyText(currentSettings.getShieldHotKey().getKeyCode()));
        inputDDButton.setText(KeyEvent.getKeyText(currentSettings.getDoubleDamageHotKey().getKeyCode()));
        // set intervals
        inputShieldInterval.setText(String.valueOf(currentSettings.getShieldDurationInterval()));
        inputDDInterval.setText(String.valueOf(currentSettings.getDoubleDamageDurationInterval()));
    }

    /**
     * «¿ –€“‹ Œ ÕŒ Õ¿—“–Œ≈ 
     * Assign the call this method in SceneBuilder.onAction
     * https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/ButtonBase.html#getOnAction--
     */
    @FXML
    private void saveButtonClick(ActionEvent event) {
        if (mainController != null) {
            currentSettings = new Settings("Y", 45, "U", 75).save();
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
