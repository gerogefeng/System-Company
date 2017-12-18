package by.psu.logical.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "passports")
public class Passport {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "nationality")
    private String nationality;

    @Column(name = "department")
    private String department;

    @Column(name = "serial_passport")
    private String serialPassport;

    @Column(name = "number_passport")
    private String numberPassport;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_out")
    private Date dateOut;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_in")
    private Date dateIn;

    @ManyToOne @JoinColumn(name = "fk_employee", nullable = false)
    private Employee employee;

    public Passport(String nationality, String department, String serialPassport, Date dateOut, Date dateIn) {
        this.nationality = nationality;
        this.department = department;
        this.serialPassport = serialPassport;
        this.dateOut = dateOut;
        this.dateIn = dateIn;
    }

    public Passport() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getSerialPassport() {
        return serialPassport;
    }

    public void setSerialPassport(String serialPassport) {
        this.serialPassport = serialPassport;
    }

    public String getNumberPassport() {
        return numberPassport;
    }

    public void setNumberPassport(String numberPassport) {
        this.numberPassport = numberPassport;
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

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
