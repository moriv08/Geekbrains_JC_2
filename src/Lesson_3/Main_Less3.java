package Lesson_3;

public class Main_Less3 {

    public static void main(String[] args) {

        new UnicWords();   // ex_1 unic words

        PhoneBook phoneBook = new PhoneBook();   // ex_2 Phone Book

        phoneBook.addPhone("Ivanov", "111-11-11");
        phoneBook.addPhone("Ivanov", "222-22-22");
        phoneBook.addPhone("Ivanov", "333-33-33");

        phoneBook.addPhone("Sidorov", "111-11-11");
        phoneBook.addPhone("Sidorov", "222-22-22");
        phoneBook.addPhone("Sidorov", "333-33-33");

        phoneBook.addMail("Petrov", "petrov@mail.ru");
        phoneBook.findeNumbers("Petrov");

        phoneBook.findeNumbers("Sidorov");
        phoneBook.findeNumbers("Ivanov");
        phoneBook.findeNumbers("Iasdf");
        phoneBook.findeNumbers("Sidorov");
        phoneBook.findMails("Sidorov");

        phoneBook.addMail("Ivanov", "mail@mail.ru");
        phoneBook.addMail("Ivanov", "mail2@mail.ru");
        phoneBook.addMail("Ivanov", "mail@mail.ru");
        phoneBook.addMail("Ivanov", "mail2@mail.ru");
        phoneBook.addMail("Ivanov", "mail@mail.ru");
        phoneBook.addMail("Ivanov", "mail2@mail.ru");
        phoneBook.addMail("Ivanov", "mail@mail.ru");
        phoneBook.addMail("Ivanov", "mail2@mail.ru");
        phoneBook.removeOne("Petrov");
        phoneBook.findMails("Ivanov");
        phoneBook.removeOne("Iv");
    }
}
