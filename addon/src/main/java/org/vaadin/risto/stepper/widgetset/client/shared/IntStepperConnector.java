package org.vaadin.risto.stepper.widgetset.client.shared;

import org.vaadin.risto.stepper.widgetset.client.ui.IntStepper;

import com.google.gwt.core.client.GWT;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.shared.ui.Connect;

@Connect(org.vaadin.risto.stepper.IntStepper.class)
public class IntStepperConnector
        extends AbstractStepperConnector<Integer, Integer> {

    private static final long serialVersionUID = -5276807014107059716L;

    @Override
    protected IntStepper createWidget() {
        return GWT.create(IntStepper.class);
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
