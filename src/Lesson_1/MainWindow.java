package Lesson_1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainWindow extends JFrame {

    int spriteCounter = 1;

    Sprite[] sprites = new Sprite[1];
    MainCanvas canvas;

    MainWindow(){
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(300, 300, 700, 400);
        canvas = new MainCanvas(this);

        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1){

                    addOne(new Ball(e.getX(), e.getY()));
                }
                else if (e.getButton() == MouseEvent.BUTTON3)
                    removeOne();
            }
        });
        add(canvas);
        initApplication();
        setVisible(true);
    }

    void addOne(Sprite s){
        if (sprites.length <= ++spriteCounter){
            Sprite[] tmp = new Sprite[spriteCounter];
            System.arraycopy(sprites, 0, tmp, 0, sprites.length);
            sprites = tmp;
        }
        sprites[spriteCounter - 1] = s;
    }

    void removeOne(){
        spriteCounter -= (spriteCounter != 0) ? 1 : 0;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater( ()-> new MainWindow() );
    }

    void onDrawFrame(MainCanvas canvas, Graphics g, float deltaTime){
        update(canvas, deltaTime);
        render(canvas, g);
    }

    void initApplication(){
        for(int i = 0; i < sprites.length; i++){
            sprites[i] = new Ball();
        }
    }

    void update(MainCanvas canvas, float deltaTime){
        for(int i = 0; i < spriteCounter; i++){
            sprites[i].update(canvas, deltaTime);
        }
    }

    void render(MainCanvas canvas, Graphics g){
        for (int i = 0; i < spriteCounter; i++) {
            sprites[i].render(canvas, g);
        }
    }

}
