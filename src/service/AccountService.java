package service;

import dao.AccountDAO;
import model.Account;
import util.ValidationUtil;
import java.util.List;
import java.util.Random;

public class AccountService {
    private AccountDAO accountDAO = new AccountDAO();

    public void createAccount(String holderName, double initialDeposit) {
        if (!ValidationUtil.isValidName(holderName)) {
            System.out.println("❌ Invalid name. Use letters only.");
            return;
        }
        if (!ValidationUtil.isValidAmount(initialDeposit)) {
            System.out.println("❌ Initial deposit must be greater than 0.");
            return;
        }

        String accountNumber = generateAccountNumber();
        Account account = new Account(accountNumber, holderName, initialDeposit);

        if (accountDAO.createAccount(account)) {
            System.out.println("✅ Account created successfully!");
            System.out.println("   Account Number : " + accountNumber);
            System.out.println("   Holder Name    : " + holderName);
            System.out.println("   Opening Balance: Rs. " + initialDeposit);
        } else {
            System.out.println("❌ Failed to create account.");
        }
    }

    public void viewAccount(String accountNumber) {
        Account acc = accountDAO.getAccountByNumber(accountNumber);
        if (acc != null) {
            System.out.println("\n--- Account Details ---");
            System.out.println("Account Number : " + acc.getAccountNumber());
            System.out.println("Holder Name    : " + acc.getHolderName());
            System.out.println("Balance        : Rs. " + acc.getBalance());
            System.out.println("Created At     : " + acc.getCreatedAt());
        } else {
            System.out.println("❌ Account not found.");
        }
    }

    public void listAllAccounts() {
        List<Account> accounts = accountDAO.getAllAccounts();
        if (accounts.isEmpty()) {
            System.out.println("No accounts found.");
        } else {
            System.out.println("\n--- All Accounts ---");
            for (Account acc : accounts) {
                System.out.println(acc);
            }
        }
    }

    private String generateAccountNumber() {
        Random random = new Random();
        int number = 100000 + random.nextInt(900000);
        return "ACC" + number;
    }
}