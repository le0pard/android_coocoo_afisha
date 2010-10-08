package ua.in.leopard.androidCoocooAfisha;

import java.util.List;

public class SingletoneStorage {
    private volatile static SingletoneStorage instance;
    private List<CinemaDB> cinemas = null;
 
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
}
