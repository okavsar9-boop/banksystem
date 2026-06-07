package org.example;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Logic {

    // REGISTER
    public static void handleRegister(Scanner scanner, Connection connection) throws SQLException {
        System.out.println("Enter your name :");
        String name = scanner.next();
        while (name.length() < 2) {
            System.out.println("Enter valid name. Name cannot be one letter ❗️");
            name = scanner.next();
        }
        System.out.println("Enter your surname :");
        String surname = scanner.next();
        while (surname.length() < 3) {
            System.out.println("Enter valid surname. Sur name should be more than 2 letter ❗️");
            surname = scanner.next();
        }
        System.out.println("\nEntered full name : " + (name.toUpperCase() + " " + surname.toUpperCase().trim()));
        System.out.println("\nSelect your desired card type:");
        System.out.println("1: National Card");
        System.out.println("2: Visa Card");
        int card = scanner.nextInt();
        while (card != 1 && card != 2) {
            System.out.println("Invalid selection! Try again");
            card = scanner.nextInt();
        }
        String cardType = "";
        switch (card) {
            case 1:
                cardType = "NATIONAL_CARD";
                System.out.println("You chose National card");
                break;
            case 2:
                cardType = "VISA";
                System.out.println("You chose Visa card");
                break;
        }


        String pin = getValidPin(scanner);
        int userId = BankDatabase.insertingNewIDToDatabase(connection, name, surname, cardType, pin);
        System.out.println("Registration was succed ✅");
        System.out.println("▫️️You get new ID : <" + userId + "> Please DO NOT show anybody ❗");
        // Operation with the new card

        System.out.println("➿➿➿➿➿➿➿➿➿➿➿➿➿➿➿➿➿➿➿➿➿➿➿➿➿➿➿");
        System.out.println("▫️Choose the next operation");
        System.out.println("1: DEPOSIT");
        System.out.println("2: EXIT");
        int menuChoice = scanner.nextInt();
        switch (menuChoice) {
            case 1:
                try {
                    System.out.println("▫️You picked 'DEPOSIT' section");
                    System.out.println("▫️How much money you want to deposit ?");
                    double deposit = scanner.nextDouble();
                    BankOperation.deposit(scanner, connection, userId, deposit);
                    System.out.println("Process is finished ");
                } catch (InputMismatchException e) {
                    scanner.next();
                    System.out.println("Wrong input " + e);
                }

                break;
            case 2:
                System.out.println("You chose EXIT , Bye");
                break;
            default:
                System.out.println("Invalid choice");
                break;
        }
    }

    // LOGIN

    public static void handleLogIn(Scanner scanner, Connection connection) throws SQLException {
        try {
            System.out.println("You picked LOG IN section");
            System.out.println("▫️Enter your User ID number :");
            int id = scanner.nextInt();
            boolean authenticated = false;
            for (int i = 0; i < 3; i++) {
                String pin = getValidPin(scanner);
                boolean isValid = BankDatabase.verifyPin(connection, id, pin);
                if (isValid) {
                    authenticated = true;
                    break;

                } else {
                    System.out.println("Wrong PIN ! " + (2 - i) + " Attempts left");
                }
            }
            if (!authenticated) {
                System.out.println("Access Denied , Too many attempts , Account is locked");
                return;
            }

            System.out.println("➿➿➿➿➿➿➿➿➿➿➿➿➿➿➿➿➿➿➿➿➿➿➿➿➿➿➿");
            System.out.println("▫️Choose the next operation");
            System.out.println("1: DEPOSIT");
            System.out.println("2: WITHDRAW");
            System.out.println("3: EXIT");
            int menu = scanner.nextInt();
            switch (menu) {
                case 1:
                    try {
                        System.out.println("▫️You picked 'DEPOSIT' section");
                        System.out.println("▫️How much money you want to deposit ?");
                        double deposit2 = scanner.nextDouble();
                        BankOperation.deposit(scanner, connection, id, deposit2);
                        System.out.println(" Process is finished ");
                    } catch (InputMismatchException e) {
                        scanner.next();
                        System.out.println("Wrong Input!");
                    }

                    break;
                case 2:
                    try {
                        System.out.println("▫️You picked 'WITHDRAW' section");
                        System.out.println("▫️How much money you want to withdraw ?");
                        double withdrawAmount = scanner.nextDouble();
                        BankOperation.withdraw(connection, id, withdrawAmount);
                    } catch (InputMismatchException e) {
                        scanner.next();
                        System.out.println("Wrong Input!");
                    }
            }
        } catch (InputMismatchException e) {
            System.out.println("Wrong Input!" + e);
        }
    }

    public static void handleBalance(Scanner scanner, Connection connection) throws SQLException {
        try {
            System.out.println("Enter your ID");
            int id = scanner.nextInt();
            String pin = getValidPin(scanner);
            boolean isValid = BankDatabase.verifyPin(connection, id, pin);
            if (!isValid) {
                System.out.println("Access Denied");
                return;
            }
            double result = BankOperation.balance(connection, id);
            System.out.println(result);
        } catch (InputMismatchException e) {
            System.out.println("Wrong Input!" + e);
        }
    }

    private static String getValidPin(Scanner scanner) {
        try {
            System.out.println("Enter the PIN");
            String pin = scanner.next();
            while (!pin.matches("\\d{4}")) {
                System.out.println("PIN must be exactly 4 digits. Try again:");
                pin = scanner.next();
            }
            return pin;
        } catch (InputMismatchException e) {
            scanner.next();
            System.out.println("Wrong Input!" + e );
        }
        return "";
    }
}



