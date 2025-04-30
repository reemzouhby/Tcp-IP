import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Scanner;

public class threadwriterReader extends Thread {
    private Socket socket;
    private static final HashMap<String, Double> datas = new HashMap<>();

    public threadwriterReader(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try (
                Scanner in = new Scanner(socket.getInputStream());
                PrintStream out = new PrintStream(socket.getOutputStream())
        ) {
            while (true) {
                String data;
                if (!in.hasNextLine()) break;
                data = in.nextLine();

                if (data.equalsIgnoreCase("exit")) break;

                if (data.contains(",")) {
                    String[] parts = data.split(",");
                    if (parts.length == 2) {
                        String city = parts[0].trim();
                        try {
                            double temp = Double.parseDouble(parts[1].trim());
                            if (temp < -50 || temp > 60) {
                                out.println("ERROR: Temperature out of valid range.");
                            } else {
                                synchronized (datas) {
                                    datas.put(city, temp);
                                }
                                out.println("UPDATED: " + city + " = " + temp + "°C");
                            }
                        } catch (NumberFormatException e) {
                            out.println("ERROR: Invalid temperature format.");
                        }
                    } else {
                        out.println("ERROR: Please send in format city,temp");
                    }
                } else {
                    synchronized (datas) {
                        Double temp = datas.get(data.trim());
                        if (temp != null) {
                            out.println(data.trim() + ": " + temp + "°C");
                        } else {
                            out.println("City not found.");
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try { socket.close(); } catch (IOException e) { e.printStackTrace(); }
        }
    }
}

