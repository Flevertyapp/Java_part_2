package lesson6;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private final int PORT = 8189;                  //порт
    private final String IP_ADDRESS = "localhost"; //айпишник
    private Socket socket;
    private Scanner input;
    private PrintWriter output;
    private Scanner inputMsg;


    public Client(){
        try {
            socket = new Socket(IP_ADDRESS, PORT);
            Scanner input = new Scanner(socket.getInputStream());
            PrintWriter output = new PrintWriter(socket.getOutputStream());
            Scanner inputMsg = new Scanner(System.in);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Thread MyThread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    String message = inputMsg.nextLine();
                    output.println(message);
                    if(message.equals("/end")){   //выход
                        System.out.println("Клиент отключился");
                        break;
                    }
                }
            }
        });

        Thread myThread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    String message = input.nextLine();
                    output.println(message);
                    if (message.equals("/end")) {   //выход
                        System.out.println("Сервер отключился");
                        break;
                    }
                }
            }
        });

        myThread2.start();
        MyThread1.start();
    }
}
