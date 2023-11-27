
/**
 * TODO: Write a comment describing your class here.
 * @author TODO: Soma Hayasaka,Hayasakas@student.unimelb.edu.au,1396301.
 *This class acts as an engine to run the parking lot application.
 *It manages user input/output via a Scanner and contains the main parking
 *lot instance. Provides menu driven interface and delegates calls to ParkingLot.
 *The main method takes optional length/width args to initialize the lot size.
 */

import java.util.Scanner;

public class ParkingLotEngine {
    private Scanner scanner = new Scanner(System.in);
    private ParkingLot lot = new ParkingLot();

    private static final int MIN_PARKING_SIZE = 7;

    private void displayWelcomeText() {
        String titleText = " _     _  _______  ___      _______  _______  __   __  _______ \n" +
                "| | _ | ||       ||   |    |       ||       ||  |_|  ||       |\n" +
                "| || || ||    ___||   |    |      _||   _   ||       ||    ___|\n" +
                "|       ||   |___ |   |    |     |  |  | |  ||       ||   |___ \n" +
                "|       ||    ___||   |___ |     |  |  |_|  || ||_|| ||    ___|\n" +
                "|   _   ||   |___ |       ||     |_ |       || |\\/|| ||   |___ \n" +
                "|__| |__||_______||_______||_______||_______||_|   |_||_______|\n" +
                "_________________________ TO JAVA PARKING _____________________";

        System.out.println(titleText);
        System.out.println();
    }

    private void MainMenu() {
        while (true) {
            if (!lot.isInitialised()) {
                System.out.println("Empty Lots: [None] | Occupied: [None]");
            }else{
            System.out.println("Empty Lots: " + lot.getNumEmpty() + " | Occupied: " + lot.getNumOccupied());
            }
            System.out.println("Please enter a command to continue.");
            System.out.println("Type 'help' to learn how to get started.");
            System.out.print("> ");
            String command = scanner.nextLine();

            if (command.equals("help")) {
                HelpMenu();
            } else if (command.equals("parkinglot")) {
                lot.parkinglotMenu(scanner);
            } else if (command.equals("checkin")) {
                lot.checkInVehicle(scanner);
            } else if (command.equals("park")) {
                lot.parkVehicle(scanner);
            } else if (command.equals("checkout")) {
                lot.checkOutVehicle(scanner);
            } else if (command.equals("parkingfeelog")) {
                lot.parkingFeeLog();
            } else if (command.equals("exit")) {
                System.out.println("Good bye from the Java Parking Lot! See you next time!");
                break;
            } else {
                System.out.println("Command not found!");
            }
        }

    }

    private void HelpMenu() {
        System.out.println();
        System.out.println("Type 'commands' to list all the available commands");
        System.out.println("Type 'menu' to return to the main menu");
        System.out.print("> ");
        String command = scanner.nextLine();
        if (command.equals("commands")) {
            showAllCommands();
        } else if (command.equals("menu")) {
            System.out.println();
            return;
        } else {
            System.out.println("Command not found!");
            HelpMenu();
        }
    }

    private void showAllCommands() {
        System.out.println();
        System.out.println("help: shows you list of commands that you can use.");
        System.out.println("parkinglot: initialise the space for parking lot or view the layout of parking lot.");
        System.out.println("checkin: add your car details while entering the parking lot.");
        System.out.println("park: park your car to one of the empty spot.");
        System.out.println("checkout: view the parking fee while exiting the parking lot.");
        System.out.println("parkingfeelog: view the transaction log for parking lot.");
        System.out.println("exit: To exit the program.");
        HelpMenu();
    }

    public static void main(String[] args) {
        ParkingLotEngine engine = new ParkingLotEngine();
        engine.lot = new ParkingLot(engine);
        if (args.length >= 2) {
            try {
                int length = Integer.parseInt(args[0]);
                int width = Integer.parseInt(args[1]);
                if (length >= MIN_PARKING_SIZE && width >= MIN_PARKING_SIZE) {
                    engine.lot.init(length, width);
                    engine.lot.isInitialized = true;
                } else {
                    System.out.println("ParkingLot size cannot be less than " + MIN_PARKING_SIZE + ". Goodbye!");
                    System.exit(0);
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Goodbye!");
                System.exit(0);
            }
        }
        engine.displayWelcomeText();
        engine.MainMenu();
    }
}
