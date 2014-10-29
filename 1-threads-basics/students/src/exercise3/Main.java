package exercise3;

import exercise2.MyThread;

public class Main {

    public static void main(String[] args) {
        Thread threads[] = new Thread[4];
        for (int i = 0; i < 4; i++) {
            threads[i] = new Thread(new MyRunnable(), "Thread-" + i);
        }
        
        for (int i = 0; i < 4; i++)
            threads[i].run();
    }
}
