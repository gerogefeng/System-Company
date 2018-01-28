package by.psu.logical.service.transport_services;

import by.psu.logical.model.employee.Employee;
import by.psu.logical.model.transport.Transport;
import by.psu.logical.service.AbstractService;
import by.psu.logical.unit.SessionHibernate;
import org.hibernate.Session;
import org.hibernate.query.Query;


import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;


public class TransportService extends AbstractService<Transport>{

    public TransportService() {
        super(Transport.class);
    }

    public List<Transport> readALLActive() {
        SessionHibernate sh = SessionHibernate.getInstance();
        Session session = sh.retriver();
        List<Transport> list = null;
        Query query = session.createQuery("from Transport t WHERE t.delete = false");
        list = query.getResultList();
        SessionHibernate.getInstance().putback(session);
        return list;
    }

    public void deleteTransport(int id){
        SessionHibernate sh = SessionHibernate.getInstance();
        Session session = sh.retriver();
        session.getTransaction().begin();
        Query query = session.createQuery("delete from Transport t WHERE t.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
        session.getTransaction().commit();
        SessionHibernate.getInstance().putback(session);
    }
}
