package by.psu.logical.model.transport;

import by.psu.logical.model.departure.Departure;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "transport_rental")
public class TransportRental {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_transport")
    private Transport transport;

    @ManyToOne
    @JoinColumn(name = "id_departure")
    private Departure departure;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_start")
    private Date dateStart;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_end")
    private Date dateEnd;

    @Column(name = "delete_status")
    private boolean delete;

    public TransportRental(Transport transport, Departure departure, Date dateStart, Date dateEnd) {
        this.transport = transport;
        this.departure = departure;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
    }

    public TransportRental() {
    }

    public boolean isDelete() {
        return delete;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Transport getTransport() {
        return transport;
    }

    public void setTransport(Transport transport) {
        this.transport = transport;
    }

    public Departure getDeparture() {
        return departure;
    }

    public void setDeparture(Departure departure) {
        this.departure = departure;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    @Override
    public String toString() {
        return "TransportRental{" +
                "id=" + id +
                ", transport=" + transport +
                ", departure=" + departure +
                ", dateStart=" + dateStart +
                ", dateEnd=" + dateEnd +
                '}';
    }
}
