package org.vaadin.risto.stepper.client.ui.helpers;

import org.vaadin.risto.stepper.client.ui.AbstractStepper;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.TextBox;

/**
 * UI-class for a TextBox that listens to keyboard-up and -down events.
 * 
 * @author Risto Yrjänä / Vaadin
 * 
 */
public class UpDownTextBox extends TextBox
        implements KeyDownHandler, KeyUpHandler, MouseWheelHandler {
    protected ButtonDownTimer keyDownTimerUp;
    protected ButtonDownTimer keyDownTimerDown;
    private final AbstractStepper<?, ?> stepper;
    private boolean isValueFilteringEnabled;

    public UpDownTextBox(AbstractStepper<?, ?> stepper) {
        this.stepper = stepper;
        addKeyDownHandler(this);
        addKeyUpHandler(this);
        addMouseWheelHandler(this);

        sinkEvents(Event.ONPASTE);
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
        ensureContentValidity();

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

    private void ensureContentValidity() {
        if (isValueFilteringEnabled && !isContentValid(this.getText())) {
            makeContentValid();
        }
    }

    @Override
    public void onKeyDown(KeyDownEvent event) {
        int keycode = event.getNativeEvent().getKeyCode();

        if (keycode == KeyCodes.KEY_UP && keyDownTimerUp == null) {
            keyDownTimerUp = new ButtonDownTimer(true, stepper);
            keyDownTimerUp.scheduleRepeating(AbstractStepper.VALUE_REPEAT_DELAY);
            event.preventDefault();
        } else if (keycode == KeyCodes.KEY_DOWN && keyDownTimerDown == null) {
            keyDownTimerDown = new ButtonDownTimer(false, stepper);
            keyDownTimerDown
                    .scheduleRepeating(AbstractStepper.VALUE_REPEAT_DELAY);
            event.preventDefault();
        }
    }

    @Override
    public void onBrowserEvent(Event event) {
        super.onBrowserEvent(event);
        switch (event.getTypeInt()) {
        case Event.ONPASTE: {
            Scheduler.get().scheduleDeferred(new ScheduledCommand() {

                @Override
                public void execute() {
                    ensureContentValidity();

                }
            });
            break;
        }
        }
    }

    protected void makeContentValid() {
        WarningAnimation.errorFlash(this);

        while (!this.getValue().isEmpty() && !isContentValid(this.getText())) {
            this.setText(getText().substring(0, getText().length() - 1));
        }
    }

    protected boolean isContentValid(String content) {
        return stepper.isValidForType(content);
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