package com.company;


import java.io.Serializable;

public class Formula1Driver extends Driver implements Serializable {

    Formula1Driver() {

    }


    // Setting the name , team and location of a new driver
    // number of fPos, sPos, tPos , points and noRaces remain 0
    Formula1Driver(String name, String location, String team) {
        setName(name);
        setLocation(location);
        setTeam(team);

    }


    // Creating new driver and setting all of the values that the class stores
    // Used to swap drivers around
    Formula1Driver(String name, String location, String team, int fPos, int sPos, int tPos, int points, int races) {
        setName(name);
        setLocation(location);
        setTeam(team);
        setStats(fPos, sPos, tPos, points, races);

    }

    // Function takes all the values stored in passed argument which is instance of Forumla1Driver class
    // and replaces values of current instance with them
    public void replace(Formula1Driver replacementDriver) {

        setName(replacementDriver.getName());
        setLocation(replacementDriver.getLocation());
        setTeam(replacementDriver.getTeam());
        setStats(replacementDriver.fPos, replacementDriver.sPos, replacementDriver.tPos,
                replacementDriver.points, replacementDriver.noRaces);



    }


    //Function displays all of the values held by the instance of Formula1Driver class
    public void printStats() {
        System.out.println("Driver : " + getName());
        System.out.println("Team : " + getTeam());
        System.out.println("Location : " + getLocation());
        System.out.println("First positions : " + fPos + " Second position " + sPos + " Third position " + tPos);
        System.out.println("Total points : " + points);
        System.out.println("Total races : " + noRaces);
        System.out.println();
    }



    // Function that adds certain amount of point based on passed argument to the instance of Formula1Driver class
    // increments number of races and number of specific positions taken
    public void newRace(int finishPos) {

        noRaces++;

        if (finishPos == 1) {
            fPos++;
            points += 25;
        }

        if (finishPos == 2) {
            sPos++;
            points += 18;
        }

        if (finishPos == 3) {
            tPos++;
            points += 15;
        }

        if (finishPos == 4) {
            points += 12;
        }

        if (finishPos == 5) {
            points += 10;
        }

        if (finishPos == 6) {
            points += 8;
        }

        if (finishPos == 7) {
            points += 6;
        }

        if (finishPos == 8) {
            points += 4;
        }

        if (finishPos == 9) {
            points += 2;
        }

        if (finishPos == 10) {
            points += 1;
        }

    }


}
