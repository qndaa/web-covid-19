package model;

import java.util.ArrayList;
import java.util.List;

public class ReportedPatients {

    private List<Patient> patients = new ArrayList<>();

    public List<Patient> getPatients() {
        return patients;
    }

    public boolean addPatient(Patient patient){

        for(Patient p : patients) {
            if(p.getHealthInsuranceNumber().equals(patient.getHealthInsuranceNumber())){
                return false;
            }
        }
        patients.add(patient);
        return true;
    }


    public List<Patient> search(String surname) {
        List<Patient> searchResult = new ArrayList<>();
        for(Patient p : patients) {
            if(p.getSurname().equals(surname)){
                searchResult.add(p);
            }
        }
        return searchResult;
    }

    public void changeHealthStatus(String healthInsuranceNumber){
        for(Patient p : patients) {
            if(p.getHealthInsuranceNumber().equals(healthInsuranceNumber)){
                p.setHealthStatus(Configuration.CONTAGIOUS);
                return;
            }
        }
    }
}
