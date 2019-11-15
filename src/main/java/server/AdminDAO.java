package server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class AdminDAO extends AbstractDAO {

    public AdminDAO(Connection connection) {
        super(connection);
    }

    public boolean addScaleSize(String size) {
        int scaleSize = Integer.parseInt(size);
        if (findScaleId() != 0) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE t_admin SET F_SCALE=? WHERE F_ID=?;")) {
                preparedStatement.setInt(1, scaleSize);
                preparedStatement.setInt(2, 1);
                preparedStatement.executeUpdate();
                return true;
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        } else {
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO t_admin(F_ID,F_SCALE) VALUES (?,?);")) {
                preparedStatement.setInt(2, scaleSize);
                preparedStatement.setInt(1, 1);
                preparedStatement.executeUpdate();
                return true;
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
        return false;
    }

    public int findScaleId() {
        int count = 0;
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT F_ID FROM t_admin  WHERE F_ID=?;")) {
            preparedStatement.setInt(1, 1);
            ResultSet resSet = preparedStatement.executeQuery();
            while (resSet.next()) {
                count++;
            }
            return count;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return count;
    }

    public int getScale() {
        int scale = 0;
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT F_SCALE FROM t_admin  WHERE F_ID=?;")) {
            preparedStatement.setInt(1, 1);
            ResultSet resSet = preparedStatement.executeQuery();
            while (resSet.next()) {
                scale = resSet.getInt("F_SCALE");
            }
            return scale;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return scale;
    }

    public boolean addAssessmets(String login, String expertAssessments) {
        int idExpert = getIdUserFK(login);
        if (idExpert == 0) return false;
        String summ = summAssessments(expertAssessments);

        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE t_expert SET F_ASSESSMENTS=? WHERE F_ID_USER_FK=?;")) {
            preparedStatement.setString(1, expertAssessments);
            preparedStatement.setInt(2, idExpert);
            preparedStatement.executeUpdate();
            try (PreparedStatement preparedStatement1 = connection.prepareStatement(
                    "UPDATE t_expert SET F_SUMMASSESSMENTS=? WHERE F_ID_USER_FK=?;")) {
                preparedStatement1.setString(1, summ);
                preparedStatement1.setInt(2, idExpert);
                preparedStatement1.executeUpdate();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
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

    public String summAssessments(String assessments) {
        String summAssessments;
        String[] ass = assessments.split(" ");

        int summ = 0;
        summ = Integer.parseInt(ass[3])+ Integer.parseInt(ass[4])+Integer.parseInt(ass[5])+Integer.parseInt(ass[6]);
        summAssessments = String.valueOf(summ) + " ";

        summ = 0;
        summ = Integer.parseInt(ass[7])+ Integer.parseInt(ass[11])+Integer.parseInt(ass[12])+Integer.parseInt(ass[13]);
        summAssessments += String.valueOf(summ) + " ";

        summ = 0;
        summ = Integer.parseInt(ass[14])+ Integer.parseInt(ass[15])+Integer.parseInt(ass[19])+Integer.parseInt(ass[20]);
        summAssessments += String.valueOf(summ) + " ";

        summ = 0;
        summ = Integer.parseInt(ass[21])+ Integer.parseInt(ass[22])+Integer.parseInt(ass[23])+Integer.parseInt(ass[27]);
        summAssessments += String.valueOf(summ);

        return summAssessments;
    }
}
