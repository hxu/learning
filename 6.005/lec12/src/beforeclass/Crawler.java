package beforeclass;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class Crawler {
    private static final String HOSTNAME = "localhost";
    private static final int NUM_THREADS = 5;
    
    /** starts crawling using background threads, then immediately returns. */
    public void start() {
        for (int i = 0; i < NUM_THREADS; ++i) {
            Thread thread = new Thread(new Worm());
            thread.start();
        }        
    }

    private class Worm implements Runnable {
        private Socket socket;
        private BufferedReader in;
        private PrintWriter out;

        public void run() {
            try {
                socket = new Socket(HOSTNAME, SocialServer.PORT);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
    
                while (true) {
                    Set<String> friends = getFriends("Harry Potter");
                }
                
            } catch (IOException e) {
                e.printStackTrace();
                // returning here is a problem -- we lose a worker thread!  Should really
                // try to reconnect to the server, and only return if the IO exception repeats so
                // much that it looks like we can't make progress.
            }
        }

        private Set<String> getFriends(String person) throws IOException {
            // send request
            out.print(person + "\n");
            out.flush(); // very important! make sure request actually gets sent to server
            
            // collect reply
            Set<String> friends = new HashSet<String>();
            for (String friend = in.readLine(); friend != null && !friend.isEmpty(); friend = in.readLine()) {
                friends.add(friend);
            }
            
            // what's risky about the line of code below???
            System.err.println(person + " is friends with " + friends);
            
            return friends;
        }

    }
    
    
    public static void main(String[] args) {
        Crawler crawler = new Crawler();
        crawler.start();
    }
}
