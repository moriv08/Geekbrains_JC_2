package ru.gb.chat.server;

import ru.gb.chat.library.Library;
import ru.gb.jwo.network.ServerSocketThread;
import ru.gb.jwo.network.ServerSocketThreadListener;
import ru.gb.jwo.network.SocketThread;
import ru.gb.jwo.network.SocketThreadListener;

import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Vector;

public class ChatServer implements ServerSocketThreadListener, SocketThreadListener {

    ChatServerListener listener;
    private Vector<SocketThread> clients = new Vector<>();

    public ChatServer(ChatServerListener listener){
        this.listener = listener;
    }

    private ServerSocketThread server;
    private final DateFormat DATE_FORMAT = new SimpleDateFormat("HH:mm:ss: ");

    public void start(int port){
        if (server == null || !server.isAlive()) {
            server = new ServerSocketThread(this, "Server", port, 2000);
        } else {
            putLog("Server has already started");
        }
    }

    public void stop(){
        if (server == null || !server.isAlive()){
            putLog("Server is not running");
        } else{
            server.interrupt();
        }
    }

    private void putLog(String msg){
        msg = DATE_FORMAT.format(System.currentTimeMillis()) +
                Thread.currentThread().getName() + ": " + msg;
        listener.onChatServerMessage(msg);
    }

    /*
    * Server listeners
     */

    @Override
    public void onServerStart(ServerSocketThread thread) {
        putLog("Server has started");
        SqlClient.connect();
        putLog(SqlClient.getNickname("max", "123"));
    }

    @Override
    public void onServerStop(ServerSocketThread thread) {
        putLog("Server has stopped");
        SqlClient.disconnect();
    }

    @Override
    public void onServerCreated(ServerSocketThread thread, ServerSocket server) {
        putLog("Server has created");
    }

    @Override
    public void onServerTimeout(ServerSocketThread thread, ServerSocket server) {
        putLog("Server Timeout...");
    }

    @Override
    public void onSocketAccepted(ServerSocketThread thread, ServerSocket server, Socket socket) {
        putLog("Client has connected");
        String name = "SocketThread " + socket.getInetAddress() + ":" + socket.getPort();
        new ClientThread(name, this, socket);
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
        putLog("Client has connected");
    }

    @Override
    public void onSocketStop(SocketThread thread) {
        putLog("Client disconnected");
        clients.remove(thread);
    }

    @Override
    public void onSocketReady(SocketThread thread, Socket socket) {
        putLog("Client is ready to chat");
        clients.add(thread);
    }

    @Override
    public void onReceiveString(SocketThread thread, Socket socket, String msg) {
        ClientThread client = (ClientThread) thread;
        if (client.isAuthorized()){
            handleAuthMessage(client, msg);
        } else {
            handleNonAuthorizedMessage(client, msg);
        }
    }

    private void handleNonAuthorizedMessage(ClientThread client, String msg){
        String[] arr = msg.split(Library.DELIMITER);
        if (arr.length != 3 || !arr[0].equals(Library.AUTH_REQUEST)){
            client.msgFormatError(msg);
            return;
        }
        String login = arr[1];
        String password = arr[2];
        String nickname = SqlClient.getNickname(login, password);
        if (nickname == null){
            client.authFail();
            return;
        }
        client.authAccept(nickname);
        sendToAllAuthorizedClients(Library.getTypeBroadcast("Server", nickname + " connected"));
    }

    private void handleAuthMessage(ClientThread client, String msg){
        sendToAllAuthorizedClients(msg);
    }

    private void sendToAllAuthorizedClients(String msg){
        for (int i = 0; i < clients.size(); i++) {
            ClientThread client = (ClientThread) clients.get(i);
            if (!client.isAuthorized()) continue;
            client.sendMsg(msg);
        }
    }

    @Override
    public void onSocketExeption(SocketThread thread, Throwable throwable) {
        throwable.printStackTrace();
        thread.close();
    }
}
