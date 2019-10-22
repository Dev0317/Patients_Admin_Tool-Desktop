package uk.ac.ucl.app;

import javax.script.ScriptException;
import javax.swing.*;
import java.io.*;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

public class Model {
    private ArrayList<Patient> patients;
    private DefaultListModel<Patient> listModel;

    public Model() {
        listModel = new DefaultListModel<>();
    }

    public DefaultListModel<Patient> getListModel() {
        return listModel;
    }

    private void setListModel(Iterable<Patient> patients) {
        listModel.clear();
        for (Patient patient : patients) {
            listModel.addElement(patient);
        }
    }


    public void readCSVFile(File file) throws IOException, ParseException, IllegalArgumentException {
        patients = (ArrayList<Patient>) ReadCSV.parse(file);
        setListModel(patients);
    }

    public void readJSONFile(File file) throws ScriptException, ParseException, FileNotFoundException, IllegalArgumentException {
        patients = (ArrayList<Patient>) ReadJSON.parse(file);
        setListModel(patients);
    }

    public void saveAs(File file) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
        ArrayList<Patient> arrayList = new ArrayList<>();
        for (int i = 0; i < listModel.size(); i++) {
            arrayList.add(listModel.get(i));
        }
        bufferedWriter.write(JSONFormatter.format(arrayList));
        bufferedWriter.close();
    }

    public ArrayList getPopulationDistributionByRace() {
        HashMap<String, HashMap<String, Integer>> map = new HashMap<>();
        HashMap<String, Integer> population = new HashMap<>();
        HashSet<String> races = new HashSet<>();

        for (int i = 0; i < listModel.size(); i++) {
            Patient patient = listModel.get(i);
            String state = patient.getState();
            population.merge(state, 1, Integer::sum);
            String race = patient.getRace();
            races.add(race);
            HashMap<String, Integer> map2 = map.get(state);
            if (map2 == null) {
                map2 = new HashMap<>();
                map2.put(race, 1);
                map.put(state, map2);
            } else {
                map2.merge(race, 1, Integer::sum);
            }
        }

        int columns = races.size() + 1;
        String[] columnNames = new String[columns];
        String[][] records = new String[population.size()][columns];
        columnNames[0] = "Location";
        int i = 1;
        for (String race : races) {
            columnNames[i++] = race;
        }

        DecimalFormat df = new DecimalFormat(".##");
        int j = 0;
        for (Map.Entry<String, HashMap<String, Integer>> entry : map.entrySet()) {
            String[] record = new String[columns];
            HashMap<String, Integer> stateData = entry.getValue();
            String state = entry.getKey();
            record[0] = state;
            for (int k = 1; k < columns; k++) {
                Integer value = stateData.get(columnNames[k]);
                if (value == null) {
                    record[k] = "0%";
                } else {
                    record[k] = df.format((double) value / population.get(state) * 100) + "%";
                }
            }
            records[j++] = record;
        }

        ArrayList tuple = new ArrayList();
        tuple.add(columnNames);
        tuple.add(records);
        return tuple;
    }

    public void filterbyId(String pattern) {
        if (!listModel.isEmpty()) {
            setListModel(patients.stream().filter(patient -> patient.getId().contains(pattern)).collect(Collectors.toList()));
        }
    }

    public void filterbyBirthDate(String pattern) {
        if (!listModel.isEmpty()) {
            setListModel(patients.stream().filter(patient -> patient.getBirthDate().contains(pattern)).collect(Collectors.toList()));
        }
    }

    public void filterbyDeathDate(String pattern) {
        if (!listModel.isEmpty()) {
            setListModel(patients.stream().filter(patient -> patient.getDeathDate().contains(pattern)).collect(Collectors.toList()));
        }
    }

    public void filterbySsn(String pattern) {
        if (!listModel.isEmpty()) {
            setListModel(patients.stream().filter(patient -> patient.getSsn().contains(pattern)).collect(Collectors.toList()));
        }
    }

    public void filterbyDrivers(String pattern) {
        if (!listModel.isEmpty()) {
            setListModel(patients.stream().filter(patient -> patient.getDrivers().contains(pattern)).collect(Collectors.toList()));
        }
    }

    public void filterbyPassport(String pattern) {
        if (!listModel.isEmpty()) {
            setListModel(patients.stream().filter(patient -> patient.getPassport().contains(pattern)).collect(Collectors.toList()));
        }
    }

    public void filterbyPrefix(String pattern) {
        if (!listModel.isEmpty()) {
            setListModel(patients.stream().filter(patient -> patient.getPrefix().contains(pattern)).collect(Collectors.toList()));
        }
    }

    public void filterbyFirst(String pattern) {
        if (!listModel.isEmpty()) {
            setListModel(patients.stream().filter(patient -> patient.getFirst().contains(pattern)).collect(Collectors.toList()));
        }
    }

    public void filterbyLast(String pattern) {
        if (!listModel.isEmpty()) {
            setListModel(patients.stream().filter(patient -> patient.getLast().contains(pattern)).collect(Collectors.toList()));
        }
    }

    public void filterbySuffix(String pattern) {
        if (!listModel.isEmpty()) {
            setListModel(patients.stream().filter(patient -> patient.getSuffix().contains(pattern)).collect(Collectors.toList()));
        }
    }

    public void filterbyMaiden(String pattern) {
        if (!listModel.isEmpty()) {
            setListModel(patients.stream().filter(patient -> patient.getMaiden().contains(pattern)).collect(Collectors.toList()));
        }
    }

    public void filterbyMarital(String pattern) {
        if (!listModel.isEmpty()) {
            setListModel(patients.stream().filter(patient -> patient.getMarital().contains(pattern)).collect(Collectors.toList()));
        }
    }

    public void filterbyRace(String pattern) {
        if (!listModel.isEmpty()) {
            setListModel(patients.stream().filter(patient -> patient.getRace().contains(pattern)).collect(Collectors.toList()));
        }
    }

    public void filterbyEthnicity(String pattern) {
        if (!listModel.isEmpty()) {
            setListModel(patients.stream().filter(patient -> patient.getEthnicity().contains(pattern)).collect(Collectors.toList()));
        }
    }

    public void filterbyGender(String pattern) {
        if (!listModel.isEmpty()) {
            setListModel(patients.stream().filter(patient -> patient.getGender().contains(pattern)).collect(Collectors.toList()));
        }
    }

    public void filterbyBirthPlace(String pattern) {
        if (!listModel.isEmpty()) {
            setListModel(patients.stream().filter(patient -> patient.getBirthPlace().contains(pattern)).collect(Collectors.toList()));
        }
    }

    public void filterbyAddress(String pattern) {
        if (!listModel.isEmpty()) {
            setListModel(patients.stream().filter(patient -> patient.getAddress().contains(pattern)).collect(Collectors.toList()));
        }
    }

    public void filterbyCity(String pattern) {
        if (!listModel.isEmpty()) {
            setListModel(patients.stream().filter(patient -> patient.getCity().contains(pattern)).collect(Collectors.toList()));
        }
    }

    public void filterbyState(String pattern) {
        if (!listModel.isEmpty()) {
            setListModel(patients.stream().filter(patient -> patient.getState().contains(pattern)).collect(Collectors.toList()));
        }
    }

    public void filterbyZip(String pattern) {
        if (!listModel.isEmpty()) {
            setListModel(patients.stream().filter(patient -> patient.getZip().contains(pattern)).collect(Collectors.toList()));
        }
    }
}
