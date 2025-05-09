
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private final ExecutorService threadPool;
    public Server(int poolSize){
        this.threadPool = Executors.newFixedThreadPool(poolSize);
    }
    public void handleClient(Socket clienSocket){
        try(PrintWriter toSocket = new PrintWriter(clienSocket.getOutputStream(),true)){
            toSocket.println("Hello from Server" + clienSocket.getInetAddress());
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
    }
    public static void main(String[] args) {
        int port = 8010;
        int poolSize = 10;
        Server server = new Server(poolSize);
        try{
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(60000);
            System.out.println("server listining on port "+ port);
            while (true) {
                Socket clienSocket = serverSocket.accept();
                server.threadPool.execute(()->server.handleClient(clienSocket));
            }
        }catch(IOException ex){
            ex.printStackTrace();
        }finally{
            server.threadPool.shutdown();
        }
    }
}
