package com.company;


import java.util.ArrayList;

public interface ChampionshipManager {

    default void createNewDriver(){}

    default void deleteDriver(int driverID){}

    default void changeDriverTeam(int driverID, String newTeam){}

    default void displaySortedTable(String sortType){}

    default Race addRace(String raceName){return null;}

    default void saveToFile(){}

    default void readFromFile(){}

    default void displaySelected(int driver){}

    default void displayPastRaces(){}

    default String[][] getData(ArrayList<Formula1Driver> championship){return null; }



}
