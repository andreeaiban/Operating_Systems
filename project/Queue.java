import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Queue {

    private String[] Queue;
    private final int cap=10; //max elements in a queue is 10 and will never change
    private int head; //first index of the queue
    private int tail; //last item in the queue
    public int size; //keep track of the size
    private Lock queueLock;
    private Condition notFullCondition;
    private Condition notEmptyCondition;
    
    //Creating the object Queue which will be an array structure
    public Queue(){
        Queue = new String[cap];
        head=0;
        tail=0; //it doesn't exist yet there's only 1 element
        size=0;

        queueLock = new ReentrantLock(); // allows the same thread to acquire the lock multiple times without deadlocking
        //There will always be these conditions like in the bankAccount there always had to be sufficient funding
        notFullCondition = queueLock.newCondition();
        notEmptyCondition = queueLock.newCondition();

        //There are only two cases that we must always check if its full or not empty, so these r used by java interface concurrent which
        //it is a mechanism for threads to suspend their execution until a certain condition

    }

    //Method of the functionality behind enqueue
    public void enqueue(String x) throws InterruptedException {
        queueLock.lock(); //Lock
        try {
            //For there to enter the queue an element we must check that there are 10 elements which is the capacitity
            while (isFull()) { //size acts like the index, it made it easier to do the code than using head+tail calculations
                notFullCondition.await(); //of its full have the thread wait until not full meaning the other thread will run
            }
            Queue[tail] = x; //adding to the end of the queue
            tail = (tail + 1) % cap; //update the tail pointer after adding an element to the queue
            //size++;
            notEmptyCondition.signalAll(); //letting all the other treats the condition is met
        }
        finally { //Unlock
            queueLock.unlock();
        }
    }

    //Method of the functionality behind dequeue
    public String dequeue() throws InterruptedException {
        queueLock.lock(); //lock
        try {
            while (isEmpty()) { //if queue if empty
                notEmptyCondition.await(); //wait until there are elements in the queue then procede
            }
            String element = Queue[head]; // head is a pointer points to the first element in the queue

            Queue[head] = null; //deleting the item that the pointer is pointed to so the last item in the queue
            head = (head + 1) % cap; //makes the pointer point to the next element in the queue while making sure it doesn't point outside of the 10 elements
            //pointers are used for index
            //size--;

            notFullCondition.signalAll(); //letting the other thread know the condition is met

            return element; //returning the element is optional but i choose to so i know what item was deleted from the queue
        }
        finally { //unlock
            queueLock.unlock();
        }
    }


    //Method to check if array is empty
    public boolean isEmpty() {
        return head > tail;
    }
    //Method to check if its array is full
    public boolean isFull() {
        return size == cap;
    }
    //Get method to get the size of the array
    public int size() {
        return size;
    }

}
   