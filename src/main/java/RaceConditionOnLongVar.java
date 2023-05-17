/* CISC/CMPE 422/835, multi-threading (i.e., shared variable concurrency)
 * Illustrate race conditions, i.e., adverse effects of thread scheduling on shared variables
 * In Java, assignments to variables of type 'long' or 'double' are not atomic
 */

public class RaceConditionOnLongVar {
//    public static final int NUM_INCREMENTS = 100;
    public static final int NUM_INCREMENTS = 1000;
    public static long x = 0;   // shared variable
    static class T extends Thread {
        private int id;
        public T(int id) {
            this.id = id;
            start();
        }
        public void run() {
            for (int i=0; i<NUM_INCREMENTS; i++) {
                  x++;
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
