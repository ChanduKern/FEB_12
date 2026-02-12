import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class FileProcessor implements Runnable {

    private String fileName;

    public FileProcessor(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void run() {

        System.out.println("Started processing " + fileName +
                " by " + Thread.currentThread().getName());

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Finished processing " + fileName +
                " by " + Thread.currentThread().getName());
    }
}

public class ParallelFileProcessing {

    public static void main(String[] args) {

        ExecutorService executor = Executors.newFixedThreadPool(5);

        executor.submit(new FileProcessor("file1.txt"));
        executor.submit(new FileProcessor("file2.txt"));
        executor.submit(new FileProcessor("file3.txt"));
        executor.submit(new FileProcessor("file4.txt"));
        executor.submit(new FileProcessor("file5.txt"));

        executor.shutdown();

        try {
            if (!executor.awaitTermination(1, TimeUnit.MINUTES)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }

        System.out.println("All files processed successfully.");
    }
}