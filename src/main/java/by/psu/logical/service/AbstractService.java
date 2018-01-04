package by.psu.logical.service;

import by.psu.logical.dao.ICRUD;
import by.psu.logical.unit.SessionHibernate;
import org.hibernate.Session;

import java.util.List;

public abstract class AbstractService<T> implements ICRUD<T> {

    private Class<T> type = null;

    public AbstractService(Class<T> tClass) {
        this.type = tClass;
    }

    @Override
    public void create(T object){
        Session session = SessionHibernate.getInstance().retriver();
        try {
            session.getTransaction().begin();
            session.saveOrUpdate(object);
            session.getTransaction().commit();
        } catch (Exception e){
            e.printStackTrace();
        }
        session.close();
    }

    @Override
    public T read(int id){
        Session session = SessionHibernate.getInstance().retriver();
        T object = session.get(type, id);
        SessionHibernate.getInstance().putback(session);
        return object;
    }

    @Override
    public void update(T object){
        try {
            Session session = SessionHibernate.getInstance().retriver();
            session.update(object);
            SessionHibernate.getInstance().putback(session);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void delete(T object){
        Session session = SessionHibernate.getInstance().retriver();
        session.getTransaction().begin();
        session.delete(object);
        session.getTransaction().commit();
        SessionHibernate.getInstance().putback(session);
    }

    @Override
    public List<T> readALL() {
        Session session = SessionHibernate.getInstance().retriver();
        List list  = session.createQuery("from "+ type.getSimpleName()).list();
        SessionHibernate.getInstance().putback(session);
        return list;
    }
}
