package by.psu.logical.model.employee;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "patronymic")
    private String patronymic;

    @Column(name = "url_photo")
    private String avatar;

    @OneToMany(mappedBy="employee")
    private Set<Passport> passport;

    @OneToMany(mappedBy="employee")
    private Set<Driver> driver;

    @OneToOne(mappedBy="employee",cascade=CascadeType.ALL)
    private Card card;

    @OneToMany(mappedBy = "employee")
    private Set<PostsEmployee> postsEmployees;

    @Column(name = "delete_status")
    private boolean delete;

    public Employee(String name, String lastName, String patronymic, String avatar, Card card) {
        this.name = name;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.avatar = avatar;
        this.card = card;
    }

    public Employee() {
    }

    public boolean isDelete() {
        return delete;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
    }

    public Set<Passport> getPassport() {
        return passport;
    }

    public void setPassport(Set<Passport> passport) {
        this.passport = passport;
    }

    public Set<Driver> getDriver() {
        return driver;
    }

    public void setDriver(Set<Driver> driver) {
        this.driver = driver;
    }

    public Set<PostsEmployee> getPostsEmployees() {
        return postsEmployees;
    }

    public void setPostsEmployees(Set<PostsEmployee> postsEmployees) {
        this.postsEmployees = postsEmployees;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    @Override
    public String toString() {
        return getName() + " " + getLastName() + " " + getPatronymic();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee)) return false;
        Employee employee = (Employee) o;
        return getId() == employee.getId() &&
                Objects.equals(getName(), employee.getName()) &&
                Objects.equals(getLastName(), employee.getLastName()) &&
                Objects.equals(getPatronymic(), employee.getPatronymic()) &&
                Objects.equals(getAvatar(), employee.getAvatar()) &&
                Objects.equals(getPassport(), employee.getPassport()) &&
                Objects.equals(getDriver(), employee.getDriver()) &&
                Objects.equals(getCard(), employee.getCard()) &&
                Objects.equals(getPostsEmployees(), employee.getPostsEmployees());
    }

    @Override
    public int hashCode() {
        return getId();
    }
}
