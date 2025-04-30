import com.sun.jdi.Value;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Scanner;

public class ProducerClient {
    public static void main(String[] args) {
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
            System.out.println("Give the city and temperature  (please in this Format city , temp ");
            String data;


                data = key.nextLine();
    if(data.equals("exit")) break;
    if (!data.contains(",") || data.split(",").length != 2) {
        System.out.println("Invalid format! Please use: city, temp (e.g., Paris, 25)");
        continue;
    }


         outSocket.println(data);
             System.out.println(insocket.nextLine());



// only read data send to user


            } insocket.close();
outSocket.close();
socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
