package by.psu.logical.service.employee_services;

import by.psu.logical.model.employee.Employee;
import by.psu.logical.service.AbstractService;

public class EmployeeService extends AbstractService<Employee> {

    public EmployeeService() {
        super(Employee.class);
    }
}
