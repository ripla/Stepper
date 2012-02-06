package org.vaadin.risto.stepper;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import org.vaadin.risto.stepper.widgetset.client.ui.VDateStepper;

import com.vaadin.terminal.PaintException;
import com.vaadin.terminal.PaintTarget;
import com.vaadin.ui.ClientWidget;

/**
 * <p>
 * Field that allows stepping through values via given up/down controls.
 * Supports values of type Date. The default value is today.
 * </p>
 * 
 * @author Risto Yrjänä / Vaadin Ltd.
 * 
 */
@ClientWidget(org.vaadin.risto.stepper.widgetset.client.ui.VDateStepper.class)
public class DateStepper extends AbstractStepper {

    private static final long serialVersionUID = 5238300195216371890L;

    private final Integer[] stepAmount;

    private Date minValue;

    private Date maxValue;

    public DateStepper() {
        stepAmount = new Integer[2];
        stepAmount[VDateStepper.DATEFIELDINDEX] = VDateStepper.DateStepField.DAY
                .ordinal();
        stepAmount[VDateStepper.DATESTEPINDEX] = 1;

        setValue(new Date());
    }

    public DateStepper(String caption) {
        this();
        setCaption(caption);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.vaadin.ui.AbstractField#getType()
     */
    @Override
    public Class<?> getType() {
        return Date.class;
    }

    @Override
    protected String getPaintValue() {
        Date internalValue = (Date) getValue();
        if (internalValue == null) {
            return super.getPaintValue();
        }
        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT,
                getLocale());

        return df.format(internalValue);
    }

    @Override
    public void paintDetails(PaintTarget target) throws PaintException {
        target.addAttribute("locale", getLocale().toString());

        target.addAttribute("stepAmount", stepAmount);
    }

    /**
     * Set the integer amount to change the value on one click.
     */
    @Override
    public void setStepAmount(Object amount) {
        if (!(amount instanceof Integer)) {
            throw new IllegalArgumentException(
                    "Step amount must be an instance of Integer");
        }

        stepAmount[VDateStepper.DATESTEPINDEX] = (Integer) amount;
        requestRepaint();

    }

    /**
     * Set the field that the stepper should step through. The field must be one
     * of the ones defined by
     * {@link org.vaadin.risto.stepper.widgetset.client.ui.VDateStepper.DateStepField
     * )}
     * 
     * @param field
     * @see org.vaadin.risto.stepper.widgetset.client.ui.VDateStepper.DateStepField
     */
    public void setStepField(VDateStepper.DateStepField field) {
        stepAmount[VDateStepper.DATEFIELDINDEX] = field.ordinal();
        requestRepaint();

    }

    @Override
    public void setMaxValue(Object maxValue) {
        if (maxValue != null && !(maxValue instanceof Date)) {
            throw new IllegalArgumentException(
                    "Max value must be an instance of Date");
        }
        if (maxValue != null) {
            this.maxValue = normalizeBoundaryDate((Date) maxValue);
        } else {
            this.maxValue = null;
        }

        requestRepaint();
    }

    @Override
    public void setMinValue(Object minValue) {
        if (minValue != null && !(minValue instanceof Date)) {
            throw new IllegalArgumentException(
                    "Min value must be an instance of Date");
        }

        if (minValue != null) {
            this.minValue = normalizeBoundaryDate((Date) minValue);
        } else {
            this.minValue = null;
        }

        requestRepaint();
    }

    @Override
    public Date getMaxValue() {
        return maxValue;
    }

    @Override
    public Date getMinValue() {
        return minValue;
    }

    @Override
    protected String[] getValueRangeAsArray() {
        String[] rangeArray = new String[2];
        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT,
                getLocale());

        if (getMinValue() != null) {
            rangeArray[0] = df.format(getMinValue());
        } else {
            rangeArray[0] = "";
        }

        if (getMaxValue() != null) {
            rangeArray[1] = df.format(getMaxValue());
        } else {
            rangeArray[1] = "";
        }

        return rangeArray;
    }

    @Override
    protected boolean isValidForRange(Object value) {
        if (value == null) {
            return true;
        }

        Date dateValue = (Date) value;
        Calendar valueCalendar = Calendar.getInstance(getLocale());
        valueCalendar.setTime(dateValue);
        Calendar compareCalendar = (Calendar) valueCalendar.clone();

        if (getMaxValue() != null) {
            compareCalendar.setTime(getMaxValue());
            if (valueCalendar.after(compareCalendar)) {
                return false;
            }
        }

        if (getMinValue() != null) {
            compareCalendar.setTime(getMinValue());
            if (valueCalendar.before(compareCalendar)) {
                return false;
            }
        }

        return true;
    }

    @Override
    protected Date parseStringValue(String value) throws ParseException {
        if (value == null || "".equals(value)) {
            return null;
        }

        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT,
                getLocale());
        return df.parse(value);
    }

    /**
     * Normalizes max and min dates by resetting hours, minutes etc.
     * 
     * @param boundaryDate
     * @return
     */
    private Date normalizeBoundaryDate(Date boundaryDate) {
        Calendar javaCalendar = Calendar.getInstance();
        javaCalendar.setTime(boundaryDate);

        javaCalendar.set(Calendar.MILLISECOND, 0);
        javaCalendar.set(Calendar.SECOND, 0);
        javaCalendar.set(Calendar.MINUTE, 0);
        javaCalendar.set(Calendar.HOUR, 0);
        javaCalendar.set(Calendar.HOUR_OF_DAY, 0);

        return javaCalendar.getTime();
    }
}
