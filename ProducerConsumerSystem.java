import java.util.concurrent.*;

class Order {
    private int orderId;

    public Order(int orderId) {
        this.orderId = orderId;
    }

    public int getOrderId() {
        return orderId;
    }
}

class OrderProducer implements Runnable {

    private BlockingQueue<Order> queue;

    public OrderProducer(BlockingQueue<Order> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {

        try {
            for (int i = 1; i <= 10; i++) {

                Order order = new Order(i);
                queue.put(order);

                System.out.println("Produced Order: " + i);

                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class OrderConsumer implements Runnable {

    private BlockingQueue<Order> queue;

    public OrderConsumer(BlockingQueue<Order> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {

        try {
            while (true) {

                Order order = queue.take();

                System.out.println("Processing Order: "
                        + order.getOrderId()
                        + " by "
                        + Thread.currentThread().getName());

                Thread.sleep(2000);
            }
        } catch (InterruptedException e) {
            System.out.println("Consumer stopped.");
        }
    }
}

public class ProducerConsumerSystem {

    public static void main(String[] args) throws InterruptedException {

        BlockingQueue<Order> queue = new ArrayBlockingQueue<>(5);

        Thread producerThread = new Thread(new OrderProducer(queue));
        producerThread.start();

        ExecutorService executor = Executors.newFixedThreadPool(2);

        executor.submit(new OrderConsumer(queue));
        executor.submit(new OrderConsumer(queue));

        Thread.sleep(15000);

        executor.shutdownNow();
        producerThread.interrupt();

        System.out.println("System shutting down...");
    }
}