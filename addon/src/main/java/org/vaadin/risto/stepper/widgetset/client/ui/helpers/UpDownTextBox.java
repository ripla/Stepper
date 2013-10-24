package org.vaadin.risto.stepper.widgetset.client.ui.helpers;

import java.util.logging.Logger;

import org.vaadin.risto.stepper.widgetset.client.ui.VAbstractStepper;

import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.dom.client.MouseWheelEvent;
import com.google.gwt.event.dom.client.MouseWheelHandler;
import com.google.gwt.user.client.ui.TextBox;

/**
 * UI-class for a TextBox that listens to keyboard-up and -down events.
 * 
 * @author Risto Yrjänä / Vaadin }>
 * 
 */
public class UpDownTextBox extends TextBox implements KeyDownHandler,
        KeyUpHandler, MouseWheelHandler {
    protected ButtonDownTimer keyDownTimerUp;
    protected ButtonDownTimer keyDownTimerDown;
    private final VAbstractStepper<?, ?> stepper;
    private boolean isValueFilteringEnabled;

    public UpDownTextBox(VAbstractStepper<?, ?> stepper) {
        this.stepper = stepper;
        addKeyDownHandler(this);
        addKeyUpHandler(this);
        addMouseWheelHandler(this);
    }

    /**
     * Cancel all key-up and -down timers.
     */
    protected void cancelTimers() {
        if (keyDownTimerUp != null) {
            keyDownTimerUp.cancel();
            keyDownTimerUp = null;
        }

        if (keyDownTimerDown != null) {
            keyDownTimerDown.cancel();
            keyDownTimerDown = null;
        }
    }

    @Override
    public void onKeyUp(KeyUpEvent event) {
        if (isValueFilteringEnabled && !isContentValid()) {
            Logger.getLogger(UpDownTextBox.class.getSimpleName()).info(
                    "Cancelling because of "
                            + (char) event.getNativeEvent().getKeyCode());
            makeContentValid();
        }

        if (!stepper.isTimerHasChangedValue()) {
            cancelTimers();
            if (event.isUpArrow()) {
                stepper.increaseValue();
                event.preventDefault();

            } else if (event.isDownArrow()) {
                stepper.decreaseValue();
                event.preventDefault();
            }
        } else {
            stepper.setTimerHasChangedValue(false);
            cancelTimers();
        }
    }

    @Override
    public void onKeyDown(KeyDownEvent event) {
        int keycode = event.getNativeEvent().getKeyCode();
        if (keycode == KeyCodes.KEY_UP && keyDownTimerUp == null) {
            keyDownTimerUp = new ButtonDownTimer(true, stepper);
            keyDownTimerUp.scheduleRepeating(VAbstractStepper.valueRepeatDelay);
            event.preventDefault();
        } else if (keycode == KeyCodes.KEY_DOWN && keyDownTimerDown == null) {
            keyDownTimerDown = new ButtonDownTimer(false, stepper);
            keyDownTimerDown
                    .scheduleRepeating(VAbstractStepper.valueRepeatDelay);
            event.preventDefault();
        }
    }

    private void makeContentValid() {
        while (!this.getValue().isEmpty() && !isContentValid()) {
            this.setText(getText().substring(0, getText().length() - 1));
        }
    }

    private boolean isContentValid() {
        return stepper.isValidForType(this.getText());
    }

    @Override
    public void onMouseWheel(MouseWheelEvent event) {

        int mouseWheelDelta = event.getDeltaY();
        if (stepper.isMouseWheelEnabled() && stepper.canChangeFromTextBox()) {
            if (mouseWheelDelta < 0) {
                stepper.increaseValue();
            } else {
                stepper.decreaseValue();
            }
        }
    }

    public void setValueFilteringEnabled(boolean isValueFilteringEnabled) {
        this.isValueFilteringEnabled = isValueFilteringEnabled;
    }

}