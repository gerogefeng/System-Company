package by.psu.logical.model.employee;

import by.psu.logical.model.employee.Employee;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="passports")
public class Passport {

    @Id
    @Column(name="id", unique=true, nullable=false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPassport;

    @Column(name = "nationality")
    private String nationality;

    @Column(name = "department")
    private String department;

    @Column(name = "serial_passport")
    private String serialPassport;

    @Column(name = "number_passport")
    private String numberPassport;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_in")
    private Date dateIn;

    @ManyToOne
    @JoinColumn(name="id_employee", nullable=false)
    private Employee employee;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_out")
    private Date dateOut;

    public Passport(String nationality, String department, String serialPassport, String numberPassport, Date dateIn, Date dateOut) {
        this.nationality = nationality;
        this.department = department;
        this.serialPassport = serialPassport;
        this.numberPassport = numberPassport;
        this.dateIn = dateIn;
        this.dateOut = dateOut;
    }

    public Passport() {}

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public int getIdPassport() {
        return idPassport;
    }

    public void setIdPassport(int idPassport) {
        this.idPassport = idPassport;
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
}
