package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(8072);

            System.out.println("Сервер готов к работе");

            while (true) {
                Socket socket = server.accept(); // ожидание подключения клиента
                System.out.println( "Подключился: "+socket.getInetAddress().getHostName());
                MainServer thread = new MainServer(socket);
                thread.start();

            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}
