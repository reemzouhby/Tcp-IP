import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Scanner;

public class server {
    public static HashMap<String, Double> tempmap = new HashMap<>();
    public static void main(String[] args) {
        try {
            System.out.println(InetAddress.getLocalHost());
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Wainting ....");
        int port = 2000;
        try {
            ServerSocket server = new ServerSocket(port);

            while (true){
                Socket socket = server.accept();
                PrintStream outSocket;
                outSocket = new PrintStream(socket.getOutputStream());
                Scanner insocket = new Scanner(socket.getInputStream());
                System.out.println(socket.getRemoteSocketAddress());
                String role = insocket.nextLine().toLowerCase();  // Read role: producer or consumer

                if (role.equalsIgnoreCase("producer")) {
                    threadProducer pr = new threadProducer(socket, tempmap);
                    pr.start();
                } else if (role.equalsIgnoreCase("consumer")) {
                    thredConsumer cs = new thredConsumer(socket, tempmap);
                    cs.start();
                } else {
                    socket.close();  // Unknown client type
                }

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
