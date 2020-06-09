package model;


public class Patient {

    private String healthInsuranceNumber;
    private String name;
    private String surname;
    private String dateOfBirthday;
    private String pol;
    private String healthStatus;


    public Patient(String healthInsuranceNumber, String name, String surname, String dateOfBirthday, String pol, String healthStatus) {
        this.healthInsuranceNumber = healthInsuranceNumber;
        this.name = name;
        this.surname = surname;
        this.dateOfBirthday = dateOfBirthday;
        this.pol = pol;
        this.healthStatus = healthStatus;
    }

    public String getHealthInsuranceNumber() {
        return healthInsuranceNumber;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getDateOfBirthday() {
        return dateOfBirthday;
    }

    public String getPol() {
        return pol;
    }

    public String getHealthStatus() {
        return healthStatus;
    }

    public void setHealthStatus(String healthStatus) {
        this.healthStatus = healthStatus;
    }

    public void changeHealthInsuranceNumber() {
        healthInsuranceNumber = healthInsuranceNumber.replace("%2F", "/");
    }

    @Override
    public String toString() {
        return "Patient{" +
                "healthInsuranceNumber='" + healthInsuranceNumber + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", dateOfBirthday=" + dateOfBirthday +
                ", pol='" + pol + '\'' +
                ", healthStatus='" + healthStatus + '\'' +
                '}';
    }


}
