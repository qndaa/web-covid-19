package model;

public class ValidationPatient {

    private String namePattern = "[A-Z][A-Za-z]+";

    public boolean isValid(Patient patient){
        if(isValidName(patient.getName()) && isValidSurname(patient.getSurname()) && isValidHealthInsuranceNumber(patient.getHealthInsuranceNumber())){
            return true;
        }
        return false;
    }

    public boolean isValidHealthInsuranceNumber(String healthInsuranceNumber) {
        return healthInsuranceNumber.matches("[A-Z]{3}/[0-9]{5}");
    }


    public boolean isValidSurname(String surname) {
        return surname.matches(namePattern);
    }

    public boolean isValidName(String name) {
        return name.matches(name);
    }
}
