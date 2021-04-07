package app.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "TypeOfHiking")
public class TypeOfHiking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "ID", nullable = false)
    private int id;

    @Column (name = "Name", nullable = false)
    private String name;

    @Column (name = "DangerousSeasonStart", nullable = false)
    private int dangSeasonStart;

    @Column (name = "DangerousSeasonFinish", nullable = false)
    private int dangSeasonFinish;

    @OneToMany(mappedBy = "type", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Routes> routesList;

    public TypeOfHiking() {
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

    public int getDangSeasonStart() {
        return dangSeasonStart;
    }

    public void setDangSeasonStart(int dangSeasonStart) {
        this.dangSeasonStart = dangSeasonStart;
    }

    public int getDangSeasonFinish() {
        return dangSeasonFinish;
    }

    public void setDangSeasonFinish(int dangSeasonFinish) {
        this.dangSeasonFinish = dangSeasonFinish;
    }

    public List<Routes> getRoutesList() {
        return routesList;
    }

    public void setRoutesList(List<Routes> routesList) {
        this.routesList = routesList;
    }
}

