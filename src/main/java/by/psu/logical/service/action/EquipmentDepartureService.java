package by.psu.logical.service.action;

import by.psu.logical.model.departure.Departure;
import by.psu.logical.model.employee.Employee;
import by.psu.logical.model.employee.PostsEmployee;
import by.psu.logical.model.instrument.Instrument;
import by.psu.logical.model.instrument.InstrumentDeparture;
import by.psu.logical.model.order.Order;
import by.psu.logical.service.AbstractService;
import by.psu.logical.service.instrument_service.InstrumentService;
import by.psu.logical.unit.SessionHibernate;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.TemporalType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EquipmentDepartureService extends AbstractService<InstrumentDeparture> {

    public EquipmentDepartureService() {
        super(InstrumentDeparture.class);
    }

    private InstrumentService instrumentService = new InstrumentService();
    public List<Instrument> readALLIntervalDate(Date start, Date end) {

        SessionHibernate sh = SessionHibernate.getInstance();
        Session session = sh.retriver();
        List<Instrument> instruments = instrumentService.readALLActive();

        Query query = session.createQuery("from InstrumentDeparture pe WHERE pe.delete = FALSE and ((pe.dateStart BETWEEN :startDate AND :endDate) OR (pe.dateEnd BETWEEN :startDate AND :endDate) OR " +
                "(:startDate BETWEEN pe.dateStart AND pe.dateEnd) OR (:endDate BETWEEN pe.dateStart AND pe.dateEnd))");

        query.setParameter("startDate", start, TemporalType.DATE);
        query.setParameter("endDate", end, TemporalType.DATE);

        List<InstrumentDeparture> instrumentDeparture = query.getResultList();

        if (!instrumentDeparture.isEmpty()) {
            for (InstrumentDeparture pe : instrumentDeparture){
                Instrument instrument = pe.getInstrument();
                for (int i = 0; i < instruments.size(); i++){
                    if(instruments.get(i).getId() == instrument.getId()) {
                        instruments.remove(i);
                    }
                }
            }
        }

        SessionHibernate.getInstance().putback(session);
        return instruments;
    }

    public List<Instrument> readALLWithDeparture(Departure departure) {

        SessionHibernate sh = SessionHibernate.getInstance();
        Session session = sh.retriver();
        List<Instrument> instruments = new ArrayList<>();

        Query query = session.createQuery("from InstrumentDeparture id WHERE id.departure.id = :departure");
        query.setParameter("departure", departure.getId());

        List<InstrumentDeparture> instrumentDeparture = query.getResultList();

        for (InstrumentDeparture anInstrumentDeparture : instrumentDeparture) {
            instruments.add(anInstrumentDeparture.getInstrument());
        }
        SessionHibernate.getInstance().putback(session);
        return instruments;
    }
}
