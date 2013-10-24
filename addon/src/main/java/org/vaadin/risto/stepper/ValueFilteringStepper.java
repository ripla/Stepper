package org.vaadin.risto.stepper;

public interface ValueFilteringStepper {
    /**
     * Enable or disable client-side value filtering. Value filtering disallows
     * the user from entering characters that would make the content invalid.
     * 
     * This feature is experimental and may change or be completely removed in
     * future versions.
     * 
     * @param enableValueFiltering
     */
    void setValueFiltering(boolean enableValueFiltering);

    boolean isValueFiltering();
}
