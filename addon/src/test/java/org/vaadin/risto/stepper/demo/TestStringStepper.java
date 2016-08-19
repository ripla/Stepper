package org.vaadin.risto.stepper.demo;

import org.vaadin.risto.stepper.AbstractStepper;
import org.vaadin.risto.stepper.StepperValueParseException;

/**
 * Simple stepper implementation for testing purposes
 */
public class TestStringStepper extends AbstractStepper<String, String> {

    @Override
    protected boolean isValidForRange(String value) {
        return true;
    }

    @Override
    protected String parseStringValue(String value)
            throws StepperValueParseException {
        return value;
    }

    @Override
    protected Class<String> getStepType() {
        return String.class;
    }

    @Override
    public Class<? extends String> getType() {
        return String.class;
    }

}
