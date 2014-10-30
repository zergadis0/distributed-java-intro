package exercise1;

import common.Counter;

public class SynchronizedCounter implements Counter {
    long value = 0;
    
    @Override
    public synchronized void increment() {
        value++;
    }

    @Override
    public long getValue() {
        return value;
    }
}
