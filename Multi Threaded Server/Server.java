import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;
public class Server {

    public Consumer<Socket> getConsumer(){
        return(clientSocket)->{
            try{
                PrintWriter toClient = new PrintWriter(clientSocket.getOutputStream());
                toClient.println("hello from server boy");
                toClient.close();
                clientSocket.close();
            }
            catch(IOException ex){
                ex.getStackTrace();
            }
        };
    }

    public static void main(String[] args) {
        Server server = new Server();
        int port = 8010;
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(10000);
            System.out.println("server listening on "+ port);
            while (true) {
                Socket acceptedSocket = serverSocket.accept();
                Thread thread = new Thread(()->server.getConsumer().accept(acceptedSocket));
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
