package app.models;

import org.hibernate.validator.constraints.Email;

import javax.crypto.Mac;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "Participants")
public class Participants {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "ID", nullable = false)
    private int id;

    @NotNull(message = "Фамилия должна быть задана!")
    @Column (name = "Surname", nullable = false)
    private String surname;

    @NotNull(message = "Имя должно быть задано!")
    @Column (name = "Name", nullable = false)
    private String name;

    @Column (name = "Patronymic", nullable = true)
    private String patronymic;

    @NotNull(message = "Номер телефона должен быть задан!")
    @Pattern(regexp = "(79[0-9]{9})", message = "Некорректный номер телефона")
    @Column (name = "PhoneNumber", nullable = false)
    private String phoneNumber;

    @NotNull(message = "e-Mail должен быть задан!")
    @Email(message = "Некорректный e-mail!")
    @Column (name = "e_mail", nullable = false)
    private String eMail;

    @Column (name = "SportsCategory", nullable = true)
    private String sportsCategory;

    @NotNull(message = "Должность должн быть задана!")
    @Column (name = "Position", nullable = false)
    private int position;

    @Column (name = "Birth", nullable = true)
    private Date birth;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "ParticipantsOfHiking",
            joinColumns = @JoinColumn(name = "ID_Participant"),
            inverseJoinColumns = @JoinColumn(name = "ID_Hiking"))
    private List<HikingSchedule> hikingScheduleList;


    public Participants() {
    }

    public Participants(String surname, String name, String patronymic, String phoneNumber, String eMail, String sportsCategory, int position, Date birth) {
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.phoneNumber = phoneNumber;
        this.eMail = eMail;
        this.sportsCategory = sportsCategory;
        this.position = position;
        this.birth = birth;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getSportsCategory() {
        return sportsCategory;
    }

    public void setSportsCategory(String sportsCategory) {
        this.sportsCategory = sportsCategory;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public List<HikingSchedule> getHikingScheduleList() {
        return hikingScheduleList;
    }

    public void setHikingScheduleList(List<HikingSchedule> hikingScheduleList) {
        this.hikingScheduleList = hikingScheduleList;
    }
}

