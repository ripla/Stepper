package org.vaadin.risto.stepper.widgetset.client.shared;

import com.vaadin.shared.communication.ServerRpc;

public interface StepperRpc extends ServerRpc {

    void valueChange(String value);
}
