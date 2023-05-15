/*
Programmer: Andreea Ibanescu
Class: CSCI340 Operating Systems Project
Project: Implement a Queue class whose enQ and deQ methods are synchronized. UAse java.util.concurrent for the project. The project uses additional classes such as deQRunnable enQrunnable and Queue.
This is the main class that will excecute the whole project, in addition the user must type in the varaible number for producer and consumer threads. In this project the consumer will be deQ and the producer will be enQ
 */

import java.util.Scanner;
public class QueueThreadRunner {
    public static void main(String[] args) {

        //Create the Queue object that will have STORED elements inside acting as a sort of shared memory
        Queue queue = new Queue();

        //Given set value
        final int iterations = 100;

        //Part b of the project user input and storing it:
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of dequeue threads: ");
        int numofDeq = scanner.nextInt();

        System.out.print("Enter the number of enqueue threads: ");
        int numofEnq = scanner.nextInt();


        //part a combined 100 iterations

        for (int i = 1; i <= iterations; i++) {

            //creating the thread objects

            enQRunnable e = new enQRunnable(queue, numofEnq);
            deQRunnable d = new deQRunnable(queue, numofDeq);

            //creating the treads to act together synchronized
            Thread producer = new Thread(e);
            Thread consumer = new Thread(d);

            //start the execution of the producer and consumer threads respectively

            producer.start();
            consumer.start();
        }
        //Code has finished executing
    }
}
