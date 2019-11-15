package server.entity;


import java.io.Serializable;

public class User implements Serializable {

    private static final long serialVersionUID = 3562076298397932355L;



    private Integer userId;
    private String login;
    private String password;
    private String role;

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                '}';
    }

    public Integer getUserId() {
        return userId;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    private User(Builder builder) {
        userId = builder.userId;
        login = builder.login;
        password = builder.password;
        role = builder.role;
    }

    public static Builder newBuilder() {
        return new Builder();
    }


    public static final class Builder {
        private Integer userId;
        private String login;
        private String password;
        private String role;

        private Builder() {
        }

        public Builder userId(Integer val) {
            userId = val;
            return this;
        }

        public Builder login(String val) {
            login = val;
            return this;
        }

        public Builder password(String val) {
            password = val;
            return this;
        }

        public Builder role(String val) {
            role = val;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }

}
