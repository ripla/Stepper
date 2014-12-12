package org.vaadin.risto.stepper;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import javax.servlet.annotation.WebServlet;

@SuppressWarnings("serial")
public class StepperMinMaxLink extends UI {

    private VerticalLayout layout;

    @Override
    protected void init(VaadinRequest request) {
        layout = new VerticalLayout();
        layout.setMargin(true);
        setContent(layout);

        final IntStepper min = new IntStepper();
        min.setValue(2);
        min.setStepAmount(1);
        min.setImmediate(true);
        min.setWidth("100px");

        final IntStepper max = new IntStepper();
        max.setValue(30);
        max.setStepAmount(1);
        max.setImmediate(true);
        max.setWidth("100px");

        ValueChangeListener minChangeListener = new ValueChangeListener() {

            @Override
            public void valueChange(ValueChangeEvent event) {
                Integer minValue = (Integer) min.getValue();
                max.setMinValue(minValue + 1);
            }
        };

        ValueChangeListener maxChangeListener = new ValueChangeListener() {

            @Override
            public void valueChange(ValueChangeEvent event) {
                Integer maxValue = (Integer) max.getValue();
                min.setMinValue(maxValue + 1);
            }
        };
        min.addValueChangeListener(minChangeListener);
        max.addValueChangeListener(maxChangeListener);

        layout.addComponent(max);
        layout.addComponent(min);
    }
}