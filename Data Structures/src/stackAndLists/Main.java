package stackAndLists;

import java.util.Random;
public class Main {
  public static void main(String[] args) {

    System.out.println("Testing MaxMinStack called MMS");
    System.out.println("------------------------------");
    MaxMinStack<Integer> mms = new MaxMinStack<Integer>();

    System.out.println("MMS size is " + mms.size());
    System.out.println("MMS is empty: " + mms.isEmpty() + "\n");

    Random rand = new Random();
    rand.setSeed(123);

    System.out.println("Pushing 10 random numbers to MMS...");
    try {
      for(int i=0; i<10; i++) {
        int r = rand.nextInt(1000);
        System.out.println("Pushing " + r);
        mms.push(r);
      }
    }
    catch(IllegalStateException e) {
      System.out.println(e.toString());
    }

    System.out.println("\nMMS size is " + mms.size());
    System.out.println("MMS is empty: " + mms.isEmpty());
    System.out.println("The top element on the MMS is:" + mms.top());
    System.out.println("The current maximum stored in MMS is: " + mms.maximum());
    System.out.println("The current minimum stored in MMS is: " + mms.minimum());

    System.out.println("\nPopping 5 numbers from MMS...");
    for(int i=0; i<5; i++) {
        System.out.println("Popped " + mms.pop());
    }
    System.out.println("\nMMS size is " + mms.size());
    System.out.println("MMS is empty: " + mms.isEmpty());
    System.out.println("The top element on the MMS is:" + mms.top());
    System.out.println("The current maximum stored in MMS is: " + mms.maximum());
    System.out.println("The current minimum stored in MMS is: " + mms.minimum());
    System.out.println("\n\n\n");

    System.out.println("Testing LinkedArrayQueue called LAQ");
    System.out.println("------------------------------");
    LinkedArrayQueue<Integer> laq = new LinkedArrayQueue<Integer>();
    System.out.println("LAQ size is: " + laq.size());
    System.out.println("LAQ is empty: " + laq.isEmpty());
    System.out.println("LAQ has " + laq.numArrays() + " arrays");
    System.out.println("\nAdding 10 random numbers to LAQ...");

    for(int i=0; i<10; i++) {
        int r = rand.nextInt(1000);
        System.out.println("Adding " + r);
        laq.enqueue(r);
    }

    System.out.println("\nFirst element in LAQ: " + laq.first());
    System.out.println("Last element in LAQ: " + laq.last());
    System.out.println("LAQ size is: " + laq.size());
    System.out.println("LAQ is empty: " + laq.isEmpty());
    System.out.println("LAQ has " + laq.numArrays() + " arrays");

    System.out.println("\nRemoving 8 numbers from LAQ...");
    for(int i=0; i<8; i++) {
        System.out.println("Removed " + laq.dequeue());
    }

    System.out.println("\nFirst element in LAQ: " + laq.first());
    System.out.println("Last element in LAQ: " + laq.last());
    System.out.println("LAQ size is: " + laq.size());
    System.out.println("LAQ is empty: " + laq.isEmpty());
    System.out.println("LAQ has " + laq.numArrays() + " arrays");
    
    System.out.printf("%20.20s %9d%n", "Hello There", 100000);
    System.out.printf("%20.20s %9d", "Hi", 100000);
  }
}