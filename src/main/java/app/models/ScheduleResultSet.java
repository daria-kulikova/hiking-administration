package app.models;

import java.sql.Date;

public class ScheduleResultSet {

    private Integer id;
    private Integer idRoute;
    private Date start;
    private Date finish;
    private Integer cost;

    public ScheduleResultSet() {
    }

    public ScheduleResultSet(Integer id, Integer idRoute, Date start, Date finish, Integer cost) {
        this.id = id;
        this.idRoute = idRoute;
        this.start = start;
        this.finish = finish;
        this.cost = cost;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdRoute() {
        return idRoute;
    }

    public void setIdRoute(Integer idRoute) {
        this.idRoute = idRoute;
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

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }
}
