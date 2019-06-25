package UnrealTimer;
// Created by Руслан on 23.06.2019.

import org.apache.commons.lang3.StringUtils;

import javax.swing.*;

/**
 * Settings class
 */
@SuppressWarnings("WeakerAccess")
public class Settings {
    // shield
    private String shieldStartShorcut;
    private KeyStroke shieldHotKey;
    private Integer shieldDurationCycle;
    // double Damage
    private String doubleDamageStartShorcut;
    private KeyStroke doubleDamageHotKey;
    private Integer doubleDamageDurationCycle;

    /**
     * Init with defaults
     */
    public Settings() {
        this("Q", "E");
    }

    /**
     * Init with user keys, but with defaults time intervals
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
        this.shieldStartShorcut = key;
        this.shieldHotKey = KeyStroke.getKeyStroke(key);
    }

    private void initDoubleDamageShorcut(String key) {
        if (StringUtils.isBlank(key)) {
            key = "E";
        }
        this.doubleDamageStartShorcut = key;
        this.doubleDamageHotKey = KeyStroke.getKeyStroke(key);
    }

    /**
     * Method will load saved settings, or default if configuration file not found
     *
     * <pre>
     * default key for Shield is Q
     * default duration for Shield is 60 sec
     *
     * default key for Double Damage is E
     * default duration for Double Damage is 60 sec
     * </pre>
     */
    public Settings load() {
        // here will be loading from file and some sort of refresh default values
        return this;
    }

    /**
     * Method will save settings with currently assign keys
     */
    public Settings save() {
        // save with current assign keys
        return this;
    }


    /**
     * Return String representation of activation key for Shield timer
     */
    public String getShieldStartShorcut() {
        return shieldStartShorcut;
    }

    /**
     * Set String representation of activation key for Shield timer
     */
    public void setShieldStartShorcut(String shieldStartShorcut) {
        this.shieldStartShorcut = shieldStartShorcut;
    }

    /**
     * Return String representation of activation key for Double Damage timer
     */
    public String getDoubleDamageStartShorcut() {
        return doubleDamageStartShorcut;
    }

    /**
     * Set String representation of activation key for Double Damage timer
     */
    public void setDoubleDamageStartShorcut(String doubleDamageStartShorcut) {
        this.doubleDamageStartShorcut = doubleDamageStartShorcut;
    }

    /**
     * Returns <code>KeyStroke</code> for Shield timer
     */
    public KeyStroke getShieldHotKey() {
        return shieldHotKey;
    }

    /**
     * Set <code>KeyStroke</code> for Shield timer
     */
    public void setShieldHotKey(KeyStroke shieldHotKey) {
        this.shieldHotKey = shieldHotKey;
    }

    /**
     * Returns <code>KeyStroke</code> for Double Damage timer
     */
    public KeyStroke getDoubleDamageHotKey() {
        return doubleDamageHotKey;
    }

    /**
     * Set <code>KeyStroke</code> for Double Damage timer
     */
    public void setDoubleDamageHotKey(KeyStroke doubleDamageHotKey) {
        this.doubleDamageHotKey = doubleDamageHotKey;
    }

    public Integer getShieldDurationCycle() {
        return shieldDurationCycle;
    }

    public void setShieldDurationCycle(Integer shieldDurationCycle) {
        this.shieldDurationCycle = shieldDurationCycle;
    }


    public Integer getDoubleDamageDurationCycle() {
        return doubleDamageDurationCycle;
    }

    public void setDoubleDamageDurationCycle(Integer doubleDamageDurationCycle) {
        this.doubleDamageDurationCycle = doubleDamageDurationCycle;
    }

}
