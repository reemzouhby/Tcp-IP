import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Scanner;

public class threadProducer  extends Thread{
    Socket socket ;
    HashMap<String, Double> tempmap;
    public   threadProducer (Socket socket ,  HashMap temp){
        this . socket = socket ;
        this.tempmap = temp;
    }
public void run () {
    try {
        PrintStream outSocket;
        outSocket = new PrintStream(socket.getOutputStream());

        Scanner insocket = new Scanner(socket.getInputStream());
        // get data should conatin ,

        while (true) {
            if (!insocket.hasNextLine()) break;
            String data = insocket.nextLine();
            if (data.equalsIgnoreCase("exit")) break;

            String[] parts = data.split(",");
            if (parts.length != 2) {
                outSocket.println("Invalid format! Use city,temp");
                continue;
            }
            String city = parts[0].trim();
            try {
                double temp = Double.parseDouble(parts[1].trim());
                if (temp < -50 || temp > 60) {
                    outSocket.println("Temperature out of valid range (-50°C to 60°C)");
                    continue;
                }
                synchronized (tempmap) {
                    tempmap.put(city, temp);
                }
                outSocket.println("Updated");
            } finally {

            }
        }


    } catch (IOException e) {
        throw new RuntimeException(e);


    }
}


}
