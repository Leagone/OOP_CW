package com.company;

import java.io.*;
import java.util.*;

public class Formula1ChampionshipManager implements ChampionshipManager {


    // Formula1ChampionshipManager class that implements functions from ChampionshipManager interface
    // Hold ArrayList of Formula1Driver's participating in the championship
    // HashSet of unique drivers teams
    // Arraylist of Race objects to store all past races that taken place
    // Integer holding length of the biggest race that occurred so far
    // that being used to create right amount of columns of JTable that will display the race details

    Scanner sc = new Scanner(System.in);
    ArrayList<Formula1Driver> alChampionship = new ArrayList<>();
    HashSet<String> teams = new HashSet<>();
    ArrayList<Race> pastRaces = new ArrayList<Race>();
    int biggestRace = 5;


    // Function to simulate time
    public void simulateTime(int time) {

        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    // Function to create new driver
    // Takes input new driver name , location and team
    // Function will check if the team already exists ( If it is already in HasSet teams)
    // If it does information will be prompted and addition canceled
    // if it doesn't new Formula1Driver will be created and added to the championship
    public void createNewDriver() {

        System.out.println("New Driver name : ");
        String name = sc.nextLine();
        System.out.println("New Driver location : ");
        String loc = sc.nextLine();
        System.out.println("New Driver team : ");
        String team = sc.nextLine();

        if (checkIfExist(team)) {
            System.out.println("==Team already have maximum number of drivers, please try again==");
        } else {
            Formula1Driver newDriver = new Formula1Driver(name, loc, team);
            alChampionship.add(newDriver);
            teams.add(team.toLowerCase());
            System.out.println("====================== Driver added ========================");
        }
    }

    // Deletes the driver from the championship
    // and deletes the team from HashSet
    // Takes integer parameter , driver with that position in ArrayList will be deleted
    public void deleteDriver(int driverID) {

        String driverTeam = alChampionship.get(driverID).getTeam();

        alChampionship.remove(driverID);
        teams.remove(driverTeam.toLowerCase());


    }

    // Function to change the team of chosen driver
    // Takes integer parameter to match position in ArrayList
    // and string holding team name. Function will first check if this team
    // does not already exist in the team HashSet
    // If it does it will prompt a message and cancel the change
    // if it doesn't the team will be changed
    public void changeDriverTeam(int driverID, String newTeam) {


        if (checkIfExist(newTeam)) {
            System.out.println("==Team already have maximum number of drivers, please try again==");
        } else {
            alChampionship.get(driverID).setTeam(newTeam);
            teams.add(newTeam.toLowerCase());
        }


    }

    // Helper function that's check if the team is unique or not
    public boolean checkIfExist(String team) {
        return teams.contains(team.toLowerCase());
    }


    // Add race function to be used in console version only
    // It asks for new race name and pass it to race creating function
    // Prompt messages informing what's happening
    // and display the race outcome
    public void addRaceConsole(){

        System.out.println("Race name : ");
        String name = sc.nextLine();



        System.out.println("====================== Lets race !! =======================");

        simulateTime(600);

        Race tempRace = addRace(name);
        pastRaces.add(tempRace);

        System.out.println("=============== Raced on " + tempRace.d  + " ==================");


        tempRace.displayRace();


        System.out.println("====================== Race finished =======================");


    }




    // Function that generates race
    // It return Race object
    // Within function:
    // HashMap that stores the outcome of a race storing it in format <Position, Driver>
    // Filled anonymous driver objects with hardcoded positions

    // Function will generate an array of same length as championship
    // filled with unique integers to represents random generated outcome
    // Each driver will have assigned one of random positions
    // and then placed in order into HashMap holding results
    // The HashMap is being passed to Race object constructor afterwards
    // This was each race is stored sorted

    public Race addRace(String raceName) {


        int champSize = alChampionship.size();

        HashMap<Integer, Formula1Driver> raceResults = new HashMap<Integer, Formula1Driver>();

        for (int i = 1; i < champSize + 1; i++) {

            raceResults.put(i, new Formula1Driver(" ", " ", " "));

        }



        ArrayList<Integer> outcomes = new ArrayList<>();

        while (outcomes.size() != champSize) {
            int random = (int) (Math.random() * champSize + 1);
            if (!outcomes.contains(random)) {
                outcomes.add(random);
            }
        }


        for (int i = 0; i < champSize; i++) {

            Formula1Driver tempDriver = alChampionship.get(i);

            tempDriver.newRace(outcomes.get(i));

            raceResults.replace(outcomes.get(i), tempDriver);

        }





        Race race = new Race(raceResults, raceName);

        pastRaces.add(race);

        return race;

    }




    // Function used to display statistic about selected driver
    // Function takes integer as argument to match position in ArrayList
    public void displaySelected(int driver) {
        alChampionship.get(driver).printStats();
    }


    // Function used to save both championship and history or races as .bin files
    public void saveToFile() {


        try {

            FileOutputStream fos = new FileOutputStream("Championship_Saved.bin");
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            FileOutputStream rfos = new FileOutputStream("Races_Saved.bin");
            ObjectOutputStream roos = new ObjectOutputStream(rfos);


            oos.writeObject(alChampionship);
            roos.writeObject(pastRaces);

            oos.flush();
            roos.flush();
            roos.close();
            oos.close();

            System.out.println("Successfully wrote to the file.");


        } catch (IOException ioex) {
            ioex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }


    // Function used to read the championship and history of races from .bin files
    // Each time the races are loaded the function will look for biggest one that occurred
    // in order to generate right amount of columns of JTable
    public void readFromFile() {

        try {

            FileInputStream fis = new FileInputStream("Championship_Saved.bin");
            ObjectInputStream ois = new ObjectInputStream(fis);
            Object o = ois.readObject();

            ArrayList<Formula1Driver> FromFileChampionship = (ArrayList<Formula1Driver>) o;

            ois.close();

            FileInputStream rfis = new FileInputStream("Races_Saved.bin");
            ObjectInputStream rois = new ObjectInputStream(rfis);
            Object r = rois.readObject();


            ArrayList<Race> FromFileRaces = (ArrayList<Race>) r;

            rois.close();


            alChampionship = FromFileChampionship;
            pastRaces = FromFileRaces;

            for (Race pastRace : pastRaces) {

                int raceSize = pastRace.participants.size();

                if (raceSize > biggestRace) {
                    biggestRace = raceSize;
                }
            }


            System.out.println("Successfully reed to the file.");

        } catch (IOException ioex) {
            ioex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }



    // Function used to display the sorted table
    // Takes sortType argument that will decide which type of sorting will be made
    // Look Sorting function for more details
    public void displaySortedTable(String sortType) {

        ArrayList<Formula1Driver> tempChamp = sort(alChampionship, sortType);

        System.out.println("====================== Table Sorted ========================");

        for (Formula1Driver temp : tempChamp) {
            temp.printStats();
        }


    }

    // Function that returns the content of the table as two-dimensional string array
    // That will be passed as data to JTable object in order to display it
    public String[][] returnSortedTable(String sortType) {


        ArrayList<Formula1Driver> tempChamp = sort(alChampionship, sortType);

        return getData(tempChamp);


    }


    // Helper function used to swap two Formula1Driver objects with each other
    public void swap(Formula1Driver first, Formula1Driver second) {

        Formula1Driver temp = new Formula1Driver();
        temp.replace(first);
        first.replace(second);
        second.replace(temp);

    }


    // Sorting function that will make different form of sorting based on passed argument
    // Return ArrayList of Formula1Drivers , takes ArrayLiast of Formula1Drivers and String of sorting type
    //
    //Types of sorting:
    //
    // classic - sort drivers in an array considering amount of points; if they are the same amount of fPos
    // if they are the same amount of sPos; if they are the same amount of tPos
    //
    // pointsDSC - sort drivers in an array considering only points in descending fashion
    //
    // pointsASC - sort drivers in an array considering only points in ascending fashion



    public ArrayList<Formula1Driver> sort(ArrayList<Formula1Driver> championship, String sortType) {

        ArrayList<Formula1Driver> tempChamp = (ArrayList<Formula1Driver>) championship.clone();
        int n = championship.size();

        switch (sortType) {

            case "classic" -> {
                for (int i = 0; i < n; i++) {
                    for (int j = i + 1; j < n; j++) {
                        Formula1Driver first = tempChamp.get(i);
                        Formula1Driver second = tempChamp.get(j);

                        boolean samePoints = (first.points == second.points);
                        boolean sameFPos = (first.fPos == second.fPos);
                        boolean sameSPos = (first.sPos == second.sPos);


                        if (first.points < second.points) {
                            swap(first, second);
                        } else if (samePoints) {
                            if (first.fPos < second.fPos) {
                                swap(first, second);
                            } else if (sameFPos) {
                                if (first.sPos < second.sPos) {
                                    swap(first, second);
                                } else if (sameSPos) {
                                    if (first.tPos < second.tPos) {
                                        swap(first, second);
                                    }
                                }
                            }

                        }

                    }
                }
            }

            case "pointsDSC" -> {

                for (int i = 0; i < n; i++) {
                    for (int j = i + 1; j < n; j++) {
                        Formula1Driver first = tempChamp.get(i);
                        Formula1Driver second = tempChamp.get(j);


                        if (first.points < second.points) {
                            swap(first, second);
                        }

                    }
                }

            }

            case "pointsASC" -> {

                for (int i = 0; i < n; i++) {
                    for (int j = i + 1; j < n; j++) {
                        Formula1Driver first = tempChamp.get(i);
                        Formula1Driver second = tempChamp.get(j);


                        if (first.points > second.points) {
                            swap(first, second);
                        }

                    }
                }

            }

            case "fPos" -> {

                for (int i = 0; i < n; i++) {
                    for (int j = i + 1; j < n; j++) {
                        Formula1Driver first = tempChamp.get(i);
                        Formula1Driver second = tempChamp.get(j);


                        if (first.fPos < second.fPos) {
                            swap(first, second);
                        }

                    }
                }


            }

            default -> throw new IllegalStateException("Unexpected value: " + sortType);
        }


        return tempChamp;

    }


    // Function that display statistic ( call displayRace function ) of each race in pastRaces ArrayList
    public void displayPastRaces() {

        for (Race pastRace : pastRaces) {
            pastRace.displayRace();

            int raceSize = pastRace.participants.size();

            if (raceSize > biggestRace) {
                biggestRace = raceSize;
            }
        }


    }

    // Helper function used to swap races around
    public void swapRace(Race first, Race second) {

        Race temp = new Race();
        temp.replaceRace(first);
        first.replaceRace(second);
        second.replaceRace(temp);

    }

    // Function that will sort races based on date they took place
    // Returns sorted ArrayList of Race objects
    public ArrayList<Race> returnSortedPastRaces() {

        int n = pastRaces.size();

        ArrayList<Race> tempPastRaces = (ArrayList<Race>) pastRaces.clone();


        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                Race first = tempPastRaces.get(i);
                Race second = tempPastRaces.get(j);


                if (first.d.after(second.d)) {

                    swapRace(first, second);

                }

            }
        }

        return tempPastRaces;

    }


    // Function to convert the entire championship into two-dimensional string array
    // that will be pased to the JTable that will display the data in GUI
    public String[][] getData(ArrayList<Formula1Driver> championship) {

        int champSize = championship.size();

        String[][] data = new String[champSize][9];

        for (int i = 0; i < champSize; i++) {

            Formula1Driver temp = championship.get(i);


            data[i] = new String[]{
                    String.valueOf(i), temp.getName(), temp.getTeam(), temp.getLocation(), String.valueOf(temp.points),
                    String.valueOf(temp.fPos), String.valueOf(temp.sPos), String.valueOf(temp.tPos), String.valueOf(temp.noRaces)
            };

        }

        return data;
    }

    // Function to convert all races into two-dimensional string array
    // that will be passed to the JTable that will display the daya in GUI
    public String[][] getRaceData(ArrayList<Race> races) {

        int racesSize = races.size();

        String[][] data = new String[racesSize][biggestRace + 2];

        for (int i = 0; i < racesSize; i++) {

            Race temp = races.get(i);

            String[] raceParticipants = temp.toData();


            data[i] = raceParticipants;
        }



        return data;
    }


    // Function that will return ArrayList of races that have common participant
    public ArrayList<Race> findRacesOf(ArrayList<Race> races , String driverName){

        ArrayList<Race> matchingRaces = new ArrayList<Race>();

        for (int i = 0; i < races.size(); i++) {

            Race tempRace = races.get(i);


            for (Map.Entry<Integer, Formula1Driver> entry : tempRace.participants.entrySet()) {

                Formula1Driver k = entry.getValue();
                if(k.getName().contains(driverName)){

                    matchingRaces.add(tempRace);

            }


        }

        }

        return matchingRaces;


    }


}
