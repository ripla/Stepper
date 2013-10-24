/**
 * 
 */
package org.vaadin.risto.stepper;

import org.vaadin.risto.stepper.widgetset.client.shared.FloatStepperState;

/**
 * <p>
 * Field that allows stepping through values via given up/down controls.
 * Supports values of type Float. Default value is 0.
 * </p>
 * 
 * <p>
 * Note: float incrementation IS NOT completely accurate, as the values are
 * always rounded. The accuracy should be enough for most use cases.
 * </p>
 * 
 * @author Risto Yrjänä / Vaadin }>
 * 
 */
public class FloatStepper extends AbstractStepper<Float, Float> implements
        ValueFilteringStepper {

    private static final long serialVersionUID = -5328027647865381265L;

    public FloatStepper() {
        setValue(0f);
        setStepAmount(1.0f);
    }

    public FloatStepper(String caption) {
        this();
        setCaption(caption);
    }

    @Override
    protected FloatStepperState getState() {
        return (FloatStepperState) super.getState();
    }

    /**
     * Set the accuracy of the float representation.
     * 
     * @param numberOfDecimals
     */
    public void setNumberOfDecimals(int numberOfDecimals) {
        getState().numberOfDecimals = numberOfDecimals;
    }

    @Override
    public Class<Float> getType() {
        return Float.class;
    }

    @Override
    protected boolean isValidForRange(Float value) {
        if (getMaxValue() != null && value > getMaxValue()) {
            return false;
        }

        if (getMinValue() != null && value < getMinValue()) {
            return false;
        }

        return true;
    }

    @Override
    protected Float parseStringValue(String value)
            throws StepperValueParseException {
        if (value == null || "".equals(value)) {
            return null;
        }

        try {
            return Float.parseFloat(value);
        } catch (NumberFormatException e) {
            throw new StepperValueParseException(e);
        }
    }

    /**
     * Enable or disable client-side value filtering. Value filtering disallows
     * the user from entering characters that would make the content invalid.
     * 
     * This feature is experimental and may change or be completely removed in
     * future versions.
     * 
     * @param enableValueFiltering
     */
    @Override
    public void setValueFiltering(boolean enableValueFiltering) {
        getState().valueFiltering = enableValueFiltering;
    }

    @Override
    public boolean isValueFiltering() {
        return getState().valueFiltering;
    }
}
