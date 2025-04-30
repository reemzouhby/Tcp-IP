import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class thredConsumer extends Thread {
    Socket socket;
    HashMap<String, Double> tempmap;

    public thredConsumer(Socket socket, HashMap tempmap) {
        this.socket = socket;
        this.tempmap = tempmap;
    }

    public void run() {
        try {
            PrintStream outsocket = new PrintStream(socket.getOutputStream());
            Scanner insocket = new Scanner(socket.getInputStream());

            while (true) {
                if (!insocket.hasNextLine()) break;
                String data = insocket.nextLine();
                if (data.equalsIgnoreCase("exit")) break;

                if (data.equalsIgnoreCase("list")) {
                    synchronized (tempmap) {
                        if (tempmap.isEmpty()) {
                            outsocket.println("No temperature data available.");
                        } else {
                            for (Map.Entry<String, Double> entry : tempmap.entrySet()) {
                                outsocket.println(entry.getKey() + ": " + entry.getValue() + "°C");
                            }
                        }
                    }
                    outsocket.println(); // End of response
                    continue;
                }

                if (data.equalsIgnoreCase("avg")) {
                    double sum = 0;
                    int count = 0;
                    synchronized (tempmap) {
                        for (double temp : tempmap.values()) {
                            sum += temp;
                            count++;
                        }
                    }
                    if (count > 0) {
                        outsocket.println("Average temperature: " + String.format("%.2f", sum / count) + "°C");
                    } else {
                        outsocket.println("No data available to calculate average.");
                    }
                    outsocket.println(); // End of response
                    continue;
                }

                // City lookup
                Double temp;
                synchronized (tempmap) {
                    temp = tempmap.get(data);
                }
                if (temp != null) {
                    outsocket.println(data + ": " + temp + "°C");
                } else {
                    outsocket.println("City not found.");
                }
                outsocket.println(); // End of response
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}