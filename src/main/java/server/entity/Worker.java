package server.entity;

import java.io.Serializable;
import java.util.Objects;

public class Worker implements Serializable {

    private static final long serialVersionUID = 3562076298397932355L;


    private Integer idWorker;
    private Integer idUserFK;
    private String name;
    private String surname;
    private String patronymic;
    private Integer salary;



    public Integer getIdWorker() {
        return idWorker;
    }

    public void setIdWorker(Integer idWorker) {
        this.idWorker = idWorker;
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

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Worker{" +
                "idWorker=" + idWorker +
                ", idUserFK=" + idUserFK +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", patronymic='"+patronymic+'\''+
                ", salary=" + salary +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Worker worker = (Worker) o;
        return Objects.equals(idWorker, worker.idWorker) &&
                Objects.equals(idUserFK, worker.idUserFK) &&
                Objects.equals(name, worker.name) &&
                Objects.equals(surname, worker.surname) &&
                Objects.equals(patronymic, worker.patronymic) &&
                Objects.equals(salary, worker.salary);
    }

    @Override
    public int hashCode() {

        return Objects.hash(idWorker, idUserFK, name, surname,patronymic, salary);
    }

    private Worker(Builder builder) {
        idWorker = builder.idWorker;
        idUserFK = builder.idUserFK;
        name = builder.name;
        surname = builder.surname;
        patronymic = builder.patronymic;
        salary = builder.salary;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private Integer idWorker;
        private Integer idUserFK;
        private String name;
        private String surname;
        private Integer salary;
        private String patronymic;

        private Builder() {

        }

        public Builder idWorker(Integer val) {
            idWorker = val;
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

        public Builder patronymic(String val){
            patronymic=val;
            return this;
        }

        public Builder salary(Integer val) {
            salary = val;
            return this;
        }

        public Worker build(){
            return new Worker(this);
        }
    }
}
