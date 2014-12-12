package org.vaadin.risto.stepper;

import java.math.BigDecimal;

import org.vaadin.risto.stepper.AbstractStepper;
import org.vaadin.risto.stepper.StepperValueParseException;
import org.vaadin.risto.stepper.ValueFilteringStepper;
import org.vaadin.risto.stepper.widgetset.client.shared.BigDecimalStepperState;

/**
 * <p>
 * Field that allows stepping through values via given up/down controls.
 * Supports values of type BigDecimal. Default value is 0.
 * </p>
 * 
 * @author Marcin Wisnicki
 */
public class BigDecimalStepper extends AbstractStepper<BigDecimal, BigDecimal>
        implements ValueFilteringStepper {

    private static final long serialVersionUID = 1L;

    public BigDecimalStepper() {
        setValue(BigDecimal.ZERO);
        setStepAmount(BigDecimal.ONE);
        setNumberOfDecimals(Integer.MAX_VALUE);
    }

    public BigDecimalStepper(String caption) {
        this();
        setCaption(caption);
    }

    @Override
    protected BigDecimalStepperState getState() {
        return (BigDecimalStepperState) super.getState();
    }

    @Override
    protected BigDecimalStepperState getState(boolean markAsDirty) {
        return (BigDecimalStepperState) super.getState(markAsDirty);
    }

    @Override
    public Class<? extends BigDecimal> getType() {
        return BigDecimal.class;
    }

    @Override
    protected boolean isValidForRange(BigDecimal value) {
        if (getMaxValue() != null && value.compareTo(getMaxValue()) > 0) {
            return false;
        }

        if (getMinValue() != null && value.compareTo(getMinValue()) < 0) {
            return false;
        }

        return true;
    }

    @Override
    protected BigDecimal parseStringValue(String value)
            throws StepperValueParseException {
        if (value == null || "".equals(value)) {
            return null;
        }

        try {
            return new BigDecimal(value);
        } catch (NumberFormatException e) {
            throw new StepperValueParseException(e);
        }
    }

    public int getNumberOfDecimals() {
        return getState(false).numberOfDecimals;
    }

    /**
     * Set the accuracy of the float representation.
     * 
     * @param numberOfDecimals
     *            number of decimals or {@link Integer#MAX_VALUE} for unlimited.
     */
    public void setNumberOfDecimals(int numberOfDecimals) {
        getState().numberOfDecimals = numberOfDecimals;
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
