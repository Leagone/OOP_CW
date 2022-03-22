package com.company;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;


public class F1Frame {


    // All necessary JComponents and variables to store data

    JFrame f;
    JTable table, raceOutcome;
    JPanel leftPanel, rightPanel, bottomLeftPanel, bottomRightPanel;
    JButton buttonD1, buttonD2, buttonD3, buttonD4, buttonD5, buttonD6, buttonD7;
    JTextField textField, textFieldRaceName;
    JMenuBar newMenuBar;
    JMenu file;
    JMenuItem save, load, exit;
    String[][] data = {};
    int columnsAmount;


    F1Frame(Formula1ChampionshipManager manager) {


        f = new JFrame();
        f.setTitle("Formula 1 Championship Manager ");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(1300, 680);
        f.setVisible(true);
        f.setResizable(false);
        f.setLayout(null);

        // Column names of table that will display statistics and table that will display race information
        String[] columnNames = {"no.", "Name", "Team", "Location", "Points", "First Positions", "Second positions ", "Third positions", " No. Races"};
        String[] columnNamesRaces = new String[manager.biggestRace + 2];
        columnsAmount = columnNamesRaces.length;
        columnNamesRaces[0] = "Name";
        columnNamesRaces[1] = "Date";
        columnNamesRaces[2] = "1st";
        columnNamesRaces[3] = "2nd";
        columnNamesRaces[4] = "3rd";

        for (int i = 5; i < columnNamesRaces.length; i++) {


            columnNamesRaces[i] = (i - 1) + "th";


        }


        leftPanel = new JPanel(new GridLayout(7, 1, 0, 20));
        leftPanel.setBounds(10, 40, 200, 400);


        bottomLeftPanel = new JPanel(new GridLayout(2, 1, 0, 20));
        bottomLeftPanel.setBounds(10, 460, 200, 100);


        rightPanel = new JPanel();
        rightPanel.setLayout(null);
        rightPanel.setBounds(220, 30, 1060, 600);

        bottomRightPanel = new JPanel();
        bottomRightPanel.setLayout(null);
        bottomRightPanel.setBounds(220, 340, 1060, 280);

        f.getContentPane().add(leftPanel);
        f.getContentPane().add(rightPanel);
        f.getContentPane().add(bottomLeftPanel);
        f.getContentPane().add(bottomRightPanel);

        table = new JTable(data, columnNames);
        raceOutcome = new JTable(data, columnNamesRaces);


        // resizing columns to desire width
        resizeColumns(raceOutcome);


        JScrollPane sp = new JScrollPane(table);
        JScrollPane spp = new JScrollPane(raceOutcome);
        sp.setBounds(10, 10, 1040, 280);
        spp.setBounds(10, 10, 1040, 260);
        rightPanel.add(sp);
        bottomRightPanel.add(spp);

        newMenuBar = new JMenuBar();

        file = new JMenu("File");

        load = new JMenuItem("Load race from file");
        load.addActionListener(
                (e) -> {
                    manager.readFromFile();
                });
        save = new JMenuItem("Save race to file");
        save.addActionListener(
                (e) -> {
                    manager.saveToFile();
                });
        exit = new JMenuItem("Exit");
        exit.addActionListener(
                (e) -> {
                    System.exit(0);

                });

        file.add(load);
        file.add(save);
        file.add(exit);


        newMenuBar.add(file);


        f.setJMenuBar(newMenuBar);


        buttonD1 = new JButton("Display Descending");
        buttonD1.addActionListener(
                (e) -> {

                    // On click the statistics table will be populated with data about championship
                    // Sorting the drivers based on their points in descending fashion

                    DefaultTableModel model = new DefaultTableModel(manager.returnSortedTable("pointsDSC"), columnNames);
                    table.setModel(model);


                }
        );

        buttonD2 = new JButton("Display Ascending");
        buttonD2.addActionListener(
                (e) -> {

                    // On click the statistics table will be populated with data about championship
                    // Sorting the drivers based on their points in ascending fashion

                    DefaultTableModel model = new DefaultTableModel(manager.returnSortedTable("pointsASC"), columnNames);
                    table.setModel(model);


                }
        );


        buttonD3 = new JButton("Display Descending by fPos");
        buttonD3.addActionListener(
                (e) -> {

                    // On click the statistics table will be populated with data about championship
                    // Sorting the drivers based on their first position in descending fashion

                    DefaultTableModel model = new DefaultTableModel(manager.returnSortedTable("fPos"), columnNames);
                    table.setModel(model);


                }
        );


        buttonD4 = new JButton("Display Races Sorted");
        buttonD4.addActionListener(
                (e) -> {


                    // On click the race table will be populated with data about all past races
                    // Sorting the races based on date they were created

                    DefaultTableModel model = new DefaultTableModel(manager.getRaceData(manager.returnSortedPastRaces()), columnNamesRaces);
                    raceOutcome.setModel(model);
                    resizeColumns(raceOutcome);

                }
        );


        buttonD5 = new JButton("Display Races");
        buttonD5.addActionListener(
                (e) -> {

                    // On click the race table will be populated with data about all past races

                    DefaultTableModel model = new DefaultTableModel(manager.getRaceData(manager.pastRaces), columnNamesRaces);
                    raceOutcome.setModel(model);
                    resizeColumns(raceOutcome);

                }
        );


        buttonD6 = new JButton("Generate Race");
        buttonD6.addActionListener(
                (e) -> {

                    // On click the listener will take the value of RaceName text field
                    // and pass it as a name of new race to race generating function
                    // Populate the race table with outcome of this one particular race
                    // and update the statics table according to race outcome

                    String raceName = textFieldRaceName.getText();

                    Race tempRace = manager.addRace(raceName);

                    String[] raceResults = tempRace.toData();
                    String[][] tempData = {raceResults};

                    DefaultTableModel model = new DefaultTableModel(manager.returnSortedTable("pointsDSC"), columnNames);
                    table.setModel(model);

                    DefaultTableModel racesModel = new DefaultTableModel(tempData, columnNamesRaces);
                    raceOutcome.setModel(racesModel);
                    resizeColumns(raceOutcome);


                }
        );


        buttonD7 = new JButton("Search Drivers Races");
        buttonD7.addActionListener(
                (e) -> {

                    // On click the listener will take the value of textFiled and try to match it with name
                    // of each driver in every race that took place and return only those races
                    // where the driver participated

                    String toSearch = textField.getText();

                    DefaultTableModel model = new DefaultTableModel(manager.getRaceData(manager.findRacesOf(manager.pastRaces, toSearch)), columnNamesRaces);
                    raceOutcome.setModel(model);
                    resizeColumns(raceOutcome);

                }
        );

        textFieldRaceName = new JTextField("New Race Name");
        textFieldRaceName.setPreferredSize(new Dimension(200, 40));

        leftPanel.add(buttonD1);
        leftPanel.add(buttonD2);
        leftPanel.add(buttonD3);
        leftPanel.add(buttonD4);
        leftPanel.add(buttonD5);
        leftPanel.add(textFieldRaceName);
        leftPanel.add(buttonD6);

        textField = new JTextField("Drivers Name");

        textField.setPreferredSize(new Dimension(200, 40));


        bottomLeftPanel.add(textField);
        bottomLeftPanel.add(buttonD7);


    }

    // Helper function to resize the columns each time the table model is changed
    public void resizeColumns(JTable tableToChange) {
        tableToChange.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tableToChange.getColumnModel().getColumn(0).setPreferredWidth(100);
        tableToChange.getColumnModel().getColumn(1).setPreferredWidth(150);

        for (int i = 2; i < columnsAmount; i++) {

            raceOutcome.getColumnModel().getColumn(i).setPreferredWidth(200);

        }
    }


}
