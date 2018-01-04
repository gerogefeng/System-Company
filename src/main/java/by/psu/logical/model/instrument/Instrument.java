package by.psu.logical.model.instrument;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "equipment")
public class Instrument {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "weight")
    private int weight;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_buy")
    private Date dateBuy;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_end")
    private Date dateEnd;

    public Instrument(String title, int weight, Date dateBuy, Date dateEnd) {
        this.title = title;
        this.weight = weight;
        this.dateBuy = dateBuy;
        this.dateEnd = dateEnd;
    }

    public Instrument() {
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

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public Date getDateBuy() {
        return dateBuy;
    }

    public void setDateBuy(Date dateBuy) {
        this.dateBuy = dateBuy;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }
}
