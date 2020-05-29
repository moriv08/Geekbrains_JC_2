package Lesson_1;

import javax.swing.*;
import java.awt.*;

public class MainCanvas extends JPanel {

    MainWindow gameController;

    long currentTime;
    float deltaTime;
    long lastFrame;

    MainCanvas(MainWindow gameController){
        this.gameController = gameController;
        lastFrame = System.nanoTime();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        currentTime = System.nanoTime();
        deltaTime = (currentTime - lastFrame) * 0.000000001f;
        gameController.onDrawFrame(this, g, deltaTime);
        lastFrame = currentTime;

        try {
            Thread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        setBackground(new BackgroundCol());
        repaint();
    }

    public int getLeft() { return 0; }
    public int getRight() { return getWidth() - 1; }
    public int getTop() { return 0; }
    public int getBottom() { return getHeight() - 1; }

}
