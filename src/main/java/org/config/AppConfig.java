package org.config;

import org.employee.Employee;
import org.employee.EmployeeService;
import org.processor.EmployeeBeanFactoryPostProcessor;
import org.processor.EmployeeBeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean(initMethod = "anotherInitMethod" , destroyMethod = "anotherDestroyMethod")
    public Employee employee(){
        Employee employee = new Employee();
        employee.setEmployeeId("1");
        employee.setEmployeeName("Kale");
        employee.setDepartment("Science");
        employee.setSalary("800000");
        return employee;
    }

    @Bean
    public EmployeeService employeeService(Employee employee){
        return new EmployeeService(employee);
    }

    @Bean
    public static EmployeeBeanFactoryPostProcessor
    employeeBeanFactoryPostProcessor() {

        return new EmployeeBeanFactoryPostProcessor();
    }

    @Bean
    public EmployeeBeanPostProcessor
    employeeBeanPostProcessor() {

        return new EmployeeBeanPostProcessor();
    }
}
