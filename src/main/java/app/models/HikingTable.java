package app.models;

import java.sql.Date;

public class HikingTable {

    private Integer idHiking;
    private String nameHiking;
    private Date startHiking;

    public HikingTable() {
    }

    public Integer getIdHiking() {
        return idHiking;
    }

    public void setIdHiking(Integer idHiking) {
        this.idHiking = idHiking;
    }

    public String getNameHiking() {
        return nameHiking;
    }

    public void setNameHiking(String nameHiking) {
        this.nameHiking = nameHiking;
    }

    public Date getStartHiking() {
        return startHiking;
    }

    public void setStartHiking(Date startHiking) {
        this.startHiking = startHiking;
    }
}
