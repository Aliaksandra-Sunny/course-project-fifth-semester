package client.model;

import client.entity.*;
import client.service.Service;

import java.io.IOException;
import java.rmi.ServerError;
import java.util.List;

public class Model {

    public User writeData(String login, String password) {
        if (Service.socket != null) {
            return Service.checkUser(login, password);
        } else
            return null;
    }



    /**
     * Administrator*/

    public List<User> getAllUsersForAdmin() {
        return Service.getUsersForAdmin();
    }

    public String deleteUser(String user) {
        return Service.deleteUsers(user);
    }

    public String regClient(User user, Client client) throws IOException {
        return Service.createNewClient(user,client);
    }

    public List<String> getAllCreditsType() {
        return Service.getAllCreditsType();
    }


    public String addCreditsType(String type) {
        return Service.addCreditsType(type);
    }

    public String deleteCreditsType(String type) {
        return Service.deleteCreditsType(type);
    }

    public List<Credit> getAllCredits() {
        return Service.getAllCredits();
    }


    public String addNewCredit(Credit credit) {
        return Service.addNewCredit(credit);
    }

    public String deleteCredit(Credit credit) {
        return Service.deleteCredit(credit);
    }

    public List<Credit> getCredit(String columnName, String findString) {
        return Service.getFindCredit(columnName, findString);
    }

    public int getIdCredit(Credit credit) {
        return Service.getIdCredit(credit);
    }

    public String editCredit(int id, Credit credit) {
        return Service.editCredit(id, credit);
    }


    public Client findClient(User user) {
        return Service.findClient(user);
    }

    public boolean editClient(Client editClient) {
        return Service.editClient(editClient);
    }

    public User getUserById(Client client) {
        return Service.getUserById(client);
    }

    public boolean takeCredit(Client client, Credit findCredit) {
        return Service.takeCredit(client, findCredit);
    }


    public List<String> getDatasByCredit(Client client) {
        return Service.getDatasByCredit(client);
    }

    public List<Credit> watchAllCreditsForUser(Client client) {
        return Service.watchAllCreditsForUser(client);
    }

    public List<Credit> watchAllCreditsForUsers() {
        return Service.watchAllCreditsForUsers();
    }

    public List<Client> watchAllClientWithCredit() {
        return Service.watchAllClientWithCredit();
    }

    public List<String> getDatasByCreditAllClients() {
        return Service.getDatasByCreditAllClients();
    }

    public boolean deleteCreditForUser(Client client, Credit credit, String data) {
       return Service.deleteCreditForUser(client,credit,data);
    }
}
