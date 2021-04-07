package app.models;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "HikingSchedule")
public class HikingSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "ID", nullable = false)
    private int id;

    @Column (name = "Start", nullable = false)
    private Date start;

    @Column (name = "Finish", nullable = false)
    private Date finish;

    @Column (name = "NumberOfParticipants", nullable = false)
    private int numberOfParticipants;

    @Column (name = "Cost", nullable = false)
    private int cost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_Route")
    private Routes idRoute;

    /*@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "EquipmentForHiking",
            joinColumns = @JoinColumn(name = "ID_Hiking"),
            inverseJoinColumns = @JoinColumn(name = "ID_Equipment"))
    private List<Equipment> equipmentList;*/

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "ParticipantsOfHiking",
            joinColumns = @JoinColumn(name = "ID_Hiking"),
            inverseJoinColumns = @JoinColumn(name = "ID_Participant"))
    private List<Participants> participantsList;

    public HikingSchedule() {
    }

    public HikingSchedule(int id, Date start, Date finish, int numberOfParticipants, int cost, Routes idRoute/*, List<Equipment> equipmentList*/) {
        this.id = id;
        this.start = start;
        this.finish = finish;
        this.numberOfParticipants = numberOfParticipants;
        this.cost = cost;
        this.idRoute = idRoute;
        //this.equipmentList = equipmentList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HikingSchedule that = (HikingSchedule) o;
        return id == that.id &&
                numberOfParticipants == that.numberOfParticipants &&
                cost == that.cost &&
                Objects.equals(start, that.start) &&
                Objects.equals(finish, that.finish) &&
                Objects.equals(idRoute, that.idRoute) &&
                //Objects.equals(equipmentList, that.equipmentList) &&
                Objects.equals(participantsList, that.participantsList);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getFinish() {
        return finish;
    }

    public void setFinish(Date finish) {
        this.finish = finish;
    }


    public int getNumberOfParticipants() {
        return numberOfParticipants;
    }

    public void setNumberOfParticipants(int numberOfParticipants) {
        this.numberOfParticipants = numberOfParticipants;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public Routes getIdRoute() {
        return idRoute;
    }

    public void setIdRoute(Routes idRoute) {
        this.idRoute = idRoute;
    }

    /*public List<Equipment> getEquipmentList() {
        return equipmentList;
    }

    public void setEquipmentList(List<Equipment> equipmentList) {
        this.equipmentList = equipmentList;
    }*/

    public List<Participants> getParticipantsList() {
        return participantsList;
    }

    public void setParticipantsList(List<Participants> participantsList) {
        this.participantsList = participantsList;
    }

    //@Override
    //public String toString(){
    //    return String.format("%d %s %s %s %d %d %d %d %d", id, surname, name, patronymic, i, ii, iii, iv, total);
    //}
}

