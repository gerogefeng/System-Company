package by.psu.logical.model.order;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "organizations")
public class Organization {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
    private String title;

    @OneToMany(mappedBy = "organization")
    private Set<Order> orders;

    public Organization(String title) {
        this.title = title;
    }

    public Organization() {}

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

}
