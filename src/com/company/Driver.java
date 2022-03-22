package com.company;


import java.io.Serializable;

public abstract class Driver implements Serializable {

    // class Driver stores information about the driver
    // His or Her name , location and team
    private String dName, dLocation, dTeam;

    // Count of first , second , third position
    // as well as total number of points and races
    int fPos , sPos , tPos, points, noRaces = 0;


    public String getName(){
        return dName;
    }

    public String getLocation(){
        return dLocation;
    }

    public String getTeam(){
        return dTeam;
    }


    public void setName(String newName){
        dName = newName;
    }

    public void setLocation(String newLocation){
       dLocation = newLocation;
    }

    public void setTeam(String newTeam){
        dTeam = newTeam;
    }


    // function that changes values of held variables
    public void setStats(int fPos, int sPos, int tPos, int points, int noRaces){
        this.fPos = fPos;
        this.sPos = sPos;
        this.tPos = tPos;
        this.points = points;
        this.noRaces = noRaces;
    }

    public void newRace(int finishPos){

    }

    public void printStats(){
    }


}
