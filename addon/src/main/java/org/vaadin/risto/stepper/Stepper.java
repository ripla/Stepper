package org.vaadin.risto.stepper;

import com.vaadin.data.HasValue;
import com.vaadin.server.Resource;
import com.vaadin.ui.Component;

/**
 * Field that allows stepping through a discrete range values.
 *
 *
 * @author Risto Yrjänä / Vaadin
 * @param <T>
 *            the type of the value in the field
 * @param <S>
 *            the type of the step amount values
 */
public interface Stepper<T, S> extends HasValue<T>, Component {

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
     * If null values are allowed, clearing the text box sets the field to null
     * (manual input has to be enabled for this to work). Otherwise, it is
     * reverted to the last valid value. In contract to
     * {@link #isInvalidValuesAllowed()}, still peforms range check on non-null
     * values. The default is false.
     *
     * @return
     */
    boolean isNullValueAllowed();

    void setNullValueAllowed(boolean nullValueAllowed);

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

    /**
     * Set the {@link Resource} to use as the icon for increasing the value.
     *
     * @param icon
     * @see Resource
     * @see com.vaadin.server.FontIcon
     */
    void setIncreaseIcon(Resource icon);

    /**
     * Set the {@link Resource} to use as the icon for decreasing the value.
     *
     * @param icon
     * @see Resource
     * @see com.vaadin.server.FontIcon
     */
    void setDecreaseIcon(Resource icon);

    Resource getIncreaseIcon();

    Resource getDecreaseIcon();
}
