import java.util.concurrent.atomic.AtomicInteger;

public final class ThreadInterference extends Thread {
    private static final int HundredMillion = 100000000;
    private static AtomicInteger counter = new AtomicInteger();
    private static int i = 0;
    private static Object lock = new Object();

    public static void example() throws InterruptedException {
        (new ThreadInterference()).start();
        (new ThreadInterference()).start();
    }

    public void run() {
        while (counter.incrementAndGet() < HundredMillion) {
            synchronized (lock) {
                //i++;
            }
            i++;
        }
        System.out.println("counter: " + counter + " i: " + i);
    }

}
