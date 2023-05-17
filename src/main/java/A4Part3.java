/* CISC/CMPE 422/835, Assignment 4, Part III
 * Implementation of non-reusable barrier using the barrier's intrinsic lock and wait/notify.
 */

public class A4Part3 {
    public static final int N = 5;
    static class Worker extends Thread {
        private int id;
        private Barrier b;
        public Worker (int id, Barrier b) {
            this.id = id;
            this.b = b;
            start();
        }
        public String indent() {
            String s="";
            for (int i=0; i<id; i++)
                s = s+"                 ";
            return s;
        }
        public void run() {
            System.out.println(indent()+"worker "+id+" in w1");   // [NuSMV: w1]
            b.barrier();                                          // [NuSMV: bc1, bc2, bc3, bc4, bc5]
            System.out.println(indent()+"worker "+id+" in w2");   // [NuSMV: w2]
        }
    }

    static class Barrier {
        private int inC=0;
        private final int N;
        public Barrier(int n) {
            this.N=n;
        }
        public synchronized void collect() {
            try {
                inC++;                          // [NuSMV: bc2]
                if (inC==N) {                   // [NuSMV: bc3]
                    assert (inC==N);
                    System.out.println("=== all arrived =======================");
                    notifyAll();            // wake up 'waiting' threads
                }
                else
                    while (inC<N) wait();   // put thread into 'waiting' state [NuSMV: bc5]
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public void barrier() {
            assert (inC<N);
            collect();
            assert (inC==N);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Worker[] Workers = new Worker[N];
        Barrier b = new Barrier(N);
        System.out.println("Start of execution: "+N+" workers");
        for (int i=0; i < N; ++i) {
            Workers[i] = new Worker(i,b);
        }
        for (int i=0; i < N; ++i) {
            Workers[i].join();
        }
        System.out.println("End of execution");
    }
}
