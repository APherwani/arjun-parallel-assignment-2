import java.util.Arrays;
import java.util.concurrent.Semaphore;
import java.util.Random;

public class MinotaursParty {
    final static int N = 8;
    static int count = 0;
    static boolean cupcake = true; // initial state
    static boolean[] used;
    static int activeThread = -1;

    static Semaphore mutex = new Semaphore(1);

    public static void main(String[] args) throws InterruptedException {
        used = new boolean[N];
        Arrays.fill(used, false); // not strictly necessary because false is the default value.

        Random rand = new Random();

        Thread[] threads = new Thread[N];
        for (int i = 0; i < N; i++) {
            final int x = i;
            if (i == 0) {
                threads[i] = new Thread(() -> {
                    try {
                        headThread(x);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
                threads[i].start();
            } else {
                threads[i] = new Thread(() -> {
                    try {
                        tailThread(x);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
                threads[i].start();
            }
        }

        while (count < N) {
            activeThread = rand.nextInt(0, N);
        }

        for (int i = 0; i < N; i++) {
            threads[i].join();
        }

        System.out.println(count);
    }

    // The algorithm for the head thread.
    // Keeps tally of the rest of the threads.
    public static void headThread (int x) throws InterruptedException {
        while (count < N) {
            mutex.acquire();
            if (x == activeThread) {
                // if this is the first time this thread entered the room
                // eat the cupcake (here it's largely symbolic), request a new one
                // and most importantly update the
                if (!cupcake) {
                    // no need for lock because it is guaranteed this is the only thread accessing the var when it does.
                    // mutex.acquire();
                    count++;
                    cupcake = true; // reset the condition
                    // mutex.release();
                }

                // if this is the first time the thread has been to the room
                // include itself in the count.
                if (!used[x] && cupcake) {
                    count++;
                    // mutex.acquire();
                    used[x] = true;
                    // mutex.release();
                }
            }
            mutex.release();
        }
    }

    // algorithm for all other threads.
    public static void tailThread (int x) throws InterruptedException {
        while (count < N) {
            mutex.acquire();
            if (activeThread == x) {
                if (cupcake && !used[x]) {
                    // mutex.acquire();
                    used[x] = true;
                    cupcake = false;
                    // mutex.release();
                }
            }
            mutex.release();
        }
    }
}