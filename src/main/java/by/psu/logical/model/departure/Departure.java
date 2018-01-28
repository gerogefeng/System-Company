package by.psu.logical.model.departure;

import by.psu.logical.model.employee.PostsEmployee;
import by.psu.logical.model.instrument.InstrumentDeparture;
import by.psu.logical.model.order.Order;
import by.psu.logical.model.transport.TransportRental;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "departure")
public class Departure {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_order")
    private Order order;

    @ManyToOne(cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH
    })
    @JoinColumn(name = "id_employee_post")
    private PostsEmployee postsEmployee;

    @OneToMany(mappedBy = "departure")
    private List<TransportRental> transportRentals;

    @OneToMany(mappedBy = "departure")
    private List<InstrumentDeparture> instrumentDepartures;

    @Column(name = "status")
    private int status = 1;

    @Column(name = "delete_status")
    private boolean delete = false;

    public Departure(Order order, PostsEmployee postsEmployee) {
        this.order = order;
        this.postsEmployee = postsEmployee;
    }

    public Departure() {}

    public List<InstrumentDeparture> getInstrumentDepartures() {
        return instrumentDepartures;
    }

    public void setInstrumentDepartures(List<InstrumentDeparture> instrumentDepartures) {
        this.instrumentDepartures = instrumentDepartures;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public boolean isDelete() {
        return delete;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
    }

    public List<TransportRental> getTransportRentals() {
        return transportRentals;
    }

    public void setTransportRentals(List<TransportRental> transportRentals) {
        this.transportRentals = transportRentals;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public PostsEmployee getPostsEmployee() {
        return postsEmployee;
    }

    public void setPostsEmployee(PostsEmployee postsEmployee) {
        this.postsEmployee = postsEmployee;
    }
}
