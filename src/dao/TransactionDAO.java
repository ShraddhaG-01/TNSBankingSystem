package dao;

import model.Transaction;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {
    private Connection conn = DBConnection.getConnection();

    public boolean saveTransaction(Transaction txn) {
        String sql = "INSERT INTO transactions (account_number, type, amount, description) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, txn.getAccountNumber());
            ps.setString(2, txn.getType());
            ps.setDouble(3, txn.getAmount());
            ps.setString(4, txn.getDescription());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error saving transaction: " + e.getMessage());
            return false;
        }
    }

    public List<Transaction> getTransactionsByAccount(String accountNumber) {
        List<Transaction> list = new ArrayList<>();
        String sql = "SELECT * FROM transactions WHERE account_number = ? ORDER BY transaction_date DESC";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, accountNumber);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Transaction txn = new Transaction();
                txn.setTransactionId(rs.getInt("transaction_id"));
                txn.setAccountNumber(rs.getString("account_number"));
                txn.setType(rs.getString("type"));
                txn.setAmount(rs.getDouble("amount"));
                txn.setDescription(rs.getString("description"));
                txn.setTransactionDate(rs.getString("transaction_date"));
                list.add(txn);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching transactions: " + e.getMessage());
        }
        return list;
    }
}