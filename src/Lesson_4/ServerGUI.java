package Lesson_4;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ServerGUI extends JFrame implements ActionListener, Thread.UncaughtExceptionHandler {

    private static final int POS_X = 1000;
    private static final int POS_Y = 550;
    private static final int WIDTH = 200;
    private static final int HEIGHT = 100;

    private final ChatServer chatServer = new ChatServer();
    private final JButton btnStart = new JButton("start");
    private final JButton btnStop = new JButton("stop");

    public static void main(String[] args) { SwingUtilities.invokeLater( () -> new ServerGUI() ); }

    public ServerGUI(){
        super("Server Window");
        Thread.setDefaultUncaughtExceptionHandler(this);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(POS_X, POS_Y, WIDTH, HEIGHT);
        setResizable(false);
        setAlwaysOnTop(true);
        setLayout(new GridLayout(1, 2));

        btnStart.addActionListener(this);
        btnStop.addActionListener(this);

        add(btnStart);
        add(btnStop);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if (src == btnStop)
            chatServer.stop();
        else if (src == btnStart){
            chatServer.start(8189);
            throw new RuntimeException("Error hapened");
        }
        else {
            throw new RuntimeException("Unknown source: " + src);
        }
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        e.printStackTrace();
        String msg;
        StackTraceElement[] ste = e.getStackTrace();
        msg = String.format("Exception in thread \"%s\" %s: %s\n\t at %s",
                t.getName(), e.getClass().getCanonicalName(), e.getMessage(), ste[0]);
        JOptionPane.showMessageDialog(this, msg, "Exeption", JOptionPane.ERROR_MESSAGE);
        System.exit(1);
    }
}
