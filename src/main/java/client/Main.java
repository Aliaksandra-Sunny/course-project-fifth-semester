package client;

import client.controller.Controller;
import client.service.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Main {
    public static void main(String[] args) {
        Socket socket = null;
        BufferedReader br = null;
        try {
            Controller controller = new Controller();

            controller.startApplication();
            Service.connection();

        } catch (UnknownHostException e) {
            System.out.println("адрес недоступен" + e);
        } catch (IOException e) {
            System.out.println("Ошибка I/О потока.\nНе удалось подключится к серверу.\n" + e.getMessage());
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (socket!= null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}