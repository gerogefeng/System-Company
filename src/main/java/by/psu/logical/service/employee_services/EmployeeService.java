package by.psu.logical.service.employee_services;

import by.psu.gui.Converter;
import by.psu.logical.dao.IEmployee;
import by.psu.logical.model.employee.Employee;
import by.psu.logical.model.employee.PostsEmployee;
import by.psu.logical.service.AbstractService;
import by.psu.logical.unit.SessionHibernate;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
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
        List<Employee> employees = readAllEmployees();

        Query query = session.createQuery("from PostsEmployee pe WHERE pe.delete = FALSE and ((pe.dateStart BETWEEN :startDate AND :endDate) OR (pe.dateEnd BETWEEN :startDate AND :endDate) OR " +
                "(:startDate BETWEEN pe.dateStart AND pe.dateEnd) OR (:endDate BETWEEN pe.dateStart AND pe.dateEnd))");

        query.setParameter("startDate", start, TemporalType.DATE);
        query.setParameter("endDate", end, TemporalType.DATE);

        List<PostsEmployee> postsEmployees = query.getResultList();

        System.out.println(postsEmployees);

        if (!postsEmployees.isEmpty()) {
            for (PostsEmployee pe : postsEmployees){
                Employee employeeSelect = pe.getEmployee();
                for (int i = 0; i < employees.size(); i++){
                    if(employees.get(i).hashCode() == employeeSelect.hashCode()) {
                        employees.remove(i);
                    }
                }
            }
        }

        SessionHibernate.getInstance().putback(session);
        return employees;
    }

    public List<Employee> readAllEmployees(){
        SessionHibernate sh = SessionHibernate.getInstance();
        Session session = sh.retriver();
        List<Employee> list = null;
        Query query = session.createQuery("from Employee e WHERE e.delete = false");
        list = query.getResultList();
        SessionHibernate.getInstance().putback(session);
        return list;
    }

    public void deleteEmployee(int id){
        SessionHibernate sh = SessionHibernate.getInstance();
        Session session = sh.retriver();
        session.getTransaction().begin();
        Query query = session.createQuery("delete from Employee e WHERE e.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
        session.getTransaction().commit();
        SessionHibernate.getInstance().putback(session);
    }
}
