package server;

import model.Configuration;
import model.Patient;
import model.ReportedPatients;
import model.ValidationPatient;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class ServerThread extends Thread{

    private Socket socket;
    private ReportedPatients patients;

    PrintWriter out;
    BufferedReader in;

    public ServerThread(Socket socket, ReportedPatients patients) {
        this.socket = socket;
        this.patients = patients;

        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);

            start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        String response = "";
        try {
            String resource = getResource(in);

            if(resource == null) {
                resource = "";
            }
            System.out.println("Resource: " + resource);

            if (resource.equals("")){
                resource = "index.html";
            }
            sendResponse(resource, out);

            in.close();
            out.close();
            socket.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void sendResponse(String resource, PrintWriter out) {
        String[] partsRequest = resource.split("\\?");


        if(partsRequest[0].equals("web-covid-19/http/patient-reporting")) {


            if(partsRequest[1].split("&").length == 6) {
                String[] tokens = partsRequest[1].split("&");
                sendResponsePatients(out, tokens);

            } else if(partsRequest[1].split("&").length == 1){
                String [] params = partsRequest[1].split("=");

                if(params[0].equals("patientId")) {
                    patients.changeHealthStatus(params[1]);
                }
                generateHTMLForPatients(out, patients.getPatients());
            }


        } else if (partsRequest[0].equals("web-covid-19/http/search") && partsRequest[1].split("&").length == 1){
            String [] params = partsRequest[1].split("=");

            try {

                if (params[0].equals("searchField")){
                    String surname = params[1];
                    generateHTMLForPatients(out, patients.search(surname));
                }

            } catch (ArrayIndexOutOfBoundsException e) {
                generateHTMLForPatients(out, patients.getPatients());
            }
        }
    }



    private void sendResponsePatients(PrintWriter out, String[] tokens) {

        ValidationPatient validationPatient = new ValidationPatient();
        try {
            String healthInsuranceNumber = tokens[0].split("=")[1];
            String name = tokens[1].split("=")[1];
            String surname = tokens[2].split("=")[1];
            String dateOfBirthday = tokens[3].split("=")[1];
            String pol = tokens[4].split("=")[1];
            String healthStatus = tokens[5].split("=")[1];

            Patient patient = new Patient(healthInsuranceNumber, name, surname, dateOfBirthday, pol, healthStatus);
            patient.changeHealthInsuranceNumber();
            boolean valid = validationPatient.isValid(patient);

            if (valid && patients.addPatient(patient)) {
                generateHTMLForPatients(out, patients.getPatients());
            } else {
                generateHTMLForError(out);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            generateHTMLForError(out);
        }
    }


    private void generateHTMLForPatients(PrintWriter out, List<Patient> listPatients) {
        out.println("HTTP/1.0 200 OK\r\n\r\n");
        out.println("<html>");
        out.println("<body>");
        out.println("<head>");

        out.println("<style> ." +
                 Configuration.CONTAGIOUS + "{background : red;} " +
                "</style>");

        out.println("</head>");
        out.println("<table width=700px border=\"1px\">");
        out.println("<tr>");
            out.println("<th>Broj zdravstvenog osiguranja</th>");
            out.println("<th>Ime pacijenta</th>");
            out.println("<th>Prezime pacijenta</th>");
            out.println("<th>Datum rodjenja</th>");
            out.println("<th>Pol</th>");
            out.println("<th>Status</th>");
            out.println("<th>Nalaz</th>");
        out.println("</tr>");

        for(Patient patient : listPatients){
            String prop = "";
            if(patient.getHealthStatus().equals(Configuration.CONTAGIOUS)){
                prop = "class =\"" + Configuration.CONTAGIOUS + "\"";
            }

            out.println("<tr " + (patient.getHealthStatus().equals(Configuration.CONTAGIOUS) ? ("class=\"" + Configuration.CONTAGIOUS + "\"") : "") + ">");
            out.println("<td>" + patient.getHealthInsuranceNumber() + "</td>");
            out.println("<td>" + patient.getName() + "</td>");
            out.println("<td>" + patient.getSurname() + "</td>");
            out.println("<td>" + patient.getDateOfBirthday() + "</td>");
            out.println("<td>" + patient.getPol() + "</td>");
            out.println("<td>" + patient.getHealthStatus() + "</td>");

            String aHref = "<a href=\"patient-reporting?patientId=" + patient.getHealthInsuranceNumber() + "\">Test je pozitivan!</a>";

            if(patient.getHealthStatus().equals(Configuration.CONTAGIOUS)) {
                out.println("<td>" + "" + "</td>");
            } else {
                out.println("<td>" + aHref + "</td>");
            }
            out.println("</tr>");
        }

        out.println("</table>");
        out.println("<br/>");
        out.println("<br/>");
        out.println("<br/>");

        out.println("<form method=\"get\" action=\"search\">\n" +
                "        <input type=\"text\" name=\"searchField\"/>\n" +
                "        <input type=\"submit\" value=\"Pretrazi\"/>\n" +
                "    </form>");


        out.println("<a href=\"http://localhost:8080/web-covid-19/http/application-form.html\">Nazad na prijavu</a>");
        out.println("<a href= \"http://localhost:8080/web-covid-19/index.html\">Nazad na pocetnu stranicu</a>");
        out.println("</body>");
        out.println("</html>");

    }

    private void generateHTMLForError(PrintWriter out) {

        out.println("HTTP/1.0 200 OK\r\n\r\n");
        out.println("<html>");
        out.println("<body>");
        out.println("<h2>Greska prilikom unosa podataka</h2><br/>");
        out.println("<a href=\"http://localhost:8080/web-covid-19/http/application-form.html\">Vrati na prijavljivanje pacijenata</a>");
        out.println("</body>");
        out.println("</html>");
    }

    private String getResource(BufferedReader br) throws IOException {
        String s = br.readLine();
        if(s == null) {
            s = "";
        }

        String [] tokens = s.split(" ");
        String method = tokens[0];

        if(!method.equals("GET")){
            return null;
        }

        String resource = tokens[1];
        resource = resource.substring(1);

        return resource;

    }


}
