package org.vaadin.risto.stepper.widgetset.client.shared;

import java.math.BigDecimal;

import org.vaadin.risto.stepper.widgetset.client.ui.BigDecimalStepper;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.shared.ui.Connect;

@Connect(org.vaadin.risto.stepper.BigDecimalStepper.class)
public class BigDecimalStepperConnector extends
        AbstractStepperConnector<BigDecimal, BigDecimal> {

    private static final long serialVersionUID = 7493920052633327240L;

    @Override
    protected Widget createWidget() {
        return GWT.create(BigDecimalStepper.class);
    }

    @Override
    public BigDecimalStepperState getState() {
        return (BigDecimalStepperState) super.getState();
    }

    @Override
    public BigDecimalStepper getWidget() {
        return (BigDecimalStepper) super.getWidget();
    }

    @Override
    public void onStateChanged(StateChangeEvent stateChangeEvent) {
        getWidget().setNumberOfDecimals(getState().numberOfDecimals);
        getWidget().setValueFilteringEnabled(getState().valueFiltering);

        super.onStateChanged(stateChangeEvent);
    }
}
