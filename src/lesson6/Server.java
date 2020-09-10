package lesson6;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    private static int PORT= 8189;

    public static void main(String[] args) {
        ServerSocket server = null;
        Socket socket = null;

        try {                   //можно сделать try с ресурсами
            server = new ServerSocket(PORT);   //передаем порт
            System.out.println("Сервер запущен");

            socket = server.accept();  //сохраняется клиентский сокет. Здесь поток останавливается и ждет подключение!
            System.out.println("Клиент подключился");

            Scanner in = new Scanner(socket.getInputStream());
            //Scanner outMsg;

            while (true){
                String str = in.nextLine();
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                if(str.equals("/end")){   //выход
                    System.out.println("Клиент отключился");
                    break;
                }
                System.out.println("Клиент: " + str);
                out.println("echo: " + str);
                }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                server.close();   //всегда закрываем системные ресурсы
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
