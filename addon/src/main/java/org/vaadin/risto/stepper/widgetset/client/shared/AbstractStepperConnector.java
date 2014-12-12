package org.vaadin.risto.stepper.widgetset.client.shared;

import org.vaadin.risto.stepper.widgetset.client.ui.VAbstractStepper;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.vaadin.client.communication.RpcProxy;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractFieldConnector;

public abstract class AbstractStepperConnector<T, S> extends
        AbstractFieldConnector {

    private static final long serialVersionUID = 6952509590080940264L;

    @Override
    @SuppressWarnings("unchecked")
    public VAbstractStepper<T, S> getWidget() {
        return (VAbstractStepper<T, S>) super.getWidget();
    }

    @Override
    public AbstractStepperState getState() {
        return (AbstractStepperState) super.getState();
    }

    @Override
    protected void init() {
        super.init();

        final StepperRpc stepperRpcProxy = RpcProxy.create(StepperRpc.class,
                this);
        getWidget().addValueChangeHandler(new ValueChangeHandler<String>() {

            @Override
            public void onValueChange(ValueChangeEvent<String> event) {
            	getState().value = event.getValue();
                stepperRpcProxy.valueChange(event.getValue());
            }
        });
    }

    @Override
    public void onStateChanged(StateChangeEvent stateChangeEvent) {

        getWidget().setDisabled(!getState().enabled);
        getWidget().setReadonly(getState().readOnly);
        getWidget().setManualInputAllowed(getState().isManualInputAllowed);
        getWidget().setMouseWheelEnabled(getState().isMouseWheelEnabled);
        getWidget().setInvalidValuesAllowed(getState().isInvalidValuesAllowed);
        getWidget().setNullValueAllowed(getState().isNullValueAllowed);

        getWidget().setMinValue(
                getWidget().parseStringValue(getState().minValue));
        getWidget().setMaxValue(
                getWidget().parseStringValue(getState().maxValue));

        getWidget().setValue(getState().value);
        getWidget().setStepAmount(
                getWidget().parseStepAmount(getState().stepAmount));

        super.onStateChanged(stateChangeEvent);
    }
}
