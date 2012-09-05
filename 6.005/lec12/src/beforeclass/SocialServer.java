package beforeclass;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * SocialServer simulates a social networking web server 
 * in which each user has a list of friends.  
 * Clients make requests of the form:
 *      Request ::= Person\n
 *      Person ::= [^\n]+
 * and for each request, the server sends a reply listing the
 * person's friends, one per line, terminated by a blank line:
 *      Reply ::= (Person\n)*\n
 * An unknown person returns an empty list of friends.
 */

//
// Thread safety argument
// --------------------------
// The threads in the system are:
// - main thread accepting new connections
// - one thread per connected client, handling just that client
// 
// The serverSocket object is confined to the main thread.
// 
// The Socket object for a client is confined to that client's thread;
// the main thread loses its reference to the object right after starting
// the client thread.
//
// The friendsOf map and all the lists inside it are confined to the main thread
// during creation and then immutable after creation.
//
// System.err is used by all threads for displaying error messages. 
// No other shared mutable data.
//

public class SocialServer {
    // default port number where the server listens for connections
    public static final int PORT = 3003;
    
    private final Map<String, List<String>> friendsOf = makeFriendsGraph();
    
    // rep invariant: 
    //   serverSocket, friendsOf, and all references in friendsOf != null
    //   all strings in friendsOf contain no \n character
    
    /**
     * Make a SocialServer.
     */
    public SocialServer() {
    }
    
    /**
     * @return immutable graph of person -> list of friends
     */
    private static Map<String, List<String>> makeFriendsGraph() {
        Map<String, List<String>> graph = new HashMap<String, List<String>>();
        add(graph, "Harry Potter",     "Ron Weasley", "Hermione Granger", "Sirius Black");
        add(graph, "Ron Weasley",      "Harry Potter", "Hermione Granger");
        add(graph, "Hermione Granger", "Ron Weasley", "Harry Potter");
        return Collections.unmodifiableMap(graph);
    }
    
    /**
     * modifies graph by adding a mapping from person to an immutable list of friends
     */
    private static void add(Map<String, List<String>> graph, String person, String... friends) {
        graph.put(person, Collections.unmodifiableList(Arrays.asList(friends)));
    }
    
    /**
     * Run the server, listening for client connections and handling them.  
     * Never returns unless an exception is thrown.
     * @throws IOException if the main server socket is broken
     * (IOExceptions from individual clients do *not* terminate serve()).
     */
    public void serve() throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        
        while (true) {
            // block until a client connects
            final Socket socket = serverSocket.accept();

            // start a new thread to handle the connection
            Thread thread = new Thread(new Runnable() {
                public void run() {
                    // the client socket object is now owned by this thread,
                    // and mustn't be touched again in the main thread
                    handle(socket);
                }
            });
            thread.start(); // IMPORTANT! easy to forget
            // when does thread.start() return?
            // when will the thread stop?
        }
    }
    
    /**
     * Handle a single client connection.  Returns when client disconnects.
     * @param socket  socket where client is connected
     * @throws IOException if connection has an error or terminates unexpectedly
     */
    private void handle(Socket socket) {        
        try {
            System.err.println("client connected");
            
            // get the socket's input stream, and wrap converters around it
            // that convert it from a byte stream to a character stream,
            // and that buffer it so that we can read a line at a time
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            // similarly, wrap character=>bytestream converter around the socket output stream,
            // and wrap a PrintWriter around that so that we have more convenient ways to write 
            // Java primitive types to it.
            PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
    
            // in and out are thread-confined
            
            try {
                // each request from the client is a single line containing a person's name
                for (String person = in.readLine(); person != null; person = in.readLine()) {
                    System.err.println("request: " + person);

                    // look up the person's friends
                    List<String> friends = friendsOf.get(person);
                    if (friends != null) {
                        for (String friend : friends) {
                            System.err.println("reply: " + friend);
                            out.print(friend + "\n");
                        }
                    }
                    
                    // send the terminating blank line
                    out.print("\n");
                    
                    // VERY IMPORTANT! flush our buffer so the client gets the reply
                    out.flush();
                }
            } finally {        
                out.close();
                in.close();
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Start a SquareServer running on the default port.
     */
    public static void main(String[] args) {
        try {
            SocialServer server = new SocialServer();
            server.serve();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
