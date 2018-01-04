package by.psu.logical.service.employee_services;

import by.psu.logical.dao.IUser;
import by.psu.logical.model.employee.User;
import by.psu.logical.service.AbstractService;
import by.psu.logical.unit.SessionHibernate;
import org.apache.log4j.Logger;
import org.hibernate.Session;

import javax.persistence.Query;

public class UserService extends AbstractService<User> implements IUser {

    private final static Logger LOGGER = Logger.getLogger(UserService.class);

    public UserService() {
        super(User.class);
    }

    @Override
    public boolean exists(String login, String password) {
        Session session = SessionHibernate.getInstance().retriver();
        Query query = session.createQuery("from User u WHERE u.login = :login AND u.password = crypt(:pass, u.password)");
        query.setParameter("login", login);
        query.setParameter("pass", password);
        boolean res = !query.getResultList().isEmpty();
        SessionHibernate.getInstance().putback(session);
        return res;
    }
}
