package in.ashwanik.counter;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Ashwani Kumar on 14/06/18.
 */
public class NonBlockingCounterTest {

    private static void sleep(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {

        final NonBlockingCounter nonBlockingCounter = new NonBlockingCounter();

        Thread thread1 = new Thread(() -> {
            int count = 0;
            while (count < 10) {
                sleep(ThreadLocalRandom.current().nextInt(100, 300));
                System.out.println("Counter from thread 1: " + nonBlockingCounter.increment());
                count++;
            }
        });
        Thread thread2 = new Thread(() -> {
            int count = 0;
            while (count < 10) {
                sleep(ThreadLocalRandom.current().nextInt(100, 300));
                System.out.println("Counter from thread 2: " + nonBlockingCounter.getValue());
                count++;
            }
        });

        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();

    }

    static class NonBlockingCounter {
        private AtomicInteger value;

        public synchronized int getValue() {
            return value.intValue();
        }

        public synchronized int increment() {
            int v;
            do {
                v = value.get();
            }
            while (!value.compareAndSet(v, v + 1));
            return v + 1;
        }

    }


}
