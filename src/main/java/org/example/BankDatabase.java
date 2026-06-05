package org.example;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class BankDatabase {
    public static void depositDatabase(Connection connection, int user_new_id, Double deposit) throws SQLException {
        PreparedStatement depositing = connection.prepareCall("INSERT INTO Action(user_id,action_type, amount) values (?,? ::action_type_enum,?)");
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


    public static int insertingNewIDToDatabase(Connection connection, String name, String surname, String cardType) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("Insert into users(name,surname,card_type) values (?,?,?::acc_enum) RETURNING user_id");
        ps.setString(1, name);
        ps.setString(2, surname);
        ps.setString(3, cardType);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            int new_id = rs.getInt("user_id");
            System.out.println("Registration was succeed ✅");
            return new_id;

        } else {

        }
    }

//    public static int getId(Connection connection, String name, String surname) throws SQLException {
//        PreparedStatement ps = connection.prepareStatement("Select user_id from users where name = ? and surname = ?");
//        ps.setString(1, name);
//        ps.setString(2, surname);
//        ResultSet rs = ps.executeQuery();
//        rs.next();
//        int id = rs.getInt("user_id");
//        return id;
//    }

    public static double balanceCheck(Connection connection, int user_id) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("select Coalesce(sum(case when action_type = 'DEPOSIT' then amount else -amount end), 0) from Action where user_id = ?");
        ps.setInt(1, user_id);
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getDouble(1);
    }
}
