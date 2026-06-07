package org.example;

import java.time.LocalDate;

public class User {
    private String name ;
    private String surname;
    private LocalDate dob;
    private String passportId;
    private String pin;
    private Double balance;
    private String cardType ;

    public User(String name, String surname, LocalDate dob, String passportId, String pin, Double balance, String cardType) {
        this.name = name;
        this.surname = surname;
        this.dob = dob;
        this.passportId = passportId;
        this.pin = pin;
        this.balance = balance;
        this.cardType = cardType;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getSurname(){
        return surname;
    }
    public void setSurname(String surname){
        this.surname = surname;
    }
    public LocalDate getDob(){
        return dob;
    }
    public void setDob(LocalDate dob){
        this.dob = dob;
    }
    public String getPassportId(){
        return passportId;
    }
    public void setPassportId(String passportId){
        this.passportId = passportId;
    }
    public String getPin(){
        return pin;
    }
    public void setPin(String pin){
        this.pin = pin;
    }
    public Double getBalance(){
        return balance;
    }
    public void setBalance(Double balance){
        this.balance = balance;
    }
    public String getCardType(){
        return cardType;
    }
    public void setCardType(String cardType){
        this.cardType = cardType;
    }
}


