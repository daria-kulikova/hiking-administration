package app.models;

import app.utils.HibernateManager;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Routes")
public class Routes {

    @Id
    @GeneratedValue
    @Column (name = "ID", nullable = false)
    private int id;

    @Column (name = "Name", nullable = false)
    private String name;

    @Column (name = "Distance", nullable = false)
    private int distance;

    @Column (name = "Duration", nullable = false)
    private int duration;

    @Column (name = "Difficulty", nullable = true)
    private int difficulty;

    //@Column (name = "Type", nullable = true)
    //private int type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Type", insertable = false, updatable = false)
    private TypeOfHiking type;

    @OneToMany(mappedBy = "idRoute", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HikingSchedule> scheduleList;

    @OneToMany(mappedBy = "idRoute", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Places> placesList;

    public Routes() {
    }

    public Routes(int id, String name, int distance, int duration, int difficulty, TypeOfHiking type,
                  List<HikingSchedule> scheduleList, List<Places> placesList) {
        this.id = id;
        this.name = name;
        this.distance = distance;
        this.duration = duration;
        this.difficulty = difficulty;
        this.type = type;
        this.scheduleList = scheduleList;
        this.placesList = placesList;
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

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public TypeOfHiking getType() {
        return type;
    }

    public void setType(TypeOfHiking type) {
        this.type = type;
    }

    public List<HikingSchedule> getScheduleList() {
        return scheduleList;
    }

    public void setScheduleList(List<HikingSchedule> scheduleList) {
        this.scheduleList = scheduleList;
    }

    public List<Places> getPlacesList() {
        return placesList;
    }

    public void setPlacesList(List<Places> placesList) {
        this.placesList = placesList;
    }
}

