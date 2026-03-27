package model;

public class Transaction {
    private int transactionId;
    private String accountNumber;
    private String type;
    private double amount;
    private String description;
    private String transactionDate;

    public Transaction() {}

    public Transaction(String accountNumber, String type, double amount, String description) {
        this.accountNumber = accountNumber;
        this.type = type;
        this.amount = amount;
        this.description = description;
    }

    // Getters and Setters
    public int getTransactionId() { return transactionId; }
    public void setTransactionId(int id) { this.transactionId = id; }

    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getTransactionDate() { return transactionDate; }
    public void setTransactionDate(String transactionDate) { this.transactionDate = transactionDate; }

    @Override
    public String toString() {
        return String.format("[%s] %s Rs. %.2f - %s (%s)",
                transactionDate, type, amount, description, accountNumber);
    }
}