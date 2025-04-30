import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class consumerclient {
    public static void main(String[] args) {
        System.out.println("Connect to:");
        int port = 2000;
        Scanner key = new Scanner(System.in);
        String host = key.nextLine();

        try {
            Socket socket = new Socket(host, port);
            PrintStream outSocket = new PrintStream(socket.getOutputStream());
            Scanner insocket = new Scanner(socket.getInputStream());
            outSocket.println("consumer"); // Identify as consumer

            while (true) {
                System.out.println("Enter city name or list or avg or exit");
                String city = key.nextLine();
                if (city.equalsIgnoreCase("exit")) break;

                outSocket.println(city); // Send command

                // Read until empty line (server's delimiter)
                String response;
                while (!(response = insocket.nextLine()).isEmpty()) {
                    System.out.println(response);
                }
            }

            // Close resources only when exiting
            insocket.close();
            outSocket.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}