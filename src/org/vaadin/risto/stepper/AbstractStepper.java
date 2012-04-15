package org.vaadin.risto.stepper;

import java.text.ParseException;

import org.vaadin.risto.stepper.widgetset.client.AbstractStepperState;

import com.vaadin.ui.AbstractField;

/**
 * Abstract base class for all stepper types. Handles value communication
 * between the server and client.
 * 
 * 
 * @author Risto Yrjänä / Vaadin Ltd.
 * 
 */
public abstract class AbstractStepper<T, S> extends AbstractField<T> {

    private static final long serialVersionUID = 4680365780881009306L;

    private T minValue;
    private T maxValue;
    private S stepAmount;

    @Override
    public AbstractStepperState getState() {
        return (AbstractStepperState) super.getState();
    }

    public void setManualInputAllowed(boolean isManualInputAllowed) {
        getState().setManualInputAllowed(isManualInputAllowed);
        requestRepaint();
    }

    /**
     * If manual input is allowed, the user can change the values with both the
     * controls and the textfield. If not allowed, only the controls change the
     * value.
     * 
     * @return
     */
    public boolean isManualInputAllowed() {
        return getState().isManualInputAllowed();
    }

    public void setMouseWheelEnabled(boolean mouseWheelEnabled) {
        getState().setMouseWheelEnabled(mouseWheelEnabled);
        requestRepaint();
    }

    /**
     * If you want (or don't want) the control to handle mouse wheel scroll
     * events, set this accordingly. Default is true, that is, mouse wheel
     * events will be handled.
     * 
     * @param mouseWheelEnabled
     *            true to handle the events (the default), false otherwise.
     * @author colinf
     */
    public boolean isMouseWheelEnabled() {
        return getState().isMouseWheelEnabled();
    }

    /**
     * If invalid values are allowed, the client sends all manually typed values
     * to the server, regardless of whether they are valid or not. The use-case
     * is to allow the server to perform validation and show validation
     * messages. Note that the Stepper controls still enforces the limits even
     * if invalid values are otherwise allowed.
     * 
     * @return
     */
    public boolean isInvalidValuesAllowed() {
        return getState().isInvalidValuesAllowed();
    }

    public void setInvalidValuesAllowed(boolean invalidValuesAllowed) {
        getState().setInvalidValuesAllowed(invalidValuesAllowed);
        requestRepaint();
    }

    @Override
    public void updateState() {
        super.updateState();
        getState().setFieldValue(parseValueToString(getValue()));
        getState().setMinValue(parseValueToString(getMinValue()));
        getState().setMaxValue(parseValueToString(getMaxValue()));
        getState().setStepAmount(parseStepAmountToString(getStepAmount()));
    }

    // TODO
    // @Override
    // public void changeVariables(Object source, Map<String, Object> variables)
    // {
    // super.changeVariables(source, variables);
    //
    // if (isEnabled() && !isReadOnly() && variables.containsKey("value")) {
    // try {
    // Object parsedValue = parseStringValue((String) variables
    // .get("value"));
    // if (areInvalidValuesAllowed() || isValidForRange(parsedValue)) {
    // setValue(parsedValue, true);
    // }
    // } catch (ParseException e) {
    // handleParseException(e);
    // }
    // }
    // }

    protected void handleParseException(ParseException e) {
        // NOOP
        // children can override if necessary
    }

    protected String parseValueToString(T value) {
        return value != null ? value.toString() : "";
    }

    protected String parseStepAmountToString(S stepAmount) {
        return stepAmount != null ? stepAmount.toString() : "";
    }

    /**
     * Set the amount for a single step when the the value is increased /
     * decreased
     * 
     * @param amount
     */
    public void setStepAmount(S amount) {
        this.stepAmount = amount;
    }

    public S getStepAmount() {
        return stepAmount;
    }

    /**
     * Set the maximum value for this field.
     * 
     * @param maxValue
     */
    public void setMaxValue(T maxValue) {
        this.maxValue = maxValue;
    }

    /**
     * Set the minumum value for this field.
     * 
     * @param maxValue
     */
    public void setMinValue(T minValue) {
        this.minValue = minValue;
    }

    public T getMaxValue() {
        return maxValue;
    }

    public T getMinValue() {
        return minValue;
    }

    /**
     * @param value
     * @return
     */
    protected abstract boolean isValidForRange(T value);

    /**
     * Parse a String value from the client to a type corresponding to
     * {@link #getType()}
     * 
     * @param value
     *            the value from client
     * @return value in the correct type
     * @throws ParseException
     */
    protected abstract T parseStringValue(String value) throws ParseException;

}
