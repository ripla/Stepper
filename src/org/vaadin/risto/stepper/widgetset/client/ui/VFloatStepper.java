/**
 * 
 */
package org.vaadin.risto.stepper.widgetset.client.ui;

import com.vaadin.terminal.gwt.client.ApplicationConnection;
import com.vaadin.terminal.gwt.client.UIDL;

/**
 * @author Risto Yrjänä / Vaadin Ltd.
 * 
 */
public class VFloatStepper extends VAbstractStepper {

    private float stepAmount;
    private int numberOfDecimals;
    private Float maxValue;
    private Float minValue;

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

        stepAmount = uidl.getFloatAttribute("stepAmount");

        numberOfDecimals = uidl.getIntAttribute("numberOfDecimals");
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
            Float.valueOf(value);
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
    public String getDecreasedValue(String startValue) {
        float floatValue = Float.valueOf(startValue).floatValue();
        floatValue -= stepAmount;

        float accuracy = (float) Math.pow(10f, numberOfDecimals);
        floatValue = Math.round(floatValue * accuracy) / accuracy;

        return Float.toString(floatValue);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.vaadin.risto.stepper.widgetset.client.ui.VAbstractStepper#
     * getIncreasedValue(java.lang.String)
     */
    @Override
    public String getIncreasedValue(String startValue) {
        float floatValue = Float.valueOf(startValue).floatValue();
        floatValue += stepAmount;

        float accuracy = (float) Math.pow(10f, numberOfDecimals);
        floatValue = Math.round(floatValue * accuracy) / accuracy;

        return Float.toString(floatValue);
    }

    @Override
    protected void setMaxValue(Object value) {
        maxValue = (Float) value;
    }

    @Override
    protected void setMinValue(Object value) {
        minValue = (Float) value;
    }

    @Override
    protected Float parseStringValue(String value) {
        if (value == null || "".equals(value)) {
            return null;
        } else {
            return Float.parseFloat(value);
        }
    }

    @Override
    protected boolean isSmallerThanMax(String stringValue) {
        float value = Float.parseFloat(stringValue);

        if (maxValue != null && value > maxValue) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    protected boolean isLargerThanMin(String stringValue) {
        float value = Float.parseFloat(stringValue);

        if (minValue != null && value < minValue) {
            return false;
        } else {
            return true;
        }
    }
}
