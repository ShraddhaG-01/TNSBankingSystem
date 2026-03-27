package service;

import dao.AccountDAO;
import dao.TransactionDAO;
import model.Account;
import model.Transaction;
import util.ValidationUtil;
import java.util.List;

public class TransactionService {
    private AccountDAO accountDAO = new AccountDAO();
    private TransactionDAO transactionDAO = new TransactionDAO();

    public void deposit(String accountNumber, double amount) {
        if (!ValidationUtil.isValidDepositOrWithdraw(amount)) return;

        Account acc = accountDAO.getAccountByNumber(accountNumber);
        if (acc == null) {
            System.out.println("❌ Account not found.");
            return;
        }

        double newBalance = acc.getBalance() + amount;
        accountDAO.updateBalance(accountNumber, newBalance);

        Transaction txn = new Transaction(accountNumber, "DEPOSIT", amount, "Cash deposit");
        transactionDAO.saveTransaction(txn);

        System.out.println("✅ Deposited Rs. " + amount);
        System.out.println("   New Balance: Rs. " + newBalance);
    }

    public void withdraw(String accountNumber, double amount) {
        if (!ValidationUtil.isValidDepositOrWithdraw(amount)) return;

        Account acc = accountDAO.getAccountByNumber(accountNumber);
        if (acc == null) {
            System.out.println("❌ Account not found.");
            return;
        }

        if (!ValidationUtil.hasSufficientBalance(acc.getBalance(), amount)) {
            System.out.println("❌ Insufficient balance. Available: Rs. " + acc.getBalance());
            return;
        }

        double newBalance = acc.getBalance() - amount;
        accountDAO.updateBalance(accountNumber, newBalance);

        Transaction txn = new Transaction(accountNumber, "WITHDRAW", amount, "Cash withdrawal");
        transactionDAO.saveTransaction(txn);

        System.out.println("✅ Withdrawn Rs. " + amount);
        System.out.println("   New Balance: Rs. " + newBalance);
    }

    public void transfer(String fromAccount, String toAccount, double amount) {
        if (!ValidationUtil.isValidDepositOrWithdraw(amount)) return;

        if (fromAccount.equals(toAccount)) {
            System.out.println("❌ Cannot transfer to the same account.");
            return;
        }

        Account from = accountDAO.getAccountByNumber(fromAccount);
        Account to = accountDAO.getAccountByNumber(toAccount);

        if (from == null) { System.out.println("❌ Source account not found."); return; }
        if (to == null)   { System.out.println("❌ Destination account not found."); return; }

        if (!ValidationUtil.hasSufficientBalance(from.getBalance(), amount)) {
            System.out.println("❌ Insufficient balance. Available: Rs. " + from.getBalance());
            return;
        }

        accountDAO.updateBalance(fromAccount, from.getBalance() - amount);
        accountDAO.updateBalance(toAccount, to.getBalance() + amount);

        transactionDAO.saveTransaction(new Transaction(fromAccount, "TRANSFER OUT", amount, "Transfer to " + toAccount));
        transactionDAO.saveTransaction(new Transaction(toAccount, "TRANSFER IN", amount, "Transfer from " + fromAccount));

        System.out.println("✅ Transfer successful!");
        System.out.println("   Transferred Rs. " + amount + " from " + fromAccount + " to " + toAccount);
    }

    public void viewTransactions(String accountNumber) {
        Account acc = accountDAO.getAccountByNumber(accountNumber);
        if (acc == null) {
            System.out.println("❌ Account not found.");
            return;
        }

        List<Transaction> list = transactionDAO.getTransactionsByAccount(accountNumber);
        if (list.isEmpty()) {
            System.out.println("No transactions found for this account.");
        } else {
            System.out.println("\n--- Transaction History: " + accountNumber + " ---");
            for (Transaction txn : list) {
                System.out.println(txn);
            }
        }
    }
}