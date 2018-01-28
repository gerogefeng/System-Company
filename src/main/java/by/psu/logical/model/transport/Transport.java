package by.psu.logical.model.transport;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "transports")
public class Transport {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "engine")
    private String engineType;

    @Column(name = "capacity_kg")
    private int capacity;

    @Column(name = "seats")
    private int seats;

    @Column(name = "status")
    private int status;

    @Column(name = "photo_url")
    private String photoURL;

    @ManyToOne
    @JoinColumn(name = "id_mark")
    private MarkAuto markAuto;

    @ManyToOne
    @JoinColumn(name = "id_model")
    private ModelAuto modelAuto;

    @OneToMany(mappedBy = "departure")
    private Set<TransportRental> departures;

    @Column(name = "delete_status")
    private boolean delete;

    public Transport(String engineType, int capacity, int status, String photoURL, MarkAuto markAuto, ModelAuto modelAuto) {
        this.engineType = engineType;
        this.capacity = capacity;
        this.status = status;
        this.photoURL = photoURL;
        this.markAuto = markAuto;
        this.modelAuto = modelAuto;
    }

    public Transport() {
    }

    public boolean isDelete() {
        return delete;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
    }

    public Set<TransportRental> getDepartures() {
        return departures;
    }

    public void setDepartures(Set<TransportRental> departures) {
        this.departures = departures;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEngineType() {
        return engineType;
    }

    public void setEngineType(String engineType) {
        this.engineType = engineType;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public MarkAuto getMarkAuto() {
        return markAuto;
    }

    public void setMarkAuto(MarkAuto markAuto) {
        this.markAuto = markAuto;
    }

    public ModelAuto getModelAuto() {
        return modelAuto;
    }

    public void setModelAuto(ModelAuto modelAuto) {
        this.modelAuto = modelAuto;
    }

    @Override
    public String toString() {
        return getMarkAuto().getTitle() + " " + getModelAuto().getTitle();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Transport)) return false;
        Transport transport = (Transport) o;
        return getId() == transport.getId() &&
                getCapacity() == transport.getCapacity() &&
                getSeats() == transport.getSeats() &&
                getStatus() == transport.getStatus() &&
                Objects.equals(getEngineType(), transport.getEngineType()) &&
                Objects.equals(getPhotoURL(), transport.getPhotoURL()) &&
                Objects.equals(getMarkAuto(), transport.getMarkAuto()) &&
                Objects.equals(getModelAuto(), transport.getModelAuto()) &&
                Objects.equals(getDepartures(), transport.getDepartures());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getEngineType(), getCapacity(), getSeats(), getStatus(), getPhotoURL(), getMarkAuto(), getModelAuto(), getDepartures());
    }
}
