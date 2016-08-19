package org.vaadin.risto.stepper.client.shared;

import com.vaadin.shared.communication.ServerRpc;

public interface StepperRpc extends ServerRpc {

    void valueChange(String value);
}
