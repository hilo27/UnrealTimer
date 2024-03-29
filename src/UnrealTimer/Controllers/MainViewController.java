package UnrealTimer.Controllers;

import UnrealTimer.Settings;
import UnrealTimer.Sound;
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
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.ResourceBundle;

import static UnrealTimer.Main.keyShortcutsProvider;
import static javafx.stage.Modality.APPLICATION_MODAL;

/**
 * Action controller for the main window
 */
@SuppressWarnings("WeakerAccess")
public class MainViewController implements HotKeyListener, Initializable {
    private static final Logger log = LoggerFactory.getLogger(MainViewController.class);
    public Settings settings = null;

    IntegerProperty shieldRespawnInterval = new SimpleIntegerProperty();
    IntegerProperty ddRespawnInterval = new SimpleIntegerProperty();

    Timeline shieldTimer = new Timeline();
    Timeline ddTimer = new Timeline();

    // this flag in case when shield timer set on one of the that trigger sound play
    Boolean playShieldSound;
    // this flag in case when double damage timer set on one of the that trigger sound play
    Boolean playDDSound;

    @FXML
    Label shieldTimerLabel = new Label();
    @FXML
    Label ddTimerLabel = new Label();

    /**
     * OPEN SETTINGS WINDOW
     * Assign the call this method in SceneBuilder.onAction
     * https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/ButtonBase.html#getOnAction--
     */
    @FXML
    private void settingsButtonClick(ActionEvent event) {
        System.out.println("settingsButtonClick");
        try {
            FXMLLoader settingsWindow = new FXMLLoader(getClass().getResource("/UnrealTimer/Frames/settings_window.fxml"));
            Parent settings = settingsWindow.load();
            // assign in settings_window tag fx:controller
            SettingsViewController settingsController = settingsWindow.getController();
            settingsController.initData(this);
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
     * EXIT
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
            // reset shield
            resetShield();
            // add fresh keyFrame
            shieldTimer.getKeyFrames().add(
                    new KeyFrame(Duration.seconds(settings.getShieldDurationInterval() + 1), new KeyValue(shieldRespawnInterval, 0)));
            shieldTimer.playFromStart();
        });
    }

    private void startDDTimer() {
        Platform.runLater(() -> {
            // reset dd
            resetDoubleDamage();
            // add fresh keyFrame
            ddTimer.getKeyFrames().add(
                    new KeyFrame(Duration.seconds(settings.getDoubleDamageDurationInterval() + 1), new KeyValue(ddRespawnInterval, 0)));
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
     * This method combines all necessary initialization.
     * Can be used if settings was changed
     */
    public void initComponents() {
        loadSettings();
        initShortcuts();
        resetShield();
        resetDoubleDamage();
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
     * This method reset shield timer, to the settings value
     */
    private void resetShield() {
        // clearing previously added keyFrames, to avoid double count or memory leaks
        shieldTimer.getKeyFrames().clear();
        // restore original duration interval
        shieldRespawnInterval.setValue(settings.getShieldDurationInterval());
    }

    /**
     * This method reset double damage timer, to the settings value
     */
    private void resetDoubleDamage() {
        // clearing previously added keyFrames, to avoid double count or memory leaks
        ddTimer.getKeyFrames().clear();
        // restore original duration interval
        ddRespawnInterval.setValue(settings.getDoubleDamageDurationInterval());
    }

    /**
     * Load saved settings
     */
    private void loadSettings() {
        settings = new Settings().load();

        playShieldSound = false;
        playDDSound = false;
    }

    /**
     * Called to initialize a controller after its root element has been completely processed.
     * Add listeners on change value of intervals and bind them to the lables
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  <tt>null</tt> if the location is not known.
     * @param resources The resources used to localize the root object, or <tt>null</tt> if
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bindVales();
        addListeners();
    }

    /**
     * Bind intervals value to the labels
     */
    private void bindVales() {
        shieldTimerLabel.textProperty().bind(shieldRespawnInterval.asString());
        ddTimerLabel.textProperty().bind(ddRespawnInterval.asString());
    }


    /**
     * Add listener on change value of intervals
     */
    private void addListeners() {
        shieldRespawnInterval.addListener((observable, oldValue, newValue) -> {
            if (playShieldSound) {
                playFemaleSound(newValue);
            } else {
                playShieldSound = true;
            }
        });

        ddRespawnInterval.addListener((observable, oldValue, newValue) -> {
            if (playDDSound) {
                playMaleSound(newValue);
            } else {
                playDDSound = true;

            }
        });
    }

    /**
     * This method play male sound at specific number
     *
     * @param value - timer value
     */
    private void playMaleSound(Number value) {
        if (value.intValue() == 20) {
            Sound.Male.TWENTY.play();
        } else if (value.intValue() == 10) {
            Sound.Male.TEN.play();
        } else if (value.intValue() == 5) {
            Sound.Male.FIVE.play();
        } else if (value.intValue() == 4) {
            Sound.Male.FOUR.play();
        } else if (value.intValue() == 3) {
            Sound.Male.THREE.play();
        } else if (value.intValue() == 2) {
            Sound.Male.TWO.play();
        } else if (value.intValue() == 1) {
            Sound.Male.ONE.play();
        }
    }

    /**
     * This method play female sound at specific number
     *
     * @param value - timer value
     */
    private void playFemaleSound(Number value) {
        if (value.intValue() == 20) {
            Sound.Female.TWENTY.play();
        } else if (value.intValue() == 10) {
            Sound.Female.TEN.play();
        } else if (value.intValue() == 5) {
            Sound.Female.FIVE.play();
        } else if (value.intValue() == 4) {
            Sound.Female.FOUR.play();
        } else if (value.intValue() == 3) {
            Sound.Female.THREE.play();
        } else if (value.intValue() == 2) {
            Sound.Female.TWO.play();
        } else if (value.intValue() == 1) {
            Sound.Female.ONE.play();
        }
    }
}
