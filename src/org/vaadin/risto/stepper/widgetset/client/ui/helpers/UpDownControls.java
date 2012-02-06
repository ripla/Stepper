package org.vaadin.risto.stepper.widgetset.client.ui.helpers;

import org.vaadin.risto.stepper.widgetset.client.ui.VAbstractStepper;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.dom.client.MouseWheelEvent;
import com.google.gwt.event.dom.client.MouseWheelHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlowPanel;

/**
 * Class for the up/down buttons and their listeners.
 * 
 * @author Risto Yrjänä / Vaadin Ltd.
 * 
 */
public class UpDownControls extends FlowPanel implements ClickHandler,
        MouseDownHandler, MouseUpHandler, MouseOverHandler, MouseOutHandler,
        MouseWheelHandler {

    protected final Anchor buttonUp;
    protected final Anchor buttonDown;
    protected final ButtonDownTimer mouseDownTimerUp;
    protected final ButtonDownTimer mouseDownTimerDown;
    private final VAbstractStepper stepper;

    public UpDownControls(VAbstractStepper stepper) {
        this.stepper = stepper;
        setStyleName("v-stepper-updown");

        buttonUp = new Anchor();
        buttonUp.addStyleName("v-stepper-up");

        buttonDown = new Anchor();
        buttonDown.addStyleName("v-stepper-down");

        buttonUp.addClickHandler(this);
        buttonUp.addMouseDownHandler(this);
        buttonUp.addMouseUpHandler(this);
        buttonUp.addMouseOverHandler(this);
        buttonUp.addMouseWheelHandler(this);

        buttonDown.addClickHandler(this);
        buttonDown.addMouseDownHandler(this);
        buttonDown.addMouseUpHandler(this);
        buttonDown.addMouseOverHandler(this);
        buttonDown.addMouseWheelHandler(this);

        addDomHandler(this, MouseOutEvent.getType());

        add(buttonUp);
        add(buttonDown);

        mouseDownTimerUp = new ButtonDownTimer(true, stepper);
        mouseDownTimerDown = new ButtonDownTimer(false, stepper);
    }

    protected void cancelTimers() {
        mouseDownTimerUp.cancel();
        mouseDownTimerDown.cancel();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.google.gwt.event.dom.client.ClickHandler#onClick(com.google.gwt
     * .event.dom.client.ClickEvent)
     */
    public void onClick(ClickEvent event) {
        if (!stepper.isTimerHasChangedValue()) {
            cancelTimers();
            if (event.getSource() == buttonUp) {
                stepper.increaseValue();
            } else if (event.getSource() == buttonDown) {
                stepper.decreaseValue();
            }
        } else {
            stepper.setTimerHasChangedValue(false);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.google.gwt.event.dom.client.MouseDownHandler#onMouseDown(com.
     * google.gwt.event.dom.client.MouseDownEvent)
     */
    public void onMouseDown(MouseDownEvent event) {
        cancelTimers();
        if (event.getSource() == buttonUp) {
            mouseDownTimerUp
                    .scheduleRepeating(VAbstractStepper.valueRepeatDelay);
        } else if (event.getSource() == buttonDown) {
            mouseDownTimerDown
                    .scheduleRepeating(VAbstractStepper.valueRepeatDelay);
        }
        event.preventDefault();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.google.gwt.event.dom.client.MouseUpHandler#onMouseUp(com.google
     * .gwt.event.dom.client.MouseUpEvent)
     */
    public void onMouseUp(MouseUpEvent event) {
        cancelTimers();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.google.gwt.event.dom.client.MouseOverHandler#onMouseOver(com.
     * google.gwt.event.dom.client.MouseOverEvent)
     */
    public void onMouseOver(MouseOverEvent event) {
        cancelTimers();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.google.gwt.event.dom.client.MouseOutHandler#onMouseOut(com.google
     * .gwt.event.dom.client.MouseOutEvent)
     */
    public void onMouseOut(MouseOutEvent event) {
        cancelTimers();
    }

    public void onMouseWheel(MouseWheelEvent event) {
        if (stepper.isMouseWheelEnabled()) {
            int mouseWheelDelta = event.getDeltaY();

            if (mouseWheelDelta < 0) {
                stepper.increaseValue();
            } else {
                stepper.decreaseValue();
            }
        }
    }
}
