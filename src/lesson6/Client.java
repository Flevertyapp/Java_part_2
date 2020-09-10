package lesson6;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private static final int PORT = 8189;                  //порт
    private static final String IP_ADDRESS = "localhost"; //айпишник

    public static void main(String[] args) {
        Socket socket = null;
        Scanner scanner = new Scanner(System.in);
        try {
            socket = new Socket(IP_ADDRESS, PORT);
            System.out.println("Подключился к серверу: " + socket.getRemoteSocketAddress()); //адрес подключенного клиента
            DataInputStream inputStream = new DataInputStream(socket.getInputStream());  //для чтения
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream()); //для отправки

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
                if (str.equals("/close")) { //отслеживаем пропажу сервера
                    System.out.println("Потеряно соединение с сервером");
                    outputStream.writeUTF("/close");
                    break;
                } else {        //печатаем сообщения
                    System.out.println("Сервер: " + str);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException | NullPointerException e) {
                e.printStackTrace();
            }
        }
    }
}
