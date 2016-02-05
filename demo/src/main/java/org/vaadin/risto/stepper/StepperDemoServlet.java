package org.vaadin.risto.stepper;

import com.vaadin.server.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

@WebServlet(value = "/*", initParams = {
        @WebInitParam(name = "UIProvider", value = "org.vaadin.risto.stepper.DemoUIProvider"),
        @WebInitParam(name = "widgetset", value = "org.vaadin.risto.stepper.widgetset.StepperDemoWidgetset")})
public class StepperDemoServlet extends VaadinServlet {

    @Override
    protected void servletInitialized() throws ServletException {
        super.servletInitialized();

        getService().addSessionInitListener(new SessionInitListener() {
            @Override
            public void sessionInit(SessionInitEvent event) throws ServiceException {
                event.getSession().addBootstrapListener(new BootstrapListener() {
                    @Override
                    public void modifyBootstrapFragment(BootstrapFragmentResponse response) {
                        //NOOP
                    }

                    @Override
                    public void modifyBootstrapPage(BootstrapPageResponse response) {
                        response.getDocument().head().appendElement("link").attr("rel", "import").attr("href","polymer.html");
                    }
                });
            }
        });
    }
}
