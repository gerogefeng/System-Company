package by.psu.logical.model.departure;

import by.psu.logical.model.employee.Post;
import by.psu.logical.model.employee.PostsEmployee;
import by.psu.logical.model.order.Order;

import javax.persistence.*;

@Entity
@Table(name = "departure")
public class Departure {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_order")
    private Order order;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_employee_post")
    private PostsEmployee postsEmployee;


    public Departure(Order order, PostsEmployee postsEmployee) {
        this.order = order;
        this.postsEmployee = postsEmployee;
    }

    public Departure() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public PostsEmployee getPostsEmployee() {
        return postsEmployee;
    }

    public void setPostsEmployee(PostsEmployee postsEmployee) {
        this.postsEmployee = postsEmployee;
    }
}
