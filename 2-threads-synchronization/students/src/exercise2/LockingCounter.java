package exercise2;

import common.Counter;
import java.util.concurrent.locks.ReentrantLock;

public class LockingCounter implements Counter {
    private long value = 0;
    private final ReentrantLock reentrantLock = new ReentrantLock();
    
    @Override
    public void increment() {
        reentrantLock.lock();
        try {
            value++;
        } finally {
            reentrantLock.unlock();
        }
    }

    @Override
    public long getValue() {
        return value;
    }
}
