/* CISC/CMPE 422/835, Assignment 4, Part II, Version 2
 * Attempt to implement non-reuseable barrier with busy wait and without locks and synchronization.
 * Wastes resources due the repeated checking of the 'inC==N' condition.
 * Likely to deadlock, because update to shared data ('inC') is complex enough to allow for race conditions.
 * DON'T DO THIS! DOES NOT WORK!
 */

public class A4Part2v2 {
    public static final int N = 5;
    public static final int NUMITER = 1000;   // determines likelihood of race condition
//    public static final int NUMITER = 10000;   // determines likelihood of race condition
    public static String indent(int id) {
        String s="";
        for (int i=0; i<id; i++)
            s = s+"                 ";
        return s;
    }
    static class Worker extends Thread {
        private int id;
        private Barrier b;
        public Worker (int id, Barrier b) {
            this.id = id;
            this.b = b;
            start();
        }
        public void run() {
            System.out.println(indent(id)+"worker "+id+" in w1");
            b.barrier(id);  // barrier
            System.out.println(indent(id)+"worker "+id+" in w2");
        }
    }
    static class Barrier {
        private int inC=0;
        private final int N;
        public Barrier(int n) {
            this.N=n;
        }
        public void collect(int id) {
            int tmp;
            tmp=inC;
            tmp=tmp+1;
            for (int i=0; i<NUMITER; i++);  // simulating longer computation; if interrupted here 'inC' might end up taking on the wrong value
            inC=tmp;
            if (inC==N) {
                assert (inC==N);
                System.out.println("=== all arrived =======================");
            }
            else
                while (inC<N) {
                    System.out.println(indent(id)+"worker "+id+" waiting");
                }
        }
        public void barrier(int id) {
            assert (inC<N);
            collect(id);
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
