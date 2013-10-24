package org.vaadin.risto.stepper.widgetset.client.ui.helpers;

import org.vaadin.risto.stepper.widgetset.client.ui.VAbstractStepper;

import com.google.gwt.user.client.Timer;

/**
 * Timer that starts to increase/decrease values after a given time.
 * 
 * @author Risto Yrjänä / Vaadin }>
 * 
 */
public class ButtonDownTimer extends Timer {

    protected final boolean increase;
    private final VAbstractStepper<?, ?> stepper;

    public ButtonDownTimer(boolean increase, VAbstractStepper<?, ?> stepper) {
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