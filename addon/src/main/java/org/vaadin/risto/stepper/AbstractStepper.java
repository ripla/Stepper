package org.vaadin.risto.stepper;

import java.text.ParseException;

import org.json.JSONException;
import org.vaadin.risto.stepper.widgetset.client.shared.AbstractStepperState;
import org.vaadin.risto.stepper.widgetset.client.shared.StepperRpc;

import com.vaadin.ui.AbstractField;

/**
 * Abstract base class for all stepper types. Handles value communication
 * between the server and client.
 * 
 * 
 * @author Risto Yrjänä / Vaadin Ltd.
 * @param <T>
 *            the type of the value in the field
 * @param <S>
 *            the type of the step amount values
 */
public abstract class AbstractStepper<T, S> extends AbstractField<T> {

    private static final long serialVersionUID = 4680365780881009306L;

    private T minValue;
    private T maxValue;
    private S stepAmount;

    public AbstractStepper() {
        registerRpc(new StepperRpc() {

            private static final long serialVersionUID = 6754152456843669358L;

            @Override
            public void valueChange(String value) {
                if (isEnabled() && !isReadOnly()) {

                    /*
                     * Client side updates the state before sending the event so
                     * we need to make sure the cached state is updated to match
                     * the client. If we do not do this, a reverting setValue()
                     * call in a listener will not cause the new state to be
                     * sent to the client. This is a problem with Vaadin state
                     * caching.
                     * 
                     * See e.g. http://dev.vaadin.com/ticket/12133
                     */
                    try {
                        getUI().getConnectorTracker()
                                .getDiffState(AbstractStepper.this)
                                .put("value", value);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                    try {
                        T parsedValue = parseStringValue(value);
                        if (isInvalidValuesAllowed()
                                || isValidForRange(parsedValue)) {
                            setValue(parsedValue, true);
                        }
                    } catch (ParseException e) {
                        handleParseException(e);
                    }
                }
            }
        });
    }

    @Override
    protected AbstractStepperState getState() {
        return (AbstractStepperState) super.getState();
    }

    public void setManualInputAllowed(boolean isManualInputAllowed) {
        getState().isManualInputAllowed = isManualInputAllowed;
    }

    /**
     * If manual input is allowed, the user can change the values with both the
     * controls and the textfield. If not allowed, only the controls change the
     * value.
     * 
     * @return
     */
    public boolean isManualInputAllowed() {
        return getState().isManualInputAllowed;
    }

    /**
     * If you want (or don't want) the control to handle mouse wheel scroll
     * events, set this accordingly. Default is true, that is, mouse wheel
     * events will be handled.
     * 
     * @param isMouseWheelEnabled
     *            true to handle the events (the default), false otherwise.
     * @author colinf
     */
    public void setMouseWheelEnabled(boolean isMouseWheelEnabled) {
        getState().isMouseWheelEnabled = isMouseWheelEnabled;
    }

    public boolean isMouseWheelEnabled() {
        return getState().isMouseWheelEnabled;
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
        return getState().isInvalidValuesAllowed;
    }

    public void setInvalidValuesAllowed(boolean invalidValuesAllowed) {
        getState().isInvalidValuesAllowed = invalidValuesAllowed;
    }

    @Override
    public void beforeClientResponse(boolean initial) {
        super.beforeClientResponse(initial);

        // update values that are parsed for the client
        getState().value = parseValueToString(getValue());
        getState().minValue = parseValueToString(getMinValue());
        getState().maxValue = parseValueToString(getMaxValue());
        getState().stepAmount = parseStepAmountToString(getStepAmount());
    }

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
        markAsDirty();
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
        markAsDirty();
    }

    /**
     * Set the minumum value for this field.
     * 
     * @param maxValue
     */
    public void setMinValue(T minValue) {
        this.minValue = minValue;
        markAsDirty();
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
