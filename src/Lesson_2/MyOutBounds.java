package Lesson_2;

public class MyOutBounds extends IndexOutOfBoundsException {

    MyOutBounds(){
        super("Проверьте количество элементов, доступно только 4 Х 4");
    }
}
