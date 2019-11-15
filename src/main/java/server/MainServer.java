package server;

import client.entity.*;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class MainServer extends Thread {
    private Socket socket;
    private PrintStream printStream;
    private BufferedReader bufferedReader;
    private InetAddress inetAddress;

    public MainServer(Socket s) throws IOException {
        socket = s;
        inetAddress = s.getInetAddress();
        printStream = new PrintStream(s.getOutputStream());
        bufferedReader = new BufferedReader(new InputStreamReader(s.getInputStream()));
    }

    public void run() {

        try {
            String str;
            Connection conn = null;
            ObjectOutputStream serializer = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream deserializer = new ObjectInputStream(socket.getInputStream());

            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank?verifyServerCertificate=false&useSSL=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "Klimyka15");

            while ((str = (String) deserializer.readObject()) != null) {
                if ("ClientRegistration".equals(str)) {
                    UserDAO auth = new UserDAO(conn);
                    User user = (User) deserializer.readObject();
                    if ((auth.getUser(user)) == 0) {
                        if (auth.createNewUser(user)) {
                            ClientDAO get = new ClientDAO(conn);
                            Client client = (Client) deserializer.readObject();
                            if (get.addNewClient(client, user.getLogin())) {
                                printStream.println("Аккаунт успешно создан!");
                            }
                        } else
                            printStream.println("Ошибка!");
                    } else {
                        printStream.println("Аккаунт с таким логином существует!");
                    }
                } else if ("Authorization".equals(str)) {
                    UserDAO reg = new UserDAO(conn);
                    User user = User.newBuilder()
                            .login((String) deserializer.readObject())
                            .password((String) deserializer.readObject())
                            .build();
                    User newUser = reg.checkUser(user);
                    serializer.writeObject(newUser);
                    serializer.flush();
                } else if ("PrintUsersForAdmin".equals(str)) {
                    UserDAO userDAO = new UserDAO(conn);
                    List<User> users = userDAO.findAllUsers();
                    serializer.writeObject(users);
                    serializer.flush();
                } else if ("DeleteUser".equals(str)) {
                    UserDAO userDAO = new UserDAO(conn);
                    String user = (String) deserializer.readObject();
                    if (userDAO.deleteUsers(user)) {
                        printStream.println("Клиент успешно удален!");
                    } else {
                        printStream.println("Ошибка при удалении!");
                    }
                    serializer.flush();
                } else if ("PrintWorkersForAdmin".equals(str)) {
                    WorkerDAO workerDAO = new WorkerDAO(conn);
                    List<Worker> workers = workerDAO.findAllWorkers();
                    serializer.writeObject(workers);
                    serializer.flush();
                } else if ("DeleteWorker".equals(str)) {
                    WorkerDAO workerDAO = new WorkerDAO(conn);
                    String worker = (String) deserializer.readObject();
                    if (workerDAO.deleteWorkers(worker)) {
                        printStream.println("Работник успешно удален!");
                    } else {
                        printStream.println("Ошибка при удалении!");
                    }
                    serializer.flush();
                } else if ("PrintExpertsForAdmin".equals(str)) {
                    ExpertDAO expertDAO = new ExpertDAO(conn);
                    List<Expert> experts = expertDAO.findAllExperts();
                    serializer.writeObject(experts);
                    serializer.flush();
                } else if ("ExpertRegistration".equals(str)) {
                    UserDAO auth = new UserDAO(conn);
                    ExpertDAO get = new ExpertDAO(conn);
                    User user = (User) deserializer.readObject();
                    Expert expert = (Expert) deserializer.readObject();
                    if ((auth.getUser(user)) == 0) {
                        if (auth.createNewUser(user)) {
                            if (get.addNewExpert(expert, user.getLogin())) {
                                printStream.println("Аккаунт успешно создан!");
                            }
                        } else
                            printStream.println("Ошибка!");
                    } else {
                        printStream.println("Аккаунт с таким логином существует!");
                    }
                } else if ("WorkerRegistration".equals(str)) {
                    UserDAO auth = new UserDAO(conn);
                    User user = (User) deserializer.readObject();
                    WorkerDAO get = new WorkerDAO(conn);
                    Worker worker = (Worker) deserializer.readObject();
                    if ((auth.getUser(user)) == 0) {
                        if (auth.createNewUser(user)) {
                            if (get.addNewWorker(worker, user.getLogin())) {
                                printStream.println("Аккаунт успешно создан!");
                            }
                        } else
                            printStream.println("Ошибка!");
                    } else {
                        printStream.println("Аккаунт с таким логином существует!");
                    }
                } else if ("ReadAllCreditsType".equals(str)) {
                    CreditDAO get = new CreditDAO(conn);
                    List<String> types = get.getAllCreditsType();
                    serializer.writeObject(types);
                    serializer.flush();
                } else if ("AddCreditsType".equals(str)) {
                    CreditDAO creditDAO = new CreditDAO(conn);
                    String type = (String) deserializer.readObject();
                    if (creditDAO.addCreditsType(type)) {
                        printStream.println("Цель успешно добавлена!");
                    } else {
                        printStream.println("Цель с таким названием уже существует!");
                    }
                    serializer.flush();
                } else if ("DeleteCreditsType".equals(str)) {
                    CreditDAO creditDAO = new CreditDAO(conn);
                    String type = (String) deserializer.readObject();
                    if (creditDAO.deleteCreditsType(type)) {
                        printStream.println("Цель успешно удалена!");
                    } else {
                        printStream.println("Ошибка при удалении!");
                    }
                    serializer.flush();
                } else if ("ReadInfoAboutCredits".equals(str)) {
                    CreditDAO get = new CreditDAO(conn);
                    List<Credit> credits = get.getAllCredits();
                    serializer.writeObject(credits);
                } else if ("AddCredit".equals(str)) {
                    CreditDAO get = new CreditDAO(conn);
                    Credit credit = (Credit) deserializer.readObject();
                    if (get.addNewCredit(credit)) {
                        printStream.println("Запись успешно создана!");
                    } else {
                        printStream.println("Ошибка в создании записи!");
                    }
                } else if ("DeleteCredit".equals(str)) {
                    CreditDAO get = new CreditDAO(conn);
                    Credit credit = (Credit) deserializer.readObject();
                    if (get.deleteCredit(credit)) {
                        printStream.println("Информация о кредите успешно удалена!");
                    } else {
                        printStream.println("Ошибка при удалении!");
                    }
                    serializer.flush();
                } else if ("FindInfoAboutCredit".equals(str)) {
                    CreditDAO get = new CreditDAO(conn);
                    String columnName = (String) deserializer.readObject();
                    String findString = (String) deserializer.readObject();
                    List<Credit> credits = get.getFindCredit(columnName, findString);
                    serializer.writeObject(credits);
                    serializer.flush();
                } else if ("GetIdCredit".equals(str)) {
                    CreditDAO get = new CreditDAO(conn);
                    Credit credit = (Credit) deserializer.readObject();
                    int id = get.getIdCredit(credit.getTerm(), credit.getPercent(), credit.getSum());
                    serializer.writeObject(id);
                    serializer.flush();
                } else if ("EditCredit".equals(str)) {
                    CreditDAO get = new CreditDAO(conn);
                    int id = (Integer) deserializer.readObject();
                    Credit credit = (Credit) deserializer.readObject();
                    if (get.editCredit(id, credit)) {
                        printStream.println("Запись успешно отредактирована!");
                    } else {
                        printStream.println("Ошибка в редактировании записи!");
                    }
                } else if ("SendCreditForAssessment".equals(str)) {
                    CreditDAO get = new CreditDAO(conn);
                    Credit credit = (Credit) deserializer.readObject();
                    if (get.sendCreditForAssessment(credit)) {
                        printStream.println("Кредит отправлен на оценку!");
                    } else {
                        printStream.println("Кол-во кредитов на оценку больше 4!");
                    }
                    serializer.flush();
                } else if ("CreditsForAssessment".equals(str)) {
                    CreditDAO get = new CreditDAO(conn);
                    List<Credit> credits = get.getAllCreditsForAssessment();
                    serializer.writeObject(credits);
                } else if ("DeleteCreditForAssessment".equals(str)) {
                    CreditDAO get = new CreditDAO(conn);
                    Credit credit = (Credit) deserializer.readObject();
                    if (get.deleteCreditForAssessment(credit)) {
                        printStream.println("Кредит удален из отправки на оценку!");
                    } else {
                        printStream.println("Ошибка при удалении!");
                    }
                    serializer.flush();
                } else if ("FindInfoAboutExpert".equals(str)) {
                    ExpertDAO get = new ExpertDAO(conn);
                    String columnName = (String) deserializer.readObject();
                    String findString = (String) deserializer.readObject();
                    List<Expert> experts = get.getFindExpert(columnName, findString);
                    serializer.writeObject(experts);
                    serializer.flush();
                } else if ("EnterScaleSize".equals(str)) {
                    AdminDAO get = new AdminDAO(conn);
                    String size = (String) deserializer.readObject();
                    if (get.addScaleSize(size)) {
                        printStream.println("Размер шкалы добавлен!");
                    } else {
                        printStream.println("Ошибка при добавлении!");
                    }
                    serializer.flush();
                } else if ("GetScale".equals(str)) {
                    AdminDAO get = new AdminDAO(conn);
                    int id = get.getScale();
                    serializer.writeObject(id);
                    serializer.flush();
                } else if ("AddAssessments".equals(str)) {
                    AdminDAO get = new AdminDAO(conn);
                    String login = (String) deserializer.readObject();
                    String expertAssessments = (String) deserializer.readObject();
                    if (get.addAssessmets(login,expertAssessments)) {
                        printStream.println("Оценки добавлены успешно!");
                    } else {
                        printStream.println("Введите правильный логин!");
                    }
                    serializer.flush();
                }else if ("WatchAllSummAssessments".equals(str)) {
                    ExpertDAO get = new ExpertDAO(conn);
                    List<Expert> experts = get.watchAllSummAssessments();
                    serializer.writeObject(experts);
                    serializer.flush();
                }else if ("GetAssByNameAndSName".equals(str)) {
                    ExpertDAO get = new ExpertDAO(conn);
                    String sName = (String) deserializer.readObject();
                    String name = (String) deserializer.readObject();
                    String string = get.getAssByNameAndSName(sName,name);
                    printStream.println(string);
                    serializer.flush();
                }else if ("GetN".equals(str)) {
                    CreditDAO get = new CreditDAO(conn);
                    int N = get.countCreditForAssessment();
                    serializer.writeObject(N);
                    serializer.flush();
                }else if ("DeleteExpert".equals(str)) {
                    ExpertDAO expertDAO = new ExpertDAO(conn);
                    String worker = (String) deserializer.readObject();
                    if (expertDAO.deleteExpert(worker)) {
                        printStream.println("Эксперт успешно удален!");
                    } else {
                        printStream.println("Ошибка при удалении!");
                    }
                    serializer.flush();
                }else if("EditClient".equals(str)){
                    ClientDAO update = new ClientDAO(conn);
                    Client editClient = (Client) deserializer.readObject();
                    if (update.editClient(editClient)) {
                       serializer.writeObject(editClient);
                    } else {
                        serializer.writeObject(null);
                    }
                    serializer.flush();
                }else if("FindClient".equals(str)){
                    ClientDAO find = new ClientDAO(conn);
                    User user = (User) deserializer.readObject();
                    Client editClient = find.findClient(user);
                    serializer.writeObject(editClient);
                    serializer.flush();
                }else if("GetUserById".equals(str)){
                    UserDAO find = new UserDAO(conn);
                    Client client = (Client) deserializer.readObject();
                    User user = find.geUserById(client.getIdUserFK());
                    serializer.writeObject(user);
                    serializer.flush();
                }else if("TakeCredit".equals(str)){
                    ClientDAO take = new ClientDAO(conn);
                    Client client = (Client) deserializer.readObject();
                    Credit credit = (Credit) deserializer.readObject();
                    printStream.println(take.takeCredit(client, credit) ? "Успешно" : "Неуспешно");
                    serializer.flush();
                }else if ("DataByCredit".equals(str)) {
                    ClientDAO get = new ClientDAO(conn);
                    Client client = (Client) deserializer.readObject();
                    List<String> datas = get.watchDatas(client);
                    serializer.writeObject(datas);
                    serializer.flush();
                }else if ("CreditsByClient".equals(str)) {
                    CreditDAO get = new CreditDAO(conn);
                    Client client = (Client) deserializer.readObject();
                    List<Credit> credits = get.watchCreditsByCleient(client);
                    serializer.writeObject(credits);
                    serializer.flush();
                }else if ("CreditsByAllClients".equals(str)) {
                    CreditDAO get = new CreditDAO(conn);
                    List<Credit> credits = get.watchCreditsByAllCleient();
                    serializer.writeObject(credits);
                    serializer.flush();
                }else if ("ClientsByAllCredits".equals(str)) {
                    ClientDAO get = new ClientDAO(conn);
                    List<Client> clients = get.watchClientsByAllCredit();
                    serializer.writeObject(clients);
                    serializer.flush();
                }else if ("Datas".equals(str)) {
                    ClientDAO get = new ClientDAO(conn);
                    List<String> datas = get.watchAllDatas();
                    serializer.writeObject(datas);
                    serializer.flush();
                }else if ("DeleteCreditForUser".equals(str)) {
                    ClientDAO get = new ClientDAO(conn);
                    Client client = (Client) deserializer.readObject();
                    Credit credit = (Credit) deserializer.readObject();
                    String data = (String) deserializer.readObject();
                    printStream.println(get.deleteCreditForUser(client,credit,data) ? "Успешно" : "Неуспешно");
                    serializer.flush();
                }

                serializer.flush();
            }

        }  catch (IOException | ClassNotFoundException | SQLException e) {
        System.out.println(e.getMessage());
    } finally {
        disconnect();
    }

    }

    public void disconnect() {
        try {
            if (printStream != null) {
                printStream.close();
            }
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            System.out.println("Закончил работу: " + inetAddress.getHostName());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            this.interrupt();
        }
    }
}
