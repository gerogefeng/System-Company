package by.psu.logical.model.employee;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "employee_post")
public class PostsEmployee {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_post")
    private Post post;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_employee")
    private Employee employee;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_start")
    private Date dateStart;


    @Temporal(TemporalType.DATE)
    @Column(name = "date_end")
    private Date dateEnd;

    public PostsEmployee(Post post, Employee employee, Date dateStart, Date dateEnd) {
        this.post = post;
        this.employee = employee;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
    }


    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public PostsEmployee() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }
}
