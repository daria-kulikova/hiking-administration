package app.models;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.criteria.CriteriaBuilder;

public class Salaries {

    private Integer id;
    private String surname;
    private String name;
    private String patronymic;
    private Integer i;
    private Integer ii;
    private Integer iii;
    private Integer iv;
    private Integer total;

    public Salaries() {
    }

    public Salaries(Integer id, String surname, String name, String patronymic, Integer i,
                    Integer ii, Integer iii, Integer iv, Integer total) {
        this.id = id;
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.i = i;
        this.ii = ii;
        this.iii = iii;
        this.iv = iv;
        this.total = total;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Integer getI() {
        return i;
    }

    public void setI(Integer i) {
        this.i = i;
    }

    public Integer getIi() {
        return ii;
    }

    public void setIi(Integer ii) {
        this.ii = ii;
    }

    public Integer getIii() {
        return iii;
    }

    public void setIii(Integer iii) {
        this.iii = iii;
    }

    public Integer getIv() {
        return iv;
    }

    public void setIv(Integer iv) {
        this.iv = iv;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    @Override
    public String toString(){
        return String.format("%d %s %s %s %d %d %d %d %d", id, surname, name, patronymic, i, ii, iii, iv, total);
    }
}
