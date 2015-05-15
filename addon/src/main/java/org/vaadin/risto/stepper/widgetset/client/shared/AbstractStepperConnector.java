package org.vaadin.risto.stepper.widgetset.client.shared;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.vaadin.client.communication.RpcProxy;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractFieldConnector;
import org.vaadin.risto.stepper.widgetset.client.ui.AbstractStepper;

public abstract class AbstractStepperConnector<T, S> extends
        AbstractFieldConnector {

    private static final long serialVersionUID = 6952509590080940264L;

    @Override
    @SuppressWarnings("unchecked")
    public AbstractStepper<T, S> getWidget() {
        return (AbstractStepper<T, S>) super.getWidget();
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

        getWidget().setIncreaseIconElement(getIconElement(getState().INCREASE_ICON_KEY));
        getWidget().setDecreaseIconElement(getIconElement(getState().DECREASE_ICON_KEY));

        super.onStateChanged(stateChangeEvent);
    }

    /**
     * Create the Icon element from the provided key. Note that we also implicitly cast the returned Element
     * to the non-deprecated variant.
     *
     * @param iconKey
     * @return
     */
    private Element getIconElement(String iconKey) {
        return getConnection().getIcon(getResourceUrl(iconKey)).getElement();
    }
}
