package org.example;
import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {

        try (Connection connection = DatabaseConnection.getConnection()) {

            // Let's start with the entry point

            Scanner scanner = new Scanner(System.in);

            while (true) {

                System.out.println("\n〰️〰️〰️〰️〰️〰️〰️ Welcome To Our Aurum Bank  〰️〰️〰️〰️〰️〰️〰️");
                System.out.println("\nChoose One Option : ");
                System.out.println("\n1 - REGISTER USER ");
                System.out.println("2 - LOG IN  ");
                System.out.println("3 - EXIT ");
                System.out.println("\n〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️");


                int option = scanner.nextInt();
                switch (option) {
                    case 1:

                        System.out.println("\nEnter your name :");
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
                        System.out.println("\nEntered full name : " + (name.toUpperCase() + " "
                                + surname.toUpperCase().trim()));
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
                        // lets put new user info into database

                        PreparedStatement ps = connection.prepareStatement("Insert into users(name,surname,card_type) values (?,?,?::acc_enum)");

                        ps.setString(1, name);
                        ps.setString(2, surname);
                        ps.setString(3, cardType);
                        int rs = ps.executeUpdate();
                        if (rs == 1) {
                            System.out.println("Registration was succeed ✅");
                        } else {
                            System.out.println("Registration failed.❌ Please try again.");
                        }

                        PreparedStatement getId = connection.prepareStatement("Select user_id from users where name = ? and surname = ?");
                        getId.setString(1, name);
                        getId.setString(2, surname);
                        ResultSet rs4 = getId.executeQuery();
                        rs4.next();
                        int user_new_id = rs4.getInt("user_id");


                        System.out.println("▫️️You get new ID : <" + user_new_id + "> Please DO NOT show anybody ❗");
                        System.out.println("➿➿➿➿➿➿➿➿➿➿➿➿➿➿➿➿➿➿➿➿➿➿➿➿➿➿➿");
                        System.out.println("▫️Choose the next operation");
                        System.out.println("1: DEPOSIT");
                        System.out.println("2: EXIT");
                        byte selection = scanner.nextByte();
                        switch (selection) {
                            case 1:
                                System.out.println("▫️You picked 'DEPOSIT' section");
                                System.out.println("▫️How much money you want to deposit ?");
                                double deposit = scanner.nextDouble();
                                Deposit(scanner, connection, user_new_id, deposit);
                                System.out.println("Process is finished ");
                                break;
                            case 2:
                                System.out.println("You chose EXIT , Bye");
                                break;
                            default:
                                System.out.println("Invalid choice");
                                break;
                        }
                        break;

                    case 2:
                        System.out.println("You picked LOG IN section");
                        System.out.println("▫️Enter your User ID number :");
                        int id_number = scanner.nextInt();
                        PreparedStatement login = connection.prepareStatement("select Coalesce(sum( case when action_type = 'DEPOSIT' then amount else -amount end ),0)   from Action where user_id = ?");
                        login.setInt(1, id_number);
                        ResultSet rss = login.executeQuery();
                        rss.next();
                        rss.getDouble(1);
                        System.out.println("➿➿➿➿➿➿➿➿➿➿➿➿➿➿➿➿➿➿➿➿➿➿➿➿➿➿➿");
                        System.out.println("▫️Choose the next operation");
                        System.out.println("1: DEPOSIT");
                        System.out.println("2: WITHDRAW");
                        System.out.println("3: EXIT");
                        int selection2 = scanner.nextInt();
                        switch (selection2) {
                            case 1:
                                System.out.println("▫️You picked 'DEPOSIT' section");
                                System.out.println("▫️How much money you want to deposit ?");
                                double deposit2 = scanner.nextDouble();
                                Deposit(scanner, connection, id_number, deposit2);
                                System.out.println(" Process is finished ");
                                break;
                            case 2:
                                System.out.println("▫️You picked 'WITHDRAW' section");
                                System.out.println("▫️How much money you want to withdraw ?");
                                double withdrawAmount = scanner.nextDouble();
                                Withdraw(scanner, connection, id_number, withdrawAmount);
                                break;
                        } break;

                    case 3:
                        System.out.println("Exit chosen");
                        System.exit(0);
                        break;

                    default:
                        System.out.println("Invalid choice");
                        break;
                }
            }

        } catch (SQLException e) {
            System.out.println("Connection Failed : " + e.getMessage());
        }
    }

    public static void Deposit(Scanner scanner, Connection connection, int user_new_id, Double deposit) throws SQLException {
        if (deposit > 0) {
            System.out.println("The transaction fee will be 2$ for national card. You want to continue?");
            System.out.println("1: YES\n2: NO ");
            int select = scanner.nextInt();
            switch (select) {
                case 1:
                    PreparedStatement depositing = connection.prepareCall("INSERT INTO Action(user_id,action_type, amount) values (?,? ::action_type_enum,?)");
                    depositing.setInt(1, user_new_id);
                    depositing.setString(2,"DEPOSIT" );
                    depositing.setDouble(3, deposit);
                    depositing.executeUpdate();
                    System.out.println("You deposited " + deposit + "$ to your card");
                    break;
            }
        } else {
            System.out.println("Deposited money cannot be NEGATIVE");
        }
    }

    public static void Withdraw(Scanner scanner, Connection connection, int user_id, Double withdraw) throws SQLException {

        double balance = 0;
        PreparedStatement login = connection.prepareStatement("select Coalesce(sum( case when action_type = 'DEPOSIT' then amount else -amount end ),0)   from Action where user_id = ?");
        login.setInt(1, user_id);
        ResultSet rss = login.executeQuery();
        rss.next();
        balance = rss.getDouble(1);
        if (withdraw > 0 && withdraw < balance) {
            PreparedStatement withdrawing = connection.prepareStatement("INSERT INTO Action(user_id,action_type, amount) values (?,? ::action_type_enum,?)");
            withdrawing.setInt(1, user_id);
            withdrawing.setString(2, "WITHDRAWAL");
            withdrawing.setDouble(3,withdraw);
            withdrawing.executeUpdate();
            System.out.println("You have received " + withdraw + "$ ");
        }
        else {
            System.out.println("Either entered balance is negative or balance is not enough !");
        }
    }
}
