import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Server {
    public static void main(String[] args) {
        int port = 2003;
        try {
            System.out.println(InetAddress.getLocalHost());
            ServerSocket serveur = new ServerSocket(port);



            while (true) {

                Socket socket = serveur.accept();
                System.out.println("connection from:" + socket.getRemoteSocketAddress());

                    threadwriterReader thread = new threadwriterReader(socket);
                    thread.start();

            }
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}