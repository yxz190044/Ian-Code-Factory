import java.io.File;
import java.io.FileWriter;
import java.io.FileOutputStream;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.*;
import java.util.concurrent.*;


public class ConcurrentHashSet implements Iterable<String> {
    
    ConcurrentHashMap<String, Boolean> map = new ConcurrentHashMap<>();
    
    public ConcurrentHashSet() {
        
    }
    
    public ConcurrentHashSet(List<String> list) {
        for (String s : list) {
            map.put(s, true);
        }
    }
    
    public void add(String s) {
        map.put(s, true);
    }
    
    public void remove(String s) {
        map.remove(s);
    }
    
    public boolean contains(String s) {
        return map.containsKey(s);
    }
    
    @Override
    public Iterator<String> iterator() {
        return map.keySet().iterator();
    }
}


