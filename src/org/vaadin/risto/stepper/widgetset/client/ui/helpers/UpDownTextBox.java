package org.vaadin.risto.stepper.widgetset.client.ui.helpers;

import org.vaadin.risto.stepper.widgetset.client.ui.VAbstractStepper;

import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.dom.client.MouseWheelEvent;
import com.google.gwt.event.dom.client.MouseWheelHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.TextBox;
import com.vaadin.terminal.gwt.client.Util;

/**
 * UI-class for a TextBox that listens to keyboard-up and -down events.
 * 
 * @author Risto Yrjänä / Vaadin Ltd.
 * 
 */
public class UpDownTextBox extends TextBox implements KeyDownHandler,
        KeyUpHandler, MouseWheelHandler {
    protected ButtonDownTimer keyDownTimerUp;
    protected ButtonDownTimer keyDownTimerDown;
    private Integer extraHorizontalPixels;
    private Integer extraVerticalPixels;
    private final VAbstractStepper stepper;

    public UpDownTextBox(VAbstractStepper stepper) {
        this.stepper = stepper;
        addKeyDownHandler(this);
        addKeyUpHandler(this);

        addMouseWheelHandler(this);
    }

    /**
     * Cancel all key-up and -down timers.
     */
    protected void cancelTimers() {
        if (keyDownTimerUp != null) {
            keyDownTimerUp.cancel();
            keyDownTimerUp = null;
        }

        if (keyDownTimerDown != null) {
            keyDownTimerDown.cancel();
            keyDownTimerDown = null;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.google.gwt.event.dom.client.KeyUpHandler#onKeyUp(com.google.gwt
     * .event.dom.client.KeyUpEvent)
     */
    public void onKeyUp(KeyUpEvent event) {
        if (!stepper.isTimerHasChangedValue()) {
            cancelTimers();
            int keycode = event.getNativeEvent().getKeyCode();

            if (keycode == KeyCodes.KEY_UP) {
                stepper.increaseValue();
                event.preventDefault();

            } else if (keycode == KeyCodes.KEY_DOWN) {
                stepper.decreaseValue();
                event.preventDefault();
            }
        } else {
            stepper.setTimerHasChangedValue(false);
            cancelTimers();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.google.gwt.event.dom.client.KeyDownHandler#onKeyDown(com.google
     * .gwt.event.dom.client.KeyDownEvent)
     */
    public void onKeyDown(KeyDownEvent event) {
        int keycode = event.getNativeEvent().getKeyCode();
        if (keycode == KeyCodes.KEY_UP && keyDownTimerUp == null) {
            keyDownTimerUp = new ButtonDownTimer(true, stepper);
            keyDownTimerUp.scheduleRepeating(VAbstractStepper.valueRepeatDelay);
            event.preventDefault();
        } else if (keycode == KeyCodes.KEY_DOWN && keyDownTimerDown == null) {
            keyDownTimerDown = new ButtonDownTimer(false, stepper);
            keyDownTimerDown
                    .scheduleRepeating(VAbstractStepper.valueRepeatDelay);
            event.preventDefault();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.google.gwt.event.dom.client.MouseWheelHandler#onMouseWheel(com
     * .google.gwt.event.dom.client.MouseWheelEvent)
     */
    public void onMouseWheel(MouseWheelEvent event) {

        int mouseWheelDelta = event.getDeltaY();
        if (stepper.canChangeFromTextBox()) {
            if (mouseWheelDelta < 0) {
                stepper.increaseValue();
            } else {
                stepper.decreaseValue();
            }
        }
    }

    /*
     * Private methods copied from VTextField
     */

    /**
     * @return space used by components paddings and borders
     */
    protected int getExtraHorizontalPixels() {
        if (extraHorizontalPixels == null) {
            detectExtraSizes();
        }
        return extraHorizontalPixels;
    }

    /**
     * @return space used by components paddings and borders
     */
    protected int getExtraVerticalPixels() {
        if (extraVerticalPixels == null) {
            detectExtraSizes();
        }
        return extraVerticalPixels;
    }

    /**
     * Detects space used by components paddings and borders. Used when
     * relational size are used.
     */
    private void detectExtraSizes() {
        Element clone = Util.cloneNode(getElement(), false);
        DOM.setElementAttribute(clone, "id", "");
        DOM.setStyleAttribute(clone, "visibility", "hidden");
        DOM.setStyleAttribute(clone, "position", "absolute");
        // due FF3 bug set size to 10px and later subtract it from extra
        // pixels
        DOM.setStyleAttribute(clone, "width", "10px");
        DOM.setStyleAttribute(clone, "height", "10px");
        DOM.appendChild(DOM.getParent(getElement()), clone);
        extraHorizontalPixels = DOM.getElementPropertyInt(clone, "offsetWidth") - 10;
        extraVerticalPixels = DOM.getElementPropertyInt(clone, "offsetHeight") - 10;

        DOM.removeChild(DOM.getParent(getElement()), clone);
    }

    @Override
    public void setHeight(String height) {
        if (height.endsWith("px")) {
            int h = Integer.parseInt(height.substring(0, height.length() - 2));
            h -= getExtraVerticalPixels();
            if (h < 0) {
                h = 0;
            }

            super.setHeight(h + "px");
        } else {
            super.setHeight(height);
        }
    }

    @Override
    public void setWidth(String width) {
        if (width.endsWith("px")) {
            int w = Integer.parseInt(width.substring(0, width.length() - 2));
            w -= getExtraHorizontalPixels();
            if (w < 0) {
                w = 0;
            }

            super.setWidth(w + "px");
        } else {
            super.setWidth(width);
        }
    }
}