/**
 * 
 */
package org.vaadin.risto.stepper;

import java.text.ParseException;

import com.vaadin.terminal.PaintException;
import com.vaadin.terminal.PaintTarget;
import com.vaadin.ui.ClientWidget;

/**
 * <p>
 * Field that allows stepping through values via given up/down controls.
 * Supports values of type Float. Default value is 0.
 * </p>
 * 
 * <p>
 * Note: float incrementation IS NOT completely accurate, as the values are
 * always rounded. The accuracy should be enough for most use cases.
 * </p>
 * 
 * @author Risto Yrjänä / Vaadin Ltd.
 * 
 */
@ClientWidget(org.vaadin.risto.stepper.widgetset.client.ui.VFloatStepper.class)
public class FloatStepper extends AbstractStepper {

    private static final long serialVersionUID = -5328027647865381265L;

    private float stepAmount = 1.0f;

    private int numberOfDecimals = 2;

    private Float maxValue;

    private Float minValue;

    public FloatStepper() {
        setValue(0);
    }

    public FloatStepper(String caption) {
        this();
        setCaption(caption);
    }

    @Override
    public void paintDetails(PaintTarget target) throws PaintException {
        target.addAttribute("stepAmount", stepAmount);

        target.addAttribute("numberOfDecimals", numberOfDecimals);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.vaadin.risto.stepper.AbstractStepper#setStepAmount(java.lang.Object)
     */
    @Override
    public void setStepAmount(Object amount) {
        if (!(amount instanceof Float)) {
            throw new IllegalArgumentException(
                    "Step amount must be an instance of Float");
        }

        stepAmount = (Float) amount;
        requestRepaint();
    }

    /**
     * Set the accuracy of the float representation.
     * 
     * @param numberOfDecimals
     */
    public void setNumberOfDecimals(int numberOfDecimals) {
        this.numberOfDecimals = numberOfDecimals;
        requestRepaint();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.vaadin.ui.AbstractField#getType()
     */
    @Override
    public Class<?> getType() {
        return Float.class;
    }

    @Override
    public void setMaxValue(Object maxValue) {
        if (maxValue != null && !(maxValue instanceof Float)) {
            throw new IllegalArgumentException(
                    "Max value must be an instance of Float");
        }
        this.maxValue = (Float) maxValue;
        requestRepaint();

    }

    @Override
    public void setMinValue(Object minValue) {
        if (minValue != null && !(minValue instanceof Float)) {
            throw new IllegalArgumentException(
                    "Min value must be an instance of Float");
        }
        this.minValue = (Float) minValue;
        requestRepaint();

    }

    @Override
    public Float getMaxValue() {
        return maxValue;
    }

    @Override
    public Float getMinValue() {
        return minValue;
    }

    @Override
    protected String[] getValueRangeAsArray() {
        String[] rangeArray = new String[2];
        if (getMinValue() != null) {
            rangeArray[0] = getMinValue().toString();
        } else {
            rangeArray[0] = "";
        }

        if (getMaxValue() != null) {
            rangeArray[1] = getMaxValue().toString();
        } else {
            rangeArray[1] = "";
        }

        return rangeArray;
    }

    @Override
    protected boolean isValidForRange(Object value) {
        float floatValue = (Float) value;
        if (getMaxValue() != null && floatValue > getMaxValue()) {
            return false;
        }

        if (getMinValue() != null && floatValue < getMinValue()) {
            return false;
        }

        return true;
    }

    @Override
    protected Float parseStringValue(String value) throws ParseException {
        if (value == null || "".equals(value)) {
            return null;
        }

        return Float.parseFloat(value);
    }
}
