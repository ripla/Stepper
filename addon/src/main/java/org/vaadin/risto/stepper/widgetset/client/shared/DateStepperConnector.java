package org.vaadin.risto.stepper.widgetset.client.shared;

import java.util.Date;

import org.vaadin.risto.stepper.widgetset.client.ui.VDateStepper;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.shared.ui.Connect;

@Connect(org.vaadin.risto.stepper.DateStepper.class)
public class DateStepperConnector extends
        AbstractStepperConnector<Date, Integer> {

    private static final long serialVersionUID = -8728733216507405676L;

    @Override
    protected VDateStepper createWidget() {
        return GWT.create(VDateStepper.class);
    }

    @Override
    public DateStepperState getState() {
        return (DateStepperState) super.getState();
    }

    @Override
    public VDateStepper getWidget() {
        return (VDateStepper) super.getWidget();
    }

    @Override
    public void onStateChanged(StateChangeEvent stateChangeEvent) {
        getWidget().setDateStepField(getState().getDateStep());

        getWidget().setDateFormat(
                DateTimeFormat.getFormat(getState().getDateFormat()));

        super.onStateChanged(stateChangeEvent);
    }

}
