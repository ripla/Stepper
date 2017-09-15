package org.vaadin.risto.stepper.client.ui.helpers;

import org.vaadin.risto.stepper.client.ui.AbstractStepper;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlowPanel;

/**
 * Panel that contains the controls for the Stepper.
 * 
 * @author Risto Yrjänä / Vaadin
 * 
 */
public class StepperControls extends FlowPanel
        implements ClickHandler, MouseDownHandler, MouseUpHandler,
        MouseOverHandler, MouseOutHandler, MouseWheelHandler {

    protected final Anchor increaseControl;
    protected final Anchor decreaseControl;
    protected final ButtonDownTimer mouseDownTimerUp;
    protected final ButtonDownTimer mouseDownTimerDown;
    private final AbstractStepper<?, ?> stepper;

    public StepperControls(AbstractStepper<?, ?> stepper) {
        this.stepper = stepper;
        setStyleName("stepper-updown");

        increaseControl = new Anchor();
        increaseControl.addStyleName("stepper-up");

        decreaseControl = new Anchor();
        decreaseControl.addStyleName("stepper-down");

        increaseControl.addClickHandler(this);
        increaseControl.addMouseDownHandler(this);
        increaseControl.addMouseUpHandler(this);
        increaseControl.addMouseOverHandler(this);
        increaseControl.addMouseWheelHandler(this);

        decreaseControl.addClickHandler(this);
        decreaseControl.addMouseDownHandler(this);
        decreaseControl.addMouseUpHandler(this);
        decreaseControl.addMouseOverHandler(this);
        decreaseControl.addMouseWheelHandler(this);

        addDomHandler(this, MouseOutEvent.getType());

        add(increaseControl);
        add(decreaseControl);

        mouseDownTimerUp = new ButtonDownTimer(true, stepper);
        mouseDownTimerDown = new ButtonDownTimer(false, stepper);
    }

    protected void cancelTimers() {
        mouseDownTimerUp.cancel();
        mouseDownTimerDown.cancel();
    }

    @Override
    public void onClick(ClickEvent event) {
        if (!stepper.isTimerHasChangedValue()) {
            cancelTimers();
            if (event.getSource() == increaseControl) {
                stepper.increaseValue();
            } else if (event.getSource() == decreaseControl) {
                stepper.decreaseValue();
            }
        } else {
            stepper.setTimerHasChangedValue(false);
        }
    }

    @Override
    public void onMouseDown(MouseDownEvent event) {
        cancelTimers();
        if (event.getSource() == increaseControl) {
            mouseDownTimerUp
                    .scheduleRepeating(AbstractStepper.VALUE_REPEAT_DELAY);
        } else if (event.getSource() == decreaseControl) {
            mouseDownTimerDown
                    .scheduleRepeating(AbstractStepper.VALUE_REPEAT_DELAY);
        }
        event.preventDefault();
    }

    @Override
    public void onMouseUp(MouseUpEvent event) {
        cancelTimers();
    }

    @Override
    public void onMouseOver(MouseOverEvent event) {
        cancelTimers();
    }

    @Override
    public void onMouseOut(MouseOutEvent event) {
        cancelTimers();
    }

    @Override
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

    public void setIncreaseIconElement(Element increaseIconElement) {
        increaseControl.getElement().removeAllChildren();
        increaseControl.getElement().appendChild(increaseIconElement);
    }

    public void setDecreaseIconElement(Element decreaseIconElement) {
        decreaseControl.getElement().removeAllChildren();
        decreaseControl.getElement().appendChild(decreaseIconElement);
    }
}
