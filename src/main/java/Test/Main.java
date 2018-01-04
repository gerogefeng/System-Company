package Test;


import by.psu.logical.model.employee.Employee;
import by.psu.logical.model.employee.Passport;
import by.psu.logical.service.employee_services.EmployeeService;
import by.psu.logical.service.employee_services.PassportService;
import by.psu.logical.service.transport_services.TransportService;

import java.util.Date;

public class Main {
    public static void main(String[] args) throws Exception {
        PassportService passportService = new PassportService();
        EmployeeService employeeService = new EmployeeService();

        /*Passport passport = new Passport("Belarus", "OVDE", "MC", "112215515", new Date(), new Date());
        Card card = new Card(new Date(), "+375299479630", "jsdevelop..@ya.com");
        Employee employee = new Employee("Pavel", "Talaiko", "Dmitrievich", null, passport, null, card);
        card.setEmployee(employee);
        passport.setEmployee(employee);
        employeeService.create(employee);*/
        /*Passport passport = new Passport();
        passport.setNationality("fasdafsafdas");
        passport.setDepartment("fdsfsfasd");
        passport.setSerialPassport("MC");
        passport.setNumberPassport("43214231");
        passport.setDateIn(new Date());
        passport.setDateOut(new Date());

        Passport passport2 = new Passport();
        passport2.setNationality("fasdafsafewfffffffffdas");
        passport2.setDepartment("ffffffffffff");
        passport2.setSerialPassport("MC");
        passport2.setNumberPassport("54354htrh3");
        passport2.setDateIn(new Date());
        passport2.setDateOut(new Date());

        Employee employee = new Employee("fsdafsdaf", "fdsafadsfds", "ffdsafdsafsa", "jhjkhjgjh", null);

        employeeService.create(employee);

        passport.setEmployee(employee);
        passport2.setEmployee(employee);

        passportService.create(passport);
        passportService.create(passport2);*/

        TransportService transportService = new TransportService();

        transportService.readALL().forEach(System.out::println);

        /*Employee employee = employeeService.read(3);
        employeeService.update(employee);
        passportService.delete(employee.getPassport().get(0));
*/
        /*Passport passport = passportService.read(1);
        passport.setEmployee(employeeService.read(1));
        passportService.update(passport);*/

    }
}
