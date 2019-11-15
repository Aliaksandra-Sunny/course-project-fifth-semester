package server.entity;

import java.io.Serializable;
import java.util.Objects;

public class Credit implements Serializable {
    private static final long serialVersionUID = 3562076298397932355L;

    private Integer idCredit;
    private String term;
    private String percent;
    private String sum;
    private String creditType;
    private String assessment;

    @Override
    public String toString() {
        return "Credit{" +
                "idCredit=" + idCredit +
                ", term='" + term + '\'' +
                ", percent='" + percent + '\'' +
                ", sum='" + sum + '\'' +
                ", creditType='" + creditType + '\'' +
                ", assessment='" + assessment + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Credit credit = (Credit) o;
        return Objects.equals(idCredit, credit.idCredit) &&
                Objects.equals(term, credit.term) &&
                Objects.equals(percent, credit.percent) &&
                Objects.equals(sum, credit.sum) &&
                Objects.equals(creditType, credit.creditType) &&
                Objects.equals(assessment, credit.assessment);
    }

    @Override
    public int hashCode() {

        return Objects.hash(idCredit, term, percent, sum, creditType, assessment);
    }

    public String getAssessment() {

        return assessment;
    }

    public void setAssessment(String assessment) {
        this.assessment = assessment;
    }

    public String getCreditType() {
        return creditType;
    }

    public void setCreditType(String creditType) {
        this.creditType = creditType;
    }


    public Integer getIdCredit() {
        return idCredit;
    }

    public void setIdCredit(Integer idCredit) {
        this.idCredit = idCredit;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }

    public String  getSum() {
        return sum;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }


    private Credit(Builder builder) {
        idCredit = builder.idCredit;
        term = builder.term;
        percent = builder.percent;
        sum = builder.sum;
        creditType=builder.creditType;
        assessment=builder.assessment;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private Integer idCredit;
        private String term;
        private String percent;
        private String sum;
        private String creditType;
        private String assessment;

        private Builder() {
        }

        public Builder idCredit(Integer val) {
            idCredit = val;
            return this;
        }


        public Builder term(String val) {
            term = val;
            return this;
        }

        public Builder percent(String val) {
            percent = val;
            return this;
        }

        public Builder sum(String val) {
            sum = val;
            return this;
        }
        public Builder creditType(String val) {
            creditType = val;
            return this;
        }
        public Builder assessment(String val) {
            assessment = val;
            return this;
        }

        public Credit build() {
            return new Credit(this);
        }


    }
}
