package org.vaadin.risto.stepper;

import com.google.common.base.Strings;
import com.vaadin.server.UIClassSelectionEvent;
import com.vaadin.server.UIProvider;
import com.vaadin.ui.UI;

public class DemoUIProvider extends UIProvider {

    private static final long serialVersionUID = 8188640517564051651L;

    @Override
    public Class<? extends UI> getUIClass(UIClassSelectionEvent event) {
        String test = event.getRequest().getParameter("test");

        if (!Strings.isNullOrEmpty(test)) {
            if ("table".equals(test)) {
                return StepperTableDemoUI.class;
            } else if ("dateformat".equals(test)) {
                return StepperDateFormatDemoUI.class;
            }

        }
        return StepperDemoUI.class;
    }

}
