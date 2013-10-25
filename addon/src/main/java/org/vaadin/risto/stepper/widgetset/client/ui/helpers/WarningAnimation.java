package org.vaadin.risto.stepper.widgetset.client.ui.helpers;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.animation.client.Animation;
import com.google.gwt.user.client.ui.TextBox;

public class WarningAnimation extends Animation {

    private static final Logger log = Logger.getLogger(WarningAnimation.class
            .toString());

    private static final int TARGET_COLOR_DEPTH = 255;

    private final TextBox target;

    public static void errorFlash(TextBox target) {
        new WarningAnimation(target).run(1000);
    }

    public WarningAnimation(TextBox target) {
        this.target = target;
    }

    @Override
    protected void onComplete() {
        super.onComplete();
        setColorEffect("#FFFFFF");// failsafe
    }

    private void setColorEffect(String color) {
        target.getElement().getStyle().setBackgroundColor(color);
    }

    @Override
    protected void onUpdate(double progress) {
        String color = getColorStringForProgress(progress);
        log.log(Level.INFO, "progress: " + progress + " color: " + color);
        setColorEffect(color);
    }

    private String getColorStringForProgress(double progress) {
        int green = getGreen(progress);
        int blue = getBlue(progress);

        log.log(Level.INFO, "green: " + green + " blue: " + blue);

        String greenHex = getHexString(green);
        String blueHex = getHexString(blue);
        return new StringBuilder().append('#').append("FF").append(greenHex)
                .append(blueHex).toString();
    }

    private String getHexString(int green) {
        String hexString = Integer.toHexString(green);

        return hexString.length() == 1 ? "0" + hexString : hexString;
    }

    private int getGreen(double progress) {
        if (progress < 0.5) {
            return (int) ((1 - 2 * progress) * TARGET_COLOR_DEPTH);
        } else {
            return (int) ((progress - 0.5) * 2 * TARGET_COLOR_DEPTH);
        }
    }

    private int getBlue(double progress) {
        if (progress < 0.5) {
            return (int) ((1 - 2 * progress) * TARGET_COLOR_DEPTH);
        } else {
            return (int) ((progress - 0.5) * 2 * TARGET_COLOR_DEPTH);
        }
    }
}
