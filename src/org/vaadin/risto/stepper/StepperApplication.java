package org.vaadin.risto.stepper;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.vaadin.risto.stepper.widgetset.client.ui.VDateStepper;

import com.vaadin.Application;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/**
 * Demo application for the Stepper add-on.
 * 
 * @author Risto Yrjänä / Vaadin Ltd.
 * 
 */
public class StepperApplication extends Application {

    private static final long serialVersionUID = 3840548109739501675L;
    private Window mainWindow;
    private IntStepper intStepper;
    private FloatStepper floatStepper;
    private DateStepper dateStepper;

    @Override
    public void init() {
        mainWindow = new Window("Stepper Application");
        setLocale(new Locale("fi", "FI"));
        setMainWindow(mainWindow);
        setTheme("reindeer");
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

        mainWindow.setContent(mainLayout);
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

        panel.addComponent(undefined);
        panel.addComponent(wide);
        panel.addComponent(fiftyPixels);
        panel.addComponent(disabled);
        panel.addComponent(readOnly);

        return panel;
    }

    private Panel getUpperPanel() {
        Panel panel = new Panel("Different Steppers");
        panel.setWidth("700px");

        String infoString = "<p>There are three different Stepper classes. Their step amount can be set from the server-side. The accuracy of the FloatStepper can be changed and the DateStepper can be set to increase days, months or years. The server-side values are added below the components as they are received from the client.</p>";
        infoString += "<p>Minimum and maximum values are: -25 to 25 for IntStepper, -25 to 25 for FloatStepper and 10 days to the past to 10 days to the future for DateStepper.</p>";
        infoString += "<p>You can change the values by using the controls, the arrow keys or the mouse wheel</p>";
        Label infoLabel = new Label(infoString);
        infoLabel.setContentMode(Label.CONTENT_XHTML);

        HorizontalLayout stepperLayout = new HorizontalLayout();

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
        dateStepper.setStepField(VDateStepper.DateStepField.DAY);
        dateStepper.setStepAmount(1);
        dateStepper.setCaption("DateStepper, step 1 day");

        Layout intStepperLayout = getStepperLayout(intStepper);
        Layout floatStepperLayout = getStepperLayout(floatStepper);
        Layout dateStepperLayout = getStepperLayout(dateStepper);

        stepperLayout.addComponent(intStepperLayout);
        stepperLayout.addComponent(floatStepperLayout);
        stepperLayout.addComponent(dateStepperLayout);

        CheckBox minValue = new CheckBox("Enable minimum value limits");
        minValue.setImmediate(true);
        minValue.addListener(new ClickListener() {

            private static final long serialVersionUID = 3201240348626812120L;

            public void buttonClick(ClickEvent event) {
                if (event.getButton().booleanValue()) {
                    setMinValue(intStepper);
                    setMinValue(floatStepper);
                    setMinValue(dateStepper);
                } else {
                    intStepper.setMinValue(null);
                    floatStepper.setMinValue(null);
                    dateStepper.setMinValue(null);
                }
            }
        });

        CheckBox maxValue = new CheckBox("Enable maximum value limits");
        maxValue.setImmediate(true);
        maxValue.addListener(new ClickListener() {

            private static final long serialVersionUID = 3201240348626812120L;

            public void buttonClick(ClickEvent event) {
                if (event.getButton().booleanValue()) {
                    setMaxValue(intStepper);
                    setMaxValue(floatStepper);
                    setMaxValue(dateStepper);
                } else {
                    intStepper.setMaxValue(null);
                    floatStepper.setMaxValue(null);
                    dateStepper.setMaxValue(null);
                }
            }

        });

        panel.addComponent(infoLabel);
        panel.addComponent(minValue);
        panel.addComponent(maxValue);
        panel.addComponent(stepperLayout);
        return panel;
    }

    protected void setMaxValue(AbstractStepper stepper) {
        if (stepper instanceof DateStepper) {
            Calendar calendar = Calendar.getInstance(getLocale());
            calendar.add(Calendar.DATE, 10);
            stepper.setMaxValue(calendar.getTime());

        } else if (stepper instanceof FloatStepper) {
            stepper.setMaxValue(25f);

        } else if (stepper instanceof IntStepper) {
            stepper.setMaxValue(25);
        }
    }

    protected void setMinValue(AbstractStepper stepper) {
        if (stepper instanceof DateStepper) {
            Calendar calendar = Calendar.getInstance(getLocale());
            calendar.add(Calendar.DATE, -10);
            stepper.setMinValue(calendar.getTime());

        } else if (stepper instanceof FloatStepper) {
            stepper.setMinValue(-25f);

        } else if (stepper instanceof IntStepper) {
            stepper.setMinValue(-25);
        }
    }

    protected Layout getStepperLayout(final AbstractStepper stepper) {
        VerticalLayout layout = new VerticalLayout();

        final Label valueLabel = new Label("");
        valueLabel.setContentMode(Label.CONTENT_XHTML);

        stepper.setImmediate(true);

        stepper.addListener(new Property.ValueChangeListener() {

            private static final long serialVersionUID = 2041886044345910145L;

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
