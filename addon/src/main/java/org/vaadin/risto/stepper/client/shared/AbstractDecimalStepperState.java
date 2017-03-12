package org.vaadin.risto.stepper.client.shared;

/**
 * Abstract shared state for all Stepper implementations
 *
 * @author Risto Yrjänä / Vaadin
 */
public class AbstractDecimalStepperState extends AbstractStepperState {
    public int numberOfDecimals;
    public char decimalSeparator;
    public boolean valueFiltering = false;
}
