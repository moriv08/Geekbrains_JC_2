package Lesson_1;

import java.awt.*;

public abstract class Sprite{

    protected float x;
    protected float y;
    float halfHeight;
    float halfWidth;

    float getLeft(){ return x - halfWidth; }
    void setLeft(float left){ x = left + halfWidth; }

    float getRight(){ return x + halfWidth; }
    void setRight(float right){ x = right - halfWidth; }

    float getBottom(){
        return y + halfHeight;
    }
    void setBottom(float bottom) { y = bottom - halfHeight; }

    float getTop(){ return y - halfHeight; }
    void setTop(float top){ y = top + halfHeight; }

    float getHeight(){
        return 2f * halfHeight;
    }
    float getWidth(){
        return 2f * halfWidth;
    }

    public abstract void update(MainCanvas canvas, float deltaTime);
    public abstract void render(MainCanvas canvas, Graphics g);
}
