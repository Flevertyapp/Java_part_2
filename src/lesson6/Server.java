package lesson6;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Socket clientSocket = null;

        try (ServerSocket serverSocket = new ServerSocket(8189)) {        //передаем порт
            System.out.println("Сервер запущен");
            clientSocket = serverSocket.accept();  //сохраняется клиентский сокет. Здесь поток останавливается и ждет подключение!
            System.out.println("Подключился клиент: " + clientSocket.getRemoteSocketAddress()); //адрес подключенного клиента
            DataInputStream inputStream = new DataInputStream(clientSocket.getInputStream());  //для чтения
            DataOutputStream outputStream = new DataOutputStream(clientSocket.getOutputStream()); //для отправки

            //поток на чтение
            Thread threadReader = new Thread(() -> {
                try {
                    while (true) {
                        outputStream.writeUTF(scanner.nextLine());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            threadReader.setDaemon(true);
            threadReader.start();

            while (true) {
                String str = inputStream.readUTF();
                if (str.equals("/close")) { //отслеживаем выход клиента
                    System.out.println("Клиент отключился");
                    outputStream.writeUTF("/close");
                    break;
                } else {        //печатаем сообщения
                    System.out.println("Клиент: " + str);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException | NullPointerException e) {
                e.printStackTrace();
            }
        }
    }
}


