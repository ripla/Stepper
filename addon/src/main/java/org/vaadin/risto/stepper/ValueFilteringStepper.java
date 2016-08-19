package org.vaadin.risto.stepper;

/**
 * Enable or disable client-side value filtering. Value filtering disallows the
 * user from entering characters that would make the content invalid.
 *
 * This feature is experimental and may change or be completely removed in
 * future versions.
 */
public interface ValueFilteringStepper {
    /**
     * @param enableValueFiltering
     *            true to enable value filtering
     */
    void setValueFiltering(boolean enableValueFiltering);

    /**
     * @return true if value filtering is enabled
     */
    boolean isValueFiltering();
}
