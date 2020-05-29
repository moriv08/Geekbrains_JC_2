package Lesson_3;

import java.util.HashMap;
import java.util.Iterator;

public class PhoneBook{

    HashMap<String, Person> phoneBook;

    public PhoneBook(){
        this.phoneBook = new HashMap<>();
    }

    public void addPhone(String name, String phone){

        if (this.phoneBook.containsKey(name)){
            phoneBook.get(name).phones.add(phone);
        }else {
            Person person = new Person(name);
            person.phones.add(phone);
            phoneBook.put(name, person);
        }
    }

    public void addMail(String name, String mail){

        if (this.phoneBook.containsKey(name)){
            phoneBook.get(name).mails.add(mail);
        }else {
            Person person = new Person(name);
            person.mails.add(mail);
            phoneBook.put(name, person);
        }
    }

    public void findeNumbers(String name){
        if (phoneBook.get(name) == null)
            System.out.println("There are no " + name + " in the Phone Book");
        else if (phoneBook.get(name).phones.size() == 0)
            System.out.println(name + " has no available numbers yet");
        else{
            System.out.printf(name + " numbers are: ");
            Iterator iter = phoneBook.get(name).phones.iterator();

            while (iter.hasNext()){
                System.out.printf("%s, ", iter.next().toString());
            }
            System.out.println();
        }
    }

    public void findMails(String name){
        if (phoneBook.get(name) == null)
            System.out.printf("There are no name %s in the book \n", name );
        else if (phoneBook.get(name).mails.size() == 0)
            System.out.println(name + " has no available mails yet");
        else
            System.out.println(name + " mails are: " + phoneBook.get(name).mails);
    }

    public void removeOne(String name){
        if (phoneBook.containsKey(name))
            phoneBook.remove(name);
    }

}
