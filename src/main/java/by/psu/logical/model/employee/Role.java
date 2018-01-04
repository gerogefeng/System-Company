package by.psu.logical.model.employee;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idRole;

    @Column(name = "title")
    private String title;

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
    private List<User> user;

    public Role(String title) {
        this.title = title;
    }

    public Role() {
    }

    public List<User> getUser() {
        return user;
    }

    public void setUser(List<User> user) {
        this.user = user;
    }

    public int getIdRole() {
        return idRole;
    }

    public void setIdRole(int idRole) {
        this.idRole = idRole;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
