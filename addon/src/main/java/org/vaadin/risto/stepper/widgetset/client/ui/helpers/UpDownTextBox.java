package org.vaadin.risto.stepper.widgetset.client.ui.helpers;

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
 * @author Risto Yrjänä / Vaadin Ltd.
 * 
 */
public class UpDownTextBox extends TextBox implements KeyDownHandler,
        KeyUpHandler, MouseWheelHandler {
    protected ButtonDownTimer keyDownTimerUp;
    protected ButtonDownTimer keyDownTimerDown;
    private final VAbstractStepper<?, ?> stepper;

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

    /*
     * (non-Javadoc)
     * 
     * @see com.google.gwt.event.dom.client.KeyUpHandler#onKeyUp(com.google.gwt
     * .event.dom.client.KeyUpEvent)
     */
    @Override
    public void onKeyUp(KeyUpEvent event) {
        if (!stepper.isTimerHasChangedValue()) {
            cancelTimers();
            int keycode = event.getNativeEvent().getKeyCode();

            if (keycode == KeyCodes.KEY_UP) {
                stepper.increaseValue();
                event.preventDefault();

            } else if (keycode == KeyCodes.KEY_DOWN) {
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
}