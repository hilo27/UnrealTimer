package UnrealTimer;
// Created by Руслан on 04.07.2019.

import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;


public class KeyListener implements EventHandler<KeyEvent> {
    private TextField textField;

    /**
     * This KeyListener can be applied to <code>{@link TextField}</code> in order to exclude incapable key presses.
     * See <code>{@link KeyListener#handle(KeyEvent)}</code> for the details about restriction.
     *
     * @param textField This is text field to which this listener will apply in order to <code>{@link TextField#setText(String)}
     */
    public KeyListener(TextField textField) {
        this.textField = textField;
    }

    /**
     * Allow Function keys like F1, F2, etc... all keys with letters, all Digit keys (excluding the keypad digits)
     *
     * @param event <code>{@link KeyEvent}</code>
     */
    @Override
    public void handle(KeyEvent event) {
        KeyCode key = event.getCode();

        if (key.isFunctionKey() || key.isLetterKey() || nonKeypadDigits(key)) {
            textField.setText(key.getName());
        }
    }

    private boolean nonKeypadDigits(KeyCode key) {
        return (key.isDigitKey() && !key.isKeypadKey());
    }
}
