<%@ page import="model.Patient" %><%--
  Created by IntelliJ IDEA.
  User: kundacina
  Date: 6/8/2020
  Time: 6:59 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.*" %>
<jsp:useBean id="patients" class="model.ReportedPatients" scope="application"/>
<html>
<head>
    <title>Reported patients</title>
</head>
<body>

    <%
        String num = request.getParameter("healthInsuranceNumber");
        if(num != null) {
            patients.changeHealthStatus(num);
        }
    %>


    <table border="1" width="800px">
        <tr>
            <th>Broj zdravstvenog osiguranja</th>
            <th>Ime pacijenta</th>
            <th>Prezime pacijenta</th>
            <th>Datum rodjenja</th>
            <th>Pol</th>
            <th>Zdravstveni status</th>
            <th></th>
        </tr>

    <%
        for(Patient patient : patients.getPatients()){
    %>
        <tr <%if(patient.getHealthStatus().equals("Bez_simptoma")){%>
                    style="background: lightblue"
                <%}%>
        >
            <td ><%=patient.getHealthInsuranceNumber()%></td>
            <td><%=patient.getName()%></td>
            <td><%=patient.getSurname()%></td>
            <td><%=patient.getDateOfBirthday()%></td>
            <td><%=patient.getPol()%></td>
            <td><%=patient.getHealthStatus()%></td>

            <% if(!patient.getHealthStatus().equals("Zarazen")){%>
                <td><a href="reported-patients.jsp?healthInsuranceNumber=<%=patient.getHealthInsuranceNumber()%>"> Test je pozitivan!</a></td>
            <% } %>


        </tr>

        <%
            }
        %>




    </table>

    <br/>

    <a href="application-form.jsp">Dodaj novog pacijenta</a> <br/>
    <a href="../index.html">Pocetna strana</a>
</body>
</html>
