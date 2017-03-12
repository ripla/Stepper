package org.vaadin.risto.stepper;

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
    public Class<String> getValueType() {
        return String.class;
    }

}
