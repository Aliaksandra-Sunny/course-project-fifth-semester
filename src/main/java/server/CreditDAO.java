package server;

import client.entity.Client;
import client.entity.Credit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CreditDAO extends AbstractDAO {
    public CreditDAO(Connection connection) {
        super(connection);
    }

    public int getIdCreditsType(String creditsType) {
        int id = -1;
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT F_ID FROM t_credittype WHERE F_TYPENAME=?;")) {
            preparedStatement.setString(1, creditsType);
            ResultSet resSet = preparedStatement.executeQuery();
            if (resSet.next()) {
                id = resSet.getInt("F_ID");
            }
            return id;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return id;
    }

    public int getIdCredit(String term, String percent, String sum) {
        int id = -1;
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT F_ID FROM t_credit WHERE F_TERM=? AND F_PERCENT=? AND F_SUM=?;")) {
            preparedStatement.setString(1, term);
            preparedStatement.setString(2, percent);
            preparedStatement.setString(3, sum);
            ResultSet resSet = preparedStatement.executeQuery();
            if (resSet.next()) {
                id = resSet.getInt("F_ID");
            }
            return id;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return id;
    }

    public List<String> getAllCreditsType() {
        List<String> types = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT F_TYPENAME FROM t_credittype;")) {
            ResultSet resSet = preparedStatement.executeQuery();
            while (resSet.next()) {
                types.add(resSet.getString("F_TYPENAME"));
            }
            return types;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public boolean addCreditsType(String type) {
        boolean flag = false;
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM t_credittype WHERE F_TYPENAME=?;")) {
            preparedStatement.setString(1, type);
            ResultSet resSet = preparedStatement.executeQuery();
            int number = 0;
            while (resSet.next()) {
                number++;
            }
            if (number == 0) {
                try (PreparedStatement preparedStatement2 = connection.prepareStatement(
                        "INSERT INTO t_credittype(F_TYPENAME) VALUE (?);")) {
                    preparedStatement2.setString(1, type);
                    preparedStatement2.executeUpdate();
                }
                flag = true;
            } else {
                flag = false;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return flag;
    }

    public boolean deleteCreditsType(String type) {
        int id = -1;
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT F_ID FROM t_credittype WHERE F_TYPENAME=?;")) {
            preparedStatement.setString(1, type);
            ResultSet resSet = preparedStatement.executeQuery();
            if (resSet.next()) {
                id = resSet.getInt("F_ID");
            }
            try (PreparedStatement preparedStatement1 = connection.prepareStatement(
                    "DELETE FROM t_credit WHERE F_CREDITTYPE_ID_FK=?")) {
                preparedStatement1.setInt(1, id);
                preparedStatement1.executeUpdate();
            }
            try (PreparedStatement preparedStatement2 = connection.prepareStatement(
                    "DELETE FROM t_credittype WHERE F_ID=?")) {
                preparedStatement2.setInt(1, id);
                preparedStatement2.executeUpdate();
            }
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public List<Credit> getAllCredits() {
        List<Credit> credits = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM t_credit, t_credittype " +
                        "WHERE t_credit.F_CREDITTYPE_ID_FK=t_credittype.F_ID; ")) {
            ResultSet resSet = preparedStatement.executeQuery();
            while (resSet.next()) {
                credits.add(Credit.newBuilder()
                        .idCredit(resSet.getInt("t_credit.F_ID"))
                        .term(resSet.getString("t_credit.F_TERM"))
                        .percent(resSet.getString("t_credit.F_PERCENT"))
                        .sum(resSet.getString("t_credit.F_SUM"))
                        .creditType(resSet.getString("t_credittype.F_TYPENAME"))
                        .build());
                credits.size();
            }
            return credits;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public boolean addNewCredit(Credit credit) {
        int idCreditsType = getIdCreditsType(credit.getCreditType());
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO t_credit(F_TERM, F_PERCENT, F_SUM, F_CREDITTYPE_ID_FK) " +
                        "VALUES(?,?,?,?) ;")) {
            preparedStatement.setString(1, credit.getTerm());
            preparedStatement.setString(2, credit.getPercent());
            preparedStatement.setString(3, credit.getSum());
            preparedStatement.setInt(4, idCreditsType);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteCredit(Credit credit) {
        int idCredit = getIdCredit(credit.getTerm(), credit.getPercent(), credit.getSum());
        try (PreparedStatement preparedStatement1 = connection.prepareStatement(
                "DELETE FROM t_credit WHERE F_ID=?")) {
            preparedStatement1.setInt(1, idCredit);
            preparedStatement1.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public List<Credit> getFindCredit(String columnName, String findString) {
        String sql = getString(columnName, findString);
        List<Credit> credits = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resSet = preparedStatement.executeQuery();
            while (resSet.next()) {
                credits.add(Credit.newBuilder()
                        .idCredit(resSet.getInt("t.F_ID"))
                        .term(resSet.getString("t.F_TERM"))
                        .percent(resSet.getString("t.F_PERCENT"))
                        .sum(resSet.getString("t.F_SUM"))
                        .creditType(resSet.getString("tcr.F_TYPENAME"))
                        .build());
            }
            return credits;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public String getString(String column, String findString) {
        String str1 = "SELECT * FROM t_credit t JOIN t_credittype tcr ON t.F_CREDITTYPE_ID_FK = tcr.F_ID WHERE ";
        String str2;
        String str3 = '"' + findString + '"';
        if (column.equals("Цель выдачи кредита")) {
            str2 = "F_TYPENAME";
        } else if (column.equals("Сумма выдачи")) {
            str2 = "F_SUM";
        } else if (column.equals("Процентная ставка")) {
            str2 = "F_PERCENT";
        } else {
            str2 = "F_TERM";
        }
        return str1 + " " + str2 + " LIKE " + str3 + ";";
    }

    public boolean editCredit(int idCredit, Credit newCredit) {
        int idCreditType = getIdCreditsType(newCredit.getCreditType());
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE t_credit SET F_TERM=?, F_PERCENT=?, F_SUM=?, F_CREDITTYPE_ID_FK=? WHERE F_ID=?; ")) {
            preparedStatement.setString(1, newCredit.getTerm());
            preparedStatement.setString(2, newCredit.getPercent());
            preparedStatement.setString(3, newCredit.getSum());
            preparedStatement.setInt(4, idCreditType);
            preparedStatement.setInt(5, idCredit);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public List<Credit> watchCreditsByCleient(Client client){
        List<Credit>  credits = new ArrayList<>();
        Integer idClient = client.getIdClient();
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT t_client_credit.F_ID_CLIENT_FK, t_client_credit.F_ID_CREDIT_FK, t_credit.F_ID, t_credit.F_CREDITTYPE_ID_FK,t_credit.F_SUM,t_credit.F_PERCENT,t_credit.F_TERM, t_credittype.F_TYPENAME FROM t_client_credit " +
                        "LEFT JOIN t_credit ON t_credit.F_ID=t_client_credit.F_ID_CREDIT_FK "+
                        "LEFT JOIN t_credittype ON t_credittype.F_ID=t_credit.F_CREDITTYPE_ID_FK "+
                        "WHERE t_client_credit.F_ID_CLIENT_FK=? ; ")) {
            preparedStatement.setInt(1, idClient);
            ResultSet resSet = preparedStatement.executeQuery();
            while (resSet.next()) {
                credits.add(Credit.newBuilder()
                        .idCredit(resSet.getInt("t_credit.F_ID"))
                        .term(resSet.getString("t_credit.F_TERM"))
                        .percent(resSet.getString("t_credit.F_PERCENT"))
                        .sum(resSet.getString("t_credit.F_SUM"))
                        .creditType(resSet.getString("t_credittype.F_TYPENAME"))
                        .build());
                credits.size();
            }
            return credits;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return credits;
    }


    public List<Credit> watchCreditsByAllCleient(){
        List<Credit>  credits = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT t_client_credit.F_ID_CLIENT_FK, t_client_credit.F_ID_CREDIT_FK, t_credit.F_ID, t_credit.F_CREDITTYPE_ID_FK,t_credit.F_SUM,t_credit.F_PERCENT,t_credit.F_TERM, t_credittype.F_TYPENAME FROM t_client_credit " +
                        "LEFT JOIN t_credit ON t_credit.F_ID=t_client_credit.F_ID_CREDIT_FK "+
                        "LEFT JOIN t_credittype ON t_credittype.F_ID=t_credit.F_CREDITTYPE_ID_FK ")) {
            ResultSet resSet = preparedStatement.executeQuery();
            while (resSet.next()) {
                credits.add(Credit.newBuilder()
                        .idCredit(resSet.getInt("t_credit.F_ID"))
                        .term(resSet.getString("t_credit.F_TERM"))
                        .percent(resSet.getString("t_credit.F_PERCENT"))
                        .sum(resSet.getString("t_credit.F_SUM"))
                        .creditType(resSet.getString("t_credittype.F_TYPENAME"))
                        .build());
                credits.size();
            }
            return credits;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return credits;


    }


}
