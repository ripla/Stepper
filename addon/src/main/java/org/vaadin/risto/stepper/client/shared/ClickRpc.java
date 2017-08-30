package org.vaadin.risto.stepper.client.shared;

import com.vaadin.shared.MouseEventDetails;
import com.vaadin.shared.communication.ServerRpc;

@FunctionalInterface
public interface ClickRpc extends ServerRpc {
    void onClick(MouseEventDetails mouseEventDetails);
}
