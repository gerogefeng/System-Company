package by.psu.logical.model.instrument;

import by.psu.logical.model.departure.Departure;
import by.psu.logical.model.employee.Employee;
import by.psu.logical.model.employee.Post;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "equipment_departure")
public class InstrumentDeparture {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_start")
    private Date dateStart;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_end")
    private Date dateEnd;

    @ManyToOne
    @JoinColumn(name = "id_departure")
    private Departure departure;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_equipment")
    private Instrument instrument;

    @Column(name = "delete_status")
    private boolean delete;

    public InstrumentDeparture(Date dateStart, Date dateEnd, Departure departure, Instrument instrument) {
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.departure = departure;
        this.instrument = instrument;
    }

    public InstrumentDeparture() {
    }

    public boolean isDelete() {
        return delete;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Departure getDeparture() {
        return departure;
    }

    public void setDeparture(Departure departure) {
        this.departure = departure;
    }

    public Instrument getInstrument() {
        return instrument;
    }

    public void setInstrument(Instrument instrument) {
        this.instrument = instrument;
    }
}
