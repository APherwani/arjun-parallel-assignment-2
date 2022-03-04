package Problem_2;

public class CrystalRoom {
    final static int N = 5;
    static CLHLock lock = new CLHLock();
    public static void main(String[] args) throws InterruptedException {
        Thread[] threads = new Thread[N];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                System.out.println("Thread "+Thread.currentThread().getId()+" is attempting to acquire lock.");
                lock.lock();
                System.out.println("Thread "+Thread.currentThread().getId()+" is running.");
                lock.unlock();
                System.out.println("Thread "+Thread.currentThread().getId()+" has released lock.");
            });
            threads[i].start();
        }
        for (int i = 0; i < threads.length; i++) {
            threads[i].join();
        }
    }
}