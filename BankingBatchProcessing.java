import java.util.concurrent.*;
import java.util.*;

class InterestCalculator implements Callable<Double> {

    private double principal;
    private double rate;
    private int time;
    private int accountNumber;

    public InterestCalculator(int accountNumber, double principal, double rate, int time) {
        this.accountNumber = accountNumber;
        this.principal = principal;
        this.rate = rate;
        this.time = time;
    }

    @Override
    public Double call() throws Exception {

        System.out.println("Calculating interest for Account " + accountNumber +
                " by " + Thread.currentThread().getName());

        Thread.sleep(2000);

        double interest = (principal * rate * time) / 100;

        System.out.println("Interest calculated for Account " + accountNumber);

        return interest;
    }
}

public class BankingBatchProcessing {

    public static void main(String[] args) throws Exception {

        ExecutorService executor = Executors.newFixedThreadPool(3);

        List<Future<Double>> results = new ArrayList<>();

        results.add(executor.submit(new InterestCalculator(101, 10000, 5, 2)));
        results.add(executor.submit(new InterestCalculator(102, 20000, 6, 3)));
        results.add(executor.submit(new InterestCalculator(103, 15000, 4, 1)));
        results.add(executor.submit(new InterestCalculator(104, 25000, 7, 2)));

        for (Future<Double> future : results) {

            Double interest = future.get();
            System.out.println("Interest Received: ₹" + interest);
        }

        executor.shutdown();
    }
}