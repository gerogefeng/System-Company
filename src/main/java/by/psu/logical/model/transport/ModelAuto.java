package by.psu.logical.model.transport;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "model")
public class ModelAuto {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String title;

    @ManyToOne
    @JoinColumn(name = "id_mark")
    private MarkAuto markAuto;

    @OneToMany(mappedBy = "modelAuto", cascade = CascadeType.ALL)
    private Set<Transport> transports;

    public ModelAuto(String title, MarkAuto markAuto) {
        this.title = title;
        this.markAuto = markAuto;
    }

    public ModelAuto() {
    }

    public Set<Transport> getTransports() {
        return transports;
    }

    public void setTransports(Set<Transport> transports) {
        this.transports = transports;
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

    public MarkAuto getMarkAuto() {
        return markAuto;
    }

    public void setMarkAuto(MarkAuto markAuto) {
        this.markAuto = markAuto;
    }

    @Override
    public String toString() {
        return title;
    }
}
