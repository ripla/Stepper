package org.vaadin.risto.stepper;

import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Element;
import org.vaadin.risto.stepper.client.shared.AbstractStepperState;
import org.vaadin.risto.stepper.client.shared.StepperRpc;

import com.vaadin.server.FontAwesome;
import com.vaadin.server.Resource;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.declarative.DesignAttributeHandler;
import com.vaadin.ui.declarative.DesignContext;

public abstract class AbstractStepper<T, S> extends AbstractField<T>
        implements Stepper<T, S> {

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
                                || (parsedValue == null && isNullValueAllowed())
                                || (parsedValue != null
                                        && isValidForRange(parsedValue))) {
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

        setIncreaseIcon(FontAwesome.SORT_UP);
        setDecreaseIcon(FontAwesome.SORT_DOWN);
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

    public void setDecreaseIcon(Resource icon) {
        if (icon == null) {
            throw new IllegalArgumentException("Icon cannot be null");
        }

        setResource(getState().DECREASE_ICON_KEY, icon);
        markAsDirty();
    }

    public void setIncreaseIcon(Resource icon) {
        if (icon == null) {
            throw new IllegalArgumentException("Icon cannot be null");
        }
        setResource(getState().INCREASE_ICON_KEY, icon);
        markAsDirty();
    }

    public Resource getDecreaseIcon() {
        return getResource(getState().DECREASE_ICON_KEY);
    }

    public Resource getIncreaseIcon() {
        return getResource(getState().INCREASE_ICON_KEY);
    }

    @Override
    public void readDesign(Element design, DesignContext designContext) {
        super.readDesign(design, designContext);

        Attributes attr = design.attributes();
        if (design.hasAttr("max-value")) {
            setMaxValue(DesignAttributeHandler.readAttribute("max-value", attr,
                    getType()));
        }

        if (design.hasAttr("min-value")) {
            setMinValue(DesignAttributeHandler.readAttribute("min-value", attr,
                    getType()));
        }

        if (design.hasAttr("step-amount")) {
            setStepAmount(DesignAttributeHandler.readAttribute("step-amount",
                    attr, getStepType()));
        }
    }

    /**
     * Check if the given value is a valid value for the current range set by
     * {@link #setMaxValue(Object)} and {@link #setMinValue(Object)}
     *
     * @param value
     *            the value to check. Null values are always valid.
     * @return true if the value is valid
     */
    protected abstract boolean isValidForRange(T value);

    /**
     * Parse a String value from the client to an instance of T
     *
     * @param value
     *            the value from client
     * @return value in the correct type
     * @throws StepperValueParseException
     */
    protected abstract T parseStringValue(String value)
            throws StepperValueParseException;

    /**
     * Returns the step type of the Stepper. The methods
     * {@link #getStepAmount()} and {@link #setStepAmount(Object)} must be
     * compatible with this type.
     *
     * @return the step type of the Stepper
     */
    protected abstract Class<S> getStepType();
}
