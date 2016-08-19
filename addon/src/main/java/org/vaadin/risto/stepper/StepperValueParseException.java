package org.vaadin.risto.stepper;

/**
 * Exception used when the server-side cannot parse the value from the client.
 * 
 * @author Risto Yrjänä / Vaadin
 * 
 */
public class StepperValueParseException extends Exception {

    private static final long serialVersionUID = -8862204924443733769L;

    public StepperValueParseException(Exception e) {
        super(e);
    }

}
