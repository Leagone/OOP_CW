package com.company;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Race implements Serializable {


    // Class used to store information about particular race
    // Hold date on which race has taken place
    // Its names
    // HashMap of all participants and their positions
    // HashMap<Position, Driver>

    java.util.Date d;
    String name;
    HashMap<Integer, Formula1Driver> participants;


    // Setting held values on creation
    Race(HashMap<Integer, Formula1Driver> raceResoults, String name) {

        d = new java.util.Date();
        this.participants = raceResoults;
        this.name = name;


    }

    Race() {

    }

    // Function used to replace current instance of Race class
    // with values taken from passed argument also of Race class
    // Used to swap races around
    public void replaceRace(Race replacementRace) {

        d = replacementRace.d;
        name = replacementRace.name;
        participants = replacementRace.participants;

    }


    // Function used to display statistics of current instance of a Race class
    // Shows its name , date it taken place on and sorted list of participants and positions taken by them
    public void displayRace() {

        System.out.println("============================ Raced name " + name + " =============================");
        System.out.println("================= Raced on " + d + " =====================");

        for (Map.Entry<Integer, Formula1Driver> entry : participants.entrySet()) {

            Integer v = entry.getKey();
            Formula1Driver k = entry.getValue();
            System.out.println("Driver : " + k.getName());
            System.out.println("Team : " + k.getTeam());
            System.out.println("Finish possition : " + v);
            System.out.println();
        }

        System.out.println("=============================================================================");

    }

    // Function used to convert the participants HashMap into array of Strings
    // Each array will be used as unique row in JTable
    public String[] toData() {

        String[] data = new String[participants.size() + 2];

        data[0] = name;
        data[1] = String.valueOf(d);
        int i = 2;

        for (Map.Entry<Integer, Formula1Driver> entry : participants.entrySet()) {

            Formula1Driver k = entry.getValue();

            data[i] = k.getName() + " " + k.getTeam();

            i++;

        }


        return data;
    }


}
