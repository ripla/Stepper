package org.vaadin.risto.stepper;

import com.vaadin.ui.Field;

/**
 * Field that allows stepping through a discrete range values.
 * 
 * 
 * @author Risto Yrjänä / Vaadin }>
 * @param <T>
 *            the type of the value in the field
 * @param <S>
 *            the type of the step amount values
 */
public interface Stepper<T, S> extends Field<T> {

    void setManualInputAllowed(boolean isManualInputAllowed);

    /**
     * If manual input is allowed, the user can change the values with both the
     * controls and the textfield. If not allowed, only the controls change the
     * value.
     * 
     * @return
     */
    boolean isManualInputAllowed();

    void setMouseWheelEnabled(boolean isMouseWheelEnabled);

    /**
     * If you want (or don't want) the control to handle mouse wheel scroll
     * events, set this accordingly. Default is true, that is, mouse wheel
     * events will be handled.
     * 
     * @param isMouseWheelEnabled
     *            true to handle the events (the default), false otherwise.
     * @author colinf
     */
    boolean isMouseWheelEnabled();

    /**
     * If invalid values are allowed, the client sends all manually typed values
     * to the server, regardless of whether they are valid or not. The use-case
     * is to allow the server to perform validation and show validation
     * messages. Note that the Stepper controls still enforces the limits even
     * if invalid values are otherwise allowed.
     * 
     * @return
     */
    boolean isInvalidValuesAllowed();

    void setInvalidValuesAllowed(boolean invalidValuesAllowed);

    /**
     * Set the amount for a single step when the the value is increased /
     * decreased
     * 
     * @param amount
     */
    void setStepAmount(S amount);

    S getStepAmount();

    /**
     * Set the maximum value for this field.
     * 
     * @param maxValue
     */
    void setMaxValue(T maxValue);

    /**
     * Set the minumum value for this field.
     * 
     * @param minValue
     */
    void setMinValue(T minValue);

    T getMaxValue();

    T getMinValue();
}
