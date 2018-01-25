package by.psu.logical.service.employee_services;

import by.psu.gui.Converter;
import by.psu.logical.dao.IEmployee;
import by.psu.logical.model.employee.Employee;
import by.psu.logical.model.employee.PostsEmployee;
import by.psu.logical.service.AbstractService;
import by.psu.logical.unit.SessionHibernate;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.TemporalType;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EmployeeService extends AbstractService<Employee> implements IEmployee{

    public EmployeeService() {
        super(Employee.class);
    }

    public List<Employee> getEmployeeIntervalDate(Date start, Date end) {
        SessionHibernate sh = SessionHibernate.getInstance();
        Session session = sh.retriver();
        List<Employee> employees = new ArrayList<>();
        Query query = session.createQuery("from PostsEmployee pe WHERE ((pe.dateStart NOT BETWEEN :startDate AND :endDate) AND  (pe.dateEnd NOT BETWEEN :startDate AND :endDate)) AND " +
                "((:startDate NOT BETWEEN pe.dateStart AND pe.dateStart) AND (:endDate NOT BETWEEN pe.dateStart AND pe.dateEnd))");
        query.setParameter("startDate", start, TemporalType.DATE);
        query.setParameter("endDate", end, TemporalType.DATE);

        List<PostsEmployee> postsEmployees = query.getResultList();

        postsEmployees.forEach(postsEmployee -> {
            employees.add(postsEmployee.getEmployee());
        });
        SessionHibernate.getInstance().putback(session);
        return employees;
    }
}
