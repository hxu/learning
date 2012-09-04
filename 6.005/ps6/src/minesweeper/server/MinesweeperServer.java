package minesweeper.server;

import java.net.*;
import java.io.*;

public class MinesweeperServer {

	private final static int PORT = 4444;
	private ServerSocket serverSocket;
	
    /**
     * Make a MinesweeperServer that listens for connections on port.
     * @param port port number, requires 0 <= port <= 65535.
     */
    public MinesweeperServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }
    
    /**
     * Run the server, listening for client connections and handling them.  
     * Never returns unless an exception is thrown.
     * @throws IOException if the main server socket is broken
     * (IOExceptions from individual clients do *not* terminate serve()).
     */
    public void serve() throws IOException {
        while (true) {
            // block until a client connects
            Socket socket = serverSocket.accept();
            
            // handle the client
            try {
                handleConnection(socket);
            } catch (IOException e) {
                e.printStackTrace(); // but don't terminate serve()
            } finally {
                socket.close();
            }
        }
    }
    
    /**
     * Handle a single client connection.  Returns when client disconnects.
     * @param socket  socket where client is connected
     * @throws IOException if connection has an error or terminates unexpectedly
     */
    private void handleConnection(Socket socket) throws IOException {
        
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        try {
        	for (String line = in.readLine(); line != null; line = in.readLine()) {
        		String output = handleRequest(line);
        		if(output != null) {
        			out.println(output);
        		}
        	}
        } finally {        
        	out.close();
        	in.close();
        }
    }

	/**
	 * handler for client input
	 * 
	 * make requested mutations on game state if applicable, then return appropriate message to the user
	 * 
	 * @param input
	 * @return
	 */
	private static String handleRequest(String input) {

		String regex = "(look)|(dig \\d+ \\d+)|(flag \\d+ \\d+)|(deflag \\d+ \\d+)|(help)|(bye)";
		if(!input.matches(regex)) {
			//invalid input
			return null;
		}
		String[] tokens = input.split(" ");
		if(tokens[0].equals("look")) {
			// 'look' request
			//TODO Question 5
		} else if(tokens[0].equals("help")) {
			// 'help' request
			//TODO Question 5
		} else if(tokens[0].equals("bye")) {
			// 'bye' request
			//TODO Question 5
		} else {
			int x = Integer.parseInt(tokens[1]);
			int y = Integer.parseInt(tokens[2]);
			if(tokens[0].equals("dig")) {
				// 'dig x y' request
				//TODO Question 5
			} else if(tokens[0].equals("flag")) {
				// 'flag x y' request
				//TODO Question 5
			} else if(tokens[0].equals("deflag")) {
				// 'deflag x y' request
				//TODO Question 5
			}
		}
		//should never get here
		return "";
	}
    
    /**
     * Start a MinesweeperServer running on the default port.
     */
    public static void main(String[] args) {
        try {
            MinesweeperServer server = new MinesweeperServer(PORT);
            server.serve();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}