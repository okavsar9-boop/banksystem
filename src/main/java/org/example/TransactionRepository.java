package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TransactionRepository {
    public static void insertTransaction(Connection connection, String senderPassportId, String receiverPassportId, String transaction_type, Double amount) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement("insert into transactions (sender_passport_id,receiver_passport_id,action_type,amount) values (?,?,?,?)")) {
            ps.setString(1, senderPassportId);
            ps.setString(2, receiverPassportId);
            ps.setString(3, transaction_type);
            ps.setDouble(4, amount);
            ps.executeUpdate();
        }
    }

    public static Double getBalance(Connection connection, String passportId) throws SQLException{
        try(PreparedStatement ps = connection.prepareStatement("select Coalesce(sum(case when action_type = 'DEPOSIT' then amount else -amount end), 0) from transactions where passport_id = ?")){
            ps.setString(1,passportId);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getDouble(1);
        }
    }
}
