package org.vaadin.risto.stepper;

/**
 * Exception used when the server-side cannot parse the value from the client.
 *
 * @author Risto Yrjänä / Vaadin
 *
 */
public class StepperValueParseException extends Exception {

    public StepperValueParseException(Exception e) {
        super(e);
    }

}
