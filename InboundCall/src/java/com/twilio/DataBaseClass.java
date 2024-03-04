package com.twilio;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Amal ELF
 */
public class DataBaseClass {

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/storemessage";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "amlahmad12345";

    public static void saveMessageToDatabase(String message) {
        try {
            Class.forName("org.postgresql.Driver");
            try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                String sql = "INSERT INTO messages (message) VALUES (?)";
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    preparedStatement.setString(1, message);
                    preparedStatement.executeUpdate();
                }
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DataBaseClass.class.getName()).log(Level.SEVERE, null, ex);

        }
    }

    public static String getMessageFromDatabase() throws SQLException, ClassNotFoundException {

        String storedMessage = null;
        Class.forName("org.postgresql.Driver");
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT message FROM messages ORDER BY id DESC LIMIT 1";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql); ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    storedMessage = resultSet.getString("message");
                }
            }
        }
        return storedMessage != null ? storedMessage : "This a default message";
    }
}
