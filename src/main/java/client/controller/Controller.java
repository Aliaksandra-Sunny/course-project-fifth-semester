package client.controller;


import client.entity.*;
import client.model.Model;
import client.view.AuthorizationView;
import client.view.RegistrationView;
import client.view.adminView.*;
import client.view.userView.*;


import javax.swing.*;

import java.util.List;

public class Controller {
    private User user = null;
    private Client editClient = null;

    public void startApplication() {
        AuthorizationView authorizationView = new AuthorizationView(this);
        authorizationView.setVisible(true);
        new RegistrationView(this);
        new MainAdminView(this);
    }

    public void writeMessage(String login, String password) {
        try {
            Model obj = new Model();
            user = obj.writeData(login, password);

            if (user != null) {
                if (user.getRole().equals("admin")) {
                    new MainAdminView(this);
                    MainAdminView.jframe.setVisible(true);
                    AuthorizationView.jframe.setVisible(false);
                } else if (user.getRole().equals("user")) {
                    new MainUserView(this, user);
                    MainUserView.jframe.setVisible(true);
                    AuthorizationView.jframe.setVisible(false);
                }

            } else {
                JOptionPane.showMessageDialog(AuthorizationView.jframe, "Неверный логин или пароль.\nПроверьте введенные данные!");
            }
        } catch (Exception er) {
            er.printStackTrace();
        }
    }

    public void openReg() {
        AuthorizationView.jframe.setVisible(false);
        new RegistrationView(this);
        RegistrationView.jframe.setVisible(true);
    }

    public void backToAuthorizationView() {
        RegistrationView.jframe.setVisible(false);
        new AuthorizationView(this);
        AuthorizationView.jframe.setVisible(true);
    }

    /**
     * Administrator
     */

    public void openAdminUserView() {
        MainAdminView.jframe.setVisible(false);
        new UsersView(this);
        UsersView.jframe.setVisible(true);

    }


    public void backToAuthorizationViewFromMainAdminView() {
        RegistrationView.jframe.setVisible(false);
        MainAdminView.jframe.setVisible(false);
        new AuthorizationView(this);
        AuthorizationView.jframe.setVisible(true);
    }

    public List<User> watchAllUsersForAdmin() {
        Model obj = new Model();
        return obj.getAllUsersForAdmin();
    }

    public String deleteUser(String user) {
        Model obj = new Model();
        return obj.deleteUser(user);
    }

    public void backToAdminViewFromUsersView() {
        RegistrationView.jframe.setVisible(false);
        MainAdminView.jframe.setVisible(true);
        AuthorizationView.jframe.setVisible(false);
        UsersView.jframe.setVisible(false);
    }



    public void regClient(User user, Client client) {
        try {
            Model obj = new Model();
            String info;
            if ((info = obj.regClient(user, client)).equals("Аккаунт успешно создан!")) {
                RegistrationView.jframe.setVisible(false);
                AuthorizationView.jframe.setVisible(true);
                JOptionPane.showMessageDialog(AuthorizationView.jframe, info);
            } else {
                JOptionPane.showMessageDialog(
                        MainUserView.jframe,
                        info);
            }
        } catch (Exception er) {
            System.out.println(er.getMessage());
        }
    }

    public void watchAllCreditsType() {
        MainAdminView.jframe.setVisible(false);
        new CreditsTypeView(this);
        CreditsTypeView.jframe.setVisible(true);
    }

    public void watchAllCreditsView() {
        MainAdminView.jframe.setVisible(false);
        new CreditsView(this);
        CreditsView.jframe.setVisible(true);
    }

    public List<String> getAllCreditsType() {
        Model obj = new Model();
        return obj.getAllCreditsType();
    }

    public String addCreditsType(String type) {
        Model obj = new Model();
        return obj.addCreditsType(type);
    }

    public String deleteCreditsType(String type) {
        Model obj = new Model();
        return obj.deleteCreditsType(type);
    }


    public void backToAdminViewFromCreditsTypeView() {
        CreditsTypeView.jframe.setVisible(false);
        new MainAdminView(this);
        MainAdminView.jframe.setVisible(true);
    }

    public List<Credit> watchAllCredits() {
        Model obj = new Model();
        return obj.getAllCredits();
    }

    public void openAddCreditView() {
        CreditsView.jframe.setVisible(false);
        new AddCreditView(this);
        AddCreditView.jframe.setVisible(true);
    }

    public void deleteCredit(Credit credit) {
        Model obj = new Model();
        JOptionPane.showMessageDialog(CreditsView.jframe, obj.deleteCredit(credit));
    }


    public void backToCreditsAdminViewFromAddCreditView() {
        AddCreditView.jframe.setVisible(false);
        new CreditsView(this);
        CreditsView.jframe.setVisible(true);
    }

    public void backToMainAdminViewFromCreditsView() {
        CreditsView.jframe.setVisible(false);
        new MainAdminView(this);
        MainAdminView.jframe.setVisible(true);
    }

