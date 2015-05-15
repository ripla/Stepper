package org.vaadin.risto.stepper.widgetset.client.shared;

import org.vaadin.risto.stepper.widgetset.client.ui.FloatStepper;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.shared.ui.Connect;

@Connect(org.vaadin.risto.stepper.FloatStepper.class)
public class FloatStepperConnector extends
        AbstractStepperConnector<Float, Float> {

    private static final long serialVersionUID = 7493920052633327240L;

    @Override
    protected Widget createWidget() {
        return GWT.create(FloatStepper.class);
    }

    @Override
    public AbstractDecimalStepperState getState() {
        return (AbstractDecimalStepperState) super.getState();
    }

    @Override
    public FloatStepper getWidget() {
        return (FloatStepper) super.getWidget();
    }

    @Override
    public void onStateChanged(StateChangeEvent stateChangeEvent) {
        getWidget().setNumberOfDecimals(getState().numberOfDecimals);
        getWidget().setValueFilteringEnabled(getState().valueFiltering);
        getWidget().setDecimalSeparator(getState().decimalSeparator);

        super.onStateChanged(stateChangeEvent);
    }
}
