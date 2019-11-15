package server;


import client.entity.Worker;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WorkerDAO extends AbstractDAO<Worker> {

    public WorkerDAO(Connection connection) {
        super(connection);
    }

    public List<Worker> findAllWorkers() {
        List<Worker> workers = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM t_worker ")) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Worker worker = Worker.newBuilder()
                        .idWorker(resultSet.getInt("F_ID"))
                        .idUserFK(resultSet.getInt("F_ID_USER_FK"))
                        .name(resultSet.getString("F_NAME"))
                        .surname(resultSet.getString("F_SURNAME"))
                        .patronymic(resultSet.getString("F_PATRONYMIC"))
                        .build();
                workers.add(worker);
            }
            return workers;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public boolean deleteWorkers(String worker) {
        String[] stringArray = worker.trim().split("[()\\s]+");
        int idUserFK = getIdUserFKwithDataAboutWorker(stringArray[0],stringArray[1],stringArray[2]);

        try (PreparedStatement statement = connection.prepareStatement(
                "DELETE FROM t_worker WHERE F_ID_USER_FK=?;")) {
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

    public int getIdUserFKwithDataAboutWorker(String surname, String name, String patronymic){
        int idUserFK=-1;
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT F_ID_USER_FK FROM t_worker WHERE F_NAME=? AND F_SURNAME=? AND F_PATRONYMIC=?;")) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, surname);
            preparedStatement.setString(3, patronymic);
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

    public int getIdUserFK (String userLogin){
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

    public boolean addNewWorker(Worker worker,String userLogin) {
        int idUserFK = getIdUserFK(userLogin);
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO t_worker(F_SURNAME, F_NAME, F_PATRONYMIC, F_SALARY, F_ID_USER_FK) " + "VALUES(?,?,?,?,?) ;")) {
            preparedStatement.setString(1, worker.getSurname());
            preparedStatement.setString(2, worker.getName());
            preparedStatement.setString(3, worker.getPatronymic());
            preparedStatement.setInt(4, worker.getSalary());
            preparedStatement.setInt(5, idUserFK);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return false;
    }
}
