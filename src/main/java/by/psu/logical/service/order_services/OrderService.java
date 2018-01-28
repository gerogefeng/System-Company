package by.psu.logical.service.order_services;

import by.psu.logical.model.order.Order;
import by.psu.logical.service.AbstractService;
import by.psu.logical.unit.SessionHibernate;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.TemporalType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderService extends AbstractService<Order> {
    public OrderService() {
        super(Order.class);
    }

    public List<Order> readAllActiveOrder(){
        Session session = SessionHibernate.getInstance().retriver();
        List<Order> orders = new ArrayList<>();
        Query query = session.createQuery("from Order o WHERE o.place.dateEnd > :date AND o.delete = false");
        query.setParameter("date", new Date(), TemporalType.DATE);
        orders = query.getResultList();
        SessionHibernate.getInstance().putback(session);
        return orders;
    }

    public void deleteOrder(int id) {
        Session session = SessionHibernate.getInstance().retriver();
        session.getTransaction().begin();
        Query query = session.createQuery("delete from Order ord where ord.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
        session.getTransaction().commit();
        SessionHibernate.getInstance().putback(session);
    }
}
