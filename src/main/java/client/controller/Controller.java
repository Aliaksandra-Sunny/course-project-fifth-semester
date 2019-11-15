package client.controller;


import client.Main;
import client.entity.*;
import client.model.Model;
import client.view.AuthorizationView;
import client.view.RegistrationView;
import client.view.adminView.*;
import client.view.expertView.AddAssessmentView;
import client.view.expertView.CreditForAssessmentForExpertView;
import client.view.expertView.MainExpertView;
import client.view.expertView.PrintAllAssessmentsForExpert;
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
        new MainExpertView(this);
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
                } else if (user.getRole().equals("expert")) {
                    new MainExpertView(this);
                    MainExpertView.jframe.setVisible(true);
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

    public void openAdminWorkerView() {
        MainAdminView.jframe.setVisible(false);
        new WorkersView((this));
        WorkersView.jframe.setVisible(true);

    }

    public void openAdminExpertView() {
        MainAdminView.jframe.setVisible(false);
        new ExpertsView(this);
        ExpertsView.jframe.setVisible(true);

    }

    public void openAddExpertView() {
        new AddExpertView(this);
        AddExpertView.jframe.setVisible(true);
        ExpertsView.jframe.setVisible(false);
    }

    public String deleteExpert(String expert) {
        Model obj = new Model();
        return obj.deleteExpert(expert);
    }

    public void backToAdminViewFromaddExpertsView() {
        AddExpertView.jframe.setVisible(false);
        new MainAdminView(this);
        MainAdminView.jframe.setVisible(true);

    }

    public void openAddWorkerView() {
        new AddWorkerView(this);
        AddWorkerView.jframe.setVisible(true);
        WorkersView.jframe.setVisible(false);
    }

    public void backToAdminViewFromaddWorkersView() {
        AddWorkerView.jframe.setVisible(false);
        new MainAdminView(this);
        MainAdminView.jframe.setVisible(true);
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


    public List<Worker> watchAllWorkersForAdmin() {
        Model obj = new Model();
        return obj.getAllWorkersForAdmin();
    }

    public String deleteWorker(String worker) {
        Model obj = new Model();
        return obj.deleteWorker(worker);
    }

    public void backToAdminViewFromWorkersView() {
        WorkersView.jframe.setVisible(false);
        new MainAdminView(this);
        MainAdminView.jframe.setVisible(true);
    }

    public List<Expert> watchAllExpertsForAdmin() {
        Model obj = new Model();
        return obj.getAllExpertsForAdmin();
    }

    public void backToAdminViewFromExpertsView() {
        ExpertsView.jframe.setVisible(false);
        new MainAdminView(this);
        MainAdminView.jframe.setVisible(true);
    }


    public void regExpert(User user, Expert expert) {
        try {
            Model obj = new Model();
            JOptionPane.showMessageDialog(AddExpertView.jframe, obj.regExpert(user, expert));
        } catch (Exception er) {
            System.out.println(er.getMessage());
        }
    }

    public void regWorker(User user, Worker worker) {
        try {
            Model obj = new Model();
            JOptionPane.showMessageDialog(AddWorkerView.jframe, obj.regWorker(user, worker));
        } catch (Exception er) {
            System.out.println(er.getMessage());
        }
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

    public void deleteCreditForAssessment(Credit credit) {
        Model obj = new Model();
        JOptionPane.showMessageDialog(CreditsForAssessmentView.jframe, obj.deleteCreditForAssessment(credit));
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

    public void backToWorkersAdminViewFromaddWorkerView() {
        AddWorkerView.jframe.setVisible(false);
        new WorkersView(this);
        WorkersView.jframe.setVisible(true);
    }

    public void backToExpertsViewFromAddExpert() {
        AddExpertView.jframe.setVisible(false);
        new ExpertsView(this);
        ExpertsView.jframe.setVisible(true);
    }


    public void sendCreditForAssessment(Credit credit) {
        Model obj = new Model();
        JOptionPane.showMessageDialog(SendCreditForAssessmentView.jframe, obj.sendCreditForAssessment(credit));
    }

    public void openSendCreditForAssessmentView() {
        CreditsForAssessmentView.jframe.setVisible(false);
        new SendCreditForAssessmentView(this);
        SendCreditForAssessmentView.jframe.setVisible(true);
    }

    public void watchAllCreditsForAssessmentView() {
        MainAdminView.jframe.setVisible(false);
        new CreditsForAssessmentView(this);
        CreditsForAssessmentView.jframe.setVisible(true);
    }

    public void backToMainAdminViewFromCreditsForAssessmentView() {
        CreditsForAssessmentView.jframe.setVisible(false);
        new MainAdminView(this);
        MainAdminView.jframe.setVisible(true);
    }

    public List<Credit> watchAllCreditsForAssessment() {
        Model obj = new Model();
        return obj.getAllCreditsForAssessment();
    }

    public void backToCreditsForAssessmentViewFromSendCreditsView() {
        SendCreditForAssessmentView.jframe.setVisible(false);
        new CreditsForAssessmentView(this);
        CreditsForAssessmentView.jframe.setVisible(true);
    }

    public void openPrintAllAssessmentsForAdminView() {
        MainAdminView.jframe.setVisible(false);
        new PrintAllAssessmentsForAdminView(this);
        PrintAllAssessmentsForAdminView.jframe.setVisible(true);
    }

    public void backToMainAdminViewFromAssessmentsView() {
        new MainAdminView(this);
        MainAdminView.jframe.setVisible(true);
        PrintAllAssessmentsForAdminView.jframe.setVisible(false);
    }

    public void openEnterScaleSizeView() {
        new EnterScaleSizeView(this);
        EnterScaleSizeView.jframe.setVisible(true);
        MainAdminView.jframe.setVisible(false);
    }

    public void enterScaleSize(String size) {
        Model obj = new Model();
        JOptionPane.showMessageDialog(EnterScaleSizeView.jframe, obj.enterScaleSize(size));

    }

    public void closedEnterScaleSizeView() {
        EnterScaleSizeView.jframe.setVisible(false);
        new MainAdminView(this);
        MainAdminView.jframe.setVisible(true);
    }

    public List<Expert> watchAllSummAssessments() {
        Model obj = new Model();
        return obj.watchAllSummAssessments();
    }

    public void openFinalGoalWeightsView() {
        MainAdminView.jframe.setVisible(false);
        new FinalGoalWeightsView(this);
        FinalGoalWeightsView.jframe.setVisible(true);
    }


    public String getAssByNameAndSName(String sName, String name) {
        Model obj = new Model();
        return obj.getAssByNameAndSName(sName, name);
    }

    public void backToMainAdminViewFromFinalGoalView() {
        new MainAdminView(this);
        MainAdminView.jframe.setVisible(true);
        FinalGoalWeightsView.jframe.setVisible(false);
    }

    public void openCreateChartView(List<String> list) {
        ChartView view = new ChartView(this);
        String arr[];
        System.out.println(list);
        for (String list1 : list) {
            arr = list1.split(";");
            view.dcd.setValue(Double.parseDouble(arr[1]), "Вес кредита", arr[0]);
        }
        ChartView.jframe.setVisible(true);
    }

    /**
     * Expert
     */

    public void backToAuthorizationViewFromMainExpertView() {
        new AuthorizationView(this);
        AuthorizationView.jframe.setVisible(true);
        MainExpertView.jframe.setVisible(false);
    }

    public void watchAllCreditsForAssessmentForExpertView() {
        MainExpertView.jframe.setVisible(false);
        new CreditForAssessmentForExpertView(this);
        CreditForAssessmentForExpertView.jframe.setVisible(true);
    }

    public void backToMainExpertViewFromCreditsForAssessmentForExpertView() {
        new MainExpertView(this);
        MainExpertView.jframe.setVisible(true);
        CreditForAssessmentForExpertView.jframe.setVisible(false);
    }

    public void backToMainExpertViewFromAssessmentsView() {
        PrintAllAssessmentsForExpert.jframe.setVisible(false);
        new MainExpertView(this);
        MainExpertView.jframe.setVisible(true);
    }

    public List<Expert> watchExpert(String columnName, String findString) {
        Model obj = new Model();
        return obj.getExpert(columnName, findString);
    }

    public void openPrintAllAssessmentsForExpertView() {
        new PrintAllAssessmentsForExpert(this);
        PrintAllAssessmentsForExpert.jframe.setVisible(true);
        MainExpertView.jframe.setVisible(false);
    }

    public void openAddAssessmentView() {
        new AddAssessmentView(this);
        AddAssessmentView.jframe.setVisible(true);

    }

    public int getScale() {
        Model obj = new Model();
        if (obj.getScale() == 0) {
            JOptionPane.showMessageDialog(MainAdminView.jframe, "Кол-во кредититов для оценки равно 0 !\nДобавьте кредит для оценки!");
            FinalGoalWeightsView.jframe.setVisible(false);
            new MainAdminView(this);
            MainAdminView.jframe.setVisible(true);
            return -1;
        } else return obj.getScale();
    }

    public int getN() {
        Model obj = new Model();
        return obj.getN();
    }

    public int[] getAssessments() {
        int[] assessments = new int[getScale()];
        for (int i = 0; i < assessments.length; i++) {
            assessments[i] = i + 1;
        }
        return assessments;
    }

    public void addAssessments(String login, String expertAssessments) {
        Model obj = new Model();
        JOptionPane.showMessageDialog(AddAssessmentView.jframe, obj.addAssessments(login, expertAssessments));

    }

    public void openAssessmentsOfOneExpertView(String assessments) {
        int scale = getScale();
        String[] ass = assessments.split(" ");

        AssessmentsOfOneExpertView view = new AssessmentsOfOneExpertView(this);
        AssessmentsOfOneExpertView.jframe.setVisible(true);

        /**Исправить ass[все параметры ]*/
        view.z1z1.setText(ass[3]);
        view.z1z2.setText(ass[4]);
        view.z1z3.setText(ass[5]);
        view.z1z4.setText(ass[6]);

        view.z2z1.setText(ass[7]);
        view.z2z2.setText(ass[11]);
        view.z2z3.setText(ass[12]);
        view.z2z4.setText(ass[13]);

        view.z3z1.setText(ass[14]);
        view.z3z2.setText(ass[15]);
        view.z3z3.setText(ass[19]);
        view.z3z4.setText(ass[20]);

        view.z4z1.setText(ass[21]);
        view.z4z2.setText(ass[22]);
        view.z4z3.setText(ass[23]);
        view.z4z4.setText(ass[27]);


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
