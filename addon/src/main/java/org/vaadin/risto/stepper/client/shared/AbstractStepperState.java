package org.vaadin.risto.stepper.client.shared;

import com.vaadin.shared.AbstractFieldState;

public class AbstractStepperState extends AbstractFieldState {

    public String INCREASE_ICON_KEY = "ICON_INCREASE";
    public String DECREASE_ICON_KEY = "ICON_DECREASE";

    public boolean isManualInputAllowed = true;
    public boolean isMouseWheelEnabled = true;
    public boolean isInvalidValuesAllowed = false;
    public boolean isNullValueAllowed = false;
    public String value;
    public String minValue;
    public String maxValue;
    public String stepAmount;
}
