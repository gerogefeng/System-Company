package by.psu.logical.model.employee;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "driver_licences")
public class Driver {

    @Id
    @Column(name="id", unique=true, nullable=false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idDriver;

    @Column(name = "department")
    private String department;

    @Column(name = "number")
    private String number;

    @Temporal(TemporalType.DATE)
    @Column (name = "date_out")
    private Date dateOut;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_in")
    private Date dateIn;

    @ManyToOne
    @JoinColumn(name="id_employee", nullable=false)
    private Employee employee;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "driver_licences_category",
            joinColumns = { @JoinColumn(name = "fk_driverlicence") },
            inverseJoinColumns = { @JoinColumn(name = "fk_category") }
    )
    private List<Category> category;

    public Driver(String department, String number, Date dateOut, Date dateIn) {
        this.department = department;
        this.number = number;
        this.dateOut = dateOut;
        this.dateIn = dateIn;
    }

    public Driver() {
    }

    public List<Category> getCategory() {
        return category;
    }

    public void setCategory(List<Category> category) {
        this.category = category;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public int getIdDriver() {
        return idDriver;
    }

    public void setIdDriver(int idDriver) {
        this.idDriver = idDriver;
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

    public Date getDateOut() {
        return dateOut;
    }

    public void setDateOut(Date dateOut) {
        this.dateOut = dateOut;
    }

    public Date getDateIn() {
        return dateIn;
    }

    public void setDateIn(Date dateIn) {
        this.dateIn = dateIn;
    }
}
