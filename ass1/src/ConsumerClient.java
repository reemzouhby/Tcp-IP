import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class ConsumerClient {
    public static void main(String[] args){
        System.out.println("Connect to :");
        int port = 2003;
        Scanner key = new Scanner(System.in);
        String host = key.nextLine();

        try {
            Socket socket = new Socket(host, port);
            PrintStream outSocket;
            outSocket = new PrintStream(socket.getOutputStream());
            Scanner insocket = new Scanner(socket.getInputStream());
            while (true){
                System.out.println("Enter city name ");
                // send the city to server and read the result
                String city;
                Double temp;

                city = key.nextLine();
                if(city.equals("exit")) break;
                if (city.contains(",") || city.split(",").length >1) {
                    System.out.println("Invalid format! Please only city ");
                    continue;
                }

                outSocket.println(city);

                System.out.println(insocket.nextLine());



            } insocket.close();
            outSocket.close();
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
