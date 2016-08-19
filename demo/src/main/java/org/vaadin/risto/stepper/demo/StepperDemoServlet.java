package org.vaadin.risto.stepper.demo;

import com.vaadin.server.VaadinServlet;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

@WebServlet(value = "/*", initParams = {
        @WebInitParam(name = "UIProvider", value = "org.vaadin.risto.stepper.DemoUIProvider") })
public class StepperDemoServlet extends VaadinServlet {
}
