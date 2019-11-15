package server;

import client.entity.Expert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExpertDAO extends AbstractDAO<Expert> {


    public ExpertDAO(Connection connection) {
        super(connection);
    }

    public List<Expert> findAllExperts() {
        List<Expert> experts = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM t_expert")) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Expert expert = Expert.newBuilder()
                        .idExpert(resultSet.getInt("F_ID"))
                        .idUserFK(resultSet.getInt("F_ID_USER_FK"))
                        .name(resultSet.getString("F_NAME"))
                        .surname(resultSet.getString("F_SURNAME"))
                        .build();
                experts.add(expert);
            }
            return experts;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public int getIdUserFK(String userLogin) {
        int idUser = 0;
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT F_ID FROM t_user WHERE F_USERNAME=?;")) {
            preparedStatement.setString(1, userLogin);
            ResultSet resSet = preparedStatement.executeQuery();
            if (resSet.next()) {
                idUser = resSet.getInt("F_ID");
            }
            return idUser;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return idUser;
    }

    public boolean addNewExpert(Expert expert, String userLogin) {
        int idUserFK = getIdUserFK(userLogin);
        String assessments ="   0 0 0 0 0    0 0 0 0 0    0 0 0 0 0    0";
        String summ = "0 0 0 0";
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO t_expert(F_SURNAME, F_NAME, F_ID_USER_FK,F_ASSESSMENTS,F_SUMMASSESSMENTS) " + "VALUES(?,?,?,?,?) ;")) {
            preparedStatement.setString(1, expert.getSurname());
            preparedStatement.setString(2, expert.getName());
            preparedStatement.setInt(3, idUserFK);
            preparedStatement.setString(4, assessments);
            preparedStatement.setString(5, summ);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteUserExpert(String user) {
        try (PreparedStatement statement = connection.prepareStatement(
                "DELETE FROM t_user WHERE F_USERNAME=?")) {
            statement.setString(1, user);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public List<Expert> getFindExpert(String columnName, String findString) {
        String sql = getString(columnName, findString);
        List<Expert> experts = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resSet = preparedStatement.executeQuery();
            while (resSet.next()) {
                experts.add(Expert.newBuilder()
                        .idExpert(resSet.getInt("te.F_ID"))
                        .surname(resSet.getString("te.F_SURNAME"))
                        .name(resSet.getString("te.F_NAME"))
                        .assessments(resSet.getString("te.F_ASSESSMENTS"))
                        .login(resSet.getString("tu.F_USERNAME"))
                        .build());
            }
            return experts;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public String getString(String column, String findString) {
        String str1 = "SELECT * FROM t_expert te JOIN t_user tu ON te.F_ID_USER_FK = tu.F_ID WHERE ";
        String str2;
        String str3 = '"' + findString + '"';
        if (column.equals("Фамилия")) {
            str2 = "F_SURNAME";
        } else if (column.equals("Имя")) {
            str2 = "F_NAME";
        } else {
            str2 = "F_USERNAME";
        }
        return str1 + " " + str2 + " LIKE " + str3 + ";";
    }

    public List<Expert> watchAllSummAssessments() {
        List<Expert> experts = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM t_expert")) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Expert expert = Expert.newBuilder()
                        .name(resultSet.getString("F_NAME"))
                        .surname(resultSet.getString("F_SURNAME"))
                        .assessments(resultSet.getString("F_SUMMASSESSMENTS"))
                        .build();
                experts.add(expert);
            }
            return experts;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public String getAssByNameAndSName(String sname, String name) {
        String assessments = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT F_ASSESSMENTS FROM t_expert  WHERE F_SURNAME=? AND F_NAME=?;")) {
            preparedStatement.setString(1, sname);
            preparedStatement.setString(2, name);
            ResultSet resSet = preparedStatement.executeQuery();
            while (resSet.next()) {
                assessments=resSet.getString("F_ASSESSMENTS");
            }
            return assessments;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return assessments;
    }

    public boolean deleteExpert(String expert) {
        String[] stringArray = expert.trim().split("[()\\s]+");
        int idUserFK = getIdUserFKwithDataAboutExpert(stringArray[0],stringArray[1]);
        try (PreparedStatement statement = connection.prepareStatement(
                "DELETE FROM t_expert WHERE F_ID_USER_FK=?;")) {
            statement.setInt(1, (idUserFK));
            statement.executeUpdate();
            try (PreparedStatement statement1 = connection.prepareStatement("DELETE FROM t_user WHERE F_ID=?;")) {
                statement1.setInt(1, idUserFK);
                statement1.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public int getIdUserFKwithDataAboutExpert(String surname, String name){
        int idUserFK=-1;
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT F_ID_USER_FK FROM t_expert WHERE F_NAME=? AND F_SURNAME=? ;")) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, surname);
            ResultSet resSet = preparedStatement.executeQuery();
            if (resSet.next()) {
                idUserFK = resSet.getInt("F_ID_USER_FK");
            }
            return idUserFK;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return idUserFK;
    }
}


