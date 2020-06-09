<%--
  Created by IntelliJ IDEA.
  User: kundacina
  Date: 6/8/2020
  Time: 6:47 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.*" %>
<jsp:useBean id="patients" class="model.ReportedPatients" scope="application"/>
<html>
<head>
    <title>Application form</title>

    <style>
        td {
            font-weight: bold;
        }

        .send {
            background: lightblue;
            text-align: right;
        }

        .send input {
            width: 100%;
        }
    </style>

</head>
<body>

    <h2>Registracija korisnika</h2>
    <form method="get" action="PatientServlet">
        <table style="width:400px">
            <tr style="left: auto">
                <td>Broj zdravstvenog osiguranja:</td>
                <td><input type="text" name="healthInsuranceNumber"/></td>
            </tr>
            <tr>
                <td>Ime pacijenta:</td>
                <td><input type="text" name="name" /></td>
            </tr>
            <tr>
                <td>Prezime pacijenta:</td>
                <td><input type="text" name="surname"/></td>

            </tr>
            <tr>
                <td>Datum rodjenja:</td>
                <td><input type="date" name="dateOfBirthday" id="datum"/></td>

            </tr>

            <tr>
                <td>Pol:</td>
                <td><select name="pol" id="pol">
                    <option value="Muski">Muski</option>
                    <option value="Zenski">Zenski</option>

                </select></td>

            </tr>
            <tr>
                <td>Datum rodjenja:</td>
                <td><select name="healthStatus" id="healthStatus">
                    <option value="Bez_simptoma">Bez simptoma </option>
                    <option value="Sa_simptomima">Sa simptomima</option>
                    <option value="Zarazen">Zarazen</option>
                    <option value="Izlecen">Izlecen</option>
                </select></td>
            </tr>
            <tr class="send">
                <td></td>
                <td class="button"><input type="submit" value="posalji"></td>
            </tr>

        </table>
    </form>


    <br/>
    <br/>




    <a href="../index.html">Nazad</a>


</body>
</html>
