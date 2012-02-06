package org.vaadin.risto.stepper.widgetset.client.ui;

import org.vaadin.risto.stepper.widgetset.client.ui.helpers.UpDownControls;
import org.vaadin.risto.stepper.widgetset.client.ui.helpers.UpDownTextBox;
import org.vaadin.risto.stepper.widgetset.client.ui.helpers.ValueUpdateTimer;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.vaadin.terminal.gwt.client.ApplicationConnection;
import com.vaadin.terminal.gwt.client.Paintable;
import com.vaadin.terminal.gwt.client.UIDL;

/**
 * 
 * @author Risto Yrjänä / Vaadin Ltd.
 * 
 */
public abstract class VAbstractStepper extends FlowPanel implements Paintable,
        ValueChangeHandler<String> {

    /** Set the CSS class name to allow styling. */
    public static final String CLASSNAME = "v-stepper";

    public static final String ATTR_VALUE = "value";

    public static final String ATTR_VALUERANGE = "valuerange";

    public static final String ATTR_MANUALINPUT = "manualinput";

    public static final String ATTR_MOUSE_WHEEL_ENABLED = "mouseWheelEnabled";

    public static final int valueRepeatDelay = 150;

    /** Component identifier in UIDL communications. */
    protected String uidlId;

    /** Reference to the server connection object. */
    protected ApplicationConnection client;

    protected final TextBox textBox;

    protected final UpDownControls upDownControls;

    protected final int updateDelay = 300;

    protected final ValueUpdateTimer valueUpdateTimer;

    protected boolean immediate;

    protected boolean timerHasChangedValue;

    protected boolean isDisabled;

    protected boolean isReadonly;

    protected boolean isManualInputAllowed;

    protected boolean mouseWheelEnabled;

    /**
     * The constructor should first call super() to initialize the component and
     * then handle any initialization relevant to Vaadin.
     */
    public VAbstractStepper() {

        // This method call of the Paintable interface sets the component
        // style name in DOM tree
        setStyleName(CLASSNAME);

        textBox = new UpDownTextBox(this);
        upDownControls = new UpDownControls(this);

        add(textBox);
        add(upDownControls);

        valueUpdateTimer = new ValueUpdateTimer(this);

        textBox.addValueChangeHandler(this);

    }

    public void updateFromUIDL(UIDL uidl, ApplicationConnection client) {
        if (!uidl.getBooleanAttribute("cached")) {
            valueUpdateTimer.cancel();
        }

        // This call should be made first. Ensure correct implementation,
        // and let the containing layout manage caption, etc.
        if (client.updateComponent(this, uidl, true)) {
            return;
        }

        // Save reference to server connection object to be able to send
        // user interaction later
        this.client = client;

        // Save the UIDL identifier for the component
        uidlId = uidl.getId();

        immediate = uidl.getBooleanAttribute("immediate");

        isDisabled = uidl.getBooleanAttribute("disabled");
        isReadonly = uidl.getBooleanAttribute("readonly");

        isManualInputAllowed = uidl.getBooleanAttribute(ATTR_MANUALINPUT);

        mouseWheelEnabled = uidl.getBooleanAttribute(ATTR_MOUSE_WHEEL_ENABLED);

        if (isDisabled || isReadonly) {
            valueUpdateTimer.cancel();
        }
        textBox.setReadOnly(isDisabled || isReadonly || !isManualInputAllowed);

        if (uidl.getAttributeNames().contains(ATTR_VALUERANGE)) {
            String[] valueRange = uidl.getStringArrayAttribute(ATTR_VALUERANGE);
            setMinValue(parseStringValue(valueRange[0]));
            setMaxValue(parseStringValue(valueRange[1]));
        }

        String value = uidl.getStringVariable(ATTR_VALUE);
        textBox.setValue(value);
    }

    @Override
    public void setWidth(String width) {
        super.setWidth(width);
        textBox.setWidth(width);
    }

    @Override
    public void setHeight(String height) {
        super.setHeight(height);
        textBox.setHeight(height);
    }

    /**
     * Calculate the string value of <code>current value + 1</code>
     * 
     * @param startValue
     * @return
     * @throws Exception
     */
    protected abstract String getIncreasedValue(String startValue)
            throws Exception;

    /**
     * Calculate the string value of <code>current value - 1</code>
     * 
     * @param startValue
     * @return
     * @throws Exception
     */
    protected abstract String getDecreasedValue(String startValue)
            throws Exception;

    /**
     * Check if the given value is valid instance of this type. If this returns
     * true, {@link #getIncreasedValue(String)} and
     * {@link #getDecreasedValue(String)} must be able to compute a result from
     * this value.
     * 
     * @param value
     * @return
     */
    protected abstract boolean isValidForType(String value);

    /**
     * Check if the given value is a valid, increased value. The given string is
     * guaranteed to be valid for this type.
     * 
     * @param value
     * @return true is the given value is a valid value in respect to the
     *         maximum value.
     */
    protected abstract boolean isSmallerThanMax(String value);

    /**
     * Check if the given value is a valid, decreased value. The given string is
     * guaranteed to be valid for this type.
     * 
     * @param value
     * @return true is the given value is a valid value in respect to the
     *         minumum value.
     */
    protected abstract boolean isLargerThanMin(String value);

    /**
     * Set the maximum possible value for this stepper. For
     * {@link #isValidForRange(String)}
     * 
     * @param value
     */
    protected abstract void setMaxValue(Object value);

    /**
     * Set the minimum possible value for this stepper. For
     * {@link #isValidForRange(String)}
     * 
     * @param value
     */
    protected abstract void setMinValue(Object value);

    /**
     * Parse the given String value. Used for setting the maximum and minimum .
     * values. Should return null on an empty string.
     * 
     * @param value
     * @return
     */
    protected abstract Object parseStringValue(String value);

    /**
     * Increase the value of the field. Value is always checked for validity
     * first.
     */
    public void increaseValue() {
        String oldValue = textBox.getValue();

        if (isChangeable() && isValidForType(oldValue)) {
            try {
                String newValue = getIncreasedValue(oldValue);
                if (isSmallerThanMax(newValue)) {
                    textBox.setValue(newValue);

                    valueUpdateTimer.schedule(updateDelay);
                    valueUpdateTimer.setValue(newValue);
                }
            } catch (Exception e) {
                valueUpdateTimer.cancel();
                GWT.log("Exception when increasing value", e);
            }
        }
    }

    /**
     * Decrease the value of the field. Value is always checked for validity
     * first.
     */
    public void decreaseValue() {
        String oldValue = textBox.getValue();

        if (isChangeable() && isValidForType(oldValue)) {
            try {
                String newValue = getDecreasedValue(oldValue);
                if (isLargerThanMin(newValue)) {
                    textBox.setValue(newValue);

                    valueUpdateTimer.schedule(updateDelay);
                    valueUpdateTimer.setValue(newValue);
                }
            } catch (Exception e) {
                valueUpdateTimer.cancel();
                GWT.log("Exception when decreasing value", e);
            }
        }

    }

    /**
     * Set the value to the UI.
     * 
     * @param newValue
     */
    protected void setValue(String newValue) {
        textBox.setValue(newValue);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.google.gwt.event.logical.shared.ValueChangeHandler#onValueChange(
     * com.google.gwt.event.logical.shared.ValueChangeEvent)
     */
    public void updateValueToServer(String value) {
        client.updateVariable(uidlId, ATTR_VALUE, value, immediate);
    }

    public void onValueChange(ValueChangeEvent<String> event) {
        String value = event.getValue();
        if (value != null && isValidForType(value)) {
            valueUpdateTimer.cancel();
            updateValueToServer(value);
        }
    }

    public void setTimerHasChangedValue(boolean timerHasChangedValue) {
        this.timerHasChangedValue = timerHasChangedValue;
    }

    public boolean isTimerHasChangedValue() {
        return timerHasChangedValue;
    }

    public boolean canChangeFromTextBox() {
        return !isDisabled && !isReadonly && isManualInputAllowed;

    }

    public boolean isChangeable() {
        return !isDisabled && !isReadonly;
    }

    public boolean isMouseWheelEnabled() {
        return mouseWheelEnabled;
    }
}
