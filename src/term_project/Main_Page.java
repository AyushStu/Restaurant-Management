package term_project;

import java.sql.*;
import javax.swing.*;

/**
 * This class represents the main page of the Friends and Snacks restaurant application.
 * It provides options for creating a new user, logging in as an existing user, or logging in as an admin.
 */
public class Main_Page extends Db_Connect {
    public static void main(String[] args) throws SQLException {

        // Declare variables
        String username = "";
        String password = "";
        PreparedStatement stmt = null;
        ResultSet rs = null;

        // Create a new database connection
        Db_Connect db = new Db_Connect();

        // Display the main menu and get the user's choice
        String[] options = {"Create User", "Existing User", "Admin"};
        int choice = JOptionPane.showOptionDialog(null, "Select an option", "Welcome To Friends and Snacks Restaurant", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        // Process the user's choice
        switch (choice) {
            case 0:
                // "Create User" option selected
                System.out.println("Create User option selected");
                String userid = JOptionPane.showInputDialog("Enter UserId for new user:");
                String Pass = JOptionPane.showInputDialog("Enter password:");
                try {
                    // Add the new user to the database
                    String sql_query = "INSERT INTO Java (username, password) VALUES ('" + userid + "', '" + Pass + "')";
                    stmt = db.prepareStatement(sql_query);
                    stmt.executeUpdate();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case 1:
                // "Existing User" option selected
                System.out.println("Existing User option selected");

                // Create a custom panel with two input fields for the username and password
                JTextField usernameField = new JTextField();
                JPasswordField passwordField = new JPasswordField();
                Object[] inputs = {"Username:", usernameField, "Password:", passwordField};

                // Display the panel and get the user's input
                int result = JOptionPane.showConfirmDialog(null, inputs, "Login", JOptionPane.OK_CANCEL_OPTION);

                if (result == JOptionPane.OK_OPTION) {
                    username = usernameField.getText();
                    password = new String(passwordField.getPassword());
                }
                try {
                    // Prepare SQL query to retrieve user information from the database
                    String sql = "SELECT * FROM Java WHERE username = '" + username + "'";
                    stmt = db.prepareStatement(sql);
                    rs = stmt.executeQuery(sql);

                    if (rs.next()) {
                        // If the user is found, compare the entered password with the stored password
                        if (password.equals(rs.getString("password"))) {
                            String[] options1 = {"Canadian", "Indian", "Italian"};

                            // Display the cuisine menu and get the user's choice
                            int selectedOption = JOptionPane.showOptionDialog(null, "Welcome!! Choose Cuisine From Menu", "Menu", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options1, options1[0]);

                            // Display a message based on the user's choice
                            switch (selectedOption) {
                                case 0:
                                    JOptionPane.showMessageDialog(null, "Canadian selected");
                                    Canadian_Restaurant cad = new Canadian_Restaurant();
                                    cad.iteminputs();
                                    break;
                                case 1:
                                    JOptionPane.showMessageDialog(null, "Indian selected");
                                    IndianRestaurant ind = new IndianRestaurant();
                                    ind.iteminputs();

                                    break;
                                case 2:
                                    JOptionPane.showMessageDialog(null, "Italian selected");
                                    ItalianRestaurant itl = new ItalianRestaurant();
                                    itl.iteminputs();
                                    break;
                                default:
                                    JOptionPane.showMessageDialog(null, "No option selected");
                                   
									break;
                          }
                       } else {
                          System.out.println("Incorrect password");
                       }
                    } else {
                       System.out.println("User not found");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
            }
                break;
            case 2:
            	 // "Admin" option selected
            	 String[] adminOptions = {"Delete User", "Update Username"};
            	 int adminSelection = JOptionPane.showOptionDialog(null, "Please select an option:", "Admin", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, adminOptions, adminOptions[0]);
            	    
            	 switch(adminSelection) {
            	        case 0:
            	            // "Delete User" option selected
            	        	String userIdToDelete = JOptionPane.showInputDialog("Enter the user ID to delete:");
            	        	// Delete user with the given ID from the database
            	        	try {
            	        	    String sql = "DELETE FROM java WHERE username = ?";
            	        	    stmt = db.prepareStatement(sql);
            	        	    stmt.setString(1, userIdToDelete);
            	        	    int rowsDeleted = stmt.executeUpdate();
            	        	    if (rowsDeleted > 0) {
            	        	        JOptionPane.showMessageDialog(null, "User with ID " + userIdToDelete + " deleted successfully.");
            	        	    } else {
            	        	        JOptionPane.showMessageDialog(null, "No user found with ID " + userIdToDelete + ".");
            	        	    }
            	        	} catch (SQLException e) {
            	        		e.printStackTrace();
            	        	}
            	            break;
            	        case 1:
            	            // "Update Username" option selected
            	        	String userIdToUpdate = JOptionPane.showInputDialog("Enter the user ID to update:");
            	        	String newUsername = JOptionPane.showInputDialog("Enter the new username:");

            	        	try {
            	        	    String sql = "UPDATE java SET username = ? WHERE username = ?";
            	        	    stmt = db.prepareStatement(sql);
            	        	    stmt.setString(1, newUsername);
            	        	    stmt.setString(2, userIdToUpdate);
            	        	    int rowsUpdated = stmt.executeUpdate();
            	        	    if (rowsUpdated > 0) {
            	        	        JOptionPane.showMessageDialog(null, "Username updated successfully.");
            	        	    } else {
            	        	        JOptionPane.showMessageDialog(null, "No user found with ID " + userIdToUpdate + ".");
            	        	    }
            	        	} catch (SQLException e) {
            	        		e.printStackTrace();
            	        	}
            	           

            	            break;
            	        default:
            	            System.out.println("No option selected");
            	            break;
            	    }
                break;
            default:
                System.out.println("No option selected");
                // Do something here or simply exit the program
                break;
        }
	
		
        stmt.close();
        db.closeConnection();

	}
}
