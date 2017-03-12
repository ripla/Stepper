package org.vaadin.risto.stepper.demo;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

import com.vaadin.server.VaadinServlet;

@WebServlet(value = "/*", initParams = {
        @WebInitParam(name = "UIProvider", value = "org.vaadin.risto.stepper.demo.DemoUIProvider") })
public class StepperDemoServlet extends VaadinServlet {
}
