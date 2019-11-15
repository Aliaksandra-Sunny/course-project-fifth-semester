package server.entity;

import java.io.Serializable;
import java.util.Objects;

public class Admin implements Serializable {
    private static final long serialVersionUID = 3562076298397932355L;
    private Integer scale;

    public Integer getScale() {
        return scale;
    }

    public void setScale(Integer scale) {
        this.scale = scale;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Admin admin = (Admin) o;
        return Objects.equals(scale, admin.scale);
    }

    @Override
    public int hashCode() {

        return Objects.hash(scale);
    }

    @Override
    public String toString() {
        return "Admin{" +
                "scale=" + scale +
                '}';
    }

    private Admin(Builder builder) {
        scale = builder.scale;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private Integer scale;

        private Builder() {
        }

        public Builder scale(Integer val) {
            scale = val;
            return this;
        }


        public Admin build() {
            return new Admin(this);
        }
    }
}
