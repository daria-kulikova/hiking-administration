package app.models;

import java.sql.Date;
import java.util.Objects;

public class ScheduleList {

    private Integer id;
    private String name;
    private String type;
    private Date start;
    private Date finish;
    private Integer cost;

    public ScheduleList() {
    }

    public ScheduleList(Integer id, String name, String type, Date start, Date finish, Integer cost) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.start = start;
        this.finish = finish;
        this.cost = cost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScheduleList that = (ScheduleList) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(type, that.type) &&
                Objects.equals(start, that.start) &&
                Objects.equals(finish, that.finish) &&
                Objects.equals(cost, that.cost);
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}
