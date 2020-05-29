package Lesson_3;

import java.util.*;

public class UnicWords {

    protected UnicWords(){

        ArrayList<String> list = new ArrayList<>();
        list.add("Ann");
        list.add("Ann");
        list.add("Monica");
        list.add("Rita");
        list.add("Marina");
        list.add("Katya");
        list.add("Monica");
        list.add("Rita");
        list.add("Marina");
        list.add("Katya");
        list.add("Rita");
        list.add("Marina");
        list.add("Katya");
        list.add("Katya");
        list.add("Rita");
        list.add("Marina");
        list.add("Katya");
        list.add("Ann");

        System.out.println(list);

        forUnificator(list);
        hashMapUficator(list);
        justLoops(list);

    }

    private void forUnificator(ArrayList<String> list){

        System.out.println("__________* for and iterator *_____________");

        TreeSet<String> treeSet = new TreeSet<>(list);

        Iterator<String> iter = treeSet.iterator();
        while (iter.hasNext()){
            String tmp = iter.next();
            int count = 0;
            for (int i = 0; i < list.size(); i++) {
                if(tmp.equals(list.get(i))){
                    count++;
                }
            }
            System.out.println("There are - " + count + " names \"" + tmp + "\" in the list");
        }
        System.out.println();
    }

    private void hashMapUficator(ArrayList<String> list){

        System.out.println("__________* hash maps *____________________");
        HashMap<String, Integer> map = new HashMap<>();

        Iterator<String> makeMap = list.iterator();
        while (makeMap.hasNext())
            map.put(makeMap.next(), 0);

        Iterator<String> countNames = list.iterator();
        while (countNames.hasNext()){
            String tmp = countNames.next();
            Integer it = map.get(tmp);
            map.put(tmp, it += 1);
        }

        Iterator iter = map.entrySet().iterator();
        while (iter.hasNext()){
            Map.Entry mapIter = (Map.Entry)iter.next();
            System.out.printf("There are - %d names \"%s\" in the list\n", mapIter.getValue(), mapIter.getKey());
        }
        System.out.println();
    }

    private void justLoops(ArrayList<String> list){

        Collections.sort(list);
        System.out.println("__________* just loops *___________________");

        for (int i = 0; i < list.size(); i++) {
            int count = 1;
            for (int k = i + 1; k < list.size(); k++) {
                if (list.get(i).equals(list.get(k))){
                    list.remove(list.get(k));
                    k = i;
                    count++;
                }
            }
            System.out.println("There are - " + count + " names \"" + list.get(i) + "\" in the list");
        }
        System.out.println();
    }

}
