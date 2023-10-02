package term_project;
/**
 * 
 */

/**
 * @author ayushgoyal
 *
 */
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Canadian_Restaurant extends Main_Page {

    // Define a menu for the restaurant, with items and their corresponding prices
	private static final String[] MENU_ITEMS = {"Burger", "Pizza", "Beef", "Meat Bowl", "Bread"};
	// Array of prices of menu items
	private static final double[] MENU_PRICES = {16.77, 23.45 , 34.67,23.54 , 12.34};
    // Define a tax rate for calculating the total price of an order
    private static final double TAX_RATE = 0.13;

    // Define a scanner to read input from the user
    private Scanner scanner;

    // Constructor for the ItalianRestaurant class
    public Canadian_Restaurant() {
        scanner = new Scanner(System.in);
    }

    // Display the menu to the user
    public void menu() {
        System.out.println("Menu:");
        for (int i = 0; i < MENU_ITEMS.length; i++) {
            System.out.printf("%d. %s - $%.2f%n", i + 1, MENU_ITEMS[i], MENU_PRICES[i]);
        }
    }

    // Get input from the user for their order
    public void iteminputs() {
        try {
            System.out.print("Enter your name: ");
            String username = scanner.nextLine();

            // Display the menu
            menu();

            System.out.print("Enter your choice of item (1-" + MENU_ITEMS.length + "): ");
            int choice = Integer.parseInt(scanner.nextLine());

            // Check if the user entered a valid choice
            if (choice < 1 || choice > MENU_ITEMS.length) {
                throw new IllegalArgumentException("Invalid choice");
            }

            // Generate a bill for the user's order
            generatebill(username, MENU_ITEMS[choice - 1], MENU_PRICES[choice - 1]);

            // Write the order to a file for profit tracking
            generateprofittxt(username, MENU_ITEMS[choice - 1], MENU_PRICES[choice - 1]);
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



