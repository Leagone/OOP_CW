package com.company;


import java.util.Scanner;

public class Main {


    // Sentinel values. Untill is set to 1 , program will ask for input
    // Instance of Formula1ChampionshipManager
    private static int sentinel = 0;
    private static final Scanner sc = new Scanner(System.in);
    private static final Formula1ChampionshipManager manager = new Formula1ChampionshipManager();


    public static void main(String[] args) {

        // On each run the program will attemp to load the data from past files
        manager.readFromFile();

        // Creates the GUI
        F1Frame frame = new F1Frame(manager);


        System.out.println(
                """
                         Welcome to Formula 1 Championship Manager!
                        V: View all drivers\s
                        A: Add a new driver
                        C: Change driver team
                        D: Delete a driver from the championship
                        T: Display current championship table
                        F: Display statistic of a driver
                        S: Store program data into file
                        L: Load program data from file
                        R: Race!
                        Q: Quit""");

        while (sentinel != 1) {
            inputProcessing(validInput().toUpperCase());


        }


    }

    // Runs desired method based on input
    public static void inputProcessing(String input) {
        switch (input) {
            case "A" -> manager.createNewDriver();
            case "V" -> System.out.println("Need to fix"); // should I display only names ?? not sure
            case "C" -> {

                System.out.println("=============== Which driver team to change ================");
                int driver = sc.nextInt();
                System.out.println("===================== What's new team  =====================");
                String newTeam = sc.nextLine();

                manager.changeDriverTeam(driver, newTeam);
            }
            case "D" -> {
                System.out.println("================ Which driver to delete ===============");
                int driver = sc.nextInt();
                manager.deleteDriver(driver);
                System.out.println("deleted");
            }
            case "F" -> {
                System.out.println("=================== Which driver to show ===================");
                int driver = sc.nextInt();
                manager.displaySelected(driver);
            }
            case "S" -> manager.saveToFile();
            case "T" -> manager.displaySortedTable("classic");
            case "L" -> manager.readFromFile();
            case "R" -> manager.addRaceConsole();
            case "Q" -> sentinel = 1;

            case "P" -> manager.displayPastRaces();

        }
    }


    // Check if the input is one of the menu options
    public static boolean validation(String input) {
        String[] Options = {"A", "V", "D", "S", "D", "F", "L", "Q", "C", "R", "P", "T"};
        input = input.toUpperCase();
        for (String option : Options) {
            if (input.equals(option)) {
                return true;
            }
        }
        return false;

    }

    // validates if input is of right type
    public static String validInput() {

        String input = sc.nextLine();

        if (validation(input)) {

            return input;
        } else {

            return validInput();
        }


    }


}
