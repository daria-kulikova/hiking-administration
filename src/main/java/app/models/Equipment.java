package app.models;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@SqlResultSetMapping(name = "Salaries",
        classes = @ConstructorResult(
                targetClass = Salaries.class,
                columns = {
                        @ColumnResult(name = "ID", type = Integer.class),
                        @ColumnResult(name = "Surname", type = String.class),
                        @ColumnResult(name = "Name", type = String.class),
                        @ColumnResult(name="Patronymic", type=String.class),
                        @ColumnResult(name="I", type = Integer.class),
                        @ColumnResult(name="II", type=Integer.class),
                        @ColumnResult(name="III", type= Integer.class),
                        @ColumnResult(name="IV", type = Integer.class),
                        @ColumnResult(name="Total", type = Integer.class)
                }
        )
)

@SqlResultSetMapping(name = "ScheduleResultSet",
        classes = @ConstructorResult(
                targetClass = ScheduleResultSet.class,
                columns = {
                        @ColumnResult(name = "ID", type = Integer.class),
                        @ColumnResult(name = "ID_Route", type = Integer.class),
                        @ColumnResult(name = "Start", type = Date.class),
                        @ColumnResult(name = "Finish", type = Date.class),
                        @ColumnResult(name = "Cost", type = Integer.class)
                }
        )
)

//@SqlResultSetMapping(name = "User",
//        classes = @ConstructorResult(
//                targetClass = User.class,
//                columns = {
//                        @ColumnResult(name="UserName", type = String.class),
//                        @ColumnResult(name = "RoleName", type = String.class)
//                }
//                ))

@SqlResultSetMapping(name = "User",
        classes = @ConstructorResult(
                targetClass = String.class,
                columns = {
                        @ColumnResult(name = "RoleName", type = String.class)
                }
        )
)

@Table(name = "Equipment")
public class Equipment {

    @Id
    @GeneratedValue
    @Column (name = "ID", nullable = false)
    private int id;

    @Column (name = "Name", nullable = false)
    private String name;

    @Column (name = "Number", nullable = false)
    private int number;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "EquipmentForHiking",
            joinColumns = @JoinColumn(name = "ID_Equipment"),
            inverseJoinColumns = @JoinColumn(name = "ID_Hiking"))
    private List<HikingSchedule> scheduleList;

    public Equipment() {
    }

    public Equipment(int id, String name, int number, List<HikingSchedule> scheduleList) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.scheduleList = scheduleList;
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

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
