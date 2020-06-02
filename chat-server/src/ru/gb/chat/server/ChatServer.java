package ru.gb.chat.server;

import ru.gb.jwo.network.ServerSocketThread;
import ru.gb.jwo.network.ServerSocketThreadListener;
import ru.gb.jwo.network.SocketThread;
import ru.gb.jwo.network.SocketThreadListener;

import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class ChatServer implements ServerSocketThreadListener, SocketThreadListener {

    private ServerSocketThread server;
    private final DateFormat data = new SimpleDateFormat("HH:mm:ss: ");

    public void start(int port){
        if (server == null || !server.isAlive()) {
            server = new ServerSocketThread(this, "Tread", port, 2000);
        } else {
            System.out.println("Server has already running");
        }
    }

    public void stop(){
        if (server == null || !server.isAlive()){
            System.out.println("Server is not running");
        }
        else{
            server.interrupt();
        }
    }

    private void putLog(String msg){
        msg = data.format(System.currentTimeMillis()) + Thread.currentThread().getName() + ": " + msg;
        System.out.println(msg);
    }

    /*
    * Server listeners
     */

    @Override
    public void onServerStart(ServerSocketThread thread) {
        putLog("Server has started");
    }

    @Override
    public void onServerStop(ServerSocketThread thread) {
        System.out.println("Server stopped");
    }

    @Override
    public void onServerCreated(ServerSocketThread thread, ServerSocket server) {
        System.out.println("Server has created");
    }

    @Override
    public void onServerTimeout(ServerSocketThread thread, ServerSocket server) {
//        System.out.println("Server on timeout");
    }

    @Override
    public void onSocketAccepted(ServerSocketThread thread, ServerSocket server, Socket socket) {
        System.out.println("Client connected");
        String name = "SocketThread " + socket.getInetAddress() + ": " + socket.getPort();
        new SocketThread(name, this, socket);

    }

    @Override
    public void onServerException(ServerSocketThread thread, Throwable trowable) {
        trowable.printStackTrace();
    }

    /*
     * Socket methods
     */

    @Override
    public void onSocketStart(SocketThread thread, Socket socket) {
        putLog("Client connected");
    }

    @Override
    public void onSocketStop(SocketThread thread) {
        putLog("Client disconnected");
    }

    @Override
    public void onSocketReady(SocketThread thread, Socket socket) {
        putLog("Client is ready to chat");
    }

    @Override
    public void onReceiveString(SocketThread thread, Socket socket, String msg) {

        thread.sendMsg("echo: " + msg);
    }

    @Override
    public void onSocketExeption(SocketThread thread, Throwable throwable) {
        throwable.printStackTrace();
    }
}
