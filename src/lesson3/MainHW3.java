package lesson3;

import java.util.Arrays;
import java.util.HashMap;

public class MainHW3 {
    public static void main(String[] args) {


        String [] words = {"apple", "wood", "home", "apple", "apple", "wood", "cat", "dog", "cat", "pen", "glue"};
        System.out.println(Arrays.toString(words));

        HashMap<String, Integer> hmap = new HashMap<>();
        for (String x: words){
            hmap.put(x, hmap.getOrDefault(x, 0)+1);
        }
        System.out.println(hmap);

        Phone phBook = new Phone();
        phBook.add("Ivanov", "895698556");
        phBook.add("Ivanov", "899854752");
        phBook.add("Petrov", "847523478");
        phBook.add("Sidorov", "892635478");
        phBook.add("Kedrov", "89568754123");


        System.out.println("Number: " + phBook.get("Ivanov"));
    }
}
