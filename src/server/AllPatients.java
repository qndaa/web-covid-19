package server;

import java.util.ArrayList;
import java.util.List;

public class AllPatients {

    private List<Patient> patients = new ArrayList<Patient>();

    public void addPatient(Patient patient){
        for(Patient p : patients) {
            if(p.getHealthInsuranceNumber().equals(patient.getHealthInsuranceNumber())){
                return;
            }
        }
        patients.add(patient);
    }

    public List<Patient> getPatients() {
        return patients;
    }

    public List<Patient> search(String surname) {
        List<Patient> searchResult = new ArrayList<Patient>();
        for(Patient p : patients) {
            if(p.getSurname().equals(surname)){
                searchResult.add(p);
            }
        }
        System.out.println(searchResult.size());
        return searchResult;
    }

    public void changeHealthStatus(String healthInsuranceNumber){
        for(Patient p : patients) {
            if(p.getHealthInsuranceNumber().equals(healthInsuranceNumber)){
                p.setHealthStatus("Zarazen");
                return;
            }
        }
    }
}
