package org.vaadin.risto.stepper.client.shared;

import org.vaadin.risto.stepper.FloatStepper;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.shared.ui.Connect;

@Connect(FloatStepper.class)
public class FloatStepperConnector
        extends AbstractStepperConnector<Float, Float> {

    @Override
    protected Widget createWidget() {
        return GWT.create(org.vaadin.risto.stepper.client.ui.FloatStepper.class);
    }

    @Override
    public AbstractDecimalStepperState getState() {
        return (AbstractDecimalStepperState) super.getState();
    }

    @Override
    public org.vaadin.risto.stepper.client.ui.FloatStepper getWidget() {
        return (org.vaadin.risto.stepper.client.ui.FloatStepper) super.getWidget();
    }

    @Override
    public void onStateChanged(StateChangeEvent stateChangeEvent) {
        getWidget().setNumberOfDecimals(getState().numberOfDecimals);
        getWidget().setValueFilteringEnabled(getState().valueFiltering);
        getWidget().setDecimalSeparator(getState().decimalSeparator);

        super.onStateChanged(stateChangeEvent);
    }
}
