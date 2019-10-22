package uk.ac.ucl.app;

import javax.script.ScriptException;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

public class View implements ActionListener, ListSelectionListener {

    private Model model;
    private JFrame frame;
    private JMenuBar menuBar;
    private JMenu menu1, menu2;
    private JMenuItem[] items;
    private JList<Patient> list;
    private JSplitPane splitPane;
    private JFileChooser fileChooser;
    private JScrollPane scrollPane1, scrollPane2;
    private JTextField textField;
    private JComboBox<String> comboBox;

    public View(Model model) {
        this.model = model;
        fileChooser = new JFileChooser();
        FileFilter filter = new FileNameExtensionFilter("json, csv", "json", "csv");
        fileChooser.setFileFilter(filter);
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == items[0]) {
            if (fileChooser.showOpenDialog(splitPane) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                String extension = getFileExtension(file);
                if (extension.equals("json")) {
                    try {
                        model.readJSONFile(file);
                    } catch (IllegalArgumentException | ScriptException | ParseException | FileNotFoundException e1) {
                        JOptionPane.showMessageDialog(frame, "File couldn't be parsed successfully", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else if (extension.equals("csv")) {
                    try {
                        model.readCSVFile(file);
                    } catch (IllegalArgumentException | IOException | ParseException e1) {
                        JOptionPane.showMessageDialog(frame, "File couldn't be parsed successfully", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "File must end with .csv, .json", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else if (e.getSource() == items[1]) {
            if (fileChooser.showSaveDialog(splitPane) == JFileChooser.APPROVE_OPTION) {
                try {
                    model.saveAs(fileChooser.getSelectedFile());
                } catch (IOException e1) {
                    JOptionPane.showMessageDialog(frame, "Couldn't save file", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else if (e.getSource() == items[2]) {
            ArrayList tuple = model.getPopulationDistributionByRace();
            JTable table = new JTable((String[][]) tuple.get(1), (String[]) tuple.get(0));
            table.setFillsViewportHeight(true);
            table.setEnabled(false);
            scrollPane2.setViewportView(table);
        } else if (e.getSource() == textField) {
            String s = textField.getText();
            switch ((String) comboBox.getSelectedItem()) {
                case "id":  model.filterbyId(s);
                    break;
                case "birthdate":  model.filterbyBirthDate(s);
                    break;
                case "deathdate":  model.filterbyDeathDate(s);
                    break;
                case "ssn":  model.filterbySsn(s);
                    break;
                case "drivers":  model.filterbyDrivers(s);
                    break;
                case "passport":  model.filterbyPassport(s);
                    break;
                case "prefix":  model.filterbyPrefix(s);
                    break;
                case "first":  model.filterbyFirst(s);
                    break;
                case "last":  model.filterbyLast(s);
                    break;
                case "suffix":  model.filterbySuffix(s);
                    break;
                case "maiden":  model.filterbyMaiden(s);
                    break;
                case "marital":  model.filterbyMarital(s);
                    break;
                case "race":  model.filterbyRace(s);
                    break;
                case "ethnicity":  model.filterbyEthnicity(s);
                    break;
                case "gender":  model.filterbyGender(s);
                    break;
                case "birthplace":  model.filterbyBirthPlace(s);
                    break;
                case "address":  model.filterbyAddress(s);
                    break;
                case "city":  model.filterbyCity(s);
                    break;
                case "state":  model.filterbyState(s);
                    break;
                case "zip":  model.filterbyZip(s);
                    break;
            }
        }
    }

    private String getFileExtension(File file) {
        String fileName = file.getName();
        return fileName.substring(fileName.lastIndexOf('.') + 1);
    }

    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            if (list.getSelectedIndex() != -1) {
                int index = list.getSelectedIndex();
                Patient patient = model.getListModel().get(index);
                String[] columnNames = {"Field", "Value"};
                String[][] data = {
                        {"id", patient.getId()}, {"birthdate", patient.getBirthDate()},
                        {"deathdate", patient.getDeathDate()}, {"ssn", patient.getSsn()},
                        {"drivers", patient.getDrivers()}, {"passport", patient.getPassport()},
                        {"prefix", patient.getPrefix()}, {"first", patient.getFirst()},
                        {"last", patient.getLast()}, {"suffix", patient.getSuffix()},
                        {"maiden", patient.getMaiden()}, {"marital", patient.getMarital()},
                        {"race", patient.getRace()}, {"ethnicity", patient.getEthnicity()},
                        {"gender", patient.getGender()}, {"birthplace", patient.getBirthPlace()},
                        {"address", patient.getAddress()}, {"city", patient.getCity()},
                        {"state", patient.getState()}, {"zip", patient.getZip()}};
                JTable table = new JTable(data, columnNames);
                table.setFillsViewportHeight(true);
                table.setEnabled(false);
                scrollPane2.setViewportView(table);
            }
        }
    }

    private JMenuBar createMenuBar() {
        items = new JMenuItem[23];
        menuBar = new JMenuBar();
        menu1 = new JMenu("File");
        menu2 = new JMenu("Analyze");
        menuBar.add(menu1);
        menuBar.add(menu2);

        items[0] = new JMenuItem("Open...");
        items[1] = new JMenuItem("Save as...");
        items[0].addActionListener(this);
        items[1].addActionListener(this);
        menu1.add(items[0]);
        menu1.add(items[1]);

        items[2] = new JMenuItem("Show Statistics");
        items[2].addActionListener(this);

        menu2.add(items[2]);
        return menuBar;
    }

    private JSplitPane createSplitPane() {
        JSplitPane splitPane1;
        JPanel jPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        list = new JList<>(model.getListModel());
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.addListSelectionListener(this);
        textField = new JTextField(20);
        textField.addActionListener(this);
        String[] fields = {"id", "birthdate", "deathdate", "ssn", "drivers", "passport",
                "prefix", "first", "last", "suffix", "maiden", "marital", "race", "ethnicity",
                "gender", "birthplace", "address", "city", "state", "zip"};
        comboBox = new JComboBox<>(fields);
        comboBox.setSelectedIndex(0);

        jPanel.add(new JLabel("Search for: "));
        jPanel.add(textField);
        jPanel.add(new JLabel(" by" ));
        jPanel.add(comboBox);

        scrollPane1 = new JScrollPane(list);

        scrollPane2 = new JScrollPane();

        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                scrollPane1, scrollPane2);

        splitPane.setDividerLocation(150);
        Dimension minimumSize = new Dimension(800, 400);
        scrollPane1.setMinimumSize(minimumSize);
        scrollPane2.setMinimumSize(minimumSize);
        splitPane.setPreferredSize(new Dimension(800, 400));

        splitPane1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                jPanel, splitPane);

        splitPane1.setDividerLocation(30);

        return splitPane1;
    }

    public static void createGUI(View view) {
        JFrame frame = new JFrame("My App");
        view.setFrame(frame);
        frame.setJMenuBar(view.createMenuBar());
        frame.getContentPane().add(view.createSplitPane());
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
