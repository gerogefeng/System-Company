package by.psu.logical.service;

import by.psu.logical.dao.ICRUD;
import by.psu.logical.unit.SessionHibernate;
import org.hibernate.Session;

import java.util.List;

public abstract class AbstractService<T> implements ICRUD<T> {

    private Class<T> type = null;

    AbstractService(Class<T> tClass) {
        this.type = tClass;
    }

    @Override
    public void create(T object) {
        Session session = SessionHibernate.getInstance().getSession();
        session.getTransaction().begin();
        session.persist(object);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public T read(int id) {
        Session session = SessionHibernate.getInstance().getSession();
        T object = session.get(type, id);
        session.close();
        return object;
    }

    @Override
    public void update(T object) {
        Session session = SessionHibernate.getInstance().getSession();
        session.update(object);
        session.close();
    }

    @Override
    public void delete(T object) {
        Session session = SessionHibernate.getInstance().getSession();
        session.delete(object);
        session.close();
    }

    @Override
    public List<T> readALL() {
        Session session = SessionHibernate.getInstance().getSession();
        List list  = session.createQuery("from "+ type.getSimpleName()).list();
        session.close();
        return list;
    }
}
