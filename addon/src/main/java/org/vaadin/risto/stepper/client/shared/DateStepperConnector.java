package org.vaadin.risto.stepper.client.shared;

import java.util.Date;

import org.vaadin.risto.stepper.client.ui.DateStepper;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.shared.ui.Connect;

@Connect(org.vaadin.risto.stepper.DateStepper.class)
public class DateStepperConnector
        extends AbstractStepperConnector<Date, Integer> {

    @Override
    protected DateStepper createWidget() {
        return GWT.create(DateStepper.class);
    }

    @Override
    public DateStepperState getState() {
        return (DateStepperState) super.getState();
    }

    @Override
    public DateStepper getWidget() {
        return (DateStepper) super.getWidget();
    }

    @Override
    public void onStateChanged(StateChangeEvent stateChangeEvent) {
        getWidget().setDateStepField(getState().getDateStep());

        getWidget().setDateFormat(
                DateTimeFormat.getFormat(getState().getDateFormat()));

        super.onStateChanged(stateChangeEvent);
    }

}
