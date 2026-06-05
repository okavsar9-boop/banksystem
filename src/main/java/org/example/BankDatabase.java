package org.example;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class BankDatabase {
    public static void depositDatabase(Connection connection, int user_new_id, Double deposit) throws SQLException {
        PreparedStatement depositing = connection.prepareStatement("INSERT INTO Action(user_id,action_type, amount) values (?,? ::action_type_enum,?)");
        depositing.setInt(1, user_new_id);
        depositing.setString(2, "DEPOSIT");
        depositing.setDouble(3, deposit);
        depositing.executeUpdate();
    }

    public static void withdrawDatabase(Connection connection, int user_id, double withdraw) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("INSERT INTO Action(user_id,action_type, amount) values (?,? ::action_type_enum,?)");
        ps.setInt(1, user_id);
        ps.setString(2, "WITHDRAWAL");
        ps.setDouble(3, withdraw);
        ps.executeUpdate();
    }


    public static int insertingNewIDToDatabase(Connection connection, String name, String surname, String cardType, int pin) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("Insert into users(name,surname,card_type,pin) values (?,?,?::acc_enum,?) RETURNING user_id");
        ps.setString(1, name);
        ps.setString(2, surname);
        ps.setString(3, cardType);
        ps.setInt(4,pin);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getInt("user_id");

        } else {
            throw new SQLException("Registration failed ❌ Please try again.");
        }
    }


    public static double balanceCheck(Connection connection, int user_id) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("select Coalesce(sum(case when action_type = 'DEPOSIT' then amount else -amount end), 0) from Action where user_id = ?");
        ps.setInt(1, user_id);
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getDouble(1);
    }

    public static boolean verifyPin(Connection connection, int userId, int pin) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("Select pin from users where  user_id = ? and pin = ?");
        ps.setInt(1,userId);
        ps.setInt(2,pin);
        ResultSet rs = ps.executeQuery();
        return  rs.next();
    }

}
