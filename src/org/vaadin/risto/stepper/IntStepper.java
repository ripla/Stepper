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
 * Supports values of type Integer. Default value is 0.
 * </p>
 * 
 * @author Risto Yrjänä / Vaadin Ltd.
 * 
 */
@ClientWidget(org.vaadin.risto.stepper.widgetset.client.ui.VIntStepper.class)
public class IntStepper extends AbstractStepper {

    private static final long serialVersionUID = 1365274510273965118L;

    private Integer stepAmount = 1;

    private Integer maxValue;

    private Integer minValue;

    public IntStepper() {
        setValue(0);
    }

    public IntStepper(String caption) {
        this();
        setCaption(caption);
    }

    @Override
    public void paintDetails(PaintTarget target) throws PaintException {
        target.addAttribute("stepAmount", stepAmount);
    }

    @Override
    public void setStepAmount(Object amount) {
        if (!(amount instanceof Integer)) {
            throw new IllegalArgumentException(
                    "Step amount must be an instance of Integer");
        }
        stepAmount = (Integer) amount;
        requestRepaint();

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.vaadin.ui.AbstractField#getType()
     */
    @Override
    public Class<?> getType() {
        return Integer.class;
    }

    @Override
    public void setMaxValue(Object maxValue) {
        if (maxValue != null && !(maxValue instanceof Integer)) {
            throw new IllegalArgumentException(
                    "Max value must be an instance of Integer");
        }
        this.maxValue = (Integer) maxValue;
        requestRepaint();

    }

    @Override
    public void setMinValue(Object minValue) {
        if (minValue != null && !(minValue instanceof Integer)) {
            throw new IllegalArgumentException(
                    "Min value must be an instance of Integer");
        }
        this.minValue = (Integer) minValue;
        requestRepaint();

    }

    @Override
    public Integer getMaxValue() {
        return maxValue;
    }

    @Override
    public Integer getMinValue() {
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
        int intValue = (Integer) value;
        if (getMaxValue() != null && intValue > getMaxValue()) {
            return false;
        }

        if (getMinValue() != null && intValue < getMinValue()) {
            return false;
        }

        return true;
    }

    @Override
    protected Integer parseStringValue(String value) throws ParseException {
        if (value == null || "".equals(value)) {
            return null;
        }

        return Integer.parseInt(value);
    }
}
