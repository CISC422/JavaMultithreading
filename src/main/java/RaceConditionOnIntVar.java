/* CISC/CMPE 422/835, multi-threading (i.e., shared variable concurrency)
 * Illustrate race conditions, i.e., adverse effects of thread scheduling on shared variables
 * In Java, assignments to variables of type 'int' is atomic, so the possibility for a thread
 * to be interrupted during the assignment needs to be created by stretching the assignment out
 * over 3 assignments
 */

public class RaceConditionOnIntVar {
//    public static final int NUM_INCREMENTS = 100;   // might have to experiment w/ different values
    public static final int NUM_INCREMENTS = 1000;   // might have to experiment w/ different values
    public static int x = 0;   // shared variable
    static class T extends Thread {
        private int id;
        public T(int id) {
            this.id = id;
            start();
        }
        public void run() {
            for (int i=0; i<NUM_INCREMENTS; i++) {
                int tmp = x;
                tmp++;
                x = tmp;
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Start: x="+x);
        T t1 = new T(1);
        T t2 = new T(2);
        t1.join();
        t2.join();
        System.out.println("End:   x="+x+" (should be "+2*NUM_INCREMENTS+")");
    }
}
