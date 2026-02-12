import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class ApiRequest implements Runnable {

    private int requestId;

    public ApiRequest(int requestId) {
        this.requestId = requestId;
    }

    @Override
    public void run() {

        System.out.println("Request " + requestId +
                " is handled by " + Thread.currentThread().getName());

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Request " + requestId +
                " completed by " + Thread.currentThread().getName());
    }
}

public class FixedThreadPoolExample {

    public static void main(String[] args) {

        ExecutorService executor = Executors.newFixedThreadPool(5);

        for (int i = 1; i <= 20; i++) {
            executor.submit(new ApiRequest(i));
        }

        executor.shutdown();

        try {
            executor.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("All API requests processed.");
    }
}