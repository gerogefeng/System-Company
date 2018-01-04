package by.psu.logical.model.order;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "places")
public class Place {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
    private String title;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_begin")
    private Date dateBegin;

    @Temporal(TemporalType.DATE)
    @Column(name = "data_end")
    private Date dateEnd;

    @OneToMany(mappedBy = "place")
    private Set<Order> orders;

    public Place(String title, Date dateBegin, Date dateEnd) {
        this.title = title;
        this.dateBegin = dateBegin;
        this.dateEnd = dateEnd;
    }

    public Place() {}

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDateBegin() {
        return dateBegin;
    }

    public void setDateBegin(Date dateBegin) {
        this.dateBegin = dateBegin;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }
}
