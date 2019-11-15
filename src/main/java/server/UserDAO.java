package server;



import client.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO extends AbstractDAO<User> {

    public UserDAO(Connection connection) {
        super(connection);
    }

    public User checkUser(User entity) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM t_user WHERE F_USERNAME=? AND F_PASSWORD=?;")) {
            preparedStatement.setString(1, entity.getLogin());
            preparedStatement.setString(2, entity.getPassword());
            ResultSet resSet = preparedStatement.executeQuery();
            if (resSet.next()) {
                return User.newBuilder()
                        .userId(resSet.getInt("F_ID"))
                        .login(resSet.getString("F_USERNAME"))
                        .password(resSet.getString("F_PASSWORD"))
                        .role(resSet.getString("F_ROLE"))
                        .build();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public int getUser(User entity) {
        int find = 0;
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM t_user WHERE F_USERNAME=?;")) {
            preparedStatement.setString(1, entity.getLogin());
            ResultSet resSet = preparedStatement.executeQuery();
            while (resSet.next()) {
                find++;
            }
            return find;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return find;
    }


    public boolean createNewUser(User user) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO t_user(F_USERNAME, F_PASSWORD, F_ROLE) VALUES (?, ?, ?)")) {
            connection.setAutoCommit(false);
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getRole());
            preparedStatement.executeUpdate();
            connection.commit();
            return true;
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public List<User> findAllUsers() {
        List<User> users = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM t_user WHERE F_ROLE=?")) {
            statement.setString(1, "user");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = User.newBuilder()
                        .login(resultSet.getString("F_USERNAME"))
                        .build();
                users.add(user);
            }

            return users;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public boolean deleteUsers(String user) {
        int idUser = getIdUser(user);
        if (idUser == -1) return false;
        if (delete(idUser)) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM t_client WHERE F_ID_USER_FK=?")) {
                statement.setInt(1, idUser);
                statement.executeUpdate();
                return true;
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
        return false;
    }

    private boolean delete(int idUser) {
        try (PreparedStatement statement = connection.prepareStatement(
                "DELETE FROM t_user WHERE F_ID=?")) {
            statement.setInt(1, idUser);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    private int getIdUser(String login) {
        int idUser = -1;
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT F_ID FROM t_user WHERE F_USERNAME=?")) {
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                idUser = resultSet.getInt("F_ID");
            }
            return idUser;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return idUser;
    }

    public User geUserById(Integer idUser) {

        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM t_user WHERE F_ID=?;")) {
            preparedStatement.setInt(1, idUser);
            ResultSet resSet = preparedStatement.executeQuery();
            if (resSet.next()) {
                User user = new User().newBuilder()
                        .userId(resSet.getInt("F_ID"))
                        .login(resSet.getString("F_USERNAME"))
                        .password(resSet.getString("F_PASSWORD"))
                        .build();
                return user;

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }


}
