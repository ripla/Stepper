package org.vaadin.risto.stepper.client.shared;

public class DateStepperState extends AbstractStepperState {

    private static final long serialVersionUID = 4107715154084103561L;

    private DateStepperField dateStep;
    private String dateFormat;

    public DateStepperField getDateStep() {
        return dateStep;
    }

    public void setDateStep(DateStepperField dateStep) {
        this.dateStep = dateStep;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }
}