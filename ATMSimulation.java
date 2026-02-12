import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class BankAccount {

    private double balance;

    public BankAccount(double balance) {
        this.balance = balance;
    }

    public synchronized void withdraw(String user, double amount) {

        System.out.println(user + " trying to withdraw ₹" + amount);

        if (balance >= amount) {

            System.out.println(user + " processing withdrawal...");

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            balance -= amount;

            System.out.println(user + " withdrawal successful. Remaining Balance: ₹" + balance);
        } else {
            System.out.println(user + " insufficient balance!");
        }
    }

    public synchronized void deposit(String user, double amount) {

        System.out.println(user + " depositing ₹" + amount);

        balance += amount;

        System.out.println(user + " deposit successful. New Balance: ₹" + balance);
    }
}

class ATMUser implements Runnable {

    private BankAccount account;
    private String userName;

    public ATMUser(BankAccount account, String userName) {
        this.account = account;
        this.userName = userName;
    }

    @Override
    public void run() {

        account.withdraw(userName, 1000);
        account.deposit(userName, 500);
    }
}

public class ATMSimulation {

    public static void main(String[] args) {

        BankAccount account = new BankAccount(5000);

        ExecutorService executor = Executors.newFixedThreadPool(5);

        for (int i = 1; i <= 5; i++) {
            executor.submit(new ATMUser(account, "User-" + i));
        }

        executor.shutdown();
    }
}