package by.psu.logical.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "category")
public class Category {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
    private String category;

    @ManyToMany(mappedBy = "categories")
    private List<DriverLicence> driverLicences;

    public Category(String category) {
        this.category = category;
    }

    public Category() { }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<DriverLicence> getDriverLicences() {
        return driverLicences;
    }

    public void setDriverLicences(List<DriverLicence> driverLicences) {
        this.driverLicences = driverLicences;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
