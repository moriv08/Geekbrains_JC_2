package Lesson_6;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {

    private static String msg;

    public static void main(String[] args) {

        System.out.println("Waits for connection");

        try (ServerSocket server = new ServerSocket(8189);
             Socket socket = server.accept()){

            System.out.println("Client connected");

            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            while (true){
                msg = in.readUTF();
                out.writeUTF("server: " + msg);
            }

        } catch (IOException e){

            e.printStackTrace();
        }

    }
}
