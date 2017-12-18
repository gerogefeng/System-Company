package by.psu.logical.model;

import javax.persistence.*;
import java.util.List;
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
    private String urlPhoto;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private PrivateCard privateCard;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private User user;

    @OneToMany(mappedBy = "employee")
    private List<Passport> passports;

    @OneToMany(mappedBy = "employee")
    private List<DriverLicence> driverLicences;

    @ManyToMany(mappedBy = "employees")
    private List<Post> posts;

    public Employee(String name, String lastName, String patronymic, String urlPhoto) {
        this.name = name;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.urlPhoto = urlPhoto;
    }

    public Employee() {
    }

    public PrivateCard getPrivateCard() {
        return privateCard;
    }

    public void setPrivateCard(PrivateCard privateCard) {
        this.privateCard = privateCard;
    }

    public List<Passport> getPassports() {
        return passports;
    }

    public void setPassports(List<Passport> passports) {
        this.passports = passports;
    }

    public List<DriverLicence> getDriverLicences() {
        return driverLicences;
    }

    public void setDriverLicences(List<DriverLicence> driverLicences) {
        this.driverLicences = driverLicences;
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

    public String getUrlPhoto() {
        return urlPhoto;
    }

    public void setUrlPhoto(String urlPhoto) {
        this.urlPhoto = urlPhoto;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
