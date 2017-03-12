package org.vaadin.risto.stepper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.junit.Test;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.declarative.Design;

import static org.junit.Assert.assertEquals;

/**
 * Tests for writing and reading Steppers in the Vaadin Declarative format
 */
public class DeclarativeTest {

    @Test
    public void stringStepper_setCommonPropertiesAndWriteDeclarative_declarativeCanBeReadBack()
            throws IOException {
        testCommonProperties(new TestStringStepper(), "1", "-1", "foobar");
    }

    @Test
    public void stringStepper_setValueAndWriteDeclarative_valueReadBackCorrectly()
            throws IOException {
        testValue(new TestStringStepper(), "value");
    }

    @Test
    public void intStepper_setCommonPropertiesAndWriteDeclarative_declarativeCanBeReadBack()
            throws IOException {
        testCommonProperties(new IntStepper(), 1, -1, 2);
    }

    @Test
    public void intStepper_setValueAndWriteDeclarative_valueReadBackCorrectly()
            throws IOException {
        testValue(new IntStepper(), 0);
    }

    @Test
    public void floatStepper_setCommonPropertiesAndWriteDeclarative_declarativeCanBeReadBack()
            throws IOException {
        testCommonProperties(new FloatStepper(), 1.1f, -1.2f, 2.3f);
    }

    @Test
    public void floatStepper_setValueAndWriteDeclarative_valueReadBackCorrectly()
            throws IOException {
        testValue(new FloatStepper(), 0.0f);
    }

    @Test
    public void bigDecimalStepper_setCommonPropertiesAndWriteDeclarative_declarativeCanBeReadBack()
            throws IOException {
        testCommonProperties(new BigDecimalStepper(), new BigDecimal(1.1f)
                        .setScale(1, RoundingMode.HALF_UP),
                new BigDecimal(-1.2f).setScale(1, RoundingMode.HALF_UP), new BigDecimal(2.3f).setScale(1, RoundingMode.HALF_UP));
    }

    @Test
    public void bigDecimalStepper_setValueAndWriteDeclarative_valueReadBackCorrectly()
            throws IOException {
        testValue(new BigDecimalStepper(), BigDecimal.ZERO);
    }

    @Test
    public void dateStepper_setCommonPropertiesAndWriteDeclarative_declarativeCanBeReadBack()
            throws IOException {
        final DateStepper expected = new DateStepper();
        expected.setLocale(new Locale("fi", "FI"));

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, 1);
        Date max = calendar.getTime();
        calendar.add(Calendar.HOUR, -2);
        Date min = calendar.getTime();
        testCommonProperties(expected, max, min, 3);
    }

    @Test
    public void dateStepper_setValueAndWriteDeclarative_valueReadBackCorrectly()
            throws IOException {
        testDateValue(new DateStepper(), new Date());
    }

    private <T, S> void testCommonProperties(AbstractStepper<T, S> expected,
            T maxValue, T minValue, S stepAmount) throws IOException {
        expected.setDecreaseIcon(VaadinIcons.ANGLE_DOUBLE_DOWN);
        expected.setIncreaseIcon(VaadinIcons.ANGLE_DOUBLE_UP);
        expected.setInvalidValuesAllowed(true);
        expected.setManualInputAllowed(true);
        expected.setMaxValue(maxValue);
        expected.setMinValue(minValue);
        expected.setMouseWheelEnabled(true);
        expected.setNullValueAllowed(true);
        expected.setStepAmount(stepAmount);

        try (ByteArrayOutputStream out = new ByteArrayOutputStream();) {
            Design.write(expected, out);

            // debug
            System.out.println(out.toString(StandardCharsets.UTF_8.name()));

            AbstractStepper<T, S> result = (AbstractStepper<T, S>) Design
                    .read(new ByteArrayInputStream(out.toByteArray()));

            assertEquals(expected.getDecreaseIcon(), result.getDecreaseIcon());
            assertEquals(expected.getIncreaseIcon(), result.getIncreaseIcon());
            assertEquals(expected.isInvalidValuesAllowed(),
                    result.isInvalidValuesAllowed());
            assertEquals(expected.isManualInputAllowed(),
                    result.isManualInputAllowed());
            assertEquals(expected.getMaxValue(), result.getMaxValue());
            assertEquals(expected.getMinValue(), result.getMinValue());
            assertEquals(expected.isMouseWheelEnabled(),
                    result.isMouseWheelEnabled());
            assertEquals(expected.isNullValueAllowed(),
                    result.isNullValueAllowed());
            assertEquals(expected.getStepAmount(), result.getStepAmount());
        }
    }

    private <T, S> void testValue(AbstractStepper<T, S> expected, T value)
            throws IOException {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();) {
            Design.write(expected, out);

            AbstractStepper<T, S> result = (AbstractStepper<T, S>) Design
                    .read(new ByteArrayInputStream(out.toByteArray()));

            assertEquals(expected.getValue(), result.getValue());
        }
    }

    private void testDateValue(DateStepper expected, Date value)
            throws IOException {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();) {
            Design.write(expected, out);

            DateStepper result = (DateStepper) Design
                    .read(new ByteArrayInputStream(out.toByteArray()));

            // a bit ugly, but since we are in the same package
            // use internal utility method
            assertEquals(
                    normalizeBoundaryDate(expected.getValue(),
                            expected.getLocale()),
                    normalizeBoundaryDate(result.getValue(),
                            expected.getLocale()));
        }
    }

    protected Date normalizeBoundaryDate(Date boundaryDate, Locale locale) {
        if (boundaryDate == null) {
            return null;
        }

        Calendar javaCalendar = locale != null
                ? Calendar.getInstance(locale) : Calendar.getInstance();
        javaCalendar.setTime(boundaryDate);

        javaCalendar.set(Calendar.MILLISECOND, 0);
        javaCalendar.set(Calendar.SECOND, 0);
        javaCalendar.set(Calendar.MINUTE, 0);
        javaCalendar.set(Calendar.HOUR, 0);
        javaCalendar.set(Calendar.HOUR_OF_DAY, 0);

        return javaCalendar.getTime();
    }
}
