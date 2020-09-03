package lesson3;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Phone {

    private HashMap<String, Set<String>> phoneBook = new HashMap<>();

    public void add (String name, String phone){
        Set<String> tell = phoneBook.getOrDefault(name, new HashSet<>());
        tell.add(phone);
        phoneBook.put(name, tell);
    }

    public void add (String name, String... phones){
        Set<String> tell = phoneBook.getOrDefault(name, new HashSet<>());
        tell.addAll(Arrays.asList(phones));
        phoneBook.put(name, tell);
    }

    public Set<String> get(String name){
        return phoneBook.get(name);
    }
}
