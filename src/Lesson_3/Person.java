package Lesson_3;

import java.util.TreeSet;

public class Person {

    String name;
    TreeSet<String> phones;
    TreeSet<String> mails;

    public Person(String name) {
        this.name = name;
        phones = new TreeSet<>();
        mails = new TreeSet<>();
    }
}