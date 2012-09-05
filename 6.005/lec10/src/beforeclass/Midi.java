package beforeclass;
import java.util.ArrayList;
import java.util.List;

// This class has a race condition in it.
public class Midi {
    
    private static Midi midi = null;
    // invariant: there should never be more than one Midi object created
    
    private Midi() {    
        System.out.println("created a Midi object");
    }

    // factory method that returns the sole Midi object, creating it if it doesn't exist
    public static Midi getInstance() {
        if (midi == null) {
            midi = new Midi();
        }
        return midi;
    }
    
    
    
    // simulate a bunch of midi players racing to play some tunes
    private static final int PLAYERS = 3;
    
    // each player gets the midi object
    private static void player() {
        Midi midi = getInstance();
        // ... use the midi object
    }
    
    public static void main(String[] args) {
        // simulate each player with a thread
        List<Thread> threads = new ArrayList<Thread>();
        for (int i = 0; i < PLAYERS; ++i) {
            Thread t = new Thread(new Runnable() {
                public void run() {
                    Thread.yield();  // give the other threads a chance to start too, so it's a fair race
                    player();        // start playing
                }
            });
            t.start(); // don't forget to start the thread!
            threads.add(t);
        }
    }
}
