package org.vaadin.risto.stepper.demo;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.common.base.CharMatcher;
import com.google.common.base.Strings;
import com.vaadin.server.UIClassSelectionEvent;
import com.vaadin.server.UIProvider;
import com.vaadin.ui.UI;

public class DemoUIProvider extends UIProvider {


    @Override
    public Class<? extends UI> getUIClass(UIClassSelectionEvent event) {
        String test = event.getRequest().getParameter("test");

        if (!Strings.isNullOrEmpty(test) &&
                CharMatcher.javaLetter().matchesAllOf(test)) {
            try {
                @SuppressWarnings("unchecked")
                Class<? extends UI> uiClass = (Class<? extends UI>) getClass()
                        .getClassLoader()
                        .loadClass("org.vaadin.risto.stepper." + test);
                return uiClass;
            } catch (ClassNotFoundException e) {
                Logger.getLogger(DemoUIProvider.class.getName())
                        .log(Level.SEVERE, "Expected UI class not found", e);
            }

        }

        return StepperDemoUI.class;
    }

}
