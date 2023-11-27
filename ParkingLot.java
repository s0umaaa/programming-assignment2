
/**
 * TODO: Write a comment describing your class here.
 *This class manages a parking lot, including initialization of the parking layout,
 *check-in and check-out of vehicles, and providing statistics.
 *The parking layout is represented as a 2D character array, where different characters
 *denote walls, spaces and parked vehicles. Vehicles are stored in separate lists by type.
 *On check-in, vehicle details are captured and it is added to the appropriate list.
 *On check-out, the parking duration and fees are calculated before removing from the list.
 *Other capabilities include initializing the layout, viewing it, getting occupancy counts,
 *and printing logs of check-out transactions.
 * @author TODO: Soma Hayasaka,Hayasakas@student.unimelb.edu.au,1396301.
 */
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Parking lot manager class
*/
public class ParkingLot {
    private static final int BIKE_HOURLY_FEE = 2;
    private static final int MOTORBIKE_HOURLY_FEE = 3;
    private static final int CAR_HOURLY_FEE = 4;
    private static final int TRUCK_HOURLY_FEE = 10;
    private static final int CAR_NIGHT_HOURY_FEE = 10;
    private static final int BIKE_NIGHT_HOURY_FEE = 5;
    private static final int MOTORBIKE_NIGHT_HOURY_FEE = 5;
    private static final int TRUCK_NIGHT_HOURY_FEE = 20;
    private static final int HITFEEFORCAR = 20;
    private static final int HITFEEFORBIKE = 0;
    private static final int HITFEEFORMOTORBIKE = 10;
    private static final int HITFEEFORTRUCK = 50;
    private int numSpaces; // The total spaces in the parking lot
    private int numOccupied; // The number of occupied spaces
    private char[][] parkingLayout;
    private ArrayList<Car> parkedCarList = new ArrayList<Car>();
    private ArrayList<Bike> parkedBikeList = new ArrayList<Bike>();
    private ArrayList<MotorBike> parkedMotorBikeList = new ArrayList<MotorBike>();
    private ArrayList<Truck> parkedTruckList = new ArrayList<Truck>();
    private ArrayList<ParkingLog> checkedOutList = new ArrayList<ParkingLog>();
    private int width;
    private int length;
    private int capacityOfBike;
    private int capacityOfTruck;
    public boolean isInitialized = false;

    public ParkingLot(ParkingLotEngine engine) {
        parkedCarList = new ArrayList<>();
        parkedBikeList = new ArrayList<>();
        parkedMotorBikeList = new ArrayList<>();
        parkedTruckList = new ArrayList<>();
        checkedOutList = new ArrayList<>();
    }

    public ParkingLot() {
        numSpaces = 0;
        numOccupied = 0;
        parkedCarList = new ArrayList<>();
        parkedBikeList = new ArrayList<>();
        parkedMotorBikeList = new ArrayList<>();
        parkedTruckList = new ArrayList<>();
        checkedOutList = new ArrayList<>();
    }

    public boolean isInitialised() {
        return isInitialized;
    }

/**

Initializes the parking lot space layout based on the provided length and width.
Creates the 2D char array to represent the layout and populates it.
*/
    public void init(int length, int width) {
        this.width = width;
        this.length = length;
        if (length < 7) {
            System.out.println("The length of the parking lot cannot be less than 7. Please re-enter.");
            return;
        } else if (width < 7) {
            System.out.println("The width of the parking lot cannot be less than 7. Please re-enter.");
            return;
        }
        parkingLayout = new char[width][length];
        capacityOfBike=width-6;
        capacityOfTruck=width-6;
        createLayout();

        numSpaces = (length - 2 - (length - 2) / 2) * (width - 4) - (length - 2 - (length - 2) / 2) * 2;
        if (isInitialized) {
            System.out.println("Parking Lot Space is setup. Here is the layout -");
            printLayout();
            System.out.println("Press any key to return to parkinglot menu");
        }
        return;
    }

