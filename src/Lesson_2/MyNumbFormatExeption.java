package Lesson_2;

public class MyNumbFormatExeption extends NumberFormatException{
    MyNumbFormatExeption(){
        super("Для получения адекватного результата необходимо использовать только цифры");
    }
}
