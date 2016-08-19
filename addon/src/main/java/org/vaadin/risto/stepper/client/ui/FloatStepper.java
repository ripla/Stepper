package org.vaadin.risto.stepper.client.ui;

/**
 * @author Risto Yrjänä / Vaadin
 * 
 */
public class FloatStepper extends AbstractDecimalStepper<Float> {

    public FloatStepper() {
        super(0);
    }

    protected Float parse(String value) {
        return Float.parseFloat(value.replace(decimalSeparator, '.'));
    }

    protected String toString(Float value) {
        return value.toString().replace('.', decimalSeparator);
    }

    protected Float round(Float value) {
        float accuracy = (float) Math.pow(10f, getNumberOfDecimals());
        return Math.round(value * accuracy) / accuracy;
    }

    protected Float add(Float value, Float increment) {
        return value + increment;
    }

    protected Float subtract(Float value, Float decrement) {
        return value - decrement;
    }

}
