package org.vaadin.risto.stepper.widgetset.client.shared;

import com.vaadin.shared.AbstractFieldState;

public class AbstractStepperState extends AbstractFieldState {

    private static final long serialVersionUID = -6837411523699098954L;

    private boolean isManualInputAllowed = true;
    private boolean mouseWheelEnabled = true;
    private boolean invalidValuesAllowed = false;
    private String fieldValue;
    private String minValue;
    private String maxValue;
    private String stepAmount;

    public boolean isManualInputAllowed() {
        return isManualInputAllowed;
    }

    public void setManualInputAllowed(boolean isManualInputAllowed) {
        this.isManualInputAllowed = isManualInputAllowed;
    }

    public boolean isMouseWheelEnabled() {
        return mouseWheelEnabled;
    }

    public void setMouseWheelEnabled(boolean mouseWheelEnabled) {
        this.mouseWheelEnabled = mouseWheelEnabled;
    }

    public boolean isInvalidValuesAllowed() {
        return invalidValuesAllowed;
    }

    public void setInvalidValuesAllowed(boolean invalidValuesAllowed) {
        this.invalidValuesAllowed = invalidValuesAllowed;
    }

    public String getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(String value) {
        this.fieldValue = value;
    }

    public String getMinValue() {
        return minValue;
    }

    public void setMinValue(String minValue) {
        this.minValue = minValue;
    }

    public String getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(String maxValue) {
        this.maxValue = maxValue;
    }

    public String getStepAmount() {
        return stepAmount;
    }

    public void setStepAmount(String stepAmount) {
        this.stepAmount = stepAmount;
    }
}