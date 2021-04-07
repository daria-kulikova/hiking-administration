package app.models;

import java.util.Objects;

public class InstructorsTable {

    private Integer idInstructor;
    private String surnameInstructor;
    private String categoryInstructor;

    public InstructorsTable() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InstructorsTable that = (InstructorsTable) o;
        return Objects.equals(idInstructor, that.idInstructor) &&
                Objects.equals(surnameInstructor, that.surnameInstructor) &&
                Objects.equals(categoryInstructor, that.categoryInstructor);
    }

    public Integer getIdInstructor() {
        return idInstructor;
    }

    public void setIdInstructor(Integer idInstructor) {
        this.idInstructor = idInstructor;
    }

    public String getSurnameInstructor() {
        return surnameInstructor;
    }

    public void setSurnameInstructor(String surnameInstructor) {
        this.surnameInstructor = surnameInstructor;
    }

    public String getCategoryInstructor() {
        return categoryInstructor;
    }

    public void setCategoryInstructor(String categoryInstructor) {
        this.categoryInstructor = categoryInstructor;
    }
}
