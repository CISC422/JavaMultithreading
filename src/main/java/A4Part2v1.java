/* CISC/CMPE 422/835, Assignment 4, Part II, Version 1
 * Attempt to implement non-reuseable barrier with busy wait and without locks and synchronization.
 * Wastes resources due the repeated checking of the 'inC==N' condition.
 * Appears to work, because the update to the shared data ('inC') is so simple that it ends up being uninteruptable.
 * DON'T DO THIS! DOES NOT WORK!
 */

public class A4Part2v1 {
    public static final int N = 5;

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
            inC++;
            if (inC==N) {
                assert (inC==N);
                System.out.println("=== all arrived =======================");
            }
            else
                while (inC<N) {
                    System.out.println(indent(id) + "worker "+id+" waiting");
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