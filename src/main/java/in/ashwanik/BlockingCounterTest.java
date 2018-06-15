package in.ashwanik;

import java.util.concurrent.ThreadLocalRandom;

import static in.ashwanik.Helpers.sleep;

/**
 * Created by Ashwani Kumar on 14/06/18.
 */
public class BlockingCounterTest {

    public static void main(String[] args) throws InterruptedException {

        final BlockingCounter blockingCounter = new BlockingCounter();

        Thread thread1 = new Thread(() -> {
            int count = 0;
            while (count < 10) {
                sleep(ThreadLocalRandom.current().nextInt(100, 300));
                System.out.println("Counter from thread 1: " + blockingCounter.increment());
                count++;
            }
        });
        Thread thread2 = new Thread(() -> {
            int count = 0;
            while (count < 10) {
                sleep(ThreadLocalRandom.current().nextInt(100, 300));
                System.out.println("Counter from thread 2: " + blockingCounter.getValue());
                count++;
            }
        });

        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();

    }

    static class BlockingCounter {
        private int value;

        public synchronized int getValue() {
            return value;
        }

        public synchronized int increment() {
            return ++value;
        }
    }


}
