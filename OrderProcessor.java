class Payment {
}

class Inventory {
}

public class OrderProcessor {

    private final Object paymentLock = new Payment();
    private final Object inventoryLock = new Inventory();

    public void processOrder() {

        synchronized (inventoryLock) {

            System.out.println(Thread.currentThread().getName()
                    + " locked Inventory");

            synchronized (paymentLock) {

                System.out.println(Thread.currentThread().getName()
                        + " locked Payment");

                System.out.println("Processing payment and reducing stock...");
            }
        }
    }

    public static void main(String[] args) {

        OrderProcessor processor = new OrderProcessor();

        Runnable task = processor::processOrder;

        Thread t1 = new Thread(task, "Thread-1");
        Thread t2 = new Thread(task, "Thread-2");

        t1.start();
        t2.start();
    }
}