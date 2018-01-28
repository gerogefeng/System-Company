package by.psu.logical.service.action;

import by.psu.logical.model.departure.Departure;
import by.psu.logical.service.AbstractService;
import by.psu.logical.unit.SessionHibernate;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class DepartureService extends AbstractService<Departure> {
    public DepartureService() {
        super(Departure.class);
    }

    public void deleteDeparture(int id){
        Session session = SessionHibernate.getInstance().retriver();
        session.getTransaction().begin();
        Query query = session.createQuery("delete from Departure d WHERE d.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
        session.getTransaction().commit();
        SessionHibernate.getInstance().putback(session);
    }
}
