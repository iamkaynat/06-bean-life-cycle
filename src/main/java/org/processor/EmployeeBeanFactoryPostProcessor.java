package org.processor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;


public class EmployeeBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println("3. BeanFactoryPostProcessor");

        BeanDefinition employeeBeanDefinition = beanFactory.getBeanDefinition("employee");
        System.out.println("Modifying employee bean definition");
        employeeBeanDefinition.getPropertyValues()
                .add("department", "Platform Engineering");

        System.out.println(
                "   Department changed to: Platform Engineering"
        );

    }
}