    public void addNewCredit(Credit credit) {
        Model obj = new Model();
        JOptionPane.showMessageDialog(AddCreditView.jframe, obj.addNewCredit(credit));
    }

    public List<Credit> watchCredit(String columnName, String findString) {
        Model obj = new Model();
        return obj.getCredit(columnName, findString);
    }

    public void openEditCredit(Credit credit) {
        EditCreditView view = new EditCreditView(this);
        EditCreditView.jframe.setVisible(true);
        view.setIdCredit(getIdCredit(credit));
        view.termField.setText(credit.getTerm());
        view.sumField.setText(credit.getSum());
        view.percentBox.setSelectedItem(credit.getPercent());
        view.creditTypeBox.setSelectedItem(credit.getCreditType());
        CreditsView.jframe.setVisible(false);
    }

    public int getIdCredit(Credit credit) {
        Model obj = new Model();
        return obj.getIdCredit(credit);
    }

    public void editCredit(int id, Credit credit) {
        Model obj = new Model();
        JOptionPane.showMessageDialog(CreditsView.jframe, obj.editCredit(id, credit));
    }

    public void backToCreditsAdminViewFromEditCreditView() {
        EditCreditView.jframe.setVisible(false);
        new CreditsView(this);
        CreditsView.jframe.setVisible(true);
    }

    public void backToAuthorizationViewFromMainUserView() {
        new AuthorizationView(this);
        MainUserView.jframe.setVisible(false);
        AuthorizationView.jframe.setVisible(true);
    }

    public void openEditUserInfoView(User user) {
        //find in client by user
        Client editClient = new Client();
        editClient = new Model().findClient(user);
        EditOwnInfoView editOwnInfoView = new EditOwnInfoView(this, editClient);
        editOwnInfoView.nameField.setText(editClient.getName());
        editOwnInfoView.surnameField.setText(editClient.getSurname());
        editOwnInfoView.passportField.setText(editClient.getPassport());
        editOwnInfoView.setVisible(true);
        MainUserView.jframe.setVisible(false);

    }

    public boolean editClient(Client editClient) {
        return new Model().editClient(editClient);
    }

    public void backToMainUserView(Client newClient) {
        User user = new Model().getUserById(newClient);
        EditOwnInfoView.jframe.setVisible(false);
        new MainUserView(this, user).setVisible(true);
    }

    public void openAllCreditsView(User user) {
        MainUserView.jframe.setVisible(false);
        new AllCreditsView(this, user).setVisible(true);
    }

    public void takeCredit(Client client, Credit findCredit) {
        JOptionPane.showMessageDialog(AllCreditsView.jframe, new Model().takeCredit(client, findCredit) == true ? "Вы успешно взяли кредит!" : "Ошибка! Кредит не взят!");

    }

    public void backToMainUserViewFromCreditsView(Client client) {
        User user = new Model().getUserById(client);
        new MainUserView(this, user).setVisible(true);
        AllCreditsView.jframe.setVisible(false);

    }

    public List<String> getDatasByCredit(Client client) {
        return new Model().getDatasByCredit(client);
    }

    public List<Credit> watchAllCreditsForUser(Client client) {
        return new Model().watchAllCreditsForUser(client);
    }

    public void showMyCredit(User user) {
        Client client = new Model().findClient(user);
        MainUserView.jframe.setVisible(false);
        new AllUsersCreditsView(this, client).setVisible(true);
    }

    public void backToMainUserViewFromUsersCreditsView(Client client) {
        User user = new Model().getUserById(client);
        AllUsersCreditsView.jframe.setVisible(false);
        new MainUserView(this, user).setVisible(true);
    }

    public void openCreateChartViewForUser(List<String> list) {
        ChartViewCredit view = new ChartViewCredit(this);
        String arr[];
        for (String list1 : list) {
            arr = list1.split(";");
            view.dcd.setValue(Double.parseDouble(arr[1]), "Процентная ставка(%)", arr[0]);
        }
        ChartViewCredit.jframe.setVisible(true);
    }

    public void openCreditsForUserView() {
        MainAdminView.jframe.setVisible(false);
        new CreditsForUserView(this).setVisible(true);
    }

    public List<Credit> watchAllCreditsForUsers() {
        return new Model().watchAllCreditsForUsers();
    }

    public List<Client> watchAllClientWithCredit() {
        return new Model().watchAllClientWithCredit();
    }

    public List<String> getDatasByCreditAllClients() {
        return new Model().getDatasByCreditAllClients();
    }

    public void deleteCreditForUser(Client client, Credit credit, String data) {
        if(new Model().deleteCreditForUser(client,credit,data)){
            JOptionPane.showMessageDialog(CreditsForUserView.jframe, "Успешное удаление");
        }else {
            JOptionPane.showMessageDialog(CreditsForUserView.jframe, "Ошибка при удалении");
        }
    }

    public void backToMainAdminViewFromUserCreditsView() {
        new MainAdminView(this).setVisible(true);
        CreditsForUserView.jframe.setVisible(false);
    }
}
