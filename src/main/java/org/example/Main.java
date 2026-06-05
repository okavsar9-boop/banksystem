package org.example;
import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        try (Connection connection = DatabaseConnection.getConnection()) {

            Scanner scanner = new Scanner(System.in);

            while (true) {

                System.out.println("\n〰️〰️〰️〰️〰️〰️〰️ Welcome To Our Aurum Bank  〰️〰️〰️〰️〰️〰️〰️");
                System.out.println("\nChoose One Option : ");
                System.out.println("\n1 - REGISTER USER ");
                System.out.println("2 - LOG IN  ");
                System.out.println("3 - CHECK THE BALANCE");
                System.out.println("4 - EXIT ");
                System.out.println("\n〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️");

                int option = scanner.nextInt();
                switch (option) {
                    case 1: Logic.handleRegister(scanner , connection);break;
                    case 2: Logic.handleLogIn(scanner, connection);break;
                    case 3: Logic.handleBalance(scanner , connection); break;
                    case 4: System.out.println("Exit chosen"); System.exit(0);break;
                    default: System.out.println("Invalid choice"); break;
                }
            }

        } catch (SQLException e) {
            System.out.println("Connection Failed : " + e.getMessage());
        }
    }
}




