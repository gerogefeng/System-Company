package by.psu.logical.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "driverlicences")
public class DriverLicence {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "department")
    private String department;

    @Column(name = "number")
    private String number;

    @Column(name = "date_in")
    private Date dateIn;

    @Column(name = "date_out")
    private Date dateOut;

    @ManyToOne @JoinColumn(name = "fk_employee", nullable = false)
    private Employee employee;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "appendcategory",
            joinColumns = { @JoinColumn(name = "id") },
            inverseJoinColumns = { @JoinColumn(name = "fk_driverlicence") }
    )
    private List<Category> categories;

    public DriverLicence(String department, String number, Date dateIn, Date dateOut) {
        this.department = department;
        this.number = number;
        this.dateIn = dateIn;
        this.dateOut = dateOut;
    }

    public DriverLicence() { }


    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Date getDateIn() {
        return dateIn;
    }

    public void setDateIn(Date dateIn) {
        this.dateIn = dateIn;
    }

    public Date getDateOut() {
        return dateOut;
    }

    public void setDateOut(Date dateOut) {
        this.dateOut = dateOut;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
