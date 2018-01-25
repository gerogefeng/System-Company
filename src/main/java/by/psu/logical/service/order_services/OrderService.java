package by.psu.logical.service.order_services;

import by.psu.gui.Converter;
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
        Query query = session.createQuery("from Order o WHERE o.place.dateEnd > :date");
        query.setParameter("date", new Date(), TemporalType.DATE);
        orders = query.getResultList();
        SessionHibernate.getInstance().putback(session);
        return orders;
    }
}
