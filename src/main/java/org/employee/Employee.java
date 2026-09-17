package org.employee;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class Employee implements InitializingBean, DisposableBean {

    public Employee(){
        System.out.println("1.Employee constructor has invoked");
    }
    @PostConstruct
    public void init(){

        System.out.println("5. PostConstruct Method invoked");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("6. Initializing bean afterPropertiesSet method invoked");
    }

    public void anotherInitMethod(){
        System.out.println("7. Bean initMethod invoked");
    }
    public String getEmployeeId() {

        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        System.out.println("2. Employee ID is getting set");
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        System.out.println("2. Employee name is getting set");
        this.employeeName = employeeName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        System.out.println("2. Employee department is getting set");
        this.department = department;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        System.out.println("2. Employee Salary is getting set");
        this.salary = salary;
    }

    private String employeeId;
    private String employeeName;
    private String department;
    private String salary;

    // Business method

    public void work() {
        System.out.println(
                "Employee " + employeeName +
                        " is working in " + department
        );
    }

    public void displayEmployee() {

        System.out.println("\nEmployee Details");
        System.out.println("----------------");
        System.out.println("Name       : " + employeeName);
        System.out.println("Department : " + department);
        System.out.println("Salary     : " + salary);
    }

    @PreDestroy
    public void destroyMethod(){
        System.out.println("9. PreDestroy method invoked");
    }
    @Override
    public void destroy() throws Exception {
        System.out.println("10. Disposable bean destroy method invoked");
    }

    public void anotherDestroyMethod(){
        System.out.println("11. Bean destroyMethod invoked");
    }


}
