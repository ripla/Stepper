package org.vaadin.risto.stepper.demo;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.vaadin.risto.stepper.BigDecimalStepper;
import org.vaadin.risto.stepper.DateStepper;
import org.vaadin.risto.stepper.FloatStepper;
import org.vaadin.risto.stepper.IntStepper;
import org.vaadin.risto.stepper.Stepper;
import org.vaadin.risto.stepper.ValueFilteringStepper;
import org.vaadin.risto.stepper.client.shared.DateStepperField;

import com.vaadin.annotations.Theme;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * Demo application for the Stepper add-on.
 *
 * @author Risto Yrjänä / Vaadin
 */
@Theme("demo")
public class StepperDemoUI extends UI {

    private static final long serialVersionUID = 3840548109739501675L;
    private static final Date DEFAULT_DATE = new Date();
    private IntStepper intStepper;
    private FloatStepper floatStepper;
    private BigDecimalStepper bigDecimalStepper;
    private DateStepper dateStepper;

    private List<Stepper> steppers;

    @Override
    protected void init(VaadinRequest request) {
        Page.getCurrent().setTitle("Stepper demo");
        setLocale(new Locale("fi", "FI"));
        initUI();
    }

    private void initUI() {
        VerticalLayout mainLayout = new VerticalLayout();
        mainLayout.setMargin(true);
        mainLayout.setSpacing(true);

        Panel upper = getUpperPanel();

        mainLayout.addComponent(upper);
        mainLayout.setComponentAlignment(upper, Alignment.MIDDLE_CENTER);

        Panel lower = getLowerPanel();
        mainLayout.addComponent(lower);
        mainLayout.setComponentAlignment(lower, Alignment.MIDDLE_CENTER);

        setContent(mainLayout);
    }

    private Panel getLowerPanel() {
        Panel panel = new Panel("Stepper properties");
        panel.setWidth("700px");

        VerticalLayout layout = new VerticalLayout();
        layout.setWidth("100%");
        layout.setSpacing(true);
        panel.setContent(layout);
        layout.setMargin(true);

        IntStepper undefined = new IntStepper("Undefined width");
        undefined.setSizeUndefined();

        IntStepper wide = new IntStepper("100% wide");
        wide.setWidth("100%");

        IntStepper fiftyPixels = new IntStepper("50px wide");
        fiftyPixels.setWidth("50px");

        IntStepper disabled = new IntStepper("Disabled");
        disabled.setEnabled(false);

        IntStepper readOnly = new IntStepper("Read-only");
        readOnly.setReadOnly(true);

        IntStepper otherIcons = new IntStepper("Other icons");
        otherIcons.setWidth("65%");
        otherIcons.setStyleName("other-icons");
        otherIcons.setIncreaseIcon(VaadinIcons.PLUS_CIRCLE);
        otherIcons.setDecreaseIcon(VaadinIcons.MINUS_CIRCLE);

        layout.addComponents(undefined, wide, fiftyPixels, disabled, readOnly,
                otherIcons);

        return panel;
    }

