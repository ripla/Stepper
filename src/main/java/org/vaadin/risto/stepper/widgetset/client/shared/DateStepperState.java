package org.vaadin.risto.stepper.widgetset.client.shared;


public class DateStepperState extends AbstractStepperState {

    private static final long serialVersionUID = 4107715154084103561L;

    private String dateStep;
    private String locale;

    public String getDateStep() {
        return dateStep;
    }

    public void setDateStep(String dateStep) {
        this.dateStep = dateStep;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }
}