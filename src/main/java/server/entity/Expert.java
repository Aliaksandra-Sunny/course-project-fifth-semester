package server.entity;

import java.io.Serializable;
import java.util.Objects;

public class Expert implements Serializable {

    private static final long serialVersionUID = 3562076298397932355L;

    private Integer idExpert;
    private Integer idUserFK;
    private String name;
    private String surname;
    private String assessments ;
    private String login;

    @Override
    public String toString() {
        return "Expert{" +
                "idExpert=" + idExpert +
                ", idUserFK=" + idUserFK +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", assessments='" + assessments + '\'' +
                ", login='" + login + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Expert expert = (Expert) o;
        return Objects.equals(idExpert, expert.idExpert) &&
                Objects.equals(idUserFK, expert.idUserFK) &&
                Objects.equals(name, expert.name) &&
                Objects.equals(surname, expert.surname) &&
                Objects.equals(assessments, expert.assessments) &&
                Objects.equals(login, expert.login);
    }

    @Override
    public int hashCode() {

        return Objects.hash(idExpert, idUserFK, name, surname, assessments, login);
    }

    public String getLogin() {

        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getAssessments() {

        return assessments;
    }

    public void setAssessments(String assessments) {
        this.assessments = assessments;
    }

    public Integer getIdExpert() {
        return idExpert;
    }

    public void setIdExpert(Integer idExpert) {
        this.idExpert = idExpert;
    }

    public Integer getIdUserFK() {
        return idUserFK;
    }

    public void setIdUserFK(Integer idUserFK) {
        this.idUserFK = idUserFK;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    private Expert(Builder builder) {
        idExpert = builder.idExpert;
        idUserFK = builder.idUserFK;
        name = builder.name;
        surname = builder.surname;
        assessments = builder.assessments;
        login = builder.login;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private Integer idExpert;
        private Integer idUserFK;
        private String name;
        private String surname;
        private String assessments;
        private String login;

        private Builder() {
        }

        public Builder idExpert(Integer val) {
            idExpert = val;
            return this;
        }

        public Builder login(String val){
            login = val;
            return this;
        }

        public Builder assessments(String val){
            assessments = val;
            return this;
        }

        public Builder idUserFK(Integer val) {
            idUserFK = val;
            return this;
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Builder surname(String val) {
            surname = val;
            return this;
        }

        public Expert build() {
            return new Expert(this);
        }
    }
}
