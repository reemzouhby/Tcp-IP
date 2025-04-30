import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class Producerclient {
    public static void main(String[] args){
         int port = 2000 ;
        System.out.println("Connect to :");
        Scanner key = new Scanner(System.in);
        String host = key.nextLine();
        try {
            Socket socket = new Socket(host , port);
            PrintStream outSocket;
            outSocket = new PrintStream(socket.getOutputStream());
            Scanner insocket = new Scanner(socket.getInputStream());
            outSocket.println("producer");  // Let the server know this is a producer

            while(true) {
                System.out.println("Give the city and temperature  (please in this Format city , temp  or exit ");
                String data;
                data = key.nextLine();
                if(data.equals("exit")) break;
                if (!data.contains(",") || data.split(",").length != 2) {
                    System.out.println("Invalid format! Please use: city, temp (e.g., Paris, 25)");
                    continue;
                }

                outSocket.println(data);
                System.out.println(insocket.nextLine());

            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

}
