package org.vaadin.risto.stepper.demo;

import org.vaadin.risto.stepper.IntStepper;

import com.vaadin.data.Binder;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;

@SuppressWarnings("serial")
public class StepperDatasourceUI extends UI {

    @Override
    protected void init(VaadinRequest request) {
        TestBean dataSource = new TestBean();
        Binder<TestBean> binder = new Binder<>();
        binder.setBean(dataSource);
        IntStepper testStepper = new IntStepper();
        binder.bind(testStepper, "testValue");
        setContent(testStepper);

    }

    public static class TestBean {
        private Integer testValue = 10;

        public Integer getTestValue() {
            return testValue;
        }

        public void setTestValue(Integer testValue) {
            this.testValue = testValue;
        }
    }
}
