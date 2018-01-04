package by.psu.logical.model.employee;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "private_cards")
public class Card {

    @Id
    @Column(name="id", unique=true, nullable=false)
    @GeneratedValue(generator="gen")
    @GenericGenerator(name="gen", strategy="foreign", parameters=@org.hibernate.annotations.Parameter(name="property", value="employee"))
    private int idCard;

    @Temporal(TemporalType.DATE)
    @Column(name = "data_birth")
    private Date birthday;

    @Column(name = "private_number_phone")
    private String numberPhone;

    @Column(name = "email")
    private String email;

    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Employee employee;

    public Card(Date birthday, String numberPhone, String email) {
        this.birthday = birthday;
        this.numberPhone = numberPhone;
        this.email = email;
    }

    public Card() { }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public int getIdCard() {
        return idCard;
    }

    public void setIdCard(int idCard) {
        this.idCard = idCard;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getNumberPhone() {
        return numberPhone;
    }

    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
