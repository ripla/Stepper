package org.vaadin.risto.stepper.widgetset.client;

import com.vaadin.terminal.gwt.client.communication.ServerRpc;

public interface StepperRpc extends ServerRpc {

    void valueChange(String value);
}
