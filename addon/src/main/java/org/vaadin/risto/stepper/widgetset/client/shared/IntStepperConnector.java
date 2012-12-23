package org.vaadin.risto.stepper.widgetset.client.shared;

import org.vaadin.risto.stepper.widgetset.client.ui.VIntStepper;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.shared.ui.Connect;

@Connect(org.vaadin.risto.stepper.IntStepper.class)
public class IntStepperConnector extends
        AbstractStepperConnector<Integer, Integer> {

    private static final long serialVersionUID = -5276807014107059716L;

    @Override
    protected Widget createWidget() {
        return GWT.create(VIntStepper.class);
    }

}
