package ru.gb.jwo.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class SocketThread extends Thread{

    private final SocketThreadListener listener;
    private final Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    public SocketThread(String name, SocketThreadListener listener, Socket socket){
        super(name);
        this.listener = listener;
        this.socket = socket;
        start();
    }

    @Override
    public void run() {
        super.run();
        try {
            listener.onSocketStart(this, socket);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            listener.onSocketReady(this, socket);
            while (!isInterrupted()){
                String msg = in.readUTF();
                listener.onReceiveString(this, socket, msg);
            }
        } catch (IOException e){
            listener.onSocketExeption(this, e);
        } finally {
            close();
        }
    }

    public boolean sendMsg(String msg) {
        try {
            out.writeUTF(msg);
            out.flush();
            return true;
        } catch (IOException e) {
            listener.onSocketExeption(this, e);
            close();
            return false;
        }
    }

    public void close(){
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        interrupt();
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        listener.onSocketStop(this);
    }
}
