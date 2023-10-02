package term_project;
/**
 * 
 */

/**
 * @author ayushgoyal
 *
 */
import java.sql.*;

public class Db_Connect {
   private Connection con;
   
   public Db_Connect() {
      try {
         // Load the MySQL JDBC driver
         Class.forName("com.mysql.jdbc.Driver");

         // Create a connection to the database
         String url = "jdbc:mysql://localhost:3308/crudDB";
         String username = "java3";
         String password = "java3";
         con = DriverManager.getConnection(url, username, password);
         System.out.println("Database connection established successfully!");
      } catch (ClassNotFoundException e) {
         System.out.println("MySQL JDBC driver not found.");
         e.printStackTrace();
      } catch (SQLException e) {
         System.out.println("Failed to establish database connection.");
         e.printStackTrace();
      }
   }
   
   public PreparedStatement prepareStatement(String sql) throws SQLException {
      return con.prepareStatement(sql);
   }
   
   public void closeConnection() {
      try {
         if (con != null) {
            con.close();
            System.out.println("Database connection closed.");
         }
      } catch (SQLException e) {
         e.printStackTrace();
      }
   }
}
