package app.models;

import javax.persistence.*;


@Table(name = "EquipmentForHiking")
public class EquipmentForHiking {

    @Id
    @GeneratedValue
    @Column (name = "ID", nullable = false)
    private int id;

    @Column (name = "ID_Hiking", nullable = false)
    private int idHiking;

    @Column (name = "ID_Equipment", nullable = false)
    private int idEquipment;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdHiking() {
        return idHiking;
    }

    public void setIdHiking(int idHiking) {
        this.idHiking = idHiking;
    }

    public int getIdEquipment() {
        return idEquipment;
    }

    public void setIdEquipment(int idEquipment) {
        this.idEquipment = idEquipment;
    }
}
