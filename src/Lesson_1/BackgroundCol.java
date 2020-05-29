package Lesson_1;

import java.awt.*;

public class BackgroundCol extends Color {

    static int r = 0;
    static int g = 0;
    static int b = 0;

    static boolean red = true;
    static boolean green = true;
    static boolean blue = true;

    public BackgroundCol(){
        super(r, g, b);
        changer();
    }

    static void changer(){

        red = counter(red, r);
        green = counter(green, g);
        blue = counter(blue, b);

        r += (red) ? chooseColor(r, 7) : -chooseColor(r, 7);
        g += (green) ? chooseColor(g, 5) : -chooseColor(g, 5);
        b += (blue) ? chooseColor(b, 3) : -chooseColor(b, 3);
    }

    static boolean counter(boolean curentBool, int i){

        boolean bool = curentBool;

        if (i >= 249)
            bool = false;
        else if (i <= 7)
            bool = true;

        return bool;
    }

    static int chooseColor(int i, int color){

        int tmp;
        tmp = (int)(Math.random() * color);

        if (tmp + i > 255)
            tmp = 1;
        if (i - tmp < 0)
            tmp = 1;

        return tmp;
    }

}
