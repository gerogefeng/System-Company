package by.psu.logical.model.transport;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "mark")
public class MarkAuto {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String title;

    @OneToMany(mappedBy = "markAuto", cascade = CascadeType.ALL)
    private List<ModelAuto> modelAutos;

    @OneToMany(mappedBy = "markAuto", cascade = CascadeType.ALL)
    private Set<Transport> transports;

    public MarkAuto(String title) {
        this.title = title;
    }

    public MarkAuto() {
    }

    public MarkAuto(String title, List<ModelAuto> modelAutos, Set<Transport> transports) {
        this.title = title;
        this.modelAutos = modelAutos;
        this.transports = transports;
    }

    public Set<Transport> getTransports() {
        return transports;
    }

    public void setTransports(Set<Transport> transports) {
        this.transports = transports;
    }

    public List<ModelAuto> getModelAutos() {
        return modelAutos;
    }

    public void setModelAutos(List<ModelAuto> modelAutos) {
        this.modelAutos = modelAutos;
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

    @Override
    public String toString() {
        return title;
    }
}
