package server;

import client.entity.Client;
import client.entity.Credit;
import client.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ClientDAO extends AbstractDAO {
    public ClientDAO(Connection connection) {
        super(connection);
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

    public boolean addNewClient(Client client, String userLogin) {
        int idUserFK = getIdUserFK(userLogin);
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO t_client(F_SURNAME, F_NAME, F_PASSPORT, F_ID_USER_FK) " + "VALUES(?,?,?,?) ;")) {
            preparedStatement.setString(1, client.getSurname());
            preparedStatement.setString(2, client.getName());
            preparedStatement.setString(3, client.getPassport());
            preparedStatement.setInt(4, idUserFK);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    public boolean editClient(Client editClient) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE t_client SET F_NAME=?, F_SURNAME=?, F_PASSPORT=?  WHERE F_ID=?; ")) {
            preparedStatement.setString(1, editClient.getName());
            preparedStatement.setString(2, editClient.getSurname());
            preparedStatement.setString(3, editClient.getPassport());
            preparedStatement.setInt(4, editClient.getIdClient());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public Client findClient(User user) {
        List<Client> clients = new ArrayList<>();
        int idUserFK = getIdUserFK(user.getLogin());
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM t_client " +
                        "WHERE t_client.F_ID_USER_FK=?; ")) {
            preparedStatement.setInt(1, idUserFK);
            ResultSet resSet = preparedStatement.executeQuery();
            while (resSet.next()) {
                clients.add(Client.newBuilder()
                        .idClient(resSet.getInt("t_client.F_ID"))
                        .name(resSet.getString("t_client.F_NAME"))
                        .surname(resSet.getString("t_client.F_SURNAME"))
                        .passport(resSet.getString("t_client.F_PASSPORT"))
                        .idUserFK(resSet.getInt("t_client.F_ID_USER_FK"))
                        .build());
                clients.size();
            }
            return clients.get(0);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public boolean takeCredit(Client  client, Credit credit){

        Date dateNow = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("E yyyy.MM.dd hh:mm:ss a");
        String data = formatForDateNow.format(dateNow);

        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO t_client_credit(F_ID_CREDIT_FK, F_ID_CLIENT_FK, F_ISSUE_DATA ) " + "VALUES(?,?,?) ;")) {
            preparedStatement.setInt(1, credit.getIdCredit());
            preparedStatement.setInt(2, client.getIdClient());
            preparedStatement.setString(3, data);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public List<String> watchDatas(Client client){
        List<String> datas = new ArrayList<>();
        Integer idClient = client.getIdClient();
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM t_client_credit " +
                        "WHERE F_ID_CLIENT_FK=?; ")) {
            preparedStatement.setInt(1, idClient);
            ResultSet resSet = preparedStatement.executeQuery();
            while (resSet.next()) {
                datas.add(resSet.getString("F_ISSUE_DATA"));
            }
            return datas;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return  datas;
    }

    public List<Client> watchClientsByAllCredit() {
        List<Client> clients = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT t_client_credit.F_ID_CLIENT_FK, t_client.F_NAME, t_client.F_SURNAME, t_client.F_PASSPORT FROM t_client_credit " +
                        "LEFT JOIN t_client ON t_client.F_ID=t_client_credit.F_ID_CLIENT_FK;")) {
            ResultSet resSet = preparedStatement.executeQuery();
            while (resSet.next()) {
                clients.add(Client.newBuilder()
                        .idClient(resSet.getInt("t_client_credit.F_ID_CLIENT_FK"))
                        .name(resSet.getString("t_client.F_NAME"))
                        .surname(resSet.getString("t_client.F_SURNAME"))
                        .passport(resSet.getString("t_client.F_PASSPORT"))
                        .build());
                clients.size();
            }
            return clients;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return clients;
    }

        public List<String> watchAllDatas(){
            List<String> datas = new ArrayList<>();
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM t_client_credit ")) {
                ResultSet resSet = preparedStatement.executeQuery();
                while (resSet.next()) {
                    datas.add(resSet.getString("F_ISSUE_DATA"));
                }
                return datas;
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
            return datas;
        }

   public boolean deleteCreditForUser(Client client, Credit credit, String data){
       int idClient = client.getIdClient();
       int idCredit = credit.getIdCredit();
       try (PreparedStatement preparedStatement1 = connection.prepareStatement(
               "DELETE FROM t_client_credit WHERE F_ID_CLIENT_FK=? AND F_ID_CREDIT_FK=? AND F_ISSUE_DATA=?")) {
           preparedStatement1.setInt(1, idClient);
           preparedStatement1.setInt(2, idCredit);
           preparedStatement1.setString(3, data);
           preparedStatement1.executeUpdate();
           return true;
       } catch (SQLException e) {
           System.out.println(e.getMessage());
           e.printStackTrace();
       }
       return false;
   }

}
