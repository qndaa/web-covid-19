package server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class WebServer {

    public  static int port = 9000;

    public static void main(String [] args) {

        AllPatients patients = new AllPatients();

        try {
            ServerSocket ss = new ServerSocket(port);
            System.out.println("Server running...");
            while(true) {
                System.out.println("Server observes.");
                Socket socket = ss.accept();
                new ServerThread(socket, patients);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }




    }

}
