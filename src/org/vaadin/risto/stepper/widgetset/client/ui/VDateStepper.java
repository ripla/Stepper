/**
 * 
 */
package org.vaadin.risto.stepper.widgetset.client.ui;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.vaadin.terminal.gwt.client.ApplicationConnection;
import com.vaadin.terminal.gwt.client.LocaleNotLoadedException;
import com.vaadin.terminal.gwt.client.LocaleService;
import com.vaadin.terminal.gwt.client.UIDL;

/**
 * @author Risto Yrjänä / Vaadin Ltd.
 * 
 */
public class VDateStepper extends VAbstractStepper {

    public static final int DATEFIELDINDEX = 0;
    public static final int DATESTEPINDEX = 1;

    public enum DateStepField {
        DAY, MONTH, YEAR
    };

    private int[] dateStepAmount;

    private String locale;

    private DateTimeFormat dateFormat;
    private Date maxDate;
    private Date minDate;

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.vaadin.risto.stepper.widgetset.client.ui.VAbstractStepper#updateFromUIDL
     * (com.vaadin.terminal.gwt.client.UIDL,
     * com.vaadin.terminal.gwt.client.ApplicationConnection)
     */
    @Override
    public void updateFromUIDL(UIDL uidl, ApplicationConnection client) {
        String newLocale = uidl.getStringAttribute("locale");
        if (!newLocale.equals(locale)) {
            locale = newLocale;
            String formatString = null;
            try {
                formatString = LocaleService.getDateFormat(locale);
            } catch (LocaleNotLoadedException e) {
                GWT.log("Locale not loaded error while fetching date format string",
                        e);
            }
            dateFormat = DateTimeFormat.getFormat(formatString);
        }

        dateStepAmount = uidl.getIntArrayAttribute("stepAmount");

        super.updateFromUIDL(uidl, client);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.vaadin.risto.stepper.widgetset.client.ui.VAbstractStepper#isValidForType
     * (java.lang.String)
     */
    @Override
    protected boolean isValidForType(String value) {
        try {
            dateFormat.parse(value);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.vaadin.risto.stepper.widgetset.client.ui.VAbstractStepper#
     * getDecreasedValue(java.lang.String)
     */
    // Date methods are not deprecated in GWT
    @SuppressWarnings("deprecation")
    @Override
    public String getDecreasedValue(String startValue) {
        Date dateValue = dateFormat.parse(startValue);

        switch (DateStepField.values()[dateStepAmount[DATEFIELDINDEX]]) {

        case DAY:
            int day = dateValue.getDate();
            day -= dateStepAmount[DATESTEPINDEX];
            dateValue.setDate(day);
            break;

        case MONTH:
            int month = dateValue.getMonth();
            month -= dateStepAmount[DATESTEPINDEX];
            dateValue.setMonth(month);
            break;

        case YEAR:
            int year = dateValue.getYear();
            year -= dateStepAmount[DATESTEPINDEX];
            dateValue.setYear(year);
            break;

        default:
            GWT.log("DateFieldIndex was out of bounds");
        }

        return dateFormat.format(dateValue);

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.vaadin.risto.stepper.widgetset.client.ui.VAbstractStepper#
     * getIncreasedValue()
     */
    // Date methods are not deprecated in GWT
    @SuppressWarnings("deprecation")
    @Override
    public String getIncreasedValue(String startValue) {
        Date dateValue = dateFormat.parse(startValue);
        switch (DateStepField.values()[dateStepAmount[DATEFIELDINDEX]]) {

        case DAY:
            int day = dateValue.getDate();
            day += dateStepAmount[DATESTEPINDEX];
            dateValue.setDate(day);
            break;

        case MONTH:
            int month = dateValue.getMonth();
            month += dateStepAmount[DATESTEPINDEX];
            dateValue.setMonth(month);
            break;

        case YEAR:
            int year = dateValue.getYear();
            year += dateStepAmount[DATESTEPINDEX];
            dateValue.setYear(year);
            break;

        default:
            GWT.log("DateFieldIndex was out of bounds", null);
        }
        return dateFormat.format(dateValue);
    }

    @Override
    protected void setMaxValue(Object value) {
        maxDate = (Date) value;
    }

    @Override
    protected void setMinValue(Object value) {
        minDate = (Date) value;
    }

    @Override
    protected Date parseStringValue(String value) {
        if (value == null || "".equals(value)) {
            return null;
        } else {
            return dateFormat.parse(value);
        }
    }

    @Override
    protected boolean isSmallerThanMax(String stringValue) {
        Date value = dateFormat.parse(stringValue);

        if (maxDate != null && value.after(maxDate)) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    protected boolean isLargerThanMin(String stringValue) {
        Date value = dateFormat.parse(stringValue);

        if (minDate != null && value.before(minDate)) {
            return false;
        } else {
            return true;
        }
    }
}