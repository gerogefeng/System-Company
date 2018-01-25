package by.psu.logical.service.employee_services;

import by.psu.logical.dao.IPostEmployee;
import by.psu.logical.model.employee.PostsEmployee;
import by.psu.logical.service.AbstractService;
import by.psu.logical.unit.SessionHibernate;
import org.hibernate.Session;

public class PostEmployee extends AbstractService<PostsEmployee> implements IPostEmployee{

    public PostEmployee() {
        super(PostsEmployee.class);
    }

    @Override
    public void getAllEmployeesInterval() {
        SessionHibernate sh = SessionHibernate.getInstance();
        Session session = sh.retriver();
        session.createQuery("from PostsEmployee ");
        sh.putback(session);
    }

}