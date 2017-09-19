package org.vaadin.risto.stepper.client.shared;

import com.vaadin.shared.MouseEventDetails;
import com.vaadin.shared.communication.ServerRpc;

/**
 * Server RPC that is called when the stepper is clicked
 */
public interface ClickRpc extends ServerRpc {
    /**
     * The stepper was clicked on the client
     * @param mouseEventDetails details on the mouse that clicked (e.g. clicked button)
     */
    void onClick(MouseEventDetails mouseEventDetails);
}
