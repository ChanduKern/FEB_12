import java.util.concurrent.*;

class ApiTask implements Runnable {

    private int taskId;

    public ApiTask(int taskId) {
        this.taskId = taskId;
    }

    @Override
    public void run() {

        System.out.println("Task " + taskId +
                " handled by " + Thread.currentThread().getName());

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            System.out.println("Task " + taskId + " interrupted");
        }

        System.out.println("Task " + taskId + " completed");
    }
}

public class CustomThreadPoolExample {

    public static void main(String[] args) {

        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                3,
                6,
                10,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(10),
                new ThreadPoolExecutor.AbortPolicy());

        for (int i = 1; i <= 30; i++) {

            try {
                executor.execute(new ApiTask(i));
                System.out.println("Submitted Task " + i);
            } catch (RejectedExecutionException e) {
                System.out.println("Task " + i + " rejected!");
            }
        }

        executor.shutdown();
    }
}