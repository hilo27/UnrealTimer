package UnrealTimer;
// Created by ������ on 23.06.2019.

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
    private Integer shieldDurationCycle;
    private KeyStroke doubleDamageHotKey;
    private Integer doubleDamageDurationCycle;

    public interface ConfigKey {
        // shortcuts to start timer
        String SHIELD_START_SHORTCUT = "shield_timer_start_button";
        String DOUBLE_DAMAGE_START_SHORTCUT = "double_damage_timer_start_button";
        // respawn intervals
        String SHIELD_DURATION_CYCLE = "shield_timer_interval";
        String DOUBLE_DAMAGE_DURATION_CYCLE = "double_damage_timer_interval";
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
     * @param shieldKey         - key for activating count for Shield <pre>Q</pre>
     * @param shieldCycle       - this is the time before shield respawn <pre>60</pre>
     * @param doubleDamageKey   - key for activating count for Double Damage <pre>E</pre>
     * @param doubleDamageCycle - this is the time before shield respawn <pre>60</pre>
     */
    public Settings(String shieldKey, Integer shieldCycle, String doubleDamageKey, Integer doubleDamageCycle) {
        initShieldShorcut(shieldKey);
        initDoubleDamageShorcut(doubleDamageKey);
        this.shieldDurationCycle = shieldCycle;
        this.doubleDamageDurationCycle = doubleDamageCycle;
    }

    private void initShieldShorcut(String key) {
        if (StringUtils.isBlank(key)) {
            key = "Q";
        }
        this.shieldHotKey = KeyStroke.getKeyStroke(key);
    }

    private void initDoubleDamageShorcut(String key) {
        if (StringUtils.isBlank(key)) {
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

            props.setProperty(ConfigKey.SHIELD_DURATION_CYCLE, String.valueOf(shieldDurationCycle));
            props.setProperty(ConfigKey.DOUBLE_DAMAGE_DURATION_CYCLE, String.valueOf(doubleDamageDurationCycle));

            props.store(writer, null);
            writer.close();

        } catch (IOException ex) {
            log.error("Configuration file was not saved", ex);
        }

        return this;
    }

    /**
     * Returns <code>KeyStroke</code> for Shield timer
     */
    public KeyStroke getShieldHotKey() {
        return shieldHotKey;
    }

    /**
     * Returns <code>KeyStroke</code> for Double Damage timer
     */
    public KeyStroke getDoubleDamageHotKey() {
        return doubleDamageHotKey;
    }

    public Integer getShieldDurationCycle() {
        return shieldDurationCycle;
    }

    public Integer getDoubleDamageDurationCycle() {
        return doubleDamageDurationCycle;
    }

    /**
     * This method load configuration file and apply it to this instance of settings
     */
    private Settings loadedConfiguration() {
        apply(configFile());
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
        shieldDurationCycle = Integer.valueOf(config.getProperty(ConfigKey.SHIELD_DURATION_CYCLE, "60"));
        doubleDamageDurationCycle = Integer.valueOf(config.getProperty(ConfigKey.DOUBLE_DAMAGE_DURATION_CYCLE, "60"));
    }

}
