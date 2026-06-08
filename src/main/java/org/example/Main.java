package org.example;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        try(Connection connection = DatabaseConnection.getConnection()){
            Bank bank = new Bank(connection);
            try {
//                bank.transfer("PA9109202","AD3255224",100.0);
//                System.out.println("Money Received");
//                bank.deposit("PA9092092",100.0);
//                System.out.println("Money put it on account");
                bank.withdraw("PA9109202",100.0);
                System.out.println("Money given");
            }catch (IllegalArgumentException | SQLException e){
                System.out.println("\n" + e.getMessage());
            }



















        }catch(SQLException e){
            System.out.println("Connection Failed : " + e.getMessage());
        }
    }
}




