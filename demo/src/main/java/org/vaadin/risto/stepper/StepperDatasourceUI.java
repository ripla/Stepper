package org.vaadin.risto.stepper;

import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;

@SuppressWarnings("serial")
public class StepperDatasourceUI extends UI {

    @Override
    protected void init(VaadinRequest request) {
        TestItem dataSource = new TestItem();
        FieldGroup fieldGroup = new FieldGroup();
        fieldGroup.setItemDataSource(new BeanItem<Object>(dataSource));
        IntStepper testStepper = new IntStepper();
        fieldGroup.bind(testStepper, "testValue");
        setContent(testStepper);

    }

    public static class TestItem {
        private Integer testValue = 10;

        public Integer getTestValue() {
            return testValue;
        }

        public void setTestValue(Integer testValue) {
            this.testValue = testValue;
        }
    }
}
