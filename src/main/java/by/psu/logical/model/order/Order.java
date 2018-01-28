package by.psu.logical.model.order;

import javax.persistence.*;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne @JoinColumn(name = "id_place")
    private Place place;

    @ManyToOne @JoinColumn(name = "id_organization")
    private Organization organization;

    @ManyToOne @JoinColumn(name = "id_report")
    private Report report;

    @Column(name = "delete_status")
    private boolean delete;

    public Order(Place place, Organization organization, Report report) {
        this.place = place;
        this.organization = organization;
        this.report = report;
    }

    public Order() {}

    public void setDelete(boolean delete) {
        this.delete = delete;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }

    public boolean isDelete() {
        return delete;
    }

    @Override
    public String toString() {
        return "Организация - " + organization.getTitle() +
                ", Место проведения - " + place.getTitle();
    }
}
