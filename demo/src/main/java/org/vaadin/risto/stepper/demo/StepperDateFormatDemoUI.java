package org.vaadin.risto.stepper.demo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.vaadin.risto.stepper.DateStepper;

import com.google.common.base.Strings;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class StepperDateFormatDemoUI extends UI {

    @Override
    protected void init(VaadinRequest request) {
        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();

        CssLayout content = new CssLayout();
        setContent(layout);

        final DateStepper stepper = new DateStepper();

        TextField format = new TextField();
        format.addValueChangeListener(event -> {
            String value = event.getSource().getValue();
            if (Strings.isNullOrEmpty(value)) {
                stepper.setDateFormat(null);
            } else {
                stepper.setDateFormat(new SimpleDateFormat(value));
            }
        });

        CheckBox change = new CheckBox();
        change.addValueChangeListener(event -> {
            if (event.getSource().getValue()) {
                stepper.setDateFormat(((SimpleDateFormat) DateFormat
                        .getDateInstance(DateFormat.LONG, getLocale())));
            } else {
                stepper.setDateFormat(null);
            }
        });

        content.addComponent(stepper);
        content.addComponent(format);
        content.addComponent(change);

        layout.addComponent(content);
        layout.setComponentAlignment(content, Alignment.MIDDLE_CENTER);
    }
}
