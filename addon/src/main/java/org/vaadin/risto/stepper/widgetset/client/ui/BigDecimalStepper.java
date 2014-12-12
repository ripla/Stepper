package org.vaadin.risto.stepper.widgetset.client.ui;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BigDecimalStepper extends
        AbstractStepper<BigDecimal, BigDecimal> {

    private static final String FLOAT_CHARACTERS = "^\\-?\\d+\\.?\\d*$";

    private int numberOfDecimals = Integer.MAX_VALUE;

    public BigDecimalStepper() {
        super(FLOAT_CHARACTERS);
    }

    @Override
    public boolean isValidForType(String value) {
        if (super.isValidForType(value)) {
            try {
                new BigDecimal(value);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    protected String getIncreasedValue(String startValue) throws Exception {
        BigDecimal value = parse(startValue);
        value = value.add(getStepAmount());
        value = round(value);
        return toString(value);
    }

    @Override
    protected String getDecreasedValue(String startValue) throws Exception {
        BigDecimal value = parse(startValue);
        value = value.subtract(getStepAmount());
        value = round(value);
        return toString(value);
    }

    @Override
    protected boolean isSmallerThanMax(String stringValue) {
        BigDecimal value = parse(stringValue);
        if (getMaxValue() != null && value.compareTo(getMaxValue()) > 0) {
            return false;
        }

        return true;
    }

    @Override
    protected boolean isLargerThanMin(String stringValue) {
        BigDecimal value = parse(stringValue);
        if (getMinValue() != null && value.compareTo(getMinValue()) < 0) {
            return false;
        }

        return true;
    }

    @Override
    public BigDecimal parseStringValue(String value) {
        if (value == null || "".equals(value))
            return null;
        return parse(value);
    }

    protected BigDecimal round(BigDecimal value) {
        if (getNumberOfDecimals() != Integer.MAX_VALUE)
            value = value.setScale(getNumberOfDecimals(), RoundingMode.HALF_UP);
        return value;
    }

    protected BigDecimal parse(String value) {
        return new BigDecimal(value);
    }

    protected String toString(BigDecimal value) {
        if (value == null)
            return null;
        return value.toPlainString();
    }

    @Override
    public BigDecimal parseStepAmount(String value) {
        return parseStringValue(value);
    }

    public int getNumberOfDecimals() {
        return numberOfDecimals;
    }

    public void setNumberOfDecimals(int numberOfDecimals) {
        this.numberOfDecimals = numberOfDecimals;
    }

}
