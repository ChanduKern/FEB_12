import java.util.concurrent.*;
import java.util.*;

class ReportTask implements Callable<String> {

    private String reportName;

    public ReportTask(String reportName) {
        this.reportName = reportName;
    }

    @Override
    public String call() throws Exception {

        System.out.println("Generating " + reportName +
                " by " + Thread.currentThread().getName());

        Thread.sleep(2000); // simulate processing time

        return reportName + " completed successfully.";
    }
}

public class ParallelReportGenerator {

    public static void main(String[] args) throws Exception {

        ExecutorService executor = Executors.newFixedThreadPool(5);

        List<Callable<String>> tasks = new ArrayList<>();

        tasks.add(new ReportTask("Sales Report"));
        tasks.add(new ReportTask("Inventory Report"));
        tasks.add(new ReportTask("User Activity Report"));
        tasks.add(new ReportTask("Revenue Report"));
        tasks.add(new ReportTask("Performance Report"));

        List<Future<String>> results = executor.invokeAll(tasks);

        System.out.println("\n===== REPORT SUMMARY =====");

        for (Future<String> future : results) {
            System.out.println(future.get());
        }

        executor.shutdown();
    }
}