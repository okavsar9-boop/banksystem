package org.example;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

public class Bank {
    private static final double TRANSACTION_FEE = 2.0;

    public static double getTransactionFee() {
        return TRANSACTION_FEE;
    }

    private Connection connection;

    public Bank(Connection connection){
        this.connection = connection;
    }


    public void register(String name, String surname, LocalDate dob, String passportId, String pin, Double balance, String cardType) throws SQLException {
        if (!passportId.matches("[A-Z]{2}\\d{7}")) {
            throw new IllegalArgumentException("Enter valid Passport ID . ID must include 2 letters in the beginning and 7 digits after !");
        }
        if(!pin.matches("\\d{4}")) {
            throw new IllegalArgumentException("Enter valid Pin, It should be 4 digits !");
        }
        User user = new User(name, surname, dob, passportId, pin, balance, cardType);
        UserRepository.insertingNewIDToDatabase(connection, user);
        if (balance > 0) {
            TransactionRepository.insertTransaction(connection, passportId, null, "DEPOSIT", balance);
        }
}
    public void deposit(String passportId, Double amount) throws SQLException {
        if (amount > 0) {
            TransactionRepository.insertTransaction(connection, passportId, null, "DEPOSIT", amount);
        } else {
            throw new IllegalArgumentException("Money cannot be negative");
        }
    }

    public void withdraw(String passportId, Double amount) throws SQLException {
        double balance = TransactionRepository.getBalance(connection, passportId);
        if (amount > 0 && amount + TRANSACTION_FEE <= balance) {
            TransactionRepository.insertTransaction(connection, passportId, null, "WITHDRAW", amount);
        } else {
            throw new IllegalArgumentException("Balance cannot be negative ");
        }
    }

    public void transfer(String senderPassportId, String receiverPassportId, Double amount) throws SQLException {
        double balance = TransactionRepository.getBalance(connection, senderPassportId);
        if (amount > 0 && amount + TRANSACTION_FEE <= balance) {
            TransactionRepository.insertTransaction(connection, senderPassportId, receiverPassportId, "TRANSFER", amount);
            TransactionRepository.insertTransaction(connection,receiverPassportId, senderPassportId,"DEPOSIT",amount);

        }else{
           throw new IllegalArgumentException("Either amount is invalid or balance is not enough!");
        }
    }
}