    @SuppressWarnings("rawtypes")
    private Panel getUpperPanel() {
        Panel panel = new Panel("Different Steppers");
        panel.setWidth("700px");

        VerticalLayout panelLayout = new VerticalLayout();
        panelLayout.setMargin(true);
        panel.setContent(panelLayout);

        GridLayout options = new GridLayout(2, 3);
        options.setSpacing(true);

        GridLayout actions = new GridLayout(2, 3);
        options.setSpacing(true);
        options.setMargin(true);

        String infoString = "<p>There are four different Stepper classes. Their step amount can be set from the server-side. The accuracy of the FloatStepper can be changed and the DateStepper can be set to increase days, months or years. The server-side values are added below the components as they are received from the client.</p>";
        infoString += "<p>Minimum and maximum values are: -25 to 25 for IntStepper, -25 to 25 for FloatStepper and 10 days to the past to 10 days to the future for DateStepper.</p>";
        infoString += "<p>You can change the values by using the controls, " +
                "the arrow keys or the mouse wheel.</p>";
        Label infoLabel = new Label(infoString);
        infoLabel.setWidth("100%");
        infoLabel.setContentMode(ContentMode.HTML);

        GridLayout stepperLayout = new GridLayout(2, 2);
        stepperLayout.setWidth("100%");
        stepperLayout.setSpacing(true);
        stepperLayout.setMargin(true);

        intStepper = new IntStepper();
        intStepper.setValue(1);
        intStepper.setStepAmount(1);
        intStepper.setCaption("IntStepper, step 1 (tabindex 3)");
        intStepper.addClickListener(e -> Notification.show("clicked"));
        intStepper.setTabIndex(3);

        floatStepper = new FloatStepper();
        floatStepper.setValue(1.0f);
        floatStepper.setStepAmount(1.222f);
        floatStepper.setNumberOfDecimals(3);
        floatStepper.setCaption("FloatStepper, step 1.222 (tabindex 2)");
        floatStepper.setTabIndex(2);

        bigDecimalStepper = new BigDecimalStepper();
        bigDecimalStepper.setValue(BigDecimal.ZERO);
        bigDecimalStepper
                .setStepAmount(new BigDecimal("0.111111111111111111111111"));
        bigDecimalStepper.setCaption(
                "BigDecimalStepper, step 0.111... (tabindex 1)");
        bigDecimalStepper.setWidth("200px");
        bigDecimalStepper.setTabIndex(1);

        dateStepper = new DateStepper();
        dateStepper.setValue(DEFAULT_DATE);
        dateStepper.setStepField(DateStepperField.DAY);
        dateStepper.setStepAmount(1);
        dateStepper.setCaption("DateStepper, step 1 day");

        steppers = Arrays.<Stepper> asList(intStepper, floatStepper,
                bigDecimalStepper, dateStepper);

        Layout intStepperLayout = getStepperLayout(intStepper);
        Layout floatStepperLayout = getStepperLayout(floatStepper);
        Layout bigDecimalStepperLayout = getStepperLayout(bigDecimalStepper);
        Layout dateStepperLayout = getStepperLayout(dateStepper);

        stepperLayout.addComponent(intStepperLayout);
        stepperLayout.addComponent(dateStepperLayout);
        stepperLayout.addComponent(floatStepperLayout);
        stepperLayout.addComponent(bigDecimalStepperLayout);

        final CheckBox minValue = new CheckBox("Enable minimum value limits");
        minValue.addValueChangeListener(event -> {
            if (minValue.getValue()) {
                steppers.forEach(this::setMinValue);
            } else {
                steppers.forEach(s -> s.setMinValue(null));
            }
        });

        final CheckBox maxValue = new CheckBox("Enable maximum value limits");
        maxValue.addValueChangeListener(event -> {
            if (maxValue.getValue()) {
                steppers.forEach(this::setMaxValue);
            } else {
                steppers.forEach(s -> s.setMaxValue(null));
            }
        });

        final CheckBox manualInput = new CheckBox("Enable manual input");
        manualInput.setValue(true);
        manualInput.addValueChangeListener(event -> steppers
                .forEach(s -> s.setManualInputAllowed(manualInput.getValue())));

        final CheckBox mousewheelEnabled = new CheckBox(
                "Enable mousewheel support");
        mousewheelEnabled.setValue(true);
        mousewheelEnabled.addValueChangeListener(event -> steppers.forEach(
                s -> s.setMouseWheelEnabled(mousewheelEnabled.getValue())));

        final CheckBox invalidValuesAllowed = new CheckBox(
                "Allow invalid values");
        invalidValuesAllowed.setValue(false);
        invalidValuesAllowed.addValueChangeListener(event -> steppers.forEach(
                s -> s.setInvalidValuesAllowed(
                        invalidValuesAllowed.getValue())));

        final CheckBox nullValueAllowed = new CheckBox("Null is valid");
        nullValueAllowed.setValue(false);
        nullValueAllowed.addValueChangeListener(event -> steppers.forEach(
                s -> s.setNullValueAllowed(nullValueAllowed.getValue())));

        final CheckBox valueFiltering = new CheckBox("Enable value filtering");
        valueFiltering.setValue(false);
        valueFiltering.addValueChangeListener(event -> steppers.stream()
                .filter(ValueFilteringStepper.class::isInstance)
                .map(ValueFilteringStepper.class::cast)
                .forEach(s -> s.setValueFiltering(valueFiltering.getValue())));

        options.addComponents(minValue, manualInput, maxValue,
                mousewheelEnabled, invalidValuesAllowed, nullValueAllowed,
                valueFiltering);

        Button disable = new Button("Disable all");
        disable.addClickListener(
                event -> steppers.forEach(s -> s.setEnabled(false)));

        Button enable = new Button("Enable all");
        enable.addClickListener(
                event -> steppers.forEach(s -> s.setEnabled(true)));

        actions.addComponent(disable);
        actions.addComponent(enable);

        panelLayout.addComponent(infoLabel);
        panelLayout.addComponent(options);
        panelLayout.addComponent(actions);
        panelLayout.addComponent(stepperLayout);

        return panel;
    }

    protected void setMaxValue(Stepper someStepper) {
        if (someStepper instanceof DateStepper) {
            DateStepper stepper = (DateStepper) someStepper;
            Calendar calendar = Calendar.getInstance(getLocale());
            calendar.add(Calendar.DATE, 10);
            stepper.setMaxValue(calendar.getTime());

        } else if (someStepper instanceof FloatStepper) {
            FloatStepper stepper = (FloatStepper) someStepper;
            stepper.setMaxValue(25f);

        } else if (someStepper instanceof IntStepper) {
            IntStepper stepper = (IntStepper) someStepper;
            stepper.setMaxValue(25);
        }
    }

    protected void setMinValue(Stepper someStepper) {
        if (someStepper instanceof DateStepper) {
            DateStepper stepper = (DateStepper) someStepper;
            Calendar calendar = Calendar.getInstance(getLocale());
            calendar.add(Calendar.DATE, -10);
            stepper.setMinValue(calendar.getTime());

        } else if (someStepper instanceof FloatStepper) {
            FloatStepper stepper = (FloatStepper) someStepper;
            stepper.setMinValue(-25f);

        } else if (someStepper instanceof IntStepper) {
            IntStepper stepper = (IntStepper) someStepper;
            stepper.setMinValue(-25);
        }
    }

    protected Layout getStepperLayout(final Stepper stepper) {
        VerticalLayout layout = new VerticalLayout();
        layout.setMargin(false);
        
        final Label valueLabel = new Label("");
        valueLabel.setContentMode(ContentMode.HTML);

        stepper.addValueChangeListener(event -> {
            String valueLine =
                    DateFormat.getTimeInstance(DateFormat.SHORT, getLocale())
                            .format(new Date()) + " " +
                            event.getSource().getValue();
            String oldValue =
                    valueLabel.getValue() != null ? valueLabel.getValue()
                             : "";

            String sb = valueLine + "<br/>" + oldValue;
            valueLabel.setValue(sb);
        });

        layout.addComponent(stepper);
        layout.addComponent(valueLabel);

        return layout;
    }
}
