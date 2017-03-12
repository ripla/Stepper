package org.vaadin.risto.stepper.client.shared;

import java.math.BigDecimal;

import org.vaadin.risto.stepper.BigDecimalStepper;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.shared.ui.Connect;

@Connect(BigDecimalStepper.class)
public class BigDecimalStepperConnector
        extends AbstractStepperConnector<BigDecimal, BigDecimal> {

    @Override
    protected Widget createWidget() {
        return GWT.create(
                org.vaadin.risto.stepper.client.ui.BigDecimalStepper.class);
    }

    @Override
    public AbstractDecimalStepperState getState() {
        return (AbstractDecimalStepperState) super.getState();
    }

    @Override
    public org.vaadin.risto.stepper.client.ui.BigDecimalStepper getWidget() {
        return (org.vaadin.risto.stepper.client.ui.BigDecimalStepper) super.getWidget();
    }

    @Override
    public void onStateChanged(StateChangeEvent stateChangeEvent) {
        getWidget().setNumberOfDecimals(getState().numberOfDecimals);
        getWidget().setValueFilteringEnabled(getState().valueFiltering);
        getWidget().setDecimalSeparator(getState().decimalSeparator);

        super.onStateChanged(stateChangeEvent);
    }
}
