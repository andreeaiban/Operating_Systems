
public class deQRunnable implements Runnable {

    private Queue queue;
    private int count;

    /**
     * Constructs a deQRunnable
     * @param aQueue the queue from which to dequeue
     * @param aCount the number of times to dequeue
     */
    public deQRunnable(Queue aQueue, int aCount) {
        queue = aQueue;
        count = aCount;
    }

    public void run() {
        try {
            for (int i = 0; i < count; i++) {
                String element = queue.dequeue(); //calling and executing the dequeue method
                System.out.println("Dequeued element: " + element);
                Thread.sleep(1000); //delay in the thread prevent the producer thread from inserting messages
                // into the queue when if full b/c it will  prevent the consumer thread from  removing element from an empty queue b/c it it accesses the same shared memory so it might not update as quick
            }
          //Coding in case of an exception here, good practice to prevent bugs and helps with debugging :)
        } catch (InterruptedException exception) {
            System.out.println("DeQRunnable interrrupted");
        }
    }
}