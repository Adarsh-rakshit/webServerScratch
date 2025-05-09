import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Server{

    public void run() throws IOException, UnknownHostException {
        int port = 8010;
        ServerSocket socket = new ServerSocket(port);
        socket.setSoTimeout(20000);
        while (true) {
            try {
                System.out.println("server listening on port " + port);
                Socket acceptedConnection = socket.accept();
                System.out.println("connected to client at " + acceptedConnection.getRemoteSocketAddress());
                PrintWriter toClient = new PrintWriter(acceptedConnection.getOutputStream());
                BufferedReader fromClient = new BufferedReader(new InputStreamReader(acceptedConnection.getInputStream()));
                toClient.println("Hello from server!");
                toClient.close();
                fromClient.close();
                acceptedConnection.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } 

    }
    public static void main(String[] args) {
        Server server = new Server();
        try{
            server.run();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}