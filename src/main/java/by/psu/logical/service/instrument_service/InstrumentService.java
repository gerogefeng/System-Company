package by.psu.logical.service.instrument_service;

import by.psu.logical.model.instrument.Instrument;
import by.psu.logical.model.transport.Transport;
import by.psu.logical.service.AbstractService;
import by.psu.logical.unit.SessionHibernate;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class InstrumentService extends AbstractService<Instrument>{
    public InstrumentService() {
        super(Instrument.class);
    }

    public List<Instrument> readALLActive() {
        SessionHibernate sh = SessionHibernate.getInstance();
        Session session = sh.retriver();
        List<Instrument> list = null;
        Query query = session.createQuery("from Instrument i WHERE i.delete = false");
        list = query.getResultList();
        SessionHibernate.getInstance().putback(session);
        return list;
    }

    public void deleteInstrument(int id){
        SessionHibernate sh = SessionHibernate.getInstance();
        Session session = sh.retriver();
        session.getTransaction().begin();
        Query query = session.createQuery("delete from Instrument i WHERE i.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
        session.getTransaction().commit();
        SessionHibernate.getInstance().putback(session);
    }
}
