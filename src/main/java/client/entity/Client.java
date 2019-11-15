package client.entity;

import java.io.Serializable;
import java.util.Objects;

public class Client implements Serializable {
    private static final long serialVersionUID = 3562076298397932355L;
    private Integer idClient;
    private Integer idUserFK;
    private String name;
    private String surname;
    private String passport;

    public Integer getIdClient() {
        return idClient;
    }

    public void setIdClient(Integer idClient) {
        this.idClient = idClient;
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

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(idClient, client.idClient) &&
                Objects.equals(idUserFK, client.idUserFK) &&
                Objects.equals(name, client.name) &&
                Objects.equals(surname, client.surname) &&
                Objects.equals(passport, client.passport);
    }

    @Override
    public int hashCode() {

        return Objects.hash(idClient, name, surname, passport, idUserFK);
    }

    @Override
    public String toString() {
        return "Client{" +
                "idClient=" + idClient +
                ", idUserFK='" + idUserFK + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", passport='" + passport + '\'' +
                '}';
    }

    private Client(Builder builder) {
        idClient = builder.idClient;
        idUserFK = builder.idUserFK;
        name = builder.name;
        surname = builder.surname;
        passport = builder.passport;
    }

    public Client(){

    }
    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private Integer idClient;
        private Integer idUserFK;
        private String name;
        private String surname;
        private String passport;

        private Builder() {
        }

        public Builder idClient(Integer val) {
            idClient = val;
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

        public Builder passport(String val) {
            passport = val;
            return this;
        }

        public Client build() {
            return new Client(this);
        }
    }
}
