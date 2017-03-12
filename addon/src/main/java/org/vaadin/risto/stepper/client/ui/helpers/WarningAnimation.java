package org.vaadin.risto.stepper.client.ui.helpers;

import com.google.gwt.animation.client.Animation;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * Animation shown when invalid content is entered and filtering is enabled
 */
public class WarningAnimation extends Animation {

    private static final int TARGET_COLOR_DEPTH = 255;

    private Widget target;

    public static void errorFlash(TextBox target) {
        final WarningAnimation animation = GWT.create(WarningAnimation.class);
        animation.setTarget(target);
        animation.run(1000);
    }

    public WarningAnimation() {
    }

    @Override
    protected void onComplete() {
        super.onComplete();
        setColorEffect("#FFFFFF");// failsafe
    }

    protected void setColorEffect(String color) {
        target.getElement().getStyle().setBackgroundColor(color);
    }

    protected void setTarget(Widget target) {
        this.target = target;
    }

    @Override
    protected void onUpdate(double progress) {
        String color = getColorStringForProgress(progress);
        setColorEffect(color);
    }

    protected String getColorStringForProgress(double progress) {
        int green = getGreen(progress);
        int blue = getBlue(progress);

        String greenHex = getHexString(green);
        String blueHex = getHexString(blue);
        return new StringBuilder().append('#').append("FF").append(greenHex)
                .append(blueHex).toString();
    }

    protected String getHexString(int green) {
        String hexString = Integer.toHexString(green);

        return hexString.length() == 1 ? "0" + hexString : hexString;
    }

    protected int getGreen(double progress) {
        if (progress < 0.5) {
            return (int) ((1 - 2 * progress) * TARGET_COLOR_DEPTH);
        } else {
            return (int) ((progress - 0.5) * 2 * TARGET_COLOR_DEPTH);
        }
    }

    protected int getBlue(double progress) {
        if (progress < 0.5) {
            return (int) ((1 - 2 * progress) * TARGET_COLOR_DEPTH);
        } else {
            return (int) ((progress - 0.5) * 2 * TARGET_COLOR_DEPTH);
        }
    }
}
