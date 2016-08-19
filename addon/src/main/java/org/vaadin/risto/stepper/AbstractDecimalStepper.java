package org.vaadin.risto.stepper;

import org.vaadin.risto.stepper.client.shared.AbstractDecimalStepperState;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;

public abstract class AbstractDecimalStepper<T extends Number & Comparable<T>>
        extends AbstractStepper<T, T> implements ValueFilteringStepper {

    protected DecimalFormat decimalFormat;

    protected AbstractDecimalStepper(T initialValue, T initalStepAmount) {
        setValue(initialValue);
        setStepAmount(initalStepAmount);
    }

    @Override
    protected AbstractDecimalStepperState getState() {
        return (AbstractDecimalStepperState) super.getState();
    }

    /**
     * Set the accuracy of the float representation.
     * 
     * @param numberOfDecimals number of decimals to display
     */
    public void setNumberOfDecimals(int numberOfDecimals) {
        getState().numberOfDecimals = numberOfDecimals;
        markAsDirty();
    }

    public int getNumberOfDecimals() {
        return getState().numberOfDecimals;
    }

    @Override
    protected boolean isValidForRange(T value) {
        if (getMaxValue() != null && value.compareTo(getMaxValue()) > 0) {
            return false;
        }

        if (getMinValue() != null && value.compareTo(getMinValue()) < 0) {
            return false;
        }

        return true;
    }

    @Override
    protected T parseStringValue(String value)
            throws StepperValueParseException {
        if (value == null || "".equals(value)) {
            return null;
        }

        try {
            return convertToValueType(getDecimalFormat().parse(value));
        } catch (ParseException e) {
            throw new StepperValueParseException(e);
        }
    }

    protected abstract T convertToValueType(Number parsedValue);

    @Override
    protected String parseValueToString(T value) {
        return value != null ? getDecimalFormat().format(value) : "";
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

    @Override
    public void beforeClientResponse(boolean initial) {
        super.beforeClientResponse(initial);

        getState().decimalSeparator = getDecimalFormat()
                .getDecimalFormatSymbols().getDecimalSeparator();
    }

    /**
     * Sets the decimal format used for parsing and displaying float values.
     * Currently only the decimal separator is used when parsing values on the
     * client-side.
     */
    public void setDecimalFormat(DecimalFormat decimalFormat) {
        this.decimalFormat = decimalFormat;
        markAsDirty();
    }

    protected DecimalFormat getDecimalFormat() {
        DecimalFormat format = decimalFormat;
        if (format == null) {
            final DecimalFormatSymbols symbols = getLocale() != null
                    ? DecimalFormatSymbols.getInstance(getLocale())
                    : DecimalFormatSymbols.getInstance();
            format = new DecimalFormat("#.#", symbols);
            format.setMinimumFractionDigits(0);
            format.setMaximumFractionDigits(getNumberOfDecimals());
        }
        return format;
    }
}
