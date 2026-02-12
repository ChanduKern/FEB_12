import java.util.concurrent.*;

class RequestTask implements Runnable {

    private int requestId;

    public RequestTask(int requestId) {
        this.requestId = requestId;
    }

    @Override
    public void run() {

        try {
            System.out.println("Processing Request " + requestId +
                    " by " + Thread.currentThread().getName());

            Thread.sleep(1000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

public class HighLoadSimulation {

    public static void main(String[] args) throws InterruptedException {

        int totalRequests = 100;

        // -----------------------------
        // 1️⃣ Sequential Processing
        // -----------------------------
        long startSequential = System.currentTimeMillis();

        for (int i = 1; i <= totalRequests; i++) {
            new RequestTask(i).run(); // Direct call (no thread)
        }

        long endSequential = System.currentTimeMillis();

        System.out.println("\nSequential Time: "
                + (endSequential - startSequential) + " ms");

        // -----------------------------
        // 2️⃣ Fixed Thread Pool (10)
        // -----------------------------
        ExecutorService executor = Executors.newFixedThreadPool(10);

        long startParallel = System.currentTimeMillis();

        for (int i = 1; i <= totalRequests; i++) {
            executor.submit(new RequestTask(i));
        }

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.HOURS);

        long endParallel = System.currentTimeMillis();

        System.out.println("Parallel Time (10 threads): "
                + (endParallel - startParallel) + " ms");
    }
}