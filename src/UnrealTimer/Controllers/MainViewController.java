package UnrealTimer.Controllers;

import UnrealTimer.Settings;
import com.tulskiy.keymaster.common.HotKey;
import com.tulskiy.keymaster.common.HotKeyListener;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static UnrealTimer.Main.keyShortcutsProvider;
import static javafx.stage.Modality.APPLICATION_MODAL;

/**
 * Action controller for the main window
 */
@SuppressWarnings("WeakerAccess")
public class MainViewController implements HotKeyListener {
    private static final Logger log = LoggerFactory.getLogger(MainViewController.class);
    public Settings settings = null;

    IntegerProperty shieldRespawnInterval;
    IntegerProperty ddRespawnInterval;

    Timeline shieldTimer = new Timeline();
    Timeline ddTimer = new Timeline();

    @FXML
    Label shieldTimerLabel = new Label();
    @FXML
    Label ddTimerLabel = new Label();

    /**
     * ÍÀÑÒÐÎÉÊÈ
     * Assign the call this method in SceneBuilder.onAction
     * https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/ButtonBase.html#getOnAction--
     */
    @FXML
    private void settingsButtonClick(ActionEvent event) {
        System.out.println("settingsButtonClick");
        try {
            FXMLLoader settingsWindow = new FXMLLoader(getClass().getResource("../Frames/settings_window.fxml"));
            Parent settings = settingsWindow.load();
            // assign in settings_window tag fx:controller
            SettingsViewController controller = settingsWindow.getController();
            Stage window = new Stage();
            // block other windows
            window.initModality(APPLICATION_MODAL);
            window.setResizable(false);
            window.setScene(new Scene(settings, 203, 309));
            window.showAndWait();

        } catch (Exception e) {
            log.error("Cannot create settings window.", e);
        }
    }

    /**
     * ÂÛÕÎÄ
     * Assign the call this method in SceneBuilder.onAction
     * https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/ButtonBase.html#getOnAction--
     */
    @FXML
    private void exitButtonClick(ActionEvent event) {
        Platform.exit();
    }

    /**
     * The method in which action handles
     *
     * @param key - Internal representation of a hotkey
     */
    public void onHotKey(HotKey key) {
        System.out.println(key.toString());

        if (isShieldKeyPress(key)) {
            // start shield timer
            startShieldTimer();

        } else if (isDoubleDamagedKeyPress(key)) {
            // start dd timer
            startDDTimer();
        }

    }

    private void startShieldTimer() {
        Platform.runLater(() -> {
            // restore original value
            shieldRespawnInterval.setValue(settings.getShieldDurationCycle());
            // clearing previous keyFrames, to avoid double count and memory leaks
            shieldTimer.getKeyFrames().clear();
            // adding fresh keyFrame
            shieldTimer.getKeyFrames().add(
                    new KeyFrame(Duration.seconds(settings.getShieldDurationCycle() + 1), new KeyValue(shieldRespawnInterval, 0)));
            shieldTimer.playFromStart();
        });
    }

    private void startDDTimer() {
        Platform.runLater(() -> {
            // restore original value
            ddRespawnInterval.setValue(settings.getDoubleDamageDurationCycle());
            // clearing previous keyFrames, to avoid double count and memory leaks
            ddTimer.getKeyFrames().clear();
            // adding fresh keyFrame
            ddTimer.getKeyFrames().add(
                    new KeyFrame(Duration.seconds(settings.getDoubleDamageDurationCycle() + 1), new KeyValue(ddRespawnInterval, 0)));
            ddTimer.playFromStart();

        });
    }

    private boolean isShieldKeyPress(HotKey hotKey) {
        return hotKey.keyStroke.getKeyCode() == settings.getShieldHotKey().getKeyCode();
    }

    private boolean isDoubleDamagedKeyPress(HotKey hotKey) {
        return hotKey.keyStroke.getKeyCode() == settings.getDoubleDamageHotKey().getKeyCode();
    }


    /**
     * This method combines all necessary initialization
     */
    public void initComponents() {
        loadSettings();
        initShield();
        initDoubleDamage();
        initShortcuts();
    }

    /**
     * Initialize shortcuts
     */
    private void initShortcuts() {
        if (settings == null) {
            loadSettings();
        }
        keyShortcutsProvider.reset();
        keyShortcutsProvider.register(settings.getShieldHotKey(), this);
        keyShortcutsProvider.register(settings.getDoubleDamageHotKey(), this);
    }

    /**
     * This method initialize shield timer, bind to label and add listener on interval change event
     */
    private void initShield() {
        // duration interval
        shieldRespawnInterval = new SimpleIntegerProperty(settings.getShieldDurationCycle());
        // add listener on change value
        shieldRespawnInterval.addListener((observable, oldValue, newValue) -> {
            // TODO run in new thread
            System.out.println(newValue);
        });
        // bind to the label and because of it there is no need to set initial value
        shieldTimerLabel.textProperty().bind(shieldRespawnInterval.asString());
    }

    /**
     * This method initialize double damage timer, bind to label and add listener on interval change event
     */
    private void initDoubleDamage() {
        // duration interval
        ddRespawnInterval = new SimpleIntegerProperty(settings.getDoubleDamageDurationCycle());
        // add listener on change value
        ddRespawnInterval.addListener((observable, oldValue, newValue) -> {
            // TODO run in new thread
            System.out.println(newValue);
        });
        // bind to the label and because of it there is no need to set initial value
        ddTimerLabel.textProperty().bind(ddRespawnInterval.asString());
    }

    /**
     * Load saved settings
     */
    private void loadSettings() {
        settings = new Settings().load();
    }
}
