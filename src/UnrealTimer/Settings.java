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
    private Integer shieldDurationCycle = 15;
    // double damaged
    private String doubleDamagedStartShorcut;
    private KeyStroke doubleDamagedHotKey;

    public Settings() {
    }

    /**
     * Init with user keys
     *
     * @param shieldKey        - key for activating count for Shield <pre>Q</pre>
     * @param doubleDamagedKey - key for activating count for Double Damage <pre>E</pre>
     */
    public Settings(String shieldKey, String doubleDamagedKey) {
        if (StringUtils.isNotBlank(shieldKey)) {
            initShieldShorcut(shieldKey);
        }
        if (StringUtils.isNotBlank(doubleDamagedKey)) {
            initDoubleDamagedShorcut(doubleDamagedKey);
        }
    }

    private void initShieldShorcut(String key) {
        this.shieldStartShorcut = key;
        this.shieldHotKey = KeyStroke.getKeyStroke(key);
    }

    private void initDoubleDamagedShorcut(String key) {
        this.doubleDamagedStartShorcut = key;
        this.doubleDamagedHotKey = KeyStroke.getKeyStroke(key);
    }

    /**
     * Method will load saved settings, or default if configuration file not found
     *
     * <pre>
     * default key for Shield is Q
     * default key for Double Damage is E
     * </pre>
     */
    public Settings load() {
        // if config not found load default
        initShieldShorcut("Q");
        initDoubleDamagedShorcut("E");

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
     * Return String representation of activation key for Double Damaged timer
     */
    public String getDoubleDamagedStartShorcut() {
        return doubleDamagedStartShorcut;
    }

    /**
     * Set String representation of activation key for Double Damaged timer
     */
    public void setDoubleDamagedStartShorcut(String doubleDamagedStartShorcut) {
        this.doubleDamagedStartShorcut = doubleDamagedStartShorcut;
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
     * Returns <code>KeyStroke</code> for Double Damaged timer
     */
    public KeyStroke getDoubleDamagedHotKey() {
        return doubleDamagedHotKey;
    }

    /**
     * Set <code>KeyStroke</code> for Double Damaged timer
     */
    public void setDoubleDamagedHotKey(KeyStroke doubleDamagedHotKey) {
        this.doubleDamagedHotKey = doubleDamagedHotKey;
    }

    public Integer getShieldDurationCycle() {
        return shieldDurationCycle;
    }

    public void setShieldDurationCycle(Integer shieldDurationCycle) {
        this.shieldDurationCycle = shieldDurationCycle;
    }
}
