package org.vaadin.risto.stepper.client.shared;

import org.vaadin.risto.stepper.IntStepper;

import com.google.gwt.core.client.GWT;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.shared.ui.Connect;

@Connect(IntStepper.class)
public class IntStepperConnector
        extends AbstractStepperConnector<Integer, Integer> {

    @Override
    protected org.vaadin.risto.stepper.client.ui.IntStepper createWidget() {
        return GWT.create(org.vaadin.risto.stepper.client.ui.IntStepper.class);
    }

    @Override
    public IntStepperState getState() {
        return (IntStepperState) super.getState();
    }

    @Override
    public void onStateChanged(StateChangeEvent stateChangeEvent) {
        super.onStateChanged(stateChangeEvent);

        getWidget().setValueFilteringEnabled(getState().valueFiltering);
    }

}
