package client.view.adminView;

import java.util.Objects;

public class UserCredit {

    private String firstName;
    private String surName;
    private String passport;
    private String typeCredit;
    private String sum;
    private String percent;
    private String term;
    private String data;
    private Integer idClient;
    private Integer idCredit;

    public Integer getIdClient() {
        return idClient;
    }

    public void setIdClient(Integer idClient) {
        this.idClient = idClient;
    }

    public Integer getIdCredit() {
        return idCredit;
    }

    public void setIdCredit(Integer idCredit) {
        this.idCredit = idCredit;
    }

    public UserCredit(String firstName, String surName, String passport, String typeCredit, String sum, String percent, String term, String data) {
        this.firstName = firstName;
        this.surName = surName;
        this.passport = passport;
        this.typeCredit = typeCredit;
        this.sum = sum;
        this.percent = percent;
        this.term = term;
        this.data = data;
    }
    public UserCredit(){

    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public String getTypeCredit() {
        return typeCredit;
    }

    public void setTypeCredit(String typeCredit) {
        this.typeCredit = typeCredit;
    }

    public String getSum() {
        return sum;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserCredit that = (UserCredit) o;
        return Objects.equals(firstName, that.firstName) &&
                Objects.equals(surName, that.surName) &&
                Objects.equals(passport, that.passport) &&
                Objects.equals(typeCredit, that.typeCredit) &&
                Objects.equals(sum, that.sum) &&
                Objects.equals(percent, that.percent) &&
                Objects.equals(term, that.term) &&
                Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {

        return Objects.hash(firstName, surName, passport, typeCredit, sum, percent, term, data);
    }
}
