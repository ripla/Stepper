package org.vaadin.risto.stepper.demo;

import org.vaadin.risto.stepper.IntStepper;

import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class StepperSizeUI extends UI {

    @Override
    protected void init(VaadinRequest request) {
        VerticalLayout layout = new VerticalLayout();
        layout.setWidth("100%");

        IntStepper pixelWidth = new IntStepper();
        pixelWidth.setWidth("50px");

        IntStepper relativeWidth = new IntStepper();
        relativeWidth.setWidth("100%");

        layout.addComponents(pixelWidth, relativeWidth);
        setContent(layout);
    }

}
