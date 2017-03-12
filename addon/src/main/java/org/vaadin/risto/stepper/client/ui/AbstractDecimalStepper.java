package org.vaadin.risto.stepper.client.ui;

public abstract class AbstractDecimalStepper<T extends Number & Comparable<T>>
        extends AbstractStepper<T, T> {

    protected char decimalSeparator = '.';

    protected int numberOfDecimals;

    public AbstractDecimalStepper(int numberOfDecimals) {
        this.numberOfDecimals = numberOfDecimals;
    }

    @Override
    public boolean isValidForType(String value) {
        try {
            parse(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    protected String getIncreasedValue(String startValue) throws Exception {
        T value = parse(startValue);
        value = add(value, getStepAmount());
        value = round(value);
        return toString(value);
    }

    @Override
    protected String getDecreasedValue(String startValue) throws Exception {
        T value = parse(startValue);
        value = subtract(value, getStepAmount());
        value = round(value);
        return toString(value);
    }

    @Override
    protected boolean isSmallerThanMax(String stringValue) {
        T value = parse(stringValue);
        return !(getMaxValue() != null && value.compareTo(getMaxValue()) > 0);
    }

    @Override
    protected boolean isLargerThanMin(String stringValue) {
        T value = parse(stringValue);
        return !(getMinValue() != null && value.compareTo(getMinValue()) < 0);
    }

    @Override
    public T parseStringValue(String value) {
        if (value == null || "".equals(value))
            return null;
        return parse(value);
    }

    @Override
    public T parseStepAmount(String value) {
        return parseStringValue(value);
    }

    public int getNumberOfDecimals() {
        return numberOfDecimals;
    }

    public void setNumberOfDecimals(int numberOfDecimals) {
        this.numberOfDecimals = numberOfDecimals;
    }

    public char getDecimalSeparator() {
        return decimalSeparator;
    }

    public void setDecimalSeparator(char decimalSeparator) {
        this.decimalSeparator = decimalSeparator;
    }

    protected abstract T parse(String value);

    protected abstract String toString(T value);

    protected abstract T round(T value);

    protected abstract T add(T value, T increment);

    protected abstract T subtract(T value, T decrement);

}
