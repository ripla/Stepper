package org.vaadin.risto.stepper;

import java.text.ParseException;
import java.util.Map;

import org.vaadin.risto.stepper.widgetset.client.ui.VAbstractStepper;

import com.vaadin.terminal.PaintException;
import com.vaadin.terminal.PaintTarget;
import com.vaadin.ui.AbstractField;

/**
 * Abstract base class for all stepper types. Handles value communication
 * between the server and client.
 * 
 * 
 * @author Risto Yrjänä / Vaadin Ltd.
 * 
 */
public abstract class AbstractStepper extends AbstractField {

    private static final long serialVersionUID = 4680365780881009306L;
    private boolean isManualInputAllowed = true;
    private boolean mouseWheelEnabled = true;

    /**
     * @see #isManualInputAllowed
     * @param isManualInputAllowed
     */
    public void setManualInputAllowed(boolean isManualInputAllowed) {
        this.isManualInputAllowed = isManualInputAllowed;
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
        return isManualInputAllowed;
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
    public void setMouseWheelEnabled(boolean mouseWheelEnabled) {
        this.mouseWheelEnabled = mouseWheelEnabled;
        requestRepaint();
    }

    public boolean isMouseWheelEnabled() {
        return mouseWheelEnabled;
    }

    @Override
    public void paintContent(PaintTarget target) throws PaintException {
        super.paintContent(target);

        target.addVariable(this, VAbstractStepper.ATTR_VALUE, getPaintValue());

        target.addAttribute(VAbstractStepper.ATTR_VALUERANGE,
                getValueRangeAsArray());

        target.addAttribute(VAbstractStepper.ATTR_MANUALINPUT,
                isManualInputAllowed());

        target.addAttribute(VAbstractStepper.ATTR_MOUSE_WHEEL_ENABLED,
                isMouseWheelEnabled());

        paintDetails(target);
    }

    @Override
    public void changeVariables(Object source, Map<String, Object> variables) {
        super.changeVariables(source, variables);

        if (isEnabled() && !isReadOnly() && variables.containsKey("value")) {
            try {
                Object parsedValue = parseStringValue((String) variables
                        .get("value"));
                if (isValidForRange(parsedValue)) {
                    setValue(parsedValue, true);
                }
            } catch (ParseException e) {
                // NOOP for now
            }
        }
    }

    /**
     * @return the current value as a non-null string
     */
    protected String getPaintValue() {
        return (getValue() != null) ? getValue().toString() : "";
    }

    /**
     * For painting additional details. Called after painting values and min/max
     * boundaries.
     * 
     * @param target
     * @throws PaintException
     */
    protected abstract void paintDetails(PaintTarget target)
            throws PaintException;

    /**
     * @return the min/max values as a String array
     */
    protected abstract String[] getValueRangeAsArray();

    /**
     * Set the amount for a single step when the the value is increased /
     * decreased
     * 
     * @param amount
     */
    public abstract void setStepAmount(Object amount);

    /**
     * Set the maximum value for this field.
     * 
     * @param maxValue
     */
    public abstract void setMaxValue(Object maxValue);

    /**
     * Set the minumum value for this field.
     * 
     * @param maxValue
     */
    public abstract void setMinValue(Object minValue);

    public abstract Object getMaxValue();

    public abstract Object getMinValue();

    /**
     * @param value
     * @return
     */
    protected abstract boolean isValidForRange(Object value);

    /**
     * Parse a String value from the client to a type corresponding to
     * {@link #getType()}
     * 
     * @param value
     *            the value from client
     * @return value in the correct type
     * @throws ParseException
     */
    protected abstract Object parseStringValue(String value)
            throws ParseException;
}
