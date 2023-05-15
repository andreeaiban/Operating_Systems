import java.util.Date;

public class enQRunnable implements Runnable {
    private Queue queue;
    private int repetitions;

    public enQRunnable(Queue q, int reps) {
        queue = q;
        repetitions = reps;
    }

    public void run() {
        for (int i = 0; i < repetitions; i++) {
            String item = new Date().toString(); //This gives us the string of the exact time 00:00:00 that was run
            try {
                queue.enqueue(item);
                System.out.println("Produced: " + item);
                Thread.sleep(10000); // the delay will cause varation in the element added
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
