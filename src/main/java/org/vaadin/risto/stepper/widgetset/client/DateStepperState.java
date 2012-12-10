package org.vaadin.risto.stepper.widgetset.client;


public class DateStepperState extends AbstractStepperState {

    private static final long serialVersionUID = 4107715154084103561L;

    private String dateStepField;
    private String locale;

    public String getDateStep() {
        return dateStepField;
    }

    public void setDateStep(String dateStep) {
        this.dateStepField = dateStep;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }
}