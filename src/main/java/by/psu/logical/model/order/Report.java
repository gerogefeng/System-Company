package by.psu.logical.model.order;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "reports")
public class Report {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "resource_document")
    private String resource;

    @OneToMany(mappedBy = "report")
    private Set<Order> orders;

    public Report(String resource) {
        this.resource = resource;
    }

    public Report() {
    }

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

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }
}