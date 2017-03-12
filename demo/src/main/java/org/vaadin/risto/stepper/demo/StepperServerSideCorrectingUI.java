package org.vaadin.risto.stepper.demo;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.vaadin.risto.stepper.IntStepper;

import com.vaadin.data.HasValue;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;

@SuppressWarnings("serial")
public class StepperServerSideCorrectingUI extends UI {

    @Override
    protected void init(VaadinRequest request) {
        final IntStepper stepper = new IntStepper();
        stepper.setInvalidValuesAllowed(true);
        stepper.addValueChangeListener(
                (HasValue.ValueChangeListener<Integer>) event -> {
                    Logger.getLogger(
                            StepperServerSideCorrectingUI.class.getName())
                            .log(Level.INFO, "Value was " +
                                    event.getSource().getValue());
                    stepper.setValue(5);
                });

        setContent(stepper);

    }
}
