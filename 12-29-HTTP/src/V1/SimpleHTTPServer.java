package V1;
import java.io.IOException;
import java.net.*;
import java.util.concurrent.*;

public class SimpleHTTPServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(80);
        ExecutorService pool = Executors.newFixedThreadPool(10);
        while (true) {
            Socket socket = serverSocket.accept();
            pool.execute(new Task(socket));
        }
    }
}
