package org.vaadin.risto.stepper;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.vaadin.risto.stepper.widgetset.client.DateStepperState;
import org.vaadin.risto.stepper.widgetset.client.ui.DateStepField;

/**
 * <p>
 * Field that allows stepping through values via given up/down controls.
 * Supports values of type Date. The default value is today.
 * </p>
 * 
 * @author Risto Yrjänä / Vaadin Ltd.
 * 
 */
public class DateStepper extends AbstractStepper<Date, Integer> {

    private static final long serialVersionUID = 5238300195216371890L;

    public DateStepper() {
        setStepAmount(1);
        setStepField(DateStepField.DAY);
        setValue(new Date());
    }

    public DateStepper(String caption) {
        this();
        setCaption(caption);
    }

    @Override
    public Class<Date> getType() {
        return Date.class;
    }

    @Override
    public DateStepperState getState() {
        return (DateStepperState) super.getState();
    }

    @Override
    public void setLocale(Locale locale) {
        getState().setLocale(locale.toString());
        super.setLocale(locale);
    }

    /**
     * Set the field that the stepper should step through. The field must be one
     * of the ones defined by
     * {@link org.vaadin.risto.stepper.widgetset.client.ui.DateStepField )}
     * 
     * @param field
     * @see org.vaadin.risto.stepper.widgetset.client.ui.DateStepField
     */
    public void setStepField(DateStepField field) {
        getState().setDateStep(field.name());
        requestRepaint();
    }

    @Override
    public void setMaxValue(Date maxValue) {
        super.setMaxValue(normalizeBoundaryDate(maxValue));
    }

    @Override
    public void setMinValue(Date minValue) {
        super.setMinValue(normalizeBoundaryDate(minValue));
    }

    @Override
    protected boolean isValidForRange(Date value) {
        if (value == null) {
            return true;
        }

        Date dateValue = value;
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
    protected Date normalizeBoundaryDate(Date boundaryDate) {
        if (boundaryDate == null) {
            return null;
        }

        Calendar javaCalendar = Calendar.getInstance(getLocale());
        javaCalendar.setTime(boundaryDate);

        javaCalendar.set(Calendar.MILLISECOND, 0);
        javaCalendar.set(Calendar.SECOND, 0);
        javaCalendar.set(Calendar.MINUTE, 0);
        javaCalendar.set(Calendar.HOUR, 0);
        javaCalendar.set(Calendar.HOUR_OF_DAY, 0);

        return javaCalendar.getTime();
    }

    @Override
    protected String parseValueToString(Date value) {
        if (value == null) {
            return super.parseValueToString(value);
        }
        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT,
                getLocale());

        return df.format(value);
    }
}
