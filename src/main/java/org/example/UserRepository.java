package org.example;

import java.sql.*;

public class UserRepository {
    public static String insertingNewIDToDatabase(Connection connection, User user) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement("Insert into users(name,surname,dob, passport_id, pin,balance ,card_type) values (?,?,?,?,?,?,?) RETURNING passport_id")) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getSurname());
            ps.setDate(3, Date.valueOf(user.getDob()));
            ps.setString(4, user.getPassportId());
            ps.setString(5, user.getPin());
            ps.setDouble(6, user.getBalance());
            ps.setString(7, user.getCardType());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("passport_id");
            } else {
                throw new SQLException("Registration failed, please try again");
            }
        }
    }

    public static boolean verifyPin(Connection connection, String passportId, String pin) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement("Select pin from users where pin = ? and passport_id = ?")) {
            ps.setString(1, pin);
            ps.setString(2, passportId);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        }
    }
}

