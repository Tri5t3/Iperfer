package Iperfer;
import java.net.*;
import java.io.*;
import java.util.Timer;
import java.util.TimerTask;
public class Iperfer{

    // String server_host;
    // int server_port;
    // int time;
    // int listen_port;

    // public Iperfer(String host, int port, int time){
    //     this.server_host = host;
    //     this.server_port = port;
    //     this.time = time;
    // }

    // public Iperfer(int listen_port){
    //     this.listen_port = listen_port;
    // }

    public void client(String host, int port, int time){
        final byte[] KB = new byte[1000];

        String hostName = host;
        int server_port = port;
        int t_ms = time * 1000;
        int sent = 0;

        try(
            Socket socket = new Socket(hostName, server_port);
            
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        ){
            new java.util.Timer().schedule( 
                new java.util.TimerTask() {
                @Override
                    public void run() {
                        while(true){
                            out.write(KB); 
                            sent++;
                        }
                    }
        }, 
        t_ms
        );

    //  public void server(String host, )

    public static void main(String[] args) {
        switch(args[0]){
            case "-c":
            // System.out.println("-s");
            if(args.length != 7){
                System.out.println("Error: missing or additional arguments");
                break;
            }
            if(Integer.parseInt(args[4]) < 1024 || Integer.parseInt(args[4]) > 65535){
                System.out.println("Error: port number must be in the range 1024 to 65535");
                break;
            }
            
            break;

            case "-f":
            // System.out.println("-f");
            if(args.length != 3){
                System.out.println("Error: missing or additional arguments");
                break;
            }
            if(Integer.parseInt(args[2]) < 1024 || Integer.parseInt(args[2]) > 65535){
                System.out.println("Error: port number must be in the range 1024 to 65535");
                break;
            }
            break;

            default:
            System.out.println("Usage (client mode): java Iperfer -c -h <server hostname> -p <server port> -t <time>");
            System.out.println("Usage (server mode): java Iperfer -s -p <listen port>");
        }
    }
}