package client.service;

import client.entity.*;


import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Service {

    public static Socket socket = null;
    public static PrintStream printStream;
    public static BufferedReader bufferedReader;
    public static ObjectInputStream deserializer;
    public static ObjectOutputStream serializer;

    public static void connection() throws IOException {
        socket = new Socket(InetAddress.getLocalHost(), 8072);
        printStream = new PrintStream(socket.getOutputStream());
        bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        serializer = new ObjectOutputStream(socket.getOutputStream());
        deserializer = new ObjectInputStream(socket.getInputStream());
    }

    public static User checkUser(String login, String password) {
        try {
            serializer.writeObject("Authorization");
            serializer.writeObject(login);
            serializer.writeObject(password);
            serializer.flush();
            User user = (User) deserializer.readObject();
            return user;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }


    /**
     * Administrator
     */

    public static List<User> getUsersForAdmin() {
        List<User> users = null;
        try {
            serializer.writeObject("PrintUsersForAdmin");
            users = (List<User>) deserializer.readObject();
            serializer.flush();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return users;
    }

    public static String deleteUsers(String user) {
        String str = null;
        try {
            serializer.writeObject("DeleteUser");
            serializer.writeObject(user);
            str = bufferedReader.readLine();
            serializer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }

    public static String createNewClient(User user, Client client) throws IOException {
        serializer.writeObject("ClientRegistration");
        serializer.writeObject(user);
        serializer.writeObject(client);
        serializer.flush();
        return bufferedReader.readLine();
    }

    public static List<String> getAllCreditsType() {
        List<String> types = null;
        try {
            serializer.writeObject("ReadAllCreditsType");
            types = (List<String>) deserializer.readObject();
            serializer.flush();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return types;
    }

    public static String addCreditsType(String type) {
        String str = null;
        try {
            serializer.writeObject("AddCreditsType");
            serializer.writeObject(type);
            str = bufferedReader.readLine();
            serializer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }

    public static String deleteCreditsType(String type) {
        String str = null;
        try {
            serializer.writeObject("DeleteCreditsType");
            serializer.writeObject(type);
            str = bufferedReader.readLine();
            serializer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }

    public static List<Credit> getAllCredits() {
        List<Credit> credits = null;
        try {
            serializer.writeObject("ReadInfoAboutCredits");
            credits = (List<Credit>) deserializer.readObject();
            serializer.flush();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return credits;
    }



    public static String addNewCredit(Credit credit) {
        String str = null;
        try {
            serializer.writeObject("AddCredit");
            serializer.writeObject(credit);
            serializer.flush();
            str = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }

    public static String deleteCredit(Credit credit) {
        String str = null;
        try {
            serializer.writeObject("DeleteCredit");
            serializer.writeObject(credit);
            str = bufferedReader.readLine();
            serializer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }



    public static List<Credit> getFindCredit(String columnName, String findString) {
        List<Credit> credits = null;
        try {
            serializer.writeObject("FindInfoAboutCredit");
            serializer.writeObject(columnName);
            serializer.writeObject(findString);
            credits = (List<Credit>) deserializer.readObject();
            serializer.flush();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return credits;
    }

    public static int getIdCredit(Credit credit) {
        int id = -1;
        try {
            serializer.writeObject("GetIdCredit");
            serializer.writeObject(credit);
            id = (Integer) deserializer.readObject();
            System.out.println("id:" + id);
            serializer.flush();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return id;
    }

    public static String editCredit(int id, Credit credit) {
        String str = null;
        try {
            serializer.writeObject("EditCredit");
            serializer.writeObject(id);
            serializer.writeObject(credit);
            serializer.flush();
            str = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }

    public static Client findClient(User user) {
        try {
            serializer.writeObject("FindClient");
            serializer.writeObject(user);
            serializer.flush();
            Client editClient = (Client) deserializer.readObject();
            return editClient;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static boolean editClient(Client editClient) {
        try {
            serializer.writeObject("EditClient");
            serializer.writeObject(editClient);
            serializer.flush();
            Client success = (Client) deserializer.readObject();
            if (success != null) {
                return true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public static User getUserById(Client client) {
        try {
            serializer.writeObject("GetUserById");
            serializer.writeObject(client);
            serializer.flush();
            User user = (User) deserializer.readObject();
            return user;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static boolean takeCredit(Client client, Credit findCredit) {
        try {
            serializer.writeObject("TakeCredit");
            serializer.writeObject(client);
            serializer.writeObject(findCredit);
            serializer.flush();
            String str = bufferedReader.readLine();
            return str.equals("Успешно") ? true : false;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public static List<String> getDatasByCredit(Client client) {
        List<String> data = new ArrayList<>();
        try {
            serializer.writeObject("DataByCredit");
            serializer.writeObject(client);
            serializer.flush();
            data = (List<String >) deserializer.readObject();
            return data;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return data;

    }

    public static List<Credit> watchAllCreditsForUser(Client client) {
        List<Credit> credits = new ArrayList<>();
        try {
            serializer.writeObject("CreditsByClient");
            serializer.writeObject(client);
            serializer.flush();
            credits = (List<Credit>) deserializer.readObject();
            return credits;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return credits;
    }

    public static List<Credit> watchAllCreditsForUsers() {
        List<Credit> credits = new ArrayList<>();
        try {
            serializer.writeObject("CreditsByAllClients");
            serializer.flush();
            credits = (List<Credit>) deserializer.readObject();
            return credits;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return credits;
    }

    public static List<Client> watchAllClientWithCredit() {
        List<Client> clients = new ArrayList<>();
        try {
            serializer.writeObject("ClientsByAllCredits");
            serializer.flush();
            clients = (List<Client>) deserializer.readObject();
            return clients;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return clients;
    }

    public static List<String> getDatasByCreditAllClients() {
        List<String> datas = new ArrayList<>();
        try {
            serializer.writeObject("Datas");
            serializer.flush();
            datas = (List<String >) deserializer.readObject();
            return datas;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return datas;
    }

    public static boolean deleteCreditForUser(Client client, Credit credit, String data) {
        try {
            serializer.writeObject("DeleteCreditForUser");
            serializer.writeObject(client);
            serializer.writeObject(credit);
            serializer.writeObject(data);
            serializer.flush();
            String str = bufferedReader.readLine();
            return str.equals("Успешно") ? true : false;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
