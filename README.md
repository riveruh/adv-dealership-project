# Advanced Car Dealership

This project is a Java console application I built for Workshop 5. It builds on my previous car dealership workshop by adding sales and lease contracts, financing calculations, and file saving.

## Features

- View all vehicles in the dealership inventory.
- Search for vehicles by price, make and model, year, color, mileage, or type.
- Add and remove vehicles from the inventory.
- Sell or lease vehicles to customers.
- Calculate sales taxes, fees, and monthly payments.
- Calculate lease payments and ending vehicle values.
- Save completed contracts to a CSV file.
- Automatically remove sold or leased vehicles from the inventory.
- Save inventory changes so they remain after restarting the application.

## Java Concepts I Used

- Object-oriented programming
- Inheritance and abstract classes
- Encapsulation
- ArrayLists
- File reading and writing
- Loops and conditional statements
- Exception handling
- BigDecimal for monetary values

## Project Structure

The application includes these main classes:

- **Program:** Starts the application.
- **UserInterface:** Displays the menus and handles user input.
- **Vehicle:** Stores information about each vehicle.
- **Dealership:** Manages the dealership and its inventory.
- **DealershipFileManager:** Loads and saves inventory data.
- **Contract:** Abstract parent class for sales and lease contracts.
- **SalesContract:** Handles sales contracts and financing calculations.
- **LeaseContract:** Handles lease contracts and payment calculations.
- **ContractDataManager:** Saves completed contracts to a CSV file.

## How to Run

1. Open the `dealership` project in IntelliJ IDEA.
2. Make sure the project is using a compatible Java JDK.
3. Set the working directory to the `dealership` folder so the CSV files can be found.
4. Run the `Program` class.
5. Follow the console menu to search inventory, manage vehicles, or create contracts.

## Data Files

- `inventory.csv` stores dealership information and vehicle inventory.
- `contracts.csv` stores completed sales and lease contracts.

The application reads inventory data when it starts and updates the CSV files when changes are made.

## What I Learned

This workshop helped me understand how different Java classes work together in a larger application. I got more practice with inheritance, abstract classes, and organizing responsibilities between classes. I also learned how to connect user input, calculations, and file saving to create a more complete program.