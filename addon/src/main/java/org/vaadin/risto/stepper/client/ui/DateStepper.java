package org.vaadin.risto.stepper.client.ui;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.vaadin.risto.stepper.client.shared.DateStepperField;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.shared.DateTimeFormat;

/**
 * @author Risto Yrjänä / Vaadin
 * 
 */

public class DateStepper extends AbstractStepper<Date, Integer> {

    private DateStepperField dateStepField;

    private DateTimeFormat dateFormat;

    private final Logger logger = Logger.getLogger("VDateStepper");

    @Override
    public boolean isValidForType(String value) {
        try {
            getDateFormat().parse(value);
            return true;
        } catch (IllegalArgumentException e) {
            logger.log(Level.WARNING, "Value " + value + " wasn't valid", e);
            return false;
        }
    }

    // Date methods are not deprecated in GWT
    @SuppressWarnings("deprecation")
    @Override
    public String getDecreasedValue(String startValue) {
        Date dateValue = getDateFormat().parse(startValue);

        switch (dateStepField) {

        case DAY:
            int day = dateValue.getDate();
            day -= getStepAmount();
            dateValue.setDate(day);
            break;

        case MONTH:
            int month = dateValue.getMonth();
            month -= getStepAmount();
            dateValue.setMonth(month);
            break;

        case YEAR:
            int year = dateValue.getYear();
            year -= getStepAmount();
            dateValue.setYear(year);
            break;

        default:
            GWT.log("Unknown date step field " + dateStepField);
        }

        return getDateFormat().format(dateValue);

    }

    // Date methods are not deprecated in GWT
    @SuppressWarnings("deprecation")
    @Override
    public String getIncreasedValue(String startValue) {
        Date dateValue = getDateFormat().parse(startValue);
        switch (dateStepField) {

        case DAY:
            int day = dateValue.getDate();
            day += getStepAmount();
            dateValue.setDate(day);
            break;

        case MONTH:
            int month = dateValue.getMonth();
            month += getStepAmount();
            dateValue.setMonth(month);
            break;

        case YEAR:
            int year = dateValue.getYear();
            year += getStepAmount();
            dateValue.setYear(year);
            break;

        default:
            GWT.log("DateFieldIndex was out of bounds", null);
        }
        return getDateFormat().format(dateValue);
    }

    @Override
    public Date parseStringValue(String value) {
        if (value == null || "".equals(value)) {
            return null;
        } else {
            return getDateFormat().parse(value);
        }
    }

    @Override
    protected boolean isSmallerThanMax(String stringValue) {
        Date value = getDateFormat().parse(stringValue);

        return getMaxValue() == null || !value.after(getMaxValue());
    }

    @Override
    protected boolean isLargerThanMin(String stringValue) {
        Date value = getDateFormat().parse(stringValue);

        return getMinValue() == null || !value.before(getMinValue());
    }

    public DateTimeFormat getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(DateTimeFormat dateTimeFormat) {
        this.dateFormat = dateTimeFormat;
    }

    public DateStepperField getDateStepField() {
        return dateStepField;
    }

    public void setDateStepField(DateStepperField dateStepField) {
        this.dateStepField = dateStepField;
    }

    @Override
    public Integer parseStepAmount(String value) {
        if (value == null || "".equals(value)) {
            return null;
        } else {
            return Integer.parseInt(value);
        }
    }

    @Override
    public void setValueFilteringEnabled(boolean isValueFilteringEnabled) {
        throw new UnsupportedOperationException(
                "DateStepper does not support value filtering");
    }
}