    /**

Creates the initial layout string by populating the char array
with characters denoting walls, spaces and entrance/exits.
*/
    public void createLayout() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < length; j++) {
                if (i == 1 && j == 0) {
                    parkingLayout[i][j] = 'D';
                } else if (i == width - 2 && j == length - 1) {
                    parkingLayout[i][j] = 'D';
                } else if ((i == 0 || i == width - 1) && (j != 0 && j != length - 1)) {
                    parkingLayout[i][j] = '-';
                } else if (j == 0 || j == length - 1) {
                    parkingLayout[i][j] = '|';
                } else if ((i == 1 || i == width - 2) && (j != 0 && j != length - 1)) {
                    parkingLayout[i][j] = '~';
                } else if ((i == 2 || i == width - 3) && (j % 2 == 1 && j != 0 && j != length - 1)) {
                    parkingLayout[i][j] = 'P';
                } else if (j % 2 == 0) {
                    parkingLayout[i][j] = '~';
                } else {
                    parkingLayout[i][j] = '.';
                }
            }
        }

        for (Car car : parkedCarList) {
            char icon = 'C';
            parkingLayout[car.getY()][car.getX()] = icon;
        }
        for (Bike bike : parkedBikeList) {
            char icon = 'B';
            parkingLayout[bike.getY()][bike.getX()] = icon;
        }
        for (MotorBike motorBike : parkedMotorBikeList) {
            char icon = 'M';
            parkingLayout[motorBike.getY()][motorBike.getX()] = icon;
        }
        for (Truck truck : parkedTruckList) {
            char icon = 'T';
            parkingLayout[truck.getY()][truck.getX()] = icon;
        }
        parkingLayout[1][0] = 'D';
        parkingLayout[width - 2][length - 1] = 'D';
    }

    /**
Prints out the 2D char array representing the parking lot layout.
*/
    public void printLayout() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < length; j++) {
                System.out.print(parkingLayout[i][j]);
            }
            System.out.println();
        }
    }

    /**

Checks in a car by adding it to the parked cars list,
if there is available space.
*/
    public void checkInCar(Car car) {
        if (numOccupied < numSpaces) {
            numOccupied++;
            parkedCarList.add(car);
        } else {
            System.out.println("Parking Lot is full. Can not check-in vehicle.");
        }
    }


    public void checkInBike(Bike bike) {
        
        if (numOccupied < numSpaces) {
            numOccupied++;
            parkedBikeList.add(bike);
        } else {
            System.out.println("Parking Lot is full. Can not check-in vehicle.");
        }
    }

    /*
     * public boolean hasBike() {
     * return parkedBikeList.size() > 0;
     * }
     */

    public void checkInMotorBike(MotorBike motorBike) {
        if (numOccupied < numSpaces) {
            numOccupied++;
            parkedMotorBikeList.add(motorBike);
        } else {
            System.out.println("Parking Lot is full. Can not check-in vehicle.");
        }
    }

    /*
     * public boolean hasMotorBike() {
     * return parkedBikeList.size() > 0;
     * }
     */

    public void checkInTruck(Truck truck) {
        if (numOccupied < numSpaces) {
            numOccupied++;
            parkedTruckList.add(truck);
        } else {
            System.out.println("Parking Lot is full. Can not check-in vehicle.");
        }
    }

    /*
     * public boolean hasTruck() {
     * return parkedTruckList.size() > 0;
     * }
     */


