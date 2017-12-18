package by.psu.logical.model;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "privatecards")
public class PrivateCard {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Temporal(TemporalType.DATE)
    @Column(name = "data_birth")
    private Date birthday;

    @Column(name = "private_number_phone")
    private String phoneNumber;

    @Column(name = "email")
    private String card;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private Employee employee;

    public PrivateCard(Date birthday, String phoneNumber, String card) {
        this.birthday = birthday;
        this.phoneNumber = phoneNumber;
        this.card = card;
    }

    public PrivateCard() {
    }

    public Employee getEmployee() {
        return employee;
    }


    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }
}
