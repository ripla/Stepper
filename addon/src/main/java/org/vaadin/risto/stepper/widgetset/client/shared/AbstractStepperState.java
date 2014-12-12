package org.vaadin.risto.stepper.widgetset.client.shared;

import com.vaadin.shared.AbstractFieldState;

public class AbstractStepperState extends AbstractFieldState {

    private static final long serialVersionUID = -6837411523699098954L;

    public boolean isManualInputAllowed = true;
    public boolean isMouseWheelEnabled = true;
    public boolean isInvalidValuesAllowed = false;
    public boolean isNullValueAllowed = false;
    public String value;
    public String minValue;
    public String maxValue;
    public String stepAmount;
}