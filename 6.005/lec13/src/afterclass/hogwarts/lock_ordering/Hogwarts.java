package afterclass.hogwarts.lock_ordering;

import java.util.HashSet;
import java.util.Set;

public class Hogwarts {
    // people in the books
    private Wizard harry =      new Wizard("Harry Potter");
    private Wizard hermione =   new Wizard("Hermione Granger");
    private Wizard ron =        new Wizard("Ron Weasley");
    private Wizard dumbledore = new Wizard("Albus Dumbledore");
    private Wizard snape =      new Wizard("Severus Snape");

    // book 1: everybody meets!
    public void book1() {
        harry.friend(hermione);
        harry.friend(ron);
        harry.friend(dumbledore);
        snape.friend(dumbledore);
    }
    
    // book 5: this is all that really happens, right?  I didn't like book 5
    public void book5() {
        harry.friend(snape);
        snape.defriend(harry);
    }

    // suppose a couple of children you know are reading book 5 over and over
    private final int NUM_READINGS = 10000;
    private final int NUM_CHILDREN = 2;

    public void readOverAndOver() {
        book1();
        
        Set<Thread> threads = new HashSet<Thread>();
        for (int i = 0; i < NUM_CHILDREN; ++i) {
            final int numChild = i;
            Thread thread = new Thread(new Runnable() {
                public void run() {
                    Thread.yield();
                    for (int i = 0; i < NUM_READINGS; ++i) {
                        System.err.println("reading for the " + i + "th time");
                        book5();
                    }
                    System.err.println("child #" + numChild + " is done reading");
                }
            });
            thread.start();
            threads.add(thread);
        }
        
        waitForAll(threads);
        System.err.println("all children are done reading");
    }

    /* wait for all threads in set to complete. */
    private void waitForAll(Set<Thread> threads) {
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new AssertionError("don't interrupt my testing");
            }
        }                
    }
    
    public static void main(String[] args) {
        // require assertions to be turned on
        try {
            assert false;
            throw new RuntimeException("turn on assertions for this exercise");
        } catch (AssertionError e) {            
        }

        Hogwarts hogwarts = new Hogwarts();
        hogwarts.readOverAndOver();
    }
}
