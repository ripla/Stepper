package org.vaadin.risto.stepper;

import java.text.ParseException;

import org.vaadin.risto.stepper.widgetset.client.IntStepperState;

/**
 * <p>
 * Field that allows stepping through values via given up/down controls.
 * Supports values of type Integer. Default value is 0.
 * </p>
 * 
 * @author Risto Yrjänä / Vaadin Ltd.
 * 
 */
public class IntStepper extends AbstractStepper<Integer, Integer> {

    private static final long serialVersionUID = 1365274510273965118L;

    public IntStepper() {
        setValue(0);
        setStepAmount(1);
    }

    public IntStepper(String caption) {
        this();
        setCaption(caption);
    }

    @Override
    public Class<Integer> getType() {
        return Integer.class;
    }

    @Override
    public IntStepperState getState() {
        return (IntStepperState) super.getState();
    }

    @Override
    protected boolean isValidForRange(Integer value) {
        if (getMaxValue() != null && value > getMaxValue()) {
            return false;
        }

        if (getMinValue() != null && value < getMinValue()) {
            return false;
        }

        return true;
    }

    @Override
    protected Integer parseStringValue(String value) throws ParseException {
        if (value == null || "".equals(value)) {
            return null;
        }

        return Integer.parseInt(value);
    }

    @Override
    protected String parseValueToString(Integer value) {
        return value != null ? value.toString() : "";
    }

    @Override
    protected String parseStepAmountToString(Integer stepAmount) {
        return stepAmount != null ? stepAmount.toString() : "";
    }
}
