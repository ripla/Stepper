package org.vaadin.risto.stepper.client.ui;

import com.google.gwt.event.logical.shared.ValueChangeEvent;

public class StepperValueChangeEvent extends ValueChangeEvent<String> {

    public StepperValueChangeEvent(String value) {
        super(value);
    }
}
