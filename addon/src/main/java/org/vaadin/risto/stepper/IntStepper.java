package org.vaadin.risto.stepper;

import org.vaadin.risto.stepper.client.shared.IntStepperState;

import com.vaadin.shared.communication.SharedState;

/**
 * <p>
 * Field that allows stepping through values via given up/down controls.
 * Supports values of type Integer. Default value is 0.
 * </p>
 *
 * @author Risto Yrjänä / Vaadin
 *
 */
public class IntStepper extends AbstractStepper<Integer, Integer>
        implements ValueFilteringStepper {

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
    public Class<Integer> getValueType() {
        return Integer.class;
    }

    @Override
    public Class<Integer> getStepType() {
        return Integer.class;
    }

    @Override
    protected IntStepperState getState() {
        return (IntStepperState) super.getState();
    }

    @Override
    protected SharedState createState() {
        return new IntStepperState();
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
    protected Integer parseStringValue(String value)
            throws StepperValueParseException {
        if (value == null || "".equals(value)) {
            return null;
        }

        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new StepperValueParseException(e);
        }
    }

    @Override
    public void setValueFiltering(boolean enableValueFiltering) {
        getState().valueFiltering = enableValueFiltering;
    }

    @Override
    public boolean isValueFiltering() {
        return getState().valueFiltering;
    }
}
