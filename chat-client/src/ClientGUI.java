import ru.gb.jwo.network.SocketThread;
import ru.gb.jwo.network.SocketThreadListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Socket;

import static java.lang.String.copyValueOf;

public class ClientGUI extends JFrame implements ActionListener, Thread.UncaughtExceptionHandler, SocketThreadListener {

    private static final int WIDTH = 400;
    private static final int HEIGHT = 300;

    private final JTextArea log = new JTextArea();
    private final JPanel panelTop = new JPanel(new GridLayout(2, 3));
    private final JTextField tfIPAdress = new JTextField("127.0.0.1");
    private final JTextField tfPort = new JTextField("8189");
    private final JCheckBox cbAlwaysOnTop = new JCheckBox("Always on top");
    private final JTextField tfLogin = new JTextField("Max");
    private final JPasswordField tfPassword = new JPasswordField("Password");
    private final JButton btnLogin = new JButton("Login");

    private final JPanel panelBottom = new JPanel(new BorderLayout());
    private final JButton btnDisconnect = new JButton("<html><b>Disconnect</b></html>");
    private final JTextField tfMessage = new JTextField();
    private final JButton btnSend = new JButton("Send");

    private final JList<String> userList = new JList<>();
    private boolean shownIoErrors = false;

    SocketThread socketThread;

    private final JScrollPane scrollLog = new JScrollPane(log);
    private final JScrollPane scrollUser = new JScrollPane(userList);

    private String[] users = {"user1", "user2", "user1", "user2",
                                "user_with_an_exceptionally_long_name_in_this_chat"};




    public static void main(String[] args) {
        SwingUtilities.invokeLater( () -> new ClientGUI() );
    }

    public ClientGUI(){
        super("Chat Window");
        Thread.setDefaultUncaughtExceptionHandler(this);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);

        log.setEditable(false);

        cbAlwaysOnTop.addActionListener(this);
        btnSend.addActionListener(this);
        tfMessage.addActionListener(this);
        btnLogin.addActionListener(this);
        btnDisconnect.addActionListener(this);

        userList.setListData(users);
        scrollUser.setPreferredSize(new Dimension(100, 0));

        panelTop.setBackground(Color.GRAY);

//        panelTop.setVisible(true);
//        panelBottom.setVisible(false);
        changePanels(true, false);

        panelBottom.setBackground(Color.LIGHT_GRAY);

        panelTop.add(tfIPAdress, tfPort);
        panelTop.add(tfPort);
        panelTop.add(cbAlwaysOnTop);
        panelTop.add(tfLogin);
        panelTop.add(tfPassword);
        panelTop.add(btnLogin);

        panelBottom.add(btnDisconnect, BorderLayout.WEST);
        panelBottom.add(tfMessage, BorderLayout.CENTER);
        panelBottom.add(btnSend, BorderLayout.EAST);

        add(scrollLog, BorderLayout.CENTER);
        add(scrollUser, BorderLayout.EAST);
        add(panelTop, BorderLayout.NORTH);
        add(panelBottom, BorderLayout.SOUTH);

        setVisible(true);
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        e.printStackTrace();
        showExeption(t, e);
        System.exit(1);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if (src == cbAlwaysOnTop){
            setAlwaysOnTop(cbAlwaysOnTop.isSelected());
        }else if (src == btnSend || src == tfMessage)
            sendMsg(tfMessage.getText());
        else if (src == btnLogin)
            connetc();
        else if (src == btnDisconnect)
            disconnect();
        else
            throw new RuntimeException("Unknown source: " + src);
    }

    private void connetc() {
        try {
            Socket socket = new Socket(tfIPAdress.getText(), Integer.parseInt(tfPort.getText()));
            socketThread = new SocketThread("Client", this, socket);
        } catch (IOException e) {
            showExeption(Thread.currentThread(), e);
        }
    }

    private void disconnect() {
        socketThread.interrupt();
        changePanels(true, false);
    }

    public void sendMsg(String msg){

        tfMessage.grabFocus();

        if ("".equals(msg))
            return;
        else if (msg.length() > 700){
            JOptionPane.showMessageDialog(this, "Максимально 700 символов");
            return;
        }

        if (msg.length() < 33)
            socketThread.sendMsg(msg);
        else{
            char[] txt = new char[700];
            if (msg.length() > 33){
                int wrap = 0;
                for (int i = 0; i < msg.length(); i++)
                        if (i % 33 == 0 && i != 0){
                            txt[i] = '\n';
                            wrap++;
                        }
                        else
                            txt[i] = msg.charAt(i - wrap);
                socketThread.sendMsg(copyValueOf(txt));
            }
        }
        logFile(msg, tfLogin.getText());
        tfMessage.setText("");
    }

    private void logFile(String msg, String username){

        try (FileWriter file = new FileWriter("log.txt", true)){
            file.write(username + ": " + msg + "\n");
            file.flush();
        }catch (IOException e){
            if (!shownIoErrors){
                shownIoErrors = true;
                showExeption(Thread.currentThread(), e);
            }
        }
    }

    private void putLog(String msg){
        if ("".equals(msg)) return;
        SwingUtilities.invokeLater(() -> {
            log.append(msg + '\n');
            log.setCaretPosition(log.getDocument().getLength());
        });
    }

    private void showExeption(Thread t, Throwable e){
        String msg;
        StackTraceElement[] ste = e.getStackTrace();
        if(ste.length == 0)
            msg = "Empty Stacktrace";
        else {
            msg = String.format("Exeption in \"%s\" %s: %s\n\t at %s",
                    t.getName(), e.getClass().getCanonicalName(), e.getMessage(), ste[0]);
        }
        JOptionPane.showMessageDialog(null, msg, "Exeption", JOptionPane.ERROR_MESSAGE);
    }

    private void changePanels(boolean top, boolean bottom){
        panelTop.setVisible(top);
        panelBottom.setVisible(bottom);
    }

    /*
    * SocketThreadListener methods
     */

    @Override
    public void onSocketStart(SocketThread thread, Socket socket) {

        putLog("Start");

    }

    @Override
    public void onSocketStop(SocketThread thread) {
        putLog("Stop");

    }

    @Override
    public void onSocketReady(SocketThread thread, Socket socket) {
        putLog("Ready");

//        this.panelTop.setVisible(false);
//        panelBottom.setVisible(true);
        changePanels(false, true);
    }

    @Override
    public void onReceiveString(SocketThread thread, Socket socket, String msg) {
        putLog(msg);
    }

    @Override
    public void onSocketExeption(SocketThread thread, Throwable throwable) {
        showExeption(thread, throwable);
    }
}
