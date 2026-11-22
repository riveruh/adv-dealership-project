package com.dealership;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class UserInterface {
    private Dealership dealership;
    private Scanner scanner = new Scanner(System.in);

    private void init() {
        DealershipFileManager fileManager = new DealershipFileManager();
        this.dealership = fileManager.getDealership();
    }

    public void display() {
        init();

        boolean running = true;

        while (running) {
            System.out.println("\n===== " + dealership.getName() + " =====");
            System.out.println("1 - Find vehicles by price range");
            System.out.println("2 - Find vehicles by make and model");
            System.out.println("3 - Find vehicles by year range");
            System.out.println("4 - Find vehicles by color");
            System.out.println("5 - Find vehicles by mileage range");
            System.out.println("6 - Find vehicles by type");
            System.out.println("7 - List all vehicles");
            System.out.println("8 - Add a vehicle");
            System.out.println("9 - Remove a vehicle");
            System.out.println("10 - Sell/Lease a vehicle");
            System.out.println("99 - Quit");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    processGetByPriceRequest();
                    break;
                case "2":
                    processGetByMakeModelRequest();
                    break;
                case "3":
                    processGetByYearRequest();
                    break;
                case "4":
                    processGetByColorRequest();
                    break;
                case "5":
                    processGetByMileageRequest();
                    break;
                case "6":
                    processGetByVehicleTypeRequest();
                    break;
                case "7":
                    processGetAllVehiclesRequest();
                    break;
                case "8":
                    processAddVehicleRequest();
                    break;
                case "9":
                    processRemoveVehicleRequest();
                    break;
                case "10":
                    processSellLeaseVehicle();
                    break;
                case "99":
                    running = false;
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void displayVehicles(List<Vehicle> vehicles) {
        if (vehicles.isEmpty()) {
            System.out.println("No vehicles found.");
            return;
        }

        System.out.printf(
                "%-8s %-6s %-12s %-12s %-8s %-10s %-10s %12s%n",
                "VIN", "Year", "Make", "Model",
                "Type", "Color", "Mileage", "Price"
        );

        for (Vehicle vehicle : vehicles) {
            System.out.printf(
                    "%-8d %-6d %-12s %-12s %-8s %-10s %-10d %12.2f%n",
                    vehicle.getVin(),
                    vehicle.getYear(),
                    vehicle.getMake(),
                    vehicle.getModel(),
                    vehicle.getVehicleType(),
                    vehicle.getColor(),
                    vehicle.getOdometer(),
                    vehicle.getPrice()
            );
        }
    }

    public void processGetByPriceRequest() {
        try {
            System.out.print("Enter minimum price (no $ or commas): ");
            BigDecimal min = new BigDecimal(scanner.nextLine().trim());

            System.out.print("Enter maximum price (no $ or commas): ");
            BigDecimal max = new BigDecimal(scanner.nextLine().trim());

            if (min.compareTo(BigDecimal.ZERO) < 0
                    || max.compareTo(BigDecimal.ZERO) < 0) {
                System.out.println("Price cannot be negative.");
                return;
            }

            if (min.compareTo(max) > 0) {
                System.out.println("Minimum price cannot exceed maximum price.");
                return;
            }

            List<Vehicle> vehicles = dealership.getVehiclesByPrice(min, max);
            displayVehicles(vehicles);

        } catch (NumberFormatException e) {
            System.out.println("Please enter valid prices without $ or commas.");
        }
    }

    public void processGetByMakeModelRequest() {
        System.out.print("Enter vehicle make: ");
        String make = scanner.nextLine().trim();

        System.out.print("Enter vehicle model: ");
        String model = scanner.nextLine().trim();

        List<Vehicle> vehicles = dealership.getVehiclesByMakeModel(make, model);
        displayVehicles(vehicles);
    }

    public void processGetByYearRequest() {
        try {
            System.out.print("Enter minimum year: ");
            int min = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("Enter maximum year: ");
            int max = Integer.parseInt(scanner.nextLine().trim());

            if (min > max) {
                System.out.println("Minimum year cannot exceed maximum year.");
                return;
            }

            List<Vehicle> vehicles = dealership.getVehiclesByYear(min, max);
            displayVehicles(vehicles);

        } catch (NumberFormatException e) {
            System.out.println("Please enter valid whole numbers for years.");
        }
    }

    public void processGetByColorRequest() {
        System.out.print("Enter vehicle color: ");
        String color = scanner.nextLine().trim();

        List<Vehicle> vehicles = dealership.getVehiclesByColor(color);
        displayVehicles(vehicles);
    }

    public void processGetByMileageRequest() {
        try {
            System.out.print("Enter minimum mileage: ");
            int min = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("Enter maximum mileage: ");
            int max = Integer.parseInt(scanner.nextLine().trim());

            if (min < 0 || max < 0) {
                System.out.println("Mileage cannot be negative.");
                return;
            }

            if (min > max) {
                System.out.println(
                        "Minimum mileage cannot exceed maximum mileage."
                );
                return;
            }

            List<Vehicle> vehicles = dealership.getVehiclesByMileage(min, max);
            displayVehicles(vehicles);

        } catch (NumberFormatException e) {
            System.out.println("Please enter valid whole numbers for mileage.");
        }
    }

    public void processGetByVehicleTypeRequest() {
        System.out.print("Enter vehicle type (car, truck, SUV, van): ");
        String vehicleType = scanner.nextLine().trim();

        List<Vehicle> vehicles = dealership.getVehiclesByType(vehicleType);
        displayVehicles(vehicles);
    }

    public void processGetAllVehiclesRequest() {
        List<Vehicle> vehicles = dealership.getAllVehicles();
        displayVehicles(vehicles);
    }

    public void processAddVehicleRequest() {
        try {
            System.out.print("Enter VIN (whole number): ");
            int vin = Integer.parseInt(scanner.nextLine().trim());

            if (vin <= 0) {
                System.out.println("VIN must be a positive number.");
                return;
            }

            for (Vehicle existingVehicle : dealership.getAllVehicles()) {
                if (existingVehicle.getVin() == vin) {
                    System.out.println("A vehicle with that VIN already exists.");
                    return;
                }
            }

            System.out.print("Enter year: ");
            int year = Integer.parseInt(scanner.nextLine().trim());

            if (year <= 0) {
                System.out.println("Year must be a positive number.");
                return;
            }

            System.out.print("Enter make: ");
            String make = scanner.nextLine().trim();

            System.out.print("Enter model: ");
            String model = scanner.nextLine().trim();

            System.out.print("Enter type (car, truck, SUV, van): ");
            String vehicleType = scanner.nextLine().trim();

            System.out.print("Enter color: ");
            String color = scanner.nextLine().trim();

            if (make.isBlank() || model.isBlank()
                    || vehicleType.isBlank() || color.isBlank()) {
                System.out.println("Vehicle details cannot be blank.");
                return;
            }

            if (make.contains("|") || model.contains("|")
                    || vehicleType.contains("|") || color.contains("|")) {
                System.out.println("Vehicle details cannot contain |.");
                return;
            }

            if (!vehicleType.equalsIgnoreCase("car")
                    && !vehicleType.equalsIgnoreCase("truck")
                    && !vehicleType.equalsIgnoreCase("SUV")
                    && !vehicleType.equalsIgnoreCase("van")) {
                System.out.println("Type must be car, truck, SUV, or van.");
                return;
            }

            System.out.print("Enter mileage (no commas): ");
            int odometer = Integer.parseInt(scanner.nextLine().trim());

            if (odometer < 0) {
                System.out.println("Mileage cannot be negative.");
                return;
            }

            System.out.print("Enter price (no $ or commas): ");
            BigDecimal price = new BigDecimal(scanner.nextLine().trim());

            if (price.compareTo(BigDecimal.ZERO) < 0) {
                System.out.println("Price cannot be negative.");
                return;
            }

            Vehicle vehicle = new Vehicle(
                    vin, year, make, model,
                    vehicleType, color, odometer, price
            );

            dealership.addVehicle(vehicle);

            DealershipFileManager fileManager = new DealershipFileManager();
            fileManager.saveDealership(dealership);

            System.out.println("Vehicle added and inventory saved.");

        } catch (NumberFormatException e) {
            System.out.println(
                    "Invalid number. Use whole numbers for VIN, year, and mileage,"
                            + " and a valid decimal for price."
            );
        } catch (IllegalStateException e) {
            System.out.println(
                    "The vehicle was added in memory, but saving failed."
                            + " The inventory file may be incomplete."
            );
        }
    }

    public void processRemoveVehicleRequest() {
        try {
            System.out.print("Enter VIN of vehicle to remove: ");
            int vin = Integer.parseInt(scanner.nextLine().trim());

            if (vin <= 0) {
                System.out.println("VIN must be a positive number.");
                return;
            }

            Vehicle vehicleToRemove = null;

            for (Vehicle vehicle : dealership.getAllVehicles()) {
                if (vehicle.getVin() == vin) {
                    vehicleToRemove = vehicle;
                    break;
                }
            }

            if (vehicleToRemove == null) {
                System.out.println("No vehicle found with that VIN.");
                return;
            }

            System.out.println(
                    "Selected: " + vehicleToRemove.getYear() + " "
                            + vehicleToRemove.getMake() + " "
                            + vehicleToRemove.getModel()
            );
            System.out.print("Remove this vehicle? (Y/N): ");
            String confirmation = scanner.nextLine().trim();

            if (!confirmation.equalsIgnoreCase("Y")) {
                System.out.println("Removal canceled.");
                return;
            }

            dealership.removeVehicle(vehicleToRemove);

            DealershipFileManager fileManager = new DealershipFileManager();
            fileManager.saveDealership(dealership);

            System.out.println("Vehicle removed and inventory saved.");

        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid whole number for VIN.");
        } catch (IllegalStateException e) {
            System.out.println(
                    "The vehicle was removed in memory, but saving failed."
                            + " The inventory file may be incomplete."
            );
        }
    }

    public void processSellLeaseVehicle() {
        System.out.println("===== Sell/Lease a Vehicle =====");

        try {
            System.out.print("Enter VIN of vehicle: ");
            int vin = Integer.parseInt(scanner.nextLine().trim());

            Vehicle selectedVehicle = null;

            for (Vehicle vehicle : dealership.getAllVehicles()) {
                if (vehicle.getVin() == vin) {
                    selectedVehicle = vehicle;
                    break;
                }
            }

            if (selectedVehicle == null) {
                System.out.println("No vehicle found with that VIN.");
                return;
            }

            System.out.println("Selected vehicle: "
                    + selectedVehicle.getYear() + " "
                    + selectedVehicle.getMake() + " "
                    + selectedVehicle.getModel());

            System.out.print("Enter customer name: ");
            String customerName = scanner.nextLine().trim();

            System.out.print("Enter customer email: ");
            String customerEmail = scanner.nextLine().trim();

            System.out.print("Enter contract date (YYYYMMDD): ");
            String contractDate = scanner.nextLine().trim();

            if (customerName.isBlank()
                    || customerEmail.isBlank()
                    || contractDate.isBlank()) {
                System.out.println("Customer information and date cannot be blank.");
                return;
            }

            if (customerName.contains("|")
                    || customerEmail.contains("|")
                    || contractDate.contains("|")) {
                System.out.println("Customer information cannot contain |.");
                return;
            }

            System.out.println("Customer information collected successfully.");

            System.out.println("\n1 - Buy vehicle");
            System.out.println("2 - Lease vehicle");
            System.out.print("Choose an option: ");
            String contractChoice = scanner.nextLine().trim();

            if (contractChoice.equals("1")) {
                System.out.println("Sale selected.");

                System.out.print("Would you like to finance this vehicle? (Y/N): ");
                String financeChoice = scanner.nextLine().trim();

                if (!financeChoice.equalsIgnoreCase("Y")
                        && !financeChoice.equalsIgnoreCase("N")) {
                    System.out.println("Invalid option. Please enter Y or N.");
                    return;
                }

                // Convert the financing choice into a boolean for the contract.
                boolean financed = financeChoice.equalsIgnoreCase("Y");

                SalesContract salesContract = new SalesContract(
                        contractDate, customerName, customerEmail,
                        selectedVehicle, financed
                );

                System.out.println("Sales contract created successfully.");
                System.out.printf("Total price: $%.2f%n", salesContract.getTotalPrice());
                System.out.printf("Monthly payment: $%.2f%n", salesContract.getMonthlyPayment());
            } else if (contractChoice.equals("2")) {
                // Only vehicles that are three years old or newer can be leased.
                int currentYear = java.time.LocalDate.now().getYear();

                if (currentYear - selectedVehicle.getYear() > 3) {
                    System.out.println("This vehicle cannot be leased.");
                    System.out.println("Vehicles older than 3 years are not eligible!");
                    return;
                }

                System.out.println("Lease selected.");

                LeaseContract leaseContract = new LeaseContract(
                        contractDate, customerName, customerEmail, selectedVehicle
                );

                System.out.println("Lease contract created successfully.");
                System.out.printf("Expected ending value: $%.2f%n", leaseContract.getExpectedEndingValue());
                System.out.printf("Lease fee: $%.2f%n", leaseContract.getLeaseFee());
                System.out.printf("Total price: $%.2f%n", leaseContract.getTotalPrice());
                System.out.printf("Monthly payment: $%.2f%n", leaseContract.getMonthlyPayment());
            } else {
                System.out.println("Invalid option. Please choose 1 or 2.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid whole number for VIN.");
        }
    }
}
