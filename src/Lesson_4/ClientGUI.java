package Lesson_4;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileWriter;
import java.io.IOException;

import static java.lang.String.copyValueOf;

public class ClientGUI extends JFrame implements ActionListener, Thread.UncaughtExceptionHandler {

    private static final int WIDTH = 500;
    private static final int HEIGHT = 700;

    private final JTextArea log = new JTextArea();
    private final JPanel panelTop = new JPanel(new GridLayout(2, 3));
    private final JTextField tfIPAdress = new JTextField("127.0.0.1");
    private final JTextField tfPort = new JTextField("8189");
    private final JCheckBox cbAlwaysOnTop = new JCheckBox("Always on top");
    private JTextField tfLogin = new JTextField("Max");
    private final JPasswordField tfPassword = new JPasswordField("Password");
    private final JButton btnLogin = new JButton("Login");

    private final JPanel panelBottom = new JPanel(new BorderLayout());
    private final JButton btnDisconnect = new JButton("<html><b>Disconnect</b></html>");
    private final JTextField tfMessage = new JTextField();
    private final JButton btnSend = new JButton("Send");

    private final JList<String> userList = new JList<>();
    private boolean shownIoErrors = false;

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
//        tfMessage.addKeyListener(new KeyAdapter() {
//            @Override
//            public void keyPressed(KeyEvent e) {
//                if (e.getKeyCode() == KeyEvent.VK_ENTER){
//                    sendMsg();
//                }
//            }
//        });
        tfMessage.addActionListener(this);

        userList.setListData(users);
        scrollUser.setPreferredSize(new Dimension(100, 0));

        panelTop.setBackground(Color.GRAY);
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
            setAlwaysOnTop(true);
        }else if (src == btnSend || src == tfMessage)
            sendMsg(tfMessage.getText());
    }

    public void sendMsg(String msg){

        tfMessage.grabFocus();

        if ("".equals(msg))
            return;
        else if (msg.length() > 700){
            JOptionPane.showMessageDialog(this, "Максимально 700 символов");
            return;
        }

        if (msg.length() < 53)
            putLog(String.format("%s: %s", tfLogin.getText(), msg));
        else{
            char[] txt = new char[700];
            if (msg.length() < 700)
                for (int i = 0; i < msg.length(); i++)
                    if (i % 53 == 0)
                        txt[i] = '\n';
                    else
                        txt[i] = msg.charAt(i);

            putLog(String.format("%s: %s", tfLogin.getText(), copyValueOf(txt)));
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
}
