package term_project;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class IndianRestaurant extends Main_Page {

    // Define a menu for the restaurant, with items and their corresponding prices
    public enum MenuItem {
        DOSA("Dosa", 15.99),
        PAV_BHAJI("Pav Bhaji", 11.99),
        SAMOSA("Samosa", 2.99),
        TIKKI("Tikki", 6.99),
        PANI_PUR("Pani Puri", 8.99);

        private final String name;
        private final double price;

        private MenuItem(String name, double price) {
            this.name = name;
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public double getPrice() {
            return price;
        }
    }

    // Define a tax rate for calculating the total price of an order
    private static final double TAX_RATE = 0.13;

    // Define a scanner to read input from the user
    private Scanner scanner;

    // Constructor for the IndianRestaurant class
    public IndianRestaurant() {
        scanner = new Scanner(System.in);
    }

    // Display the menu to the user
    public void menu() {
        System.out.println("Menu:");
        for (int i = 0; i < MenuItem.values().length; i++) {
            MenuItem menuItem = MenuItem.values()[i];
            System.out.printf("%d. %s - $%.2f%n", i + 1, menuItem.getName(), menuItem.getPrice());
        }
    }

    // Get input from the user for their order
    public void iteminputs() {
        try {
            System.out.print("Enter your name: ");
            String username = scanner.nextLine();

            // Display the menu
            menu();

            System.out.print("Enter your choice of item (1-" + MenuItem.values().length + "): ");
            int choice = Integer.parseInt(scanner.nextLine());

            // Check if the user entered a valid choice
            if (choice < 1 || choice > MenuItem.values().length) {
                throw new IllegalArgumentException("Invalid choice");
            }

            // Generate a bill for the user's order
            MenuItem menuItem = MenuItem.values()[choice - 1];
            generatebill(username, menuItem.getName(), menuItem.getPrice());

            // Write the order to a file for profit tracking
            generateprofittxt(username, menuItem.getName(), menuItem.getPrice());
        } catch (NumberFormatException e) {
            System.err.println("Invalid input");
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    // Generate a bill for the user's order
    public void generatebill(String username, String item, double price) {
        double tax = price * TAX_RATE;
        double total = price + tax;
        System.out.printf("Name: %s%nItem: %s - $%.2f%nTax: $%.2f%nTotal: $%.2f%n", username, item, price, tax, total);
    }

    // Write the user's order to a file for profit tracking
    public void generateprofittxt(String username, String item, double price) {
        try (FileWriter writer = new FileWriter("profits.txt", true)) {
            writer.write(String.format("Name: %s, Item: %s - $%.2f%n", username, item, price));
        } catch (IOException e) {
            System.err.println("Error writing to file");
        }
    }
}
