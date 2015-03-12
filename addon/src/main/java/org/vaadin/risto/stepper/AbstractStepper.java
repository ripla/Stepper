package org.vaadin.risto.stepper;

import java.text.ParseException;

import org.vaadin.risto.stepper.widgetset.client.shared.AbstractStepperState;
import org.vaadin.risto.stepper.widgetset.client.shared.StepperRpc;

import com.vaadin.ui.AbstractField;

public abstract class AbstractStepper<T, S> extends AbstractField<T> implements
        Stepper<T, S> {

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

                    try {
                        T parsedValue = parseStringValue(value);
                        if (isInvalidValuesAllowed()
                                || (parsedValue==null && isNullValueAllowed()) || (parsedValue!=null && isValidForRange(parsedValue))) {
                            setValue(parsedValue, true);
                        }

                        /*
                         * Client side updates the state before sending the
                         * event so we need to make sure the cached state is
                         * updated to match the client. If we do not do this, a
                         * reverting setValue() call in a listener will not
                         * cause the new state to be sent to the client. This is
                         * a problem with Vaadin state caching.
                         * 
                         * See e.g. http://dev.vaadin.com/ticket/12133
                         */
                            getUI().getConnectorTracker()
                                    .getDiffState(AbstractStepper.this)
                                    .put("value", value);

                    } catch (StepperValueParseException e) {
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

    @Override
    public void setManualInputAllowed(boolean isManualInputAllowed) {
        getState().isManualInputAllowed = isManualInputAllowed;
    }

    @Override
    public boolean isManualInputAllowed() {
        return getState().isManualInputAllowed;
    }

    @Override
    public void setMouseWheelEnabled(boolean isMouseWheelEnabled) {
        getState().isMouseWheelEnabled = isMouseWheelEnabled;
    }

    @Override
    public boolean isMouseWheelEnabled() {
        return getState().isMouseWheelEnabled;
    }

    @Override
    public boolean isInvalidValuesAllowed() {
        return getState().isInvalidValuesAllowed;
    }

    @Override
    public void setInvalidValuesAllowed(boolean invalidValuesAllowed) {
        getState().isInvalidValuesAllowed = invalidValuesAllowed;
    }
    
    @Override
    public boolean isNullValueAllowed() {
    	return getState().isNullValueAllowed;
    }
    
    @Override
    public void setNullValueAllowed(boolean nullValueAllowed) {
    	getState().isNullValueAllowed = nullValueAllowed;
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

    /**
     * Called whenever the parseStringValue throws an exception
     * 
     * @param e
     */
    protected void handleParseException(StepperValueParseException e) {
        // NOOP
        // children can override if necessary
    }

    protected String parseValueToString(T value) {
        return value != null ? value.toString() : "";
    }

    protected String parseStepAmountToString(S stepAmount) {
        return stepAmount != null ? stepAmount.toString() : "";
    }

    @Override
    public void setStepAmount(S amount) {
        this.stepAmount = amount;
        markAsDirty();
    }

    @Override
    public S getStepAmount() {
        return stepAmount;
    }

    @Override
    public void setMaxValue(T maxValue) {
        this.maxValue = maxValue;
        markAsDirty();
    }

    @Override
    public void setMinValue(T minValue) {
        this.minValue = minValue;
        markAsDirty();
    }
    
    @Override
    public T getMaxValue() {
        return maxValue;
    }

    @Override
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
    protected abstract T parseStringValue(String value)
            throws StepperValueParseException;

}
