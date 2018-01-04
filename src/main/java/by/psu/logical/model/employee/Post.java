package by.psu.logical.model.employee;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "posts")
public class Post {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
    private String title;

    @OneToMany(mappedBy = "post")
    private Set<PostsEmployee> postsEmployees;

    public Post(String title) {
        this.title = title;
    }

    public Post() {
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
