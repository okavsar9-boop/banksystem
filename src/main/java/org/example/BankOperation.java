package org.example;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class BankOperation {
    public static void deposit(Scanner scanner, Connection connection, int user_new_id, Double deposit) throws SQLException {
        if (deposit > 0) {
            System.out.println("The transaction fee will be 2$ for national card. You want to continue?");
            System.out.println("1: YES\n2: NO ");
            int select = scanner.nextInt();
            if (select == 1) {
                BankDatabase.depositDatabase(connection, user_new_id, deposit);
                System.out.println("You deposited " + deposit + "$ to your card");
                double currentBalance = balance(connection, user_new_id);
                System.out.println("💳 Your current balance: " + currentBalance + "$");
            }
        } else {
            System.out.println("Deposited money cannot be NEGATIVE");
        }
    }

    public static void withdraw( Connection connection, int user_id, Double withdraw) throws SQLException {

        double balance = balance(connection, user_id);
        if (withdraw > 0 && withdraw <= balance) {
            BankDatabase.withdrawDatabase(connection,user_id,withdraw);
            System.out.println("You have received " + withdraw + "$ ");
            double currentBalance = balance(connection, user_id);
            System.out.println("💳 Your current balance: " + currentBalance + "$");
        } else {
            System.out.println("Either entered balance is negative or balance is not enough !");
        }
    }

    public static double balance(Connection connection, int user_id) throws SQLException {
       return BankDatabase.balanceCheck(connection,user_id);

    }
}
