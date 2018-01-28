package by.psu.logical.service.action;

import by.psu.logical.model.departure.Departure;
import by.psu.logical.model.transport.Transport;
import by.psu.logical.model.transport.TransportRental;
import by.psu.logical.service.AbstractService;
import by.psu.logical.service.transport_services.TransportService;
import by.psu.logical.unit.SessionHibernate;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.EntityNotFoundException;
import javax.persistence.TemporalType;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Transactional
public class TransportRentalService extends AbstractService<TransportRental> {
    public TransportRentalService() {
        super(TransportRental.class);
    }

    private TransportService transportService = new TransportService();

    public List<Transport> readALLIntervalDate(Date start, Date end) {

        Session session = SessionHibernate.getInstance().retriver();

        List<Transport> transports = transportService.readALLActive();
        System.out.println(transports);


        Query query = session.createQuery("from TransportRental pe WHERE pe.delete = FALSE and ((pe.dateStart BETWEEN :startDate AND :endDate) OR (pe.dateEnd BETWEEN :startDate AND :endDate) OR " +
                "(:startDate BETWEEN pe.dateStart AND pe.dateEnd) OR (:endDate BETWEEN pe.dateStart AND pe.dateEnd))");

        query.setParameter("startDate", start, TemporalType.DATE);
        query.setParameter("endDate", end, TemporalType.DATE);


        List<TransportRental> transportRentals = query.getResultList();
        System.out.println(transportRentals);

        if (!transportRentals.isEmpty()) {
            for (TransportRental pe : transportRentals){
                Transport transport = pe.getTransport();
                for (int i = 0; i < transports.size(); i++){
                    if(transports.get(i).getId() == transport.getId()) {
                        transports.remove(i);
                    }
                }
            }
        }

        SessionHibernate.getInstance().putback(session);
        return transports;
    }
}
