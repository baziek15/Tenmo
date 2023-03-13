package com.techelevator.tenmo.services;


import com.techelevator.tenmo.model.UserCredentials;

import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.model.UserCredentials;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Scanner;
import java.math.BigDecimal;
import java.util.Scanner;

public class ConsoleService {

    private final Scanner scanner = new Scanner(System.in);

//    private PrintWriter out;
//    private Scanner in;





    public int promptForMenuSelection(String prompt) {
        int menuSelection;
        System.out.print(prompt);
        try {
            menuSelection = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            menuSelection = -1;
        }
        return menuSelection;
    }

    public void printGreeting() {
        System.out.println("*********************");
        System.out.println("* Welcome to TEnmo! *");
        System.out.println("*********************");
    }

    public void printLoginMenu() {
        System.out.println();
        System.out.println("1: Register");
        System.out.println("2: Login");
        System.out.println("0: Exit");
        System.out.println();
    }

    public void printMainMenu() {
        System.out.println();
        System.out.println("1: View your current balance");
        System.out.println("2: View your past transfers");
        System.out.println("3: View your pending requests");
        System.out.println("4: Send TE bucks");
        System.out.println("5: Request TE bucks");
        System.out.println("0: Exit");
        System.out.println();
    }
    // transfer menu, after user selects 4 or 5
    public void printTransferMenu() {
        System.out.println();
//        System.out.println("1: View your current balance");
//        System.out.println("2: View your past transfers");
//        System.out.println("3: View your pending requests");
//        System.out.println("4: Send TE bucks");
//        System.out.println("5: Request TE bucks");
//        System.out.println("0: Exit");
//        System.out.println();
    }

    public UserCredentials promptForCredentials() {
        String username = promptForString("Username: ");
        String password = promptForString("Password: ");
        return new UserCredentials(username, password);
    }

    public String promptForString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public int promptForInt(String prompt) {
        System.out.print(prompt);
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number.");
            }
        }
    }

    public BigDecimal promptForBigDecimal(String prompt) {
        System.out.print(prompt);
        while (true) {
            try {
                return new BigDecimal(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a decimal number.");
            }
        }
    }

    public void pause() {
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    public void printErrorMessage() {
        System.out.println("An error occurred. Check the log for details.");
    }



    public void printTransferDetails(int id, String from, String to, String type, String status, BigDecimal amount) {
        System.out.println("-------------------------------");
        System.out.println("Transfer Details");
        System.out.println("-------------------------------");
        System.out.println("Id: " + id);
        System.out.println("From: " + from);
        System.out.println("To: " + to);
        System.out.println("Type: " + type);
        System.out.println("Status: " + status);
        System.out.println("Amount: $" + amount);
    }

    public void printTransfers(int transferId, String fromOrTo, BigDecimal amount) {
        System.out.println(transferId + "     " + fromOrTo + "          " + "$ " + amount);
    }
    public void printUsers(User[] users) {
        for(User user: users) {
            System.out.println(user.getId() + "          " + user.getUsername());
        }
        System.out.println("---------");
//       flush();
    }
    public void printApproveOrRejectOptions() {
        System.out.println("1: Approve");
        System.out.println("2: Reject");
        System.out.println("0: Don't approve or reject\n");
    }


}
