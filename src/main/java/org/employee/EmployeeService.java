package org.employee;

import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    private final Employee employee;

    public EmployeeService(Employee employee){
        this.employee=employee;
        System.out.println("EmployeeService constructor called");
    }
    public void processEmployee() {

        System.out.println("\n========== EMPLOYEE SERVICE ==========");

        employee.work();
        employee.displayEmployee();
    }

}
