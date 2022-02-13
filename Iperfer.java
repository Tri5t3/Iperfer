import java.net.*;
import java.io.*;

public class Iperfer {

    public void client(String host, int port, int time) {
        final byte[] KB = new byte[1000];

        String hostName = host;
        int server_port = port;
        int t_ms = time * 1000;
        int sent = 0;

        try (
                Socket socket = new Socket(hostName, server_port);
                OutputStream out = socket.getOutputStream();) {
            long start = System.currentTimeMillis();
            long end = start + t_ms;
            while (System.currentTimeMillis() < end) {
                out.write(KB);
                sent++;
                out.flush();
            }
            out.close();
            socket.close();
            double rate = sent * 0.008 / (double) time;
            System.out.println("sent=" + sent + " KB rate=" + String.format("%.3f", rate) + " Mbps");
        } catch (Exception e) {
            System.out.println("Error detected in client");
            System.exit(1);
        }
    }

    public void server(Integer portNumber) {

        byte[] KB = new byte[1000];
        double received = 0;
        try (
                ServerSocket serverSocket = new ServerSocket(portNumber);
                Socket clientSocket = serverSocket.accept();
                InputStream in = clientSocket.getInputStream();) {
            long start = System.currentTimeMillis();
            while (in.read() != -1) {
                received += in.read(KB);
            }
            received /= 1000;
            long end = System.currentTimeMillis();
            long elapse = (end - start) / 1000;
            in.close();
            clientSocket.close();
            serverSocket.close();
            double rate = received * 0.008 / elapse;
            System.out.println("received=" + received + " KB rate=" + String.format("%.3f", rate) + " Mbps");
        } catch (Exception e) {
            System.out.println("Error detected in server");
            System.exit(1);
        }
    }

    public static void main(String[] args) {
        Iperfer iperfer = new Iperfer();
        if (args.length == 0) {
            System.out.println(
                    "Usage (client mode): java Iperfer -c -h <server hostname> -p <server port> -t <time>");
            System.out.println("Usage (server mode): java Iperfer -s -p <listen port>");
        }
        switch (args[0]) {
            case "-c":
                if (args.length != 7) {
                    System.out.println("Error: missing or additional arguments");
                    break;
                }
                if (Integer.parseInt(args[4]) < 1024 || Integer.parseInt(args[4]) > 65535) {
                    System.out.println("Error: port number must be in the range 1024 to 65535");
                    break;
                }
                iperfer.client(args[2], Integer.parseInt(args[4]), Integer.parseInt(args[6]));
                break;

            case "-s":
                if (args.length != 3) {
                    System.out.println("Error: missing or additional arguments");
                    break;
                }
                if (Integer.parseInt(args[2]) < 1024 || Integer.parseInt(args[2]) > 65535) {
                    System.out.println("Error: port number must be in the range 1024 to 65535");
                    break;
                }
                iperfer.server(Integer.parseInt(args[2]));
                break;

            default:
                System.out.println(
                        "Usage (client mode): java Iperfer -c -h <server hostname> -p <server port> -t <time>");
                System.out.println("Usage (server mode): java Iperfer -s -p <listen port>");
        }
    }
}
