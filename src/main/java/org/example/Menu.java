package org.example;

import java.util.Scanner;

public class Menu {
    public static void showMenu(Bank bank, Scanner scanner) {

        while (true) {

            System.out.println("\n〰️〰️〰️〰️〰️〰️〰️ Welcome To Our Aurum Bank  〰️〰️〰️〰️〰️〰️〰️");
            System.out.println("\nChoose One Option : ");
            System.out.println("\n1 - REGISTER USER ");
            System.out.println("2 - LOG IN  ");
            System.out.println("3 - CHECK THE BALANCE");
            System.out.println("4 - EXIT ");
            System.out.println("\n〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1: System.out.println("Coming soon...");break;
                case 2: System.out.println("Coming soon...");break;
                case 3: System.out.println("Coming soon...");break;
                case 4: System.out.println("You chose Exit. Bye !"); System.exit(0);break;
                default: System.out.println("Invalid selection. Try Again !");break;
            }
        }
    }
}

