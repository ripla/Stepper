package org.vaadin.risto.stepper.widgetset.client.shared;

public class FloatStepperState extends AbstractStepperState {

    private static final long serialVersionUID = -8106857138367846695L;

    private Integer numberOfDecimals;

    public Integer getNumberOfDecimals() {
        return numberOfDecimals;
    }

    public void setNumberOfDecimals(Integer numberOfDecimals) {
        this.numberOfDecimals = numberOfDecimals;
    }
}
