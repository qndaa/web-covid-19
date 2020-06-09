package server;

import model.Configuration;
import model.ReportedPatients;

import java.net.ServerSocket;
import java.net.Socket;

public class WebServer {

    public static void main(String [] args) {

        ReportedPatients patients = new ReportedPatients();

        try {
            ServerSocket ss = new ServerSocket(Configuration.WEB_SERVER_PORT);
            System.out.println("Server running...");
            while(true) {
                Socket socket = ss.accept();
                new ServerThread(socket, patients);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
