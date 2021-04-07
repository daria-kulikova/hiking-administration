package app.models;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "Places")
public class Places {

    @Id
    @GeneratedValue
    @Column (name = "ID", nullable = false)
    private int id;

    @Column (name = "Name", nullable = false)
    private String name;

    //@Column (name = "ID_Route", nullable = false)
    //private int idRoute;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID", insertable = false, updatable = false)
    private Routes idRoute;

    @Column (name = "NumberInOrder", nullable = false)
    private int numberInOrder;

    public Places() {
    }

    public Places(int id, String name, Routes idRoute, int numberInOrder) {
        this.id = id;
        this.name = name;
        this.idRoute = idRoute;
        this.numberInOrder = numberInOrder;
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

    public int getNumberInOrder() {
        return numberInOrder;
    }

    public void setNumberInOrder(int numberInOrder) {
        this.numberInOrder = numberInOrder;
    }

    public Routes getIdRoute() {
        return idRoute;
    }

    public void setIdRoute(Routes idRoute) {
        this.idRoute = idRoute;
    }
}

