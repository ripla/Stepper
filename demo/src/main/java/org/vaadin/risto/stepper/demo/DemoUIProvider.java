package org.vaadin.risto.stepper.demo;

import com.google.common.base.CharMatcher;
import com.google.common.base.Strings;
import com.vaadin.server.UIClassSelectionEvent;
import com.vaadin.server.UIProvider;
import com.vaadin.ui.UI;

public class DemoUIProvider extends UIProvider {

    private static final long serialVersionUID = 8188640517564051651L;

    @Override
    public Class<? extends UI> getUIClass(UIClassSelectionEvent event) {
        String test = event.getRequest().getParameter("test");

        if (!Strings.isNullOrEmpty(test)
                && CharMatcher.JAVA_LETTER.matchesAllOf(test)) {
            try {
                @SuppressWarnings("unchecked")
                Class<? extends UI> uiClass = (Class<? extends UI>) getClass()
                        .getClassLoader().loadClass(
                                "org.vaadin.risto.stepper." + test);
                return uiClass;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }

        return StepperDemoUI.class;
    }

}
