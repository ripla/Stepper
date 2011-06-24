package org.vaadin.risto.stepper.widgetset.client.ui;

import com.vaadin.terminal.gwt.client.ApplicationConnection;
import com.vaadin.terminal.gwt.client.UIDL;

/**
 * @author Risto Yrjänä / Vaadin Ltd.
 * 
 */
public class VIntStepper extends VAbstractStepper {

    private int stepAmount;
    private Integer maxValue;
    private Integer minValue;

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.vaadin.risto.stepper.widgetset.client.ui.VAbstractStepper#updateFromUIDL
     * (com.vaadin.terminal.gwt.client.UIDL,
     * com.vaadin.terminal.gwt.client.ApplicationConnection)
     */
    @Override
    public void updateFromUIDL(UIDL uidl, ApplicationConnection client) {
        super.updateFromUIDL(uidl, client);

        stepAmount = uidl.getIntAttribute("stepAmount");
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.vaadin.risto.stepper.widgetset.client.ui.VAbstractStepper#isValidForType
     * (java.lang.String)
     */
    @Override
    protected boolean isValidForType(String value) {
        try {
            Integer.valueOf(value);
            return true;
        } catch (NumberFormatException exception) {
            return false;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.vaadin.risto.stepper.widgetset.client.ui.VAbstractStepper#
     * getDecreasedValue(java.lang.String)
     */
    @Override
    protected String getDecreasedValue(String startValue) throws Exception {
        int intValue = Integer.valueOf(startValue).intValue();
        intValue -= stepAmount;
        return Integer.toString(intValue);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.vaadin.risto.stepper.widgetset.client.ui.VAbstractStepper#
     * getIncreasedValue(java.lang.String)
     */
    @Override
    protected String getIncreasedValue(String startValue) throws Exception {
        int intValue = Integer.valueOf(startValue).intValue();
        intValue += stepAmount;
        return Integer.toString(intValue);
    }

    @Override
    protected void setMaxValue(Object value) {
        maxValue = (Integer) value;
    }

    @Override
    protected void setMinValue(Object value) {
        minValue = (Integer) value;
    }

    @Override
    protected Integer parseStringValue(String value) {
        if (value == null || "".equals(value)) {
            return null;
        } else {
            return Integer.parseInt(value);
        }
    }

    @Override
    protected boolean isSmallerThanMax(String stringValue) {
        int value = Integer.parseInt(stringValue);
        if (maxValue != null && value > maxValue) {
            return false;
        }

        return true;
    }

    @Override
    protected boolean isLargerThanMin(String stringValue) {
        int value = Integer.parseInt(stringValue);

        if (minValue != null && value < minValue) {
            return false;
        } else {
            return true;
        }
    }
}
