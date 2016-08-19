package org.vaadin.risto.stepper.client.ui;

/**
 * @author Risto Yrjänä / Vaadin
 * 
 */
public class IntStepper extends AbstractStepper<Integer, Integer> {

    private static final String INTEGER_REGEXP = "^\\-?\\d+$";

    public IntStepper() {
        super(INTEGER_REGEXP);
    }

    @Override
    public boolean isValidForType(String value) {
        if (super.isValidForType(value)) {
            try {
                Integer.parseInt(value);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        } else {
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
