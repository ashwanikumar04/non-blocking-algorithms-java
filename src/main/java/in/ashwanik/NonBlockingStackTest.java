package in.ashwanik;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicReference;

import static in.ashwanik.Helpers.sleep;

/**
 * Created by Ashwani Kumar on 15/06/18.
 */
public class NonBlockingStackTest {
    public static void main(String[] args) throws InterruptedException {

        final NonBlockingStack<Integer> nonBlockingStack = new NonBlockingStack<>();

        Thread thread1 = new Thread(() -> {
            int count = 0;
            while (count < 10) {
                sleep(ThreadLocalRandom.current().nextInt(100, 300));
                nonBlockingStack.push(ThreadLocalRandom.current().nextInt(100, 300));
                count++;
            }
        });
        Thread thread2 = new Thread(() -> {
            int count = 0;
            while (count < 10) {
                sleep(ThreadLocalRandom.current().nextInt(100, 300));
                System.out.println("Value from thread 2: " + nonBlockingStack.pop());
                count++;
            }
        });

        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
    }


    public static class NonBlockingStack<E> {
        AtomicReference<Node<E>> head = new AtomicReference<>();

        public void push(E item) {
            Node<E> newHead = new Node<>(item);
            Node<E> oldHead;
            do {
                oldHead = head.get();
                newHead.next = oldHead;
            } while (!head.compareAndSet(oldHead, newHead));

        }

        public E pop() {
            Node<E> newHead;
            Node<E> oldHead;
            do {
                oldHead = head.get();
                if (oldHead == null) {
                    return null;
                }
                newHead = oldHead.next;
            } while (!head.compareAndSet(oldHead, newHead));

            return oldHead.value;
        }
    }

    public static class Node<E> {
        final E value;
        Node<E> next;

        public Node(E value) {
            this.value = value;
        }

    }
}
