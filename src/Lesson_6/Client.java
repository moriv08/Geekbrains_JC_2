package Lesson_6;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private static String msg;

    public static void main(String[] args) {
        System.out.println("Connecting...");
        try (Socket client = new Socket("localhost", 8189);
             Scanner scanner = new Scanner(System.in)){
            System.out.println("Connected");

            DataInputStream in = new DataInputStream(client.getInputStream());
            DataOutputStream out = new DataOutputStream(client.getOutputStream());

            while (true){
                msg = scanner.nextLine();
                out.writeUTF(msg);
                System.out.println(in.readUTF());
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
