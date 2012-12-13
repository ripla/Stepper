package org.vaadin.risto.stepper;

import java.text.DateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.vaadin.risto.stepper.widgetset.client.ui.DateStepField;

import com.vaadin.annotations.Theme;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * Demo application for the Stepper add-on.
 * 
 * @author Risto Yrjänä / Vaadin Ltd.
 * 
 */
@Theme("reindeer")
public class StepperDemoUI extends UI {

    private static final long serialVersionUID = 3840548109739501675L;
    private IntStepper intStepper;
    private FloatStepper floatStepper;
    private DateStepper dateStepper;
    @SuppressWarnings("rawtypes")
    private List<AbstractStepper> steppers;

    @Override
    protected void init(VaadinRequest request) {
        Page.getCurrent().setTitle("Stepper demo");
        // TODO disabled until client-side locales are fixed
        // setLocale(new Locale("fi", "FI"));
        initUI();
    }

    protected void initUI() {
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

        layout.addComponent(undefined);
        layout.addComponent(wide);
        layout.addComponent(fiftyPixels);
        layout.addComponent(disabled);
        layout.addComponent(readOnly);

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

        String infoString = "<p>There are three different Stepper classes. Their step amount can be set from the server-side. The accuracy of the FloatStepper can be changed and the DateStepper can be set to increase days, months or years. The server-side values are added below the components as they are received from the client.</p>";
        infoString += "<p>Minimum and maximum values are: -25 to 25 for IntStepper, -25 to 25 for FloatStepper and 10 days to the past to 10 days to the future for DateStepper.</p>";
        infoString += "<p>You can change the values by using the controls, the arrow keys or the mouse wheel</p>";
        Label infoLabel = new Label(infoString);
        infoLabel.setContentMode(ContentMode.HTML);

        HorizontalLayout stepperLayout = new HorizontalLayout();
        stepperLayout.setWidth("100%");
        stepperLayout.setSpacing(true);
        stepperLayout.setMargin(true);

        intStepper = new IntStepper();
        intStepper.setValue(1);
        intStepper.setStepAmount(1);
        intStepper.setCaption("IntStepper, step 1");

        floatStepper = new FloatStepper();
        floatStepper.setValue(1.0f);
        floatStepper.setStepAmount(1.222f);
        floatStepper.setNumberOfDecimals(3);
        floatStepper.setCaption("FloatStepper, step 1.222");

        dateStepper = new DateStepper();
        dateStepper.setValue(new Date());
        dateStepper.setStepField(DateStepField.DAY);
        dateStepper.setStepAmount(1);
        dateStepper.setCaption("DateStepper, step 1 day");

        steppers = Arrays.<AbstractStepper> asList(intStepper, floatStepper,
                dateStepper);

        Layout intStepperLayout = getStepperLayout(intStepper);
        Layout floatStepperLayout = getStepperLayout(floatStepper);
        Layout dateStepperLayout = getStepperLayout(dateStepper);

        stepperLayout.addComponent(intStepperLayout);
        stepperLayout.addComponent(floatStepperLayout);
        stepperLayout.addComponent(dateStepperLayout);

        final CheckBox minValue = new CheckBox("Enable minimum value limits");
        minValue.setImmediate(true);
        minValue.addValueChangeListener(new Property.ValueChangeListener() {

            private static final long serialVersionUID = -8342007719460917804L;

            @SuppressWarnings({ "unchecked" })
            @Override
            public void valueChange(ValueChangeEvent event) {
                if (minValue.getValue()) {
                    for (AbstractStepper stepper : steppers) {
                        setMinValue(stepper);
                    }
                } else {
                    for (AbstractStepper stepper : steppers) {
                        stepper.setMinValue(null);
                    }
                }
            }
        });

        final CheckBox maxValue = new CheckBox("Enable maximum value limits");
        maxValue.setImmediate(true);
        maxValue.addValueChangeListener(new Property.ValueChangeListener() {

            private static final long serialVersionUID = 3201240348626812120L;

            @Override
            @SuppressWarnings({ "unchecked" })
            public void valueChange(ValueChangeEvent event) {
                if (maxValue.getValue()) {
                    for (AbstractStepper stepper : steppers) {
                        setMaxValue(stepper);
                    }
                } else {
                    for (AbstractStepper stepper : steppers) {
                        stepper.setMaxValue(null);
                    }
                }
            }

        });

        final CheckBox manualInput = new CheckBox("Enable manual input");
        manualInput.setValue(true);
        manualInput.setImmediate(true);
        manualInput.addValueChangeListener(new ValueChangeListener() {

            private static final long serialVersionUID = 1556003158228491207L;

            @Override
            public void valueChange(ValueChangeEvent event) {
                for (AbstractStepper stepper : steppers) {
                    stepper.setManualInputAllowed(manualInput.getValue());
                }
            }
        });

        final CheckBox mousewheelEnabled = new CheckBox(
                "Enable mousewheel support");
        mousewheelEnabled.setValue(true);
        mousewheelEnabled.setImmediate(true);
        mousewheelEnabled.addValueChangeListener(new ValueChangeListener() {

            private static final long serialVersionUID = 1556003158228491207L;

            @Override
            public void valueChange(ValueChangeEvent event) {
                for (AbstractStepper stepper : steppers) {
                    stepper.setMouseWheelEnabled(mousewheelEnabled.getValue());
                }
            }
        });
        final CheckBox invalidValuesAllowed = new CheckBox(
                "Allow invalid values");
        invalidValuesAllowed.setValue(false);
        invalidValuesAllowed.setImmediate(true);
        invalidValuesAllowed.addValueChangeListener(new ValueChangeListener() {

            private static final long serialVersionUID = 1556003158228491207L;

            @Override
            public void valueChange(ValueChangeEvent event) {
                for (AbstractStepper stepper : steppers) {
                    stepper.setInvalidValuesAllowed(invalidValuesAllowed
                            .getValue());
                }
            }
        });

        options.addComponent(minValue);
        options.addComponent(manualInput);
        options.addComponent(maxValue);
        options.addComponent(mousewheelEnabled);
        options.addComponent(invalidValuesAllowed);

        panelLayout.addComponent(infoLabel);
        panelLayout.addComponent(options);
        panelLayout.addComponent(stepperLayout);

        return panel;
    }

    protected void setMaxValue(AbstractStepper<?, ?> someStepper) {
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

    protected void setMinValue(AbstractStepper<?, ?> someStepper) {
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

    protected Layout getStepperLayout(final AbstractStepper<?, ?> stepper) {
        VerticalLayout layout = new VerticalLayout();

        final Label valueLabel = new Label("");
        valueLabel.setContentMode(ContentMode.HTML);

        stepper.setImmediate(true);

        stepper.addValueChangeListener(new Property.ValueChangeListener() {

            private static final long serialVersionUID = 2041886044345910145L;

            @Override
            public void valueChange(ValueChangeEvent event) {
                String valueLine = DateFormat.getTimeInstance(DateFormat.SHORT,
                        getLocale()).format(new Date())
                        + " " + event.getProperty().getValue();
                String oldValue = valueLabel.getValue() != null ? valueLabel
                        .getValue().toString() : "";

                StringBuffer sb = new StringBuffer();
                sb.append(valueLine);
                sb.append("<br/>");
                sb.append(oldValue);
                valueLabel.setValue(sb.toString());
            }
        });

        layout.addComponent(stepper);
        layout.addComponent(valueLabel);

        return layout;
    }
}
