package org.vaadin.risto.stepper.client.ui;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BigDecimalStepper extends AbstractDecimalStepper<BigDecimal> {

    public BigDecimalStepper() {
        super(Integer.MAX_VALUE);
    }

    @Override
    protected BigDecimal parse(String value) {
        return new BigDecimal(value.replace(decimalSeparator, '.'));
    }

    @Override
    protected String toString(BigDecimal value) {
        if (value == null)
            return null;
        return value.toPlainString().replace('.', decimalSeparator);
    }

    @Override
    protected BigDecimal round(BigDecimal value) {
        if (getNumberOfDecimals() != Integer.MAX_VALUE)
            value = value.setScale(getNumberOfDecimals(), RoundingMode.HALF_UP);
        return value;
    }

    @Override
    protected BigDecimal add(BigDecimal value, BigDecimal increment) {
        return value.add(increment);
    }

    @Override
    protected BigDecimal subtract(BigDecimal value, BigDecimal decrement) {
        return value.subtract(decrement);
    }
}
