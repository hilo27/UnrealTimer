package UnrealTimer;

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

import javax.swing.*;

import static UnrealTimer.Main.keyShortcutsProvider;

/**
 * Action controller for the main window
 */
@SuppressWarnings("WeakerAccess")
public class MainController implements HotKeyListener {
    private static final Logger log = LoggerFactory.getLogger(MainController.class);
    private Settings settings = null;

    IntegerProperty shieldRespawnInterval;
    IntegerProperty ddRespawnInterval;

    @FXML
    Label shieldTimerLabel = new Label();
    @FXML
    Label ddTimerLabel = new Label();

    /**
     * Õ¿—“–Œ… »
     * Assign the call this method in SceneBuilder.onAction
     * https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/ButtonBase.html#getOnAction--
     */
    @FXML
    private void settingsButtonClick(ActionEvent event) {
        System.out.println("settingsButtonClick");
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("settings_window.fxml"));
            // assign in settings_window tag fx:controller
            // fxmlLoader.setController(new SettingsController());
            Parent settings = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(settings, 500, 600));
            stage.show();

        } catch (Exception e) {
            log.error("Cannot create settings window.", e);
        }
    }

    /**
     * ¬€’Œƒ
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
        Platform.runLater(new Runnable() {
            public void run() {
                Timeline timeline = new Timeline();
                // restore original value
                shieldRespawnInterval.setValue(settings.getShieldDurationCycle());
                // set binding to label
                shieldTimerLabel.textProperty().bind(shieldRespawnInterval.asString());

//                if (shieldRespawnInterval.isEqualTo(5).get()) {
//                    System.out.println("its 5 sec");
//                }

                timeline.getKeyFrames().add(
                        new KeyFrame(Duration.seconds(settings.getShieldDurationCycle() + 1),
                                new KeyValue(shieldRespawnInterval, 0)));
                timeline.playFromStart();

            }
        });
    }

    private void startDDTimer() {
        Platform.runLater(new Runnable() {
            public void run() {
                Timeline timeline = new Timeline();
                // restore original value
                ddRespawnInterval.setValue(settings.getDoubleDamageDurationCycle());
                // set binding to label
                ddTimerLabel.textProperty().bind(ddRespawnInterval.asString());

                timeline.getKeyFrames().add(
                        new KeyFrame(Duration.seconds(settings.getDoubleDamageDurationCycle() + 1),
                                new KeyValue(ddRespawnInterval, 0)));
                timeline.playFromStart();

            }
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
        initTimers();
        initShortcuts();
    }

    /**
     * Initialize timers properties
     */
    private void initTimers() {
        if (settings == null) {
            loadSettings();
        }
        shieldRespawnInterval = new SimpleIntegerProperty(settings.getShieldDurationCycle());
        shieldTimerLabel.setText(String.format("%02d", shieldRespawnInterval.get()));

        ddRespawnInterval = new SimpleIntegerProperty(settings.getDoubleDamageDurationCycle());
        ddTimerLabel.setText(String.format("%02d", ddRespawnInterval.get()));
    }

    /**
     * Initialize shortcuts
     */
    private void initShortcuts() {
        if (settings == null) {
            loadSettings();
        }
        keyShortcutsProvider.reset();
        keyShortcutsProvider.register(KeyStroke.getKeyStroke(settings.getShieldStartShorcut()), this);
        keyShortcutsProvider.register(KeyStroke.getKeyStroke(settings.getDoubleDamageStartShorcut()), this);
    }

    /**
     * Load saved settings
     */
    private void loadSettings() {
        settings = new Settings().load();
    }
}
