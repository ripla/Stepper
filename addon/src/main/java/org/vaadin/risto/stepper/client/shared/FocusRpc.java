package org.vaadin.risto.stepper.client.shared;

import com.vaadin.shared.communication.ServerRpc;

@FunctionalInterface
public interface FocusRpc extends ServerRpc {

    void focus();
}
