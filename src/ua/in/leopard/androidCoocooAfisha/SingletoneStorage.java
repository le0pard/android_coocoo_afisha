package ua.in.leopard.androidCoocooAfisha;

import java.util.HashMap;
import java.util.List;
import java.util.Timer;

public class SingletoneStorage {
    private volatile static SingletoneStorage instance;
    private List<CinemaDB> cinemas = null;
    private HashMap<Integer, Timer> timers = new HashMap<Integer, Timer>();
	private HashMap<Integer, Integer> cinemas_iterators = new HashMap<Integer, Integer>();
 
    private SingletoneStorage() {}
 
    public static SingletoneStorage getInstance() {
        if (instance == null) {
            synchronized (SingletoneStorage.class) {
                if (instance == null) {
                    instance = new SingletoneStorage();
                }
            }
        }
        return instance;
    }
    
    public static void set_cinemas(List<CinemaDB> cinemas){
    	getInstance().cinemas = cinemas;
    }
    
    public static List<CinemaDB> get_cinemas(){
    	return getInstance().cinemas;
    }
    
    public static void set_timers(HashMap<Integer, Timer> timers){
    	getInstance().timers = timers;
    }
    
    public static HashMap<Integer, Timer> get_timers(){
    	return getInstance().timers;
    }
    
    public static void set_cinemas_iterators(HashMap<Integer, Integer> cinemas_iterators){
    	getInstance().cinemas_iterators = cinemas_iterators;
    }
    
    public static void put_cinemas_iterators(Integer key, Integer value){
    	getInstance().cinemas_iterators.put(key, value);
    }
    
    public static Integer get_value_cinemas_iterators(Integer key){
    	return getInstance().cinemas_iterators.get(key);
    }
    
    public static HashMap<Integer, Integer> get_cinemas_iterators(){
    	return getInstance().cinemas_iterators;
    }
}
