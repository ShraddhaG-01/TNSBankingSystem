package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Account;

public class AccountDAO {
    private final Connection conn = DBConnection.getConnection();

    public boolean createAccount(Account account) {
        String sql = "INSERT INTO accounts (account_number, holder_name, balance) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, account.getAccountNumber());
            ps.setString(2, account.getHolderName());
            ps.setDouble(3, account.getBalance());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error creating account: " + e.getMessage());
            return false;
        }
    }

    public Account getAccountByNumber(String accountNumber) {
        String sql = "SELECT * FROM accounts WHERE account_number = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, accountNumber);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Account acc = new Account();
                acc.setAccountId(rs.getInt("account_id"));
                acc.setAccountNumber(rs.getString("account_number"));
                acc.setHolderName(rs.getString("holder_name"));
                acc.setBalance(rs.getDouble("balance"));
                acc.setCreatedAt(rs.getString("created_at"));
                return acc;
            }
        } catch (SQLException e) {
            System.out.println("Error fetching account: " + e.getMessage());
        }
        return null;
    }

    public boolean updateBalance(String accountNumber, double newBalance) {
        String sql = "UPDATE accounts SET balance = ? WHERE account_number = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, newBalance);
            ps.setString(2, accountNumber);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error updating balance: " + e.getMessage());
            return false;
        }
    }

    public boolean accountExists(String accountNumber) {
        return getAccountByNumber(accountNumber) != null;
    }

    public List<Account> getAllAccounts() {
        List<Account> list = new ArrayList<>();
        String sql = "SELECT * FROM accounts";
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Account acc = new Account();
                acc.setAccountId(rs.getInt("account_id"));
                acc.setAccountNumber(rs.getString("account_number"));
                acc.setHolderName(rs.getString("holder_name"));
                acc.setBalance(rs.getDouble("balance"));
                acc.setCreatedAt(rs.getString("created_at"));
                list.add(acc);
            }
        } catch (SQLException e) {
            System.out.println("Error listing accounts: " + e.getMessage());
        }
        return list;
    }
}