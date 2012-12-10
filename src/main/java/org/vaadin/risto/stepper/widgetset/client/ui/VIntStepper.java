package org.vaadin.risto.stepper.widgetset.client.ui;

/**
 * @author Risto Yrjänä / Vaadin Ltd.
 * 
 */
public class VIntStepper extends VAbstractStepper<Integer, Integer> {

    @Override
    protected boolean isValidForType(String value) {
        try {
            Integer.valueOf(value);
            return true;
        } catch (NumberFormatException exception) {
            return false;
        }
    }

    @Override
    protected String getDecreasedValue(String startValue) throws Exception {
        int intValue = Integer.valueOf(startValue).intValue();
        intValue -= getStepAmount();
        return Integer.toString(intValue);
    }

    @Override
    protected String getIncreasedValue(String startValue) throws Exception {
        int intValue = Integer.valueOf(startValue).intValue();
        intValue += getStepAmount();
        return Integer.toString(intValue);
    }

    @Override
    public Integer parseStringValue(String value) {
        if (value == null || "".equals(value)) {
            return null;
        } else {
            return Integer.parseInt(value);
        }
    }

    @Override
    protected boolean isSmallerThanMax(String stringValue) {
        int value = Integer.parseInt(stringValue);
        if (getMaxValue() != null && value > getMaxValue()) {
            return false;
        }

        return true;
    }

    @Override
    protected boolean isLargerThanMin(String stringValue) {
        int value = Integer.parseInt(stringValue);

        if (getMinValue() != null && value < getMinValue()) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public Integer parseStepAmount(String value) {
        return parseStringValue(value);
    }

}
