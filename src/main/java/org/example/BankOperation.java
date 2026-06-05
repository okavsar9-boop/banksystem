package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class BankOperation {
    public static void deposit(Scanner scanner, Connection connection, int user_new_id, Double deposit) throws SQLException {
        if (deposit > 0) {
            System.out.println("The transaction fee will be 2$ for national card. You want to continue?");
            System.out.println("1: YES\n2: NO ");
            int select = scanner.nextInt();
            switch (select) {
                case 1:
                    PreparedStatement depositing = connection.prepareCall("INSERT INTO Action(user_id,action_type, amount) values (?,? ::action_type_enum,?)");
                    depositing.setInt(1, user_new_id);
                    depositing.setString(2, "DEPOSIT");
                    depositing.setDouble(3, deposit);
                    depositing.executeUpdate();
                    System.out.println("You deposited " + deposit + "$ to your card");
                    double currentBalance = balance(connection, user_new_id);
                    System.out.println("💳 Your current balance: " + currentBalance + "$");
                    break;
            }
        } else {
            System.out.println("Deposited money cannot be NEGATIVE");
        }
    }

    public static void withdraw(Scanner scanner, Connection connection, int user_id, Double withdraw) throws SQLException {

        double balance = balance(connection, user_id);
        if (withdraw > 0 && withdraw <= balance) {
            PreparedStatement withdrawing = connection.prepareStatement("INSERT INTO Action(user_id,action_type, amount) values (?,? ::action_type_enum,?)");
            withdrawing.setInt(1, user_id);
            withdrawing.setString(2, "WITHDRAWAL");
            withdrawing.setDouble(3, withdraw);
            withdrawing.executeUpdate();
            System.out.println("You have received " + withdraw + "$ ");
            double currentBalance = balance(connection, user_id);
            System.out.println("💳 Your current balance: " + currentBalance + "$");
        } else {
            System.out.println("Either entered balance is negative or balance is not enough !");
        }
    }

    public static double balance(Connection connection, int user_id) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("select Coalesce(sum(case when action_type = 'DEPOSIT' then amount else -amount end), 0) from Action where user_id = ?");
        ps.setInt(1, user_id);
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getDouble(1);

    }
}
