package org.vaadin.risto.stepper.client.ui.helpers;

import org.vaadin.risto.stepper.client.ui.AbstractStepper;

import com.google.gwt.user.client.Timer;

/**
 * Timer that updates the given value to the server.
 * 
 * @author Risto Yrjänä / Vaadin
 * 
 */
public class ValueUpdateTimer extends Timer {

    protected String value;
    private final AbstractStepper<?, ?> stepper;

    public ValueUpdateTimer(AbstractStepper<?, ?> stepper) {
        this.stepper = stepper;
    }

    @Override
    public void run() {
        stepper.updateValueToServer(value);
    }

    public void setValue(String value) {
        this.value = value;
    }
}