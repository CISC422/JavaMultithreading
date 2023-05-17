/* CISC/CMPE 422/835, Assignment 4, Part IV
 * Failed attempts to make non-reusable barrier reusable.
 * Attempt 1 (Question 5): don't reset 'inC' at all
 * Attempt 2 (Question 6): reset 'inC' when all threads have arrived, i.e., when 'inC==N'
 */
public class A4Part4 {
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
            System.out.println(indent()+"Worker"+id+":w1");
            b.barrier();  // barrier 1
            System.out.println(indent()+"Worker"+id+":w2");
            b.barrier();  // barrier 2
            System.out.println(indent()+"Worker"+id+":w3");
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
                inC++;
                if (inC==N) {
                    assert (inC==N);
//    				inC=0;  // uncomment for Attempt 2 (Question 6)
                    System.out.println("=== all arrived =======================");
                    notifyAll();
                }
                else
                    while (inC<N)
                        wait();
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public void barrier() {
            // assert (inC<N);
            collect();
            // assert(inC==N);
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