package org.vaadin.risto.stepper.widgetset.client.shared;

import java.util.Date;

import org.vaadin.risto.stepper.widgetset.client.ui.DateStepField;
import org.vaadin.risto.stepper.widgetset.client.ui.VDateStepper;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.vaadin.client.LocaleNotLoadedException;
import com.vaadin.client.LocaleService;
import com.vaadin.client.VConsole;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.shared.ui.Connect;

@Connect(org.vaadin.risto.stepper.DateStepper.class)
public class DateStepperConnector extends
        AbstractStepperConnector<Date, Integer> {

    private static final long serialVersionUID = -8728733216507405676L;

    @Override
    protected VDateStepper createWidget() {
        return GWT.create(VDateStepper.class);
    }

    @Override
    public DateStepperState getState() {
        return (DateStepperState) super.getState();
    }

    @Override
    public VDateStepper getWidget() {
        return (VDateStepper) super.getWidget();
    }

    @Override
    public void onStateChanged(StateChangeEvent stateChangeEvent) {
        getWidget().setDateStepField(
                DateStepField.valueOf(getState().getDateStep()));
        try {
            String formatString = LocaleService.getDateFormat(getState()
                    .getLocale());
            getWidget().setDateFormat(DateTimeFormat.getFormat(formatString));
        } catch (LocaleNotLoadedException e) {
            String defaultLocale = LocaleService.getDefaultLocale();
            VConsole.error("Locale " + getState().getLocale()
                    + "not loaded. Using default locale " + defaultLocale
                    + " for DateStepper.");
            VConsole.error(e);
            getWidget().setDateFormat(DateTimeFormat.getFormat(defaultLocale));
        }

        super.onStateChanged(stateChangeEvent);
    }

}
