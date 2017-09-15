package org.vaadin.risto.stepper.demo;

import org.vaadin.risto.stepper.IntStepper;

import com.vaadin.data.HasValue;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class StepperMinMaxLink extends UI {

    @Override
    protected void init(VaadinRequest request) {
        VerticalLayout layout = new VerticalLayout();
        layout.setMargin(true);
        setContent(layout);

        final IntStepper min = new IntStepper();
        min.setValue(2);
        min.setStepAmount(1);
        min.setWidth("100px");

        final IntStepper max = new IntStepper();
        max.setValue(30);
        max.setStepAmount(1);
        max.setWidth("100px");

        HasValue.ValueChangeListener<Integer> minChangeListener = event -> {
            Integer minValue = min.getValue();
            max.setMinValue(minValue + 1);
        };

        HasValue.ValueChangeListener<Integer> maxChangeListener = event -> {
            Integer maxValue = max.getValue();
            min.setMinValue(maxValue + 1);
        };
        min.addValueChangeListener(minChangeListener);
        max.addValueChangeListener(maxChangeListener);

        layout.addComponent(max);
        layout.addComponent(min);
    }
}
