package exercise2;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MyThread extends Thread {
    public MyThread(String name) {
        super.setName(name);
    }
    
    @Override
    public void run() {
        for (int i = 1; i < 11; i++) {
            System.out.println(this.getName() + i);
            
        }
    }
}
