// Import utilities to functions such as file writer, arraylist, scanner
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

// Vehicle class to store vehicle information
class Vehicle {
    private String make;
    private String model;
    private double mpg;

    public Vehicle(String make, String model, double mpg) {
        this.make = make;
        this.model = model;
        this.mpg = mpg;
    }

    // Getters - returns vehicles information
    public String getMake() {
        return make;
    }
    public String getModel() {
        return model;
    }
    public double getMpg() {
        return mpg;
    }

    @Override
    public String toString() {
        // Returns vehicle's details in formatted look
        return String.format("Make: %s, Model: %s, MPG: %.2f", make, model, mpg);
    }
}

//Main class for vehicle data collection, sorting, and file output
public class VehicleDataProgram {

    private static final String OUTPUT_FILE_NAME = "sorted_vehicle_data.txt";
    private static final String EXIT_COMMAND = "exit";

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            // Temporary list to collect vehicles before creating the LinkedList
            List<Vehicle> tempVehicleList = new ArrayList<>();

            System.out.println("Vehicle Data Program");

            // Input loop for collecting vehicle data
            while (true) {
                System.out.print("\nEnter vehicle make (Enter '" + EXIT_COMMAND + "' to close program): ");
                String make = scanner.nextLine().trim();
                // Compares input to exit command
                if (make.equalsIgnoreCase(EXIT_COMMAND)) {
                    break;
                }
                // Error message if input is empty
                if (make.isEmpty()) {
                    System.out.println("Error: Make cannot be empty. Please try again.");
                    continue;
                }

                System.out.print("Enter vehicle model: ");
                String model = scanner.nextLine().trim();
                // Error message if input is empty
                if (model.isEmpty()) {
                    System.out.println("Error: Model cannot be empty. Please try again.");
                    continue;
                }

                // MPG validation loop
                double mpg = -1;
                boolean validMpgEntered = false;
                while (!validMpgEntered) {
                    System.out.print("Enter miles per gallon (MPG): ");
                    String mpgInput = scanner.nextLine().trim();
                    try {
                        mpg = Double.parseDouble(mpgInput);
                        // Checks if input is a positive number
                        if (mpg > 0) {
                            validMpgEntered = true;
                        } else {
                            System.out.println("Error: MPG must be a positive number. Please try again.");
                        }
                    // Catches other non-valid inputs
                    } catch (NumberFormatException e) {
                        System.out.println("Error: Please enter a positive numeric value for MPG.");
                    }
                }
                //Create new vehicle object with collected vehicle data
                tempVehicleList.add(new Vehicle(make, model, mpg));
                // Confirmation of new vehicle added
                System.out.println("Vehicle added: " + make + " " + model);
            }

            // Displays message if no vehicles were entered
            if (tempVehicleList.isEmpty()) {
                System.out.println("\nNo vehicles were entered. The program will now exit.");
            } else {
                // Sort by MPG and create LinkedList from the sorted ArrayList
                tempVehicleList.sort(Comparator.comparingDouble(Vehicle::getMpg));
                LinkedList<Vehicle> vehicleList = new LinkedList<>(tempVehicleList);
                System.out.println("\nVehicles sorted by MPG.");
                writeVehiclesToFile(vehicleList);
            }
        }
        System.out.println("\nProgram finished.");
    }

    //Writes vehicle data to a text file
    private static void writeVehiclesToFile(List<Vehicle> vehicles) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(OUTPUT_FILE_NAME))) {
            // Header for created text file
            writer.write("Vehicle Data - Sorted by Miles Per Gallon - Ascending");
            writer.newLine();
            writer.write("-----------------------------------------------------");
            writer.newLine();

            // Goes through sorted vehicle list and adds details to the file
            for (Vehicle vehicle : vehicles) {
                writer.write(vehicle.toString());
                writer.newLine();
            }
            // Confirms text file creation
            System.out.println("Data successfully written to " + OUTPUT_FILE_NAME);
        // Exception handling with error message
        } catch (IOException e) {
            System.err.println("An error occurred while writing to the file: " + e.getMessage());
        }
    }
}