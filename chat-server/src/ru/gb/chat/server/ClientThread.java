package ru.gb.chat.server;

import ru.gb.chat.library.Library;
import ru.gb.jwo.network.SocketThread;
import ru.gb.jwo.network.SocketThreadListener;

import java.net.Socket;

public class ClientThread extends SocketThread {

    private String nickname;
    private boolean isAuthorized;

    public ClientThread(String name, SocketThreadListener listener, Socket socket) {
        super(name, listener, socket);
    }

    public String getNickname(){
        return nickname;
    }

    public boolean isAuthorized(){
        return isAuthorized;
    }

    void authAccept(String nickname){
        isAuthorized = true;
        this.nickname = nickname;
        sendMsg(Library.getAuthAccept(nickname));
    }

    void authFail(){
        sendMsg(Library.getAuthDenied());
        close();
    }

    void msgFormatError(String msg){
        sendMsg(Library.getMsgFormatError(msg));
        close();
    }
}
