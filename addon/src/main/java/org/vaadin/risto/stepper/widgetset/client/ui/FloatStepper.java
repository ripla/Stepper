package org.vaadin.risto.stepper.widgetset.client.ui;

/**
 * @author Risto Yrjänä / Vaadin }>
 * 
 */
public class FloatStepper extends AbstractStepper<Float, Float> {

    private int numberOfDecimals;

    // TODO localization of the decimal separator
    private static final String FLOAT_CHARACTERS = "^\\-?\\d+\\.?\\d*$";

    public FloatStepper() {
        super(FLOAT_CHARACTERS);
    }

    @Override
    public boolean isValidForType(String value) {
        if (super.isValidForType(value)) {
            try {
                Float.parseFloat(value);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public String getDecreasedValue(String startValue) {
        float floatValue = Float.valueOf(startValue).floatValue();
        floatValue -= getStepAmount();

        float accuracy = (float) Math.pow(10f, getNumberOfDecimals());
        floatValue = Math.round(floatValue * accuracy) / accuracy;

        return Float.toString(floatValue);
    }

    @Override
    public String getIncreasedValue(String startValue) {
        float floatValue = Float.valueOf(startValue).floatValue();
        floatValue += getStepAmount();

        float accuracy = (float) Math.pow(10f, getNumberOfDecimals());
        floatValue = Math.round(floatValue * accuracy) / accuracy;

        return Float.toString(floatValue);
    }

    @Override
    public Float parseStringValue(String value) {
        if (value == null || "".equals(value)) {
            return null;
        } else {
            return Float.parseFloat(value);
        }
    }

    @Override
    protected boolean isSmallerThanMax(String stringValue) {
        float value = Float.parseFloat(stringValue);

        if (getMaxValue() != null && value > getMaxValue()) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    protected boolean isLargerThanMin(String stringValue) {
        float value = Float.parseFloat(stringValue);

        if (getMinValue() != null && value < getMinValue()) {
            return false;
        } else {
            return true;
        }
    }

    public int getNumberOfDecimals() {
        return numberOfDecimals;
    }

    public void setNumberOfDecimals(int numberOfDecimals) {
        this.numberOfDecimals = numberOfDecimals;
    }

    @Override
    public Float parseStepAmount(String value) {
        return parseStringValue(value);
    }
}
