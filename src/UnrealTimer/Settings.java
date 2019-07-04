package UnrealTimer;
// Created by Руслан on 23.06.2019.

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

/**
 * Settings class
 */
@SuppressWarnings("WeakerAccess")
public class Settings {
    private static final Logger log = LoggerFactory.getLogger(Settings.class);

    private KeyStroke shieldHotKey;
    private Integer shieldDurationInterval;
    private KeyStroke doubleDamageHotKey;
    private Integer doubleDamageDurationInterval;

    public interface ConfigKey {
        // shortcuts to start timer
        String SHIELD_START_SHORTCUT = "shield_timer_start_button";
        String DOUBLE_DAMAGE_START_SHORTCUT = "double_damage_timer_start_button";
        // respawn intervals
        String SHIELD_DURATION_INTERVAL = "shield_timer_interval";
        String DOUBLE_DAMAGE_DURATION_INTERVAL = "double_damage_timer_interval";
    }

    /**
     * Init with defaults
     * <pre>
     *    default key for Shield is Q
     *    default duration for Shield is 60 sec
     *
     *    default key for Double Damage is E
     *    default duration for Double Damage is 60 sec
     * </pre>
     */
    public Settings() {
        this("Q", "E");
    }

    /**
     * Init with user keys, but with defaults time intervals
     * <pre>
     *    default duration for Shield is 60 sec
     *    default duration for Double Damage is 60 sec
     * </pre>
     *
     * @param shieldKey       - key for activating count for Shield <pre>Q</pre>
     * @param doubleDamageKey - key for activating count for Double Damage <pre>E</pre>
     */
    public Settings(String shieldKey, String doubleDamageKey) {
        this(shieldKey, 60, doubleDamageKey, 60);
    }

    /**
     * Init with user keys and timers
     *
     * @param shieldKey            - key for activating count for Shield <pre>Q</pre>
     * @param shieldInterval       - this is the time before shield respawn <pre>60</pre>
     * @param doubleDamageKey      - key for activating count for Double Damage <pre>E</pre>
     * @param doubleDamageInterval - this is the time before shield respawn <pre>60</pre>
     */
    public Settings(String shieldKey, Integer shieldInterval, String doubleDamageKey, Integer doubleDamageInterval) {
        initShieldShorcut(shieldKey);
        initDoubleDamageShorcut(doubleDamageKey);
        this.shieldDurationInterval = shieldInterval;
        this.doubleDamageDurationInterval = doubleDamageInterval;
    }

    private void initShieldShorcut(String key) {
        if (StringUtils.isBlank(key) ||  KeyStroke.getKeyStroke(key) == null) {
            log.warn("Wrong key code. Reset to default Q");
            key = "Q";
        }
        this.shieldHotKey = KeyStroke.getKeyStroke(key);
    }

    private void initDoubleDamageShorcut(String key) {
        if (StringUtils.isBlank(key) ||  KeyStroke.getKeyStroke(key) == null) {
            log.warn("Wrong key code. Reset to default E");
            key = "E";
        }
        this.doubleDamageHotKey = KeyStroke.getKeyStroke(key);
    }

    /**
     * Method will load saved settings, or default if configuration file or properties not found
     *
     * <pre>
     *    default key for Shield is Q
     *    default duration for Shield is 60 sec
     *
     *    default key for Double Damage is E
     *    default duration for Double Damage is 60 sec
     * </pre>
     */
    public Settings load() {
        return loadedConfiguration();
    }

    /**
     * Method will save settings with currently assign keys
     */
    public Settings save() {
        // save with current assign keys
        File configFile = new File("config.properties");

        try {
            Properties props = new Properties();
            FileWriter writer = new FileWriter(configFile);

            // content of configuration file
            props.setProperty(ConfigKey.SHIELD_START_SHORTCUT, KeyEvent.getKeyText(shieldHotKey.getKeyCode()));
            props.setProperty(ConfigKey.DOUBLE_DAMAGE_START_SHORTCUT, KeyEvent.getKeyText(doubleDamageHotKey.getKeyCode()));

            props.setProperty(ConfigKey.SHIELD_DURATION_INTERVAL, String.valueOf(shieldDurationInterval));
            props.setProperty(ConfigKey.DOUBLE_DAMAGE_DURATION_INTERVAL, String.valueOf(doubleDamageDurationInterval));

            props.store(writer, null);
            writer.close();

        } catch (IOException ex) {
            log.error("Configuration file was not saved", ex);
        }

        return this;
    }

    /**
     * Returns <code>{@link KeyStroke}</code> for Shield timer
     */
    public KeyStroke getShieldHotKey() {
        return shieldHotKey;
    }

    /**
     * Returns <code>{@link KeyStroke}</code> for Double Damage timer
     */
    public KeyStroke getDoubleDamageHotKey() {
        return doubleDamageHotKey;
    }

    public Integer getShieldDurationInterval() {
        return shieldDurationInterval;
    }

    public Integer getDoubleDamageDurationInterval() {
        return doubleDamageDurationInterval;
    }

    /**
     * This method load configuration file and apply it to this instance of settings
     * If any problems occurs the default config will be returned
     */
    private Settings loadedConfiguration() {
        try {
            apply(configFile());
        } catch (Exception anyException) {
            log.error("Incorrect configuration file. Fallback to default", anyException);
            return new Settings();
        }
        return this;
    }

    /**
     * This methods return Properties represenation of configuration file
     */
    private Properties configFile() {
        Properties props = new Properties();
        File configFile = new File("config.properties");

        try {
            FileReader reader = new FileReader(configFile);
            props.load(reader);
            reader.close();

        } catch (IOException e) {
            log.error("Configuration file was not loaded, fallback to defaults properties", e);
        }

        return props;
    }

    /**
     * This method applies loaded from file configuration to this instance of settings
     */
    private void apply(Properties config) {
        initShieldShorcut(config.getProperty(ConfigKey.SHIELD_START_SHORTCUT));
        initDoubleDamageShorcut(config.getProperty(ConfigKey.DOUBLE_DAMAGE_START_SHORTCUT));
        shieldDurationInterval = Integer.valueOf(config.getProperty(ConfigKey.SHIELD_DURATION_INTERVAL, "60"));
        doubleDamageDurationInterval = Integer.valueOf(config.getProperty(ConfigKey.DOUBLE_DAMAGE_DURATION_INTERVAL, "60"));
    }

}
