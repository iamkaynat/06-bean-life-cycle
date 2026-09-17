package org.processor;

import org.employee.Employee;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class EmployeeBeanPostProcessor implements BeanPostProcessor {


    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if(bean instanceof Employee){
            System.out.println(" 4. Bean Post Processor before initialization is called");
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if(bean instanceof Employee){
            System.out.println("8. Bean Post Processor After initialization is called");
        }
        return bean;
    }
}
