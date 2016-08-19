package org.vaadin.risto.stepper.client.ui.helpers;

import org.vaadin.risto.stepper.client.ui.AbstractStepper;

import com.google.gwt.user.client.Timer;

/**
 * Timer that starts to increase/decrease values after a given time.
 * 
 * @author Risto Yrjänä / Vaadin
 * 
 */
public class ButtonDownTimer extends Timer {

    protected final boolean increase;
    private final AbstractStepper<?, ?> stepper;

    public ButtonDownTimer(boolean increase, AbstractStepper<?, ?> stepper) {
        this.increase = increase;
        this.stepper = stepper;
    }

    @Override
    public void run() {
        stepper.setTimerHasChangedValue(true);
        if (increase) {
            stepper.increaseValue();
        } else {
            stepper.decreaseValue();
        }
    }

}