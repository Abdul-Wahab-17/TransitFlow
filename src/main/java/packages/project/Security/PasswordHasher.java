/*
package packages.project.Security;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordHasher {

    public static void main(String[] args) {
        // Initialize BCryptPasswordEncoder
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        // Retrieve unhashed passwords from the database
        List<String> unhashedPasswords = retrieveUnhashedPasswordsFromDatabase();

        // Iterate through each unhashed password and hash it
        for (String unhashedPassword : unhashedPasswords) {
            String hashedPassword = encoder.encode( unhashedPassword);
            // Update the database with the hashed password
            updatePasswordInDatabase(unhashedPassword, hashedPassword);
        }
    }

    private static List<String> retrieveUnhashedPasswordsFromDatabase() {
        List<String> unhashedPasswords = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            // Connect to the database
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/tms", "root", "lollol");

            // Prepare SQL query to retrieve unhashed passwords
            String sql = "SELECT pin FROM login";
            statement = connection.prepareStatement(sql);

            // Execute query
            resultSet = statement.executeQuery();

            // Retrieve unhashed passwords from the result set
            while (resultSet.next()) {
                String unhashedPassword = resultSet.getString("pin");
                unhashedPasswords.add(unhashedPassword);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return unhashedPasswords;
    }

    private static void updatePasswordInDatabase(String unhashedPassword, String hashedPassword) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            // Connect to the database
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/tms", "root", "lollol");

            // Prepare SQL query to update the password
            String sql = "UPDATE login SET pin = ? WHERE pin = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, hashedPassword);
            statement.setString(2, unhashedPassword);

            // Execute update
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Updated password in database for password: " + unhashedPassword);
            } else {
                System.out.println("Failed to update password in database for password: " + unhashedPassword);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
*/
