package servlets;

import model.Patient;
import model.ReportedPatients;
import model.ValidationPatient;

import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Console;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "PatientServlet")
public class PatientServlet extends HttpServlet {


    public PatientServlet() {
        super();

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ServletContext context = getServletContext();
        ReportedPatients patients = (ReportedPatients) context.getAttribute("patients");
        if(patients == null) {
            patients = new ReportedPatients();
            context.setAttribute("patients", patients);
        }


        String num = request.getParameter("healthInsuranceNumber");
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String data = request.getParameter("dateOfBirthday");
        String pol = request.getParameter("pol");
        String healthStatus = request.getParameter("healthStatus");



        ValidationPatient vp = new ValidationPatient();
        Patient patient = new Patient(num, name, surname, data, pol, healthStatus);
        boolean valid = vp.isValid(patient);
        patient.changeHealthInsuranceNumber();
        PrintWriter out = response.getWriter();

        if(valid == true) {
            if(patients.addPatient(patient))
                response.sendRedirect("reported-patients.jsp");
            else  {

                out.println("<html>");
                out.println("<head>");

                out.println("<body>");
                out.println("<h3>Pacijenta je vec pregledan.<br />");
                out.println("<a href=\"application-form.jsp\">Nazad na prijavu pacijenata</a>");

                out.println("</body>");
                out.println("</html>");
            }
        } else {

            out.println("<html>");
            out.println("<head>");

            out.println("<body>");
            out.println("<h3>Greska pri unosu podataka<br />");
            out.println("<a href=\"application-form.jsp\">Nazad na prijavu pacijenata</a>");

            out.println("</body>");
            out.println("</html>");
        }
    }
}