/**
Checks out a car by calculating fees, removing it from
the parked cars list and adding to checkout log.
*/
    public void checkOutCar(String dateOfExit, String timeOfExit) {
        if (numOccupied > 0) {
            numOccupied--;
            Car car = parkedCarList.get(0);
            String entryTime = car.getEntryTime();
            String entryDate = car.getEntryDate();
            String exitTime = timeOfExit;
            String exitDate = dateOfExit;
            int timeSpent = this.getTimeDifferenceInHours(car.getEntryDate(), car.getEntryTime(), exitDate, exitTime);
            int parkingFee = (timeSpent % 24) * CAR_HOURLY_FEE + countDivisions(timeSpent, 24) * CAR_NIGHT_HOURY_FEE;
            int fines = car.getNumberOfHits() * HITFEEFORCAR;
            int totalFee = parkingFee + fines;
            ParkingLog log = new ParkingLog("Car", car.getRegnId(), entryDate, entryTime, exitDate, exitTime, totalFee);
            checkedOutList.add(log);
            parkedCarList.remove(car);

        } else {
            System.out.println("No cars checked-in currently.");
        }
    }

    public void checkOutBike(String dateOfExit, String timeOfExit) {
        if (numOccupied > 0) {
            numOccupied--;
            Bike bike = parkedBikeList.get(0);
            String entryTime = bike.getEntryTime();
            String entryDate = bike.getEntryDate();
            String exitTime = timeOfExit;
            String exitDate = dateOfExit;
            int timeSpent = this.getTimeDifferenceInHours(bike.getEntryDate(), bike.getEntryTime(), exitDate, exitTime);
            int parkingFee = (timeSpent % 24) * BIKE_HOURLY_FEE + countDivisions(timeSpent, 24) * BIKE_NIGHT_HOURY_FEE;
            int fines = bike.getNumberOfHits() * HITFEEFORBIKE;
            int totalFee = parkingFee + fines;
            ParkingLog log = new ParkingLog("Bike", bike.getRegnId(), entryDate, entryTime, exitDate, exitTime,
                    totalFee);
            checkedOutList.add(log);
            parkedBikeList.remove(bike);
        } else {
            System.out.println("No bikes checked-in currently.");
        }
    }

    public void checkOutMotorBike(String dateOfExit, String timeOfExit) {
        if (numOccupied > 0) {
            numOccupied--;
            MotorBike motorBike = parkedMotorBikeList.get(0);
            String entryTime = motorBike.getEntryTime();
            String entryDate = motorBike.getEntryDate();
            String exitTime = timeOfExit;
            String exitDate = dateOfExit;
            int timeSpent = this.getTimeDifferenceInHours(motorBike.getEntryDate(), motorBike.getEntryTime(), exitDate,
                    exitTime);
            int parkingFee = (timeSpent % 24) * MOTORBIKE_HOURLY_FEE
                    + countDivisions(timeSpent, 24) * MOTORBIKE_NIGHT_HOURY_FEE;
            int fines = motorBike.getNumberOfHits() * HITFEEFORMOTORBIKE;
            int totalFee = parkingFee + fines;
            ParkingLog log = new ParkingLog("Motorbike", motorBike.getRegnId(), entryDate, entryTime, exitDate,
                    exitTime, totalFee);
            checkedOutList.add(log);
            parkedMotorBikeList.remove(motorBike);

        } else {
            System.out.println("No motorbikes checked-in currently.");
        }
    }

    public void checkOutTruck(String dateOfExit, String timeOfExit) {
        if (numOccupied > 0) {
            numOccupied--;
            Truck truck = parkedTruckList.get(0);
            String entryTime = truck.getEntryTime();
            String entryDate = truck.getEntryDate();
            String exitTime = timeOfExit;
            String exitDate = dateOfExit;
            int timeSpent = this.getTimeDifferenceInHours(truck.getEntryDate(), truck.getEntryTime(), exitDate,
                    exitTime);
            int parkingFee = (timeSpent % 24) * TRUCK_HOURLY_FEE
                    + countDivisions(timeSpent, 24) * TRUCK_NIGHT_HOURY_FEE;
            int fines = truck.getNumberOfHits() * HITFEEFORTRUCK;
            int totalFee = parkingFee + fines;
            ParkingLog log = new ParkingLog("Truck", truck.getRegnId(), entryDate, entryTime, exitDate, exitTime,
                    totalFee);
            checkedOutList.add(log);
            parkedTruckList.remove(truck);

        } else {
            System.out.println("No trucks checked-in currently.");
        }
    }

    /**
Returns the time difference in hours between check-in and check-out datetimes
*/
    public int getTimeDifferenceInHours(String checkInDate, String checkInTime, String checkOutDate,
            String checkOutTime) {

        int startDayInt = Integer.parseInt(checkInDate.split("-")[2]);
        int startHourInt = Integer.parseInt(checkInTime.split(":")[0]);
        int startMinuteInt = Integer.parseInt(checkInTime.split(":")[1]);
        int endDayInt = Integer.parseInt(checkOutDate.split("-")[2]);
        int endHourInt = Integer.parseInt(checkOutTime.split(":")[0]);
        int endMinuteInt = Integer.parseInt(checkOutTime.split(":")[1]);

        int diffDate = endDayInt - startDayInt;
        int diffHour = endHourInt - startHourInt;
        int diffMinute = endMinuteInt - startMinuteInt;
        if (diffMinute > 0) {
            diffHour++;
        }
        return diffDate * 24 + diffHour;
    }

    public String getNumEmpty() {
        if ((numSpaces - numOccupied) == 0) {
            return "[None]";
        } else {
            return Integer.toString(numSpaces - numOccupied);
        }
    }

    public String getNumOccupied() {
        return Integer.toString(numOccupied);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (char[] row : parkingLayout) {
            for (char c : row) {
                builder.append(c);
            }
            builder.append('\n');
        }
        return builder.toString();
    }

    public void parkinglotMenu(Scanner scanner) {
        while (true) {
            System.out.println("Type 'init' to initialise the parking space");
            System.out.println("Type 'view' to view the layout of the parking space");
            System.out.println("Type 'menu' to return to the main menu");
            // System.out.print("> ");
            String command = scanner.nextLine();
            if (command.equals("init")) {
                isInitialized = true;
                initParkingLot(scanner);
            } else if (command.equals("view")) {
                System.out.print("> ");
                viewParkingLot(scanner);
            } else if (command.equals("menu")) {
                System.out.print("> ");
                break;
            } else {
                System.out.println("> Command not found!");
            }
        }
        return;
    }

    public void parkVehicle(Scanner scanner) {

        System.out.println("To park a vehicle provide the details.");
        System.out.print("> Regn Id: ");
        String regnId = scanner.nextLine();
        while (!isValidRegnId(regnId)) {
            System.out.println("Invalid detail, please enter detail again!");
            System.out.print("> Regn Id: ");
            regnId = scanner.nextLine();
        }

        Vehicle vehicle = getVehicleDetails(regnId);
        String vehicleType = vehicle.getVehicleType();

        if (vehicleType.equals("car")) {
            while (true) {

                printLayout();
                String command=validateCommand(scanner);
                if (command.equals("q"))
                    break;
                if (command.equalsIgnoreCase("w")) {
                    if (carHitCheck(parkedCarList.get(0).getX(), parkedCarList.get(0).getY() - 1)) {
                        createLayout();
                    } else {
                        parkedCarList.get(0).moveY(-1);
                        createLayout();
                    }
                } else if (command.equalsIgnoreCase("a")) {
                    if (carHitCheck(parkedCarList.get(0).getX() - 1, parkedCarList.get(0).getY())) {
                        createLayout();
                    } else {
                        parkedCarList.get(0).moveX(-1);
                        createLayout();
                    }
                } else if (command.equalsIgnoreCase("s")) {
                    if (carHitCheck(parkedCarList.get(0).getX(), parkedCarList.get(0).getY() + 1)) {
                        createLayout();
                    } else {
                        parkedCarList.get(0).moveY(1);
                        createLayout();
                    }
                } else if (command.equalsIgnoreCase("d")) {
                    if (carHitCheck(parkedCarList.get(0).getX() + 1, parkedCarList.get(0).getY())) {
                        createLayout();
                    } else {
                        parkedCarList.get(0).moveX(1);
                        createLayout();
                    }
                }
            }
        } else if (vehicleType.equals("bike")) {
            while (true) {

                printLayout();
                String command=validateCommand(scanner);
                if (command.equals("q"))
                    break;
                if (command.equalsIgnoreCase("w")) {
                    if (bikeHitCheck(parkedBikeList.get(0).getX(), parkedBikeList.get(0).getY() - 1)) {
                        createLayout();
                    } else {
                        parkedBikeList.get(0).moveY(-1);
                        createLayout();
                    }
                } else if (command.equalsIgnoreCase("a")) {
                    if (bikeHitCheck(parkedBikeList.get(0).getX() - 1, parkedBikeList.get(0).getY())) {
                        createLayout();
                    } else {
                        parkedBikeList.get(0).moveX(-1);
                        createLayout();
                    }
                } else if (command.equalsIgnoreCase("s")) {
                    if (bikeHitCheck(parkedBikeList.get(0).getX(), parkedBikeList.get(0).getY() + 1)) {
                        createLayout();
                    } else {
                        parkedBikeList.get(0).moveY(1);
                        createLayout();
                    }
                } else if (command.equalsIgnoreCase("d")) {
                    if (bikeHitCheck(parkedBikeList.get(0).getX() + 1, parkedBikeList.get(0).getY())) {
                        createLayout();
                    } else {
                        parkedBikeList.get(0).moveX(1);
                        createLayout();
                    }
                }
            }
        } else if (vehicleType.equals("motorbike")) {
            while (true) {

                printLayout();
                String command=validateCommand(scanner);
                if (command.equals("q"))
                    break;
                if (command.equalsIgnoreCase("w")) {
                    if (motorBikeHitCheck(parkedMotorBikeList.get(0).getX(), parkedMotorBikeList.get(0).getY() - 1)) {
                        createLayout();
                    } else {
                        parkedMotorBikeList.get(0).moveY(-1);
                        createLayout();
                    }
                } else if (command.equalsIgnoreCase("a")) {
                    if (motorBikeHitCheck(parkedMotorBikeList.get(0).getX() - 1, parkedMotorBikeList.get(0).getY())) {
                        createLayout();
                    } else {
                        parkedMotorBikeList.get(0).moveX(-1);
                        createLayout();
                    }
                } else if (command.equalsIgnoreCase("s")) {
                    if (motorBikeHitCheck(parkedMotorBikeList.get(0).getX(), parkedMotorBikeList.get(0).getY() + 1)) {
                        createLayout();
                    } else {
                        parkedMotorBikeList.get(0).moveY(1);
                        createLayout();
                    }
                } else if (command.equalsIgnoreCase("d")) {
                    if (motorBikeHitCheck(parkedMotorBikeList.get(0).getX() + 1, parkedMotorBikeList.get(0).getY())) {
                        createLayout();
                    } else {
                        parkedMotorBikeList.get(0).moveX(1);
                        createLayout();
                    }
                }
            }
        } else if (vehicleType.equals("truck")) {
            while (true) {

                printLayout();
                String command=validateCommand(scanner);
                if (command.equals("q"))
                    break;
                if (command.equalsIgnoreCase("w")) {
                    if (truckHitCheck(parkedTruckList.get(0).getX(), parkedTruckList.get(0).getY() - 1)) {
                        createLayout();
                    } else {
                        parkedTruckList.get(0).moveY(-1);
                        createLayout();
                    }
                } else if (command.equalsIgnoreCase("a")) {
                    if (truckHitCheck(parkedTruckList.get(0).getX() - 1, parkedTruckList.get(0).getY())) {
                        createLayout();
                    } else {
                        parkedTruckList.get(0).moveX(-1);
                        createLayout();
                    }
                } else if (command.equalsIgnoreCase("s")) {
                    if (truckHitCheck(parkedTruckList.get(0).getX(), parkedTruckList.get(0).getY() + 1)) {
                        createLayout();
                    } else {
                        parkedTruckList.get(0).moveY(1);
                        createLayout();
                    }
                } else if (command.equalsIgnoreCase("d")) {
                    if (truckHitCheck(parkedTruckList.get(0).getX() + 1, parkedTruckList.get(0).getY())) {
                        createLayout();
                    } else {
                        parkedTruckList.get(0).moveX(1);
                        createLayout();
                    }
                }
            }
        }
    }


    private boolean carHitCheck(int x, int y) {
        int nx = x;
        int ny = y;
        char nextChar = parkingLayout[ny][nx];
        if (x <= 0) {
            System.out.println("You have hit the wall, there will be a damage fee!");
            parkedCarList.get(0).hit();
            return true;
        } else if (x >= length) {
            System.out.println("You have hit the wall, there will be a damage fee!");
            parkedCarList.get(0).hit();
            return true;
        } else if (y <= 0) {
            System.out.println("You have hit the wall, there will be a damage fee!");
            parkedCarList.get(0).hit();
            return true;
        } else if (y >= width) {
            System.out.println("You have hit the wall, there will be a damage fee!");
            parkedCarList.get(0).hit();
            return true;
        } else if ('~' != nextChar && '.' != nextChar && 'D' != nextChar) {
            System.out.println("You have hit a vehicle, there will be a damage fee!");
            parkedCarList.get(0).hit();
            return true;
        }
        return false;
    }

    private boolean bikeHitCheck(int x, int y) {
        int nx = x;
        int ny = y;
        char nextChar = parkingLayout[ny][nx];
        if (x <= 0) {
            System.out.println("You have hit the wall, there will be a damage fee!");
            parkedBikeList.get(0).hit();
            return true;
        } else if (x >= length) {
            System.out.println("You have hit the wall, there will be a damage fee!");
            parkedBikeList.get(0).hit();
            return true;
        } else if (y <= 0) {
            System.out.println("You have hit the wall, there will be a damage fee!");
            parkedBikeList.get(0).hit();
            return true;
        } else if (y >= width) {
            System.out.println("You have hit the wall, there will be a damage fee!");
            parkedBikeList.get(0).hit();
            return true;
        } else if ('~' != nextChar && '.' != nextChar && 'D' != nextChar) {
            System.out.println("You have hit a vehicle!");
            return true;
        }
        return false;
    }

    private boolean motorBikeHitCheck(int x, int y) {
        int nx = x;
        int ny = y;
        char nextChar = parkingLayout[ny][nx];
        if (x <= 0) {
            System.out.println("You have hit the wall, there will be a damage fee!");
            parkedMotorBikeList.get(0).hit();
            return true;
        } else if (x >= length) {
            System.out.println("You have hit the wall, there will be a damage fee!");
            parkedMotorBikeList.get(0).hit();
            return true;
        } else if (y <= 0) {
            System.out.println("You have hit the wall, there will be a damage fee!");
            parkedMotorBikeList.get(0).hit();
            return true;
        } else if (y >= width) {
            System.out.println("You have hit the wall, there will be a damage fee!");
            parkedMotorBikeList.get(0).hit();
            return true;
        } else if ('~' != nextChar && '.' != nextChar && 'D' != nextChar) {
            System.out.println("You have hit a vehicle, there will be a damage fee!");
            parkedMotorBikeList.get(0).hit();
            return true;
        }
        return false;
    }

    private boolean truckHitCheck(int x, int y) {
        int nx = x;
        int ny = y;
        char nextChar = parkingLayout[ny][nx];
        if (x <= 0) {
            System.out.println("You have hit the wall, there will be a damage fee!");
            parkedTruckList.get(0).hit();
            return true;
        } else if (x >= length) {
            System.out.println("You have hit the wall, there will be a damage fee!");
            parkedTruckList.get(0).hit();
            return true;
        } else if (y <= 0) {
            System.out.println("You have hit the wall, there will be a damage fee!");
            parkedTruckList.get(0).hit();
            return true;
        } else if (y >= width) {
            System.out.println("You have hit the wall, there will be a damage fee!");
            parkedTruckList.get(0).hit();
            return true;
        } else if ('~' != nextChar && '.' != nextChar && 'D' != nextChar) {
            System.out.println("You have hit a vehicle, there will be a damage fee!");
            parkedTruckList.get(0).hit();
            return true;
        }
        return false;
    }





    private String validateCommand(Scanner scanner) {
        while(true){
        System.out.println("Type w/s/a/d to move the vehicle to up/down/left/right or else press q to exit.");
        String command = scanner.nextLine();
        System.out.print("> ");

        if (command.equals("w") ||
                command.equals("a") ||
                command.equals("s") ||
                command.equals("d") ||
                command.equals("q")) {
                    return command;
        }

        System.out.println("Invalid command!");

        continue;
    }
    }

    public void initParkingLot(Scanner scanner) {
        int length = 0;
        int width = 0;
        System.out.println("> Please enter the length of the parking lot.");
        while (length < 7) {
            System.out.print("> ");
            length = Integer.parseInt(scanner.nextLine());
            if (length < 7) {
                System.out.println("The length of the parking lot cannot be less than 7. Please re-enter.");
            }
        }
        System.out.println("Please enter the width of the parking lot.");
        while (width < 7) {
            System.out.print("> ");

            width = Integer.parseInt(scanner.nextLine());
            if (width < 7) {
                System.out.println("The width of the parking lot cannot be less than 7. Please re-enter.");
            }
        }
        init(length, width);
        scanner.nextLine();
        return;
    }

    public void viewParkingLot(Scanner scanner) {
        if (!isInitialised()) {
            System.out.println("The parking lot is not initialised. Please run init!");
            System.out.println("Press any key to return to parkinglot menu");
            scanner.nextLine();
            return;
        } else {
            printLayout();
            System.out.println("Press any key to return to parkinglot menu");
            scanner.nextLine();
            return;
        }
    }

    public boolean isNumber(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public void checkInVehicle(Scanner scanner) {
        if (!isInitialised()) {
            System.out.println(
                    "The parking lot hasn't been initialised. Please set up a space for the parking lot. Taking you back to main menu.");
            return;
        }
        if ((numSpaces - numOccupied) == 0) {
            System.out.println("The parking is full. Please come back later. Taking you back to main menu.");
            return;
        }
        System.out.println("Please enter the vehicle details");
        String vehicleType = "";
        while (!vehicleType.equalsIgnoreCase("car") && !vehicleType.equalsIgnoreCase("bike")
                && !vehicleType.equalsIgnoreCase("motorbike") && !vehicleType.equalsIgnoreCase("truck")) {
            System.out.print("> Vehicle Type: ");
            vehicleType = scanner.nextLine();
            if (!vehicleType.equalsIgnoreCase("car") && !vehicleType.equalsIgnoreCase("bike")
                    && !vehicleType.equalsIgnoreCase("motorbike") && !vehicleType.equalsIgnoreCase("truck")) {
                System.out.println("Invalid detail, please enter detail again!");
            }
            if(vehicleType.equalsIgnoreCase("bike")){
            if(capacityOfBike<=0){
                System.out.println("Parking full for bike. Please come back later. Taking you back to main menu.");
                return;
            }else{
                capacityOfBike--;
            }
            }
            if(vehicleType.equalsIgnoreCase("motorbike")){
            if(capacityOfBike<=0){
                System.out.println("Parking full for motorbike. Please come back later. Taking you back to main menu.");
                return;
            }else{
                capacityOfBike--;
            }
            }
            if(vehicleType.equalsIgnoreCase("truck")){
            if(capacityOfTruck<=0){
                System.out.println("Parking full for truck. Please come back later. Taking you back to main menu.");
                return;
            }else{
                capacityOfTruck--;
            }
            }
        }

        System.out.print("> Regn Id: ");
        String regnId = scanner.nextLine();
        while (!isValidRegnId(regnId)) {
            System.out.println("Invalid detail, please enter detail again!");
            System.out.print("> Regn Id: ");
            regnId = scanner.nextLine();
        }
        System.out.print("> Vehicle Model: ");
        String vehicleModel = scanner.nextLine();
        System.out.print("> Vehicle Colour: ");
        String vehicleColour = scanner.nextLine();
        Date entryDate= new Date();


        while (true) {
            System.out.print("> Date of entry: ");
            String date = scanner.nextLine();
            if (date.charAt(4) != '-') {
                System.out.println("Incorrect date format, please enter date in yyyy-MM-dd format again!");
                continue;
            }
            int year = Integer.parseInt(date.split("-")[0]);
            int month = Integer.parseInt(date.split("-")[1]);
            int day = Integer.parseInt(date.split("-")[2]);
            if (year < 1970 || year > 2099 || month > 12 || day > 31) {
                System.out.println(
                        "Incorrect date format, please enter date in yyyy-MM-dd format again between 1970-01-01 and 2099-12-31!");
                continue;
            }

            entryDate.setDate(date);
            System.out.print("> Time of entry: ");
            String time = scanner.nextLine();
            // System.out.print(time);
            if (!time.matches("[0-9][0-9]:[0-9][0-9]")) {
                System.out.println("Incorrect time format, please enter time in HH:mm format again!");
                continue;
            }
            int hour = Integer.parseInt(time.split(":")[0]);
            int min = Integer.parseInt(time.split(":")[1]);
            if (hour > 24 && min > 60) {
                System.out.println("Incorrect time format, please enter time in HH:mm format again!");
                continue;
            }
            // get first and last 2 words
            String first2 = time.substring(0, 2);
            String last2 = time.substring(time.length() - 2);

            if (!isNumber(first2) || !isNumber(last2)) {
                System.out.println("Input must contain only numbers");
                continue;
            }

            entryDate.setTime(time);

            if (vehicleType.equalsIgnoreCase("car")) {
                Car car = new Car(regnId, vehicleModel, vehicleColour, entryDate.getDate(), entryDate.getTime(), 0, 1);
                checkInCar(car);
            } else if (vehicleType.equalsIgnoreCase("bike")) {
                Bike bike = new Bike(regnId, vehicleModel, vehicleColour, entryDate.getDate(), entryDate.getTime(), 0, 1);
                checkInBike(bike);
            } else if (vehicleType.equalsIgnoreCase("motorbike")) {
                MotorBike motorBike = new MotorBike(regnId, vehicleModel, vehicleColour, entryDate.getDate(), entryDate.getTime(), 0,
                        1);
                checkInMotorBike(motorBike);
            } else if (vehicleType.equalsIgnoreCase("truck")) {
                Truck truck = new Truck(regnId, vehicleModel, vehicleColour, entryDate.getDate(), entryDate.getTime(), 0, 1);
                checkInTruck(truck);
            }
            return;
        }
    }

    /**

Processes a vehicle checkout. Gets vehicle details by registration ID,
calculates parking duration and fees, displays details for verification,
and calls the appropriate checkout method if confirmed.
*/
    public void checkOutVehicle(Scanner scanner) {
        if (!isInitialised()) {
            System.out.println("Invalid command! The parking is empty. Taking you back to main menu.");
            return;
        }
        System.out.println("Please enter your vehicle details");
        System.out.print("> Regn Id: ");
        String regnId = scanner.nextLine();
        while (!isValidRegnId(regnId)) {
            System.out.println("Invalid detail, please enter detail again!");
            System.out.print("> Regn Id: ");
            regnId = scanner.nextLine();
        }
        Vehicle vehicle = getVehicleDetails(regnId);
        if (vehicle.getX() != length- 2 || vehicle.getY() != width  - 2) {
            System.out.println("The selected vehicle type is not present in the parking lot. Taking you back to main menu");
            return;
        }

        System.out.print("> Date of exit: ");
        String dateOfExit = scanner.nextLine().trim();
        System.out.print("> Time of exit: ");
        String timeOfExit = scanner.nextLine().trim();


        String vehicleType = vehicle.getVehicleType();
        String entryTime = vehicle.getEntryTime();
        String entryDate = vehicle.getEntryDate();

        if (entryTime == null) {
            System.out.println("The selected vehicle type is not present in the parking lot.");
            return;
        }

        Date exitDate= new Date();
        exitDate.setDate(dateOfExit);
        exitDate.setTime(timeOfExit);
        int duration = getTimeDifferenceInHours(entryDate, entryTime, exitDate.getDate(), exitDate.getTime());
        int daytime = duration % 24;
        int nights = countDivisions(duration, 24);

        int hourlyFee = 0;
        int fineFee = 0;
        int overNightFee = 0;
        if (vehicle instanceof Car) {
            hourlyFee = CAR_HOURLY_FEE;
            fineFee = HITFEEFORCAR;
            overNightFee = CAR_NIGHT_HOURY_FEE;
        } else if (vehicle instanceof Bike) {
            hourlyFee = BIKE_HOURLY_FEE;
            fineFee = HITFEEFORBIKE;
            overNightFee = BIKE_HOURLY_FEE;
        } else if (vehicle instanceof MotorBike) {
            hourlyFee = MOTORBIKE_HOURLY_FEE;
            fineFee = HITFEEFORMOTORBIKE;
            overNightFee = MOTORBIKE_NIGHT_HOURY_FEE;
        } else if (vehicle instanceof Truck) {
            hourlyFee = TRUCK_HOURLY_FEE;
            fineFee = HITFEEFORTRUCK;
            overNightFee = TRUCK_NIGHT_HOURY_FEE;
        }

        int timeSpent = this.getTimeDifferenceInHours(vehicle.getEntryDate(), vehicle.getEntryTime(), exitDate.getDate(),
                exitDate.getTime());
        double parkingFee = (timeSpent % 24) * hourlyFee + countDivisions(timeSpent, 24) * overNightFee;
        double fines = vehicle.getNumberOfHits() * fineFee;
        double totalFee = parkingFee + fines;

        if (vehicleType.equalsIgnoreCase("car")) {
            System.out.println("Please verify your details.");
            System.out.println("Total number of hours: " + daytime);
            if (nights != 0) {
                System.out.println("Total number of overnight parking: " + nights);
            }
            System.out.println("Total number of hits:" + String.valueOf(parkedCarList.get(0).getNumberOfHits()));
            System.out.println("Vehicle Type: Car");
            System.out.println("Regn Id: " + parkedCarList.get(0).getRegnId());
        } else if (vehicleType.equalsIgnoreCase("bike")) {
            System.out.println("Please verify your details.");
            System.out.println("Total number of hours: " + daytime);
            if (nights != 0) {
                System.out.println("Total number of overnight parking: " + nights);
            }
            System.out.println("Total number of hits:" + String.valueOf(parkedBikeList.get(0).getNumberOfHits()));
            System.out.println("Vehicle Type: Bike");
            System.out.println("Regn Id: " + parkedBikeList.get(0).getRegnId());
        } else if (vehicleType.equalsIgnoreCase("motorbike")) {
            System.out.println("Please verify your details.");
            System.out.println("Total number of hours: " + daytime);
            if (nights != 0) {
                System.out.println("Total number of overnight parking: " + nights);
            }
            System.out.println("Total number of hits:" + String.valueOf(parkedMotorBikeList.get(0).getNumberOfHits()));
            System.out.println("Vehicle Type: MotorBike");
            System.out.println("Regn Id: " + parkedMotorBikeList.get(0).getRegnId());
        } else if (vehicleType.equalsIgnoreCase("truck")) {
            System.out.println("Please verify your details.");
            System.out.println("Total number of hours: " + daytime);
            if (nights != 0) {
                System.out.println("Total number of overnight parking: " + nights);
            }
            System.out.println("Total number of hits:" + String.valueOf(parkedTruckList.get(0).getNumberOfHits()));
            System.out.println("Vehicle Type: Truck");
            System.out.println("Regn Id: " + parkedTruckList.get(0).getRegnId());
        }
        System.out.println("Total Parking Fee: $" + totalFee);
        System.out.println("Type Y to accept the fee or menu to return to main menu");

        String confirmation = scanner.nextLine().trim();

        if (confirmation.equalsIgnoreCase("Y")) {
            System.out.println("> Thank you for visiting Java Parking Lot. See you next time!");
            if (vehicleType.equals("car")) {
                checkOutCar(dateOfExit, timeOfExit);
            } else if (vehicleType.equals("bike")) {
                checkOutBike(dateOfExit, timeOfExit);
            } else if (vehicleType.equals("motorbike")) {
                checkOutMotorBike(dateOfExit, timeOfExit);
            } else if (vehicleType.equals("truck")) {
                checkOutTruck(dateOfExit, timeOfExit);
            }
            createLayout();
        } else if (confirmation.equalsIgnoreCase("menu")) {
            return;
        } else {
            System.out.println(
                    "You cannot checkout your vehicle. Please accept the fee by pressing Y or type menu to return to main menu and park your vehicle.");
        }
        return;
    }

    private boolean isValidRegnId(String regnId) {
        return regnId.length() == 6;
    }

    private int countDivisions(int dividend, int divisor) {
        return dividend / divisor;
    }


    public void parkingFeeLog() {
        System.out.println("============ Here are the Transaction logs for the Java Parking Lot =============");
        System.out.println("---------------------------------------------------------------------------------");
        System.out.println("| Vehicle Type | Registration Id | Entry DateTime | Exit DateTime | Parking Fee |");
        System.out.println("---------------------------------------------------------------------------------");
        printCheckOutLog();
    }

    public void printCheckOutLog() {
        if (checkedOutList.isEmpty()) {
            System.out.println("No records found!");
            return;
        }

        for (ParkingLog log : checkedOutList) {

            String vehicleType = String.format("%14s", log.vehicleType);
            String regId = String.format("%17s", log.regId);
            String entryTime = log.entryTime;
            String entryDate = log.entryDate;
            String exitTime = log.exitTime;
            String exitDate = log.exitDate;
            String parkingFee = String.format("%13s", String.valueOf(log.parkingFee));

            String str = "|" + vehicleType + "|" + regId + "|" + entryDate + ' ' + entryTime + "|" + exitDate + ' '
                    + exitTime + "|" + parkingFee + "|";

            System.out.println(str);

        }

    }


/**

Validates the registration ID format
and returns the matched vehicle object
*/
    private Vehicle getVehicleDetails(String regnId) {

        if (parkedCarList.size() > 0) {
            for (Car car : parkedCarList) {
                if (car.getRegnId().equals(regnId)) {
                    return car;
                }
            }
        }

        if (parkedBikeList.size() > 0) {
            for (Bike bike : parkedBikeList) {
                if (bike.getRegnId().equals(regnId)) {
                    return bike;
                }
            }
        }
        if (parkedCarList.size() > 0) {
            for (MotorBike motorbike : parkedMotorBikeList) {
                if (motorbike.getRegnId().equals(regnId)) {
                    return motorbike;
                }
            }
        }
        if (parkedTruckList.size() > 0) {
            for (Truck truck : parkedTruckList) {
                if (truck.getRegnId().equals(regnId)) {
                    return truck;
                }
            }
        }

        return null;

    }

}