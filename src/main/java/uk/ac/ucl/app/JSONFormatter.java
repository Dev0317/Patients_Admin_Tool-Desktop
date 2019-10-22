package uk.ac.ucl.app;

import java.util.List;

public class JSONFormatter {

    public static String format(Patient patient) {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("\"id\":").append("\"").append(patient.getId()).append("\"").append(",");
        sb.append("\"birthdate\":").append("\"").append(patient.getBirthDate()).append("\"").append(",");
        sb.append("\"deathdate\":").append("\"").append(patient.getDeathDate()).append("\"").append(",");
        sb.append("\"ssn\":").append("\"").append(patient.getSsn()).append("\"").append(",");
        sb.append("\"drivers\":").append("\"").append(patient.getDrivers()).append("\"").append(",");
        sb.append("\"passport\":").append("\"").append(patient.getPassport()).append("\"").append(",");
        sb.append("\"prefix\":").append("\"").append(patient.getPrefix()).append("\"").append(",");
        sb.append("\"first\":").append("\"").append(patient.getFirst()).append("\"").append(",");
        sb.append("\"last\":").append("\"").append(patient.getLast()).append("\"").append(",");
        sb.append("\"suffix\":").append("\"").append(patient.getSuffix()).append("\"").append(",");
        sb.append("\"maiden\":").append("\"").append(patient.getMaiden()).append("\"").append(",");
        sb.append("\"marital\":").append("\"").append(patient.getMarital()).append("\"").append(",");
        sb.append("\"race\":").append("\"").append(patient.getRace()).append("\"").append(",");
        sb.append("\"ethnicity\":").append("\"").append(patient.getEthnicity()).append("\"").append(",");
        sb.append("\"gender\":").append("\"").append(patient.getGender()).append("\"").append(",");
        sb.append("\"birthplace\":").append("\"").append(patient.getBirthPlace()).append("\"").append(",");
        sb.append("\"address\":").append("\"").append(patient.getAddress()).append("\"").append(",");
        sb.append("\"city\":").append("\"").append(patient.getCity()).append("\"").append(",");
        sb.append("\"state\":").append("\"").append(patient.getState()).append("\"").append(",");
        sb.append("\"zip\":").append("\"").append(patient.getZip()).append("\"");
        sb.append("}");
        return sb.toString();
    }

    public static String format(List<Patient> patients) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\"patients\":[");
        if (!patients.isEmpty()) {
            sb.append(format(patients.get(0)));
            for (int i = 1; i < patients.size(); i++) {
                sb.append(",").append(format(patients.get(i)));
            }
        }
        sb.append("]}");
        return sb.toString();
    }
}
