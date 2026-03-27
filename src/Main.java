import dao.DBConnection;
import java.util.Scanner;
import service.AccountService;
import service.TransactionService;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static AccountService accountService = new AccountService();
    static TransactionService transactionService = new TransactionService();

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("     WELCOME TO JAVA BANKING SYSTEM     ");
        System.out.println("========================================");

        boolean running = true;
        while (running) {
            printMenu();
            int choice = getIntInput("Enter your choice: ");

            switch (choice) {
                case 1 -> createAccount();
                case 2 -> viewAccount();
                case 3 -> deposit();
                case 4 -> withdraw();
                case 5 -> transfer();
                case 6 -> viewTransactions();
                case 7 -> listAllAccounts();
                case 0 -> {
                    running = false;
                    DBConnection.closeConnection();
                    System.out.println("\n👋 Thank you for using the Banking System. Goodbye!");
                }
                default -> System.out.println("❌ Invalid choice. Please try again.");
            }
        }
        scanner.close();
    }

    static void printMenu() {
        System.out.println("\n========== MAIN MENU ==========");
        System.out.println(" 1. Create New Account");
        System.out.println(" 2. View Account Details");
        System.out.println(" 3. Deposit Money");
        System.out.println(" 4. Withdraw Money");
        System.out.println(" 5. Transfer Money");
        System.out.println(" 6. View Transaction History");
        System.out.println(" 7. List All Accounts");
        System.out.println(" 0. Exit");
        System.out.println("================================");
    }

    static void createAccount() {
        System.out.println("\n--- Create New Account ---");
        System.out.print("Enter holder name: ");
        String name = scanner.nextLine().trim();
        double deposit = getDoubleInput("Enter initial deposit amount (Rs.): ");
        accountService.createAccount(name, deposit);
    }

    static void viewAccount() {
        System.out.println("\n--- View Account ---");
        System.out.print("Enter account number (e.g. ACC123456): ");
        String accNum = scanner.nextLine().trim().toUpperCase();
        accountService.viewAccount(accNum);
    }

    static void deposit() {
        System.out.println("\n--- Deposit Money ---");
        System.out.print("Enter account number: ");
        String accNum = scanner.nextLine().trim().toUpperCase();
        double amount = getDoubleInput("Enter deposit amount (Rs.): ");
        transactionService.deposit(accNum, amount);
    }

    static void withdraw() {
        System.out.println("\n--- Withdraw Money ---");
        System.out.print("Enter account number: ");
        String accNum = scanner.nextLine().trim().toUpperCase();
        double amount = getDoubleInput("Enter withdrawal amount (Rs.): ");
        transactionService.withdraw(accNum, amount);
    }

    static void transfer() {
        System.out.println("\n--- Transfer Money ---");
        System.out.print("Enter FROM account number: ");
        String from = scanner.nextLine().trim().toUpperCase();
        System.out.print("Enter TO account number: ");
        String to = scanner.nextLine().trim().toUpperCase();
        double amount = getDoubleInput("Enter transfer amount (Rs.): ");
        transactionService.transfer(from, to, amount);
    }

    static void viewTransactions() {
        System.out.println("\n--- Transaction History ---");
        System.out.print("Enter account number: ");
        String accNum = scanner.nextLine().trim().toUpperCase();
        transactionService.viewTransactions(accNum);
    }

    static void listAllAccounts() {
        accountService.listAllAccounts();
    }

    // Helper: safe integer input
    static int getIntInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                int val = Integer.parseInt(scanner.nextLine().trim());
                return val;
            } catch (NumberFormatException e) {
                System.out.println("❌ Please enter a valid number.");
            }
        }
    }

    // Helper: safe double input
    static double getDoubleInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                double val = Double.parseDouble(scanner.nextLine().trim());
                return val;
            } catch (NumberFormatException e) {
                System.out.println("❌ Please enter a valid amount.");
            }
        }
    }
}