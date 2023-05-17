/* CISC/CMPE 422/835, Assignment 4, Part V
 * Reusable (cyclic) barrier with double turnstile using the barrier's intrinsic lock.
 * Works.
 */


public class A4Part5 {
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
            while (true) {
                System.out.println(indent()+"worker "+id+" in w1");
                b.barrier();  // barrier 1
                System.out.println(indent()+"worker "+id+" in w2");
                b.barrier();  // barrier 2
                System.out.println(indent()+"worker "+id+" in w3");
                b.barrier();  // barrier 3
            }
        }
    }

    static class Barrier {
        private int inC=0;
        private int outC=0;
        private final int N;

        public Barrier(int n) {
            this.N=n;
        }

        public synchronized void collect() {
            try {
                inC++;
                if (inC==N) {
                    assert (inC==N & outC==0);
                    outC=N;
                    assert (inC==N & outC==N);
                    System.out.println("=== all arrived =======================");
                    notifyAll();
                }
                else
                    while (inC<N)
                        wait();
                assert (inC==N);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public synchronized void release() {
            try {
                outC--;
                if (outC==0) {
                    assert (inC==N & outC==0);
                    inC=0;
                    assert (inC==0 & outC==0);
                    System.out.println("=== all departed ======================");
                    notifyAll();
                }
                else
                    while (outC>0) wait();
                assert (outC==0);
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
        }

        public void barrier() {
            assert (inC<N & outC==0);
            collect();	// first turnstile
            assert (inC==N & outC>0);
            release();	// second turnstile
            assert (inC<N & outC==0);
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
