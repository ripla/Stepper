package org.vaadin.risto.stepper;

import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Element;
import org.vaadin.risto.stepper.client.shared.AbstractStepperState;
import org.vaadin.risto.stepper.client.shared.StepperRpc;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Resource;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.declarative.DesignAttributeHandler;
import com.vaadin.ui.declarative.DesignContext;

public abstract class AbstractStepper<T, S> extends AbstractField<T>
        implements Stepper<T, S> {

    private T minValue;
    private T maxValue;
    private S stepAmount;
    private T value;

    public AbstractStepper() {
        registerRpc(new StepperRpc() {

            @Override
            public void valueChange(String value) {
                if (isEnabled() && !isReadOnly()) {

                    try {
                        T parsedValue = parseStringValue(value);
                        if (isInvalidValuesAllowed() ||
                                (parsedValue == null && isNullValueAllowed()) ||
                                (parsedValue != null &&
                                         isValidForRange(parsedValue))) {
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

        setIncreaseIcon(VaadinIcons.CHEVRON_UP);
        setDecreaseIcon(VaadinIcons.CHEVRON_DOWN);
    }

    @Override
    protected AbstractStepperState getState() {
        return (AbstractStepperState) super.getState();
    }

    @Override
    public boolean isManualInputAllowed() {
        return getState().isManualInputAllowed;
    }

    @Override
    public void setManualInputAllowed(boolean isManualInputAllowed) {
        getState().isManualInputAllowed = isManualInputAllowed;
    }

    @Override
    public boolean isMouseWheelEnabled() {
        return getState().isMouseWheelEnabled;
    }

    @Override
    public void setMouseWheelEnabled(boolean isMouseWheelEnabled) {
        getState().isMouseWheelEnabled = isMouseWheelEnabled;
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
    public S getStepAmount() {
        return stepAmount;
    }

    @Override
    public void setStepAmount(S amount) {
        this.stepAmount = amount;
        markAsDirty();
    }

    @Override
    public T getMaxValue() {
        return maxValue;
    }

    @Override
    public void setMaxValue(T maxValue) {
        this.maxValue = maxValue;
        markAsDirty();
    }

    @Override
    public T getMinValue() {
        return minValue;
    }

    @Override
    public void setMinValue(T minValue) {
        this.minValue = minValue;
        markAsDirty();
    }

    public Resource getDecreaseIcon() {
        return getResource(getState().DECREASE_ICON_KEY);
    }

    public void setDecreaseIcon(Resource icon) {
        if (icon == null) {
            throw new IllegalArgumentException("Icon cannot be null");
        }

        setResource(getState().DECREASE_ICON_KEY, icon);
        markAsDirty();
    }

    public Resource getIncreaseIcon() {
        return getResource(getState().INCREASE_ICON_KEY);
    }

    public void setIncreaseIcon(Resource icon) {
        if (icon == null) {
            throw new IllegalArgumentException("Icon cannot be null");
        }
        setResource(getState().INCREASE_ICON_KEY, icon);
        markAsDirty();
    }

    @Override
    public void readDesign(Element design, DesignContext designContext) {
        super.readDesign(design, designContext);

        Attributes attr = design.attributes();
        if (design.hasAttr("max-value")) {
            setMaxValue(DesignAttributeHandler
                    .readAttribute("max-value", attr, getValueType()));
        }

        if (design.hasAttr("min-value")) {
            setMinValue(DesignAttributeHandler
                    .readAttribute("min-value", attr, getValueType()));
        }

        if (design.hasAttr("step-amount")) {
            setStepAmount(DesignAttributeHandler
                    .readAttribute("step-amount", attr, getStepType()));
        }
    }

    @Override
    public T getValue() {
        return value;
    }

    @Override
    protected void doSetValue(T value) {
        this.value = value;
    }

    /**
     * Check if the given value is a valid value for the current range set by
     * {@link #setMaxValue(Object)} and {@link #setMinValue(Object)}
     *
     * @param value
     *         the value to check. Null values are always valid.
     * @return true if the value is valid
     */
    protected abstract boolean isValidForRange(T value);

    /**
     * Parse a String value from the client to an instance of T
     *
     * @param value
     *         the value from client
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

    /**
     * Returns the value type of the Stepper.
     *
     * @return the type of the value in this field
     */
    protected abstract Class<T> getValueType();
}
