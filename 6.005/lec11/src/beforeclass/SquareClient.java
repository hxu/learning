package beforeclass;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * SquareClient is a client that sends requests to the SquareServer
 * and interprets its replies.
 */
public class SquareClient {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    // rep invariant: 
    //    socket, in, out != null
    //
    // states:
    //   open: socket.isConnected() and in and out are open
    //   closed: socket.isClosed() and in and out are closed
    
    /**
     * Make a SquareClient and connect it to a server running on
     * hostname at the specified port.
     * @throws IOException if can't connect
     */
    public SquareClient(String hostname, int port) throws IOException {
        socket = new Socket(hostname, port);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));        
    }
    
    /**
     * Send a request to the server. Requires this is not closed. 
     * @param x number to square
     * @throws IOException if network or server failure
     */
    public void sendRequest(int x) throws IOException {
        out.print(x + "\n");
        out.flush(); // very important! make sure x actually gets sent to server
    }
    
    /**
     * Get a reply from the next request that was submitted. Requires this is not closed. 
     * @return square of requested number
     * @throws IOException if network or server failure
     */
    public int getReply() throws IOException {
        String reply = in.readLine();
        if (reply == null) {
            throw new IOException("connection terminated unexpectedly");
        }
        
        try {
            return Integer.valueOf(reply);
        } catch (NumberFormatException e) {
            throw new IOException("server returned misformatted reply: " + reply);
        }
    }

    /**
     * Closes the client's connection to the server.
     * @throws IOException if close fails
     */
    public void close() throws IOException {
        in.close();
        out.close();
        socket.close();
    }
    
    
    
    
    private static final int N = 1000000;
    
    /**
     * Use a SquareServer to square all the integers from 1 to N.
     */
    public static void main(String[] args) {
        try {
            SquareClient client = new SquareClient("localhost", SquareServer.SQUARE_PORT);

            // send the requests to square 1...N
            for (int x = 1; x <= N; ++x) {
                client.sendRequest(x);
                System.out.println(x + "^2 = ?");
            }
            
            // collect the replies
            for (int x = 1; x <= N; ++x) {
                int y = client.getReply();
                System.out.println(x + "^2 = " + y);
            }
            
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
