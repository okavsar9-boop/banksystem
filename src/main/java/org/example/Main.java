package org.example;
import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        try (Connection connection = DatabaseConnection.getConnection()) {

            // Let's start with the entry point

            Scanner scanner = new Scanner(System.in);
            boolean running = true;

            while (running) {

                System.out.println("\n----------- Welcome To Our Bank --------------");
                System.out.println("\nChoose One Option : ");
                System.out.println("\n1 - Register User ");
                System.out.println("2 - Log In  ");
                System.out.println("3 - Exit ");
                System.out.println("\n-----------------------------------------------");

                // We check the user's intended option here

                int option = scanner.nextInt();
                switch (option) {
                    case 1:

                        System.out.println("Enter your name :");
                        String name = scanner.next();

                        System.out.println("Enter your surname :");
                        String surname = scanner.next();
                        System.out.println(" Entered full name : " + name + surname);
                        System.out.println("Select your card type:");
                        System.out.println("1: National Card");
                        System.out.println("2: Visa Card");

                        // Here user should enter their card type because each card type has different actions

                        int card = scanner.nextInt();

                        switch (card) {
                            case 1:
                                System.out.println("You chose National card");
                                break;
                            case 2:
                                System.out.println("You chose Visa card");
                                break;
                            default:
                                System.out.println("Invalid selection !");
                                break;
                        }

                        // Here If user chooses the option 2 that means they are already registered
                        // We have to just operate the another action

                    case 2:
                        System.out.println("Enter your User ID number :");
                        int id_number = scanner.nextInt();

                        // Here we are going to check if the ID is valid or not

                        PreparedStatement checkTheId = connection.prepareStatement("Select * from users where user_id = ?");
                        checkTheId.setInt(1,id_number);
                        ResultSet rs = checkTheId.executeQuery();

                        if (rs.next()){
                            System.out.println("How much Money you want to deposit ? :");
                            double money = scanner.nextDouble();

                        }else {
                            System.out.println("Your ID is invalid! Move on to the 'REGISTER' page ");
                        }
                        break;

                    // The user chose exit section ,so we terminate all things !

                    case 3:
                        System.out.println("Exit chosen");
                        System.exit(0);
                        break;

                    // If user enters the invalid number or other than from , 1 - 3 it will be show an error
                    default:
                        System.out.println("Invalid choice");
                        break;
                }
            }
        } catch (SQLException e) {
            System.out.println("Connection Failed");
        }
    }
}
