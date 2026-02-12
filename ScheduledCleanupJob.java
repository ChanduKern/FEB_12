import java.util.concurrent.*;

class CleanupTask implements Runnable {

    @Override
    public void run() {

        System.out.println("Cleaning temporary files by "
                + Thread.currentThread().getName()
                + " at " + System.currentTimeMillis());

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            System.out.println("Cleanup interrupted");
        }
    }
}

public class ScheduledCleanupJob {

    public static void main(String[] args) {

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        ScheduledFuture<?> future = scheduler.scheduleAtFixedRate(
                new CleanupTask(),
                0,
                5,
                TimeUnit.SECONDS);

        scheduler.schedule(() -> {
            System.out.println("Stopping scheduler...");
            future.cancel(true);
            scheduler.shutdown();
        }, 20, TimeUnit.SECONDS);
    }
}