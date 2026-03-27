package util;

public class ValidationUtil {

    public static boolean isValidName(String name) {
        return name != null && !name.trim().isEmpty() && name.matches("[a-zA-Z ]+");
    }

    public static boolean isValidAccountNumber(String accNum) {
        return accNum != null && accNum.matches("ACC\\d{6}");
        // Format: ACC followed by 6 digits, e.g. ACC100001
    }

    public static boolean isValidAmount(double amount) {
        return amount > 0;
    }

    public static boolean hasSufficientBalance(double balance, double amount) {
        return balance >= amount;
    }

    public static boolean isValidDepositOrWithdraw(double amount) {
        if (!isValidAmount(amount)) {
            System.out.println("❌ Amount must be greater than 0.");
            return false;
        }
        return true;
    }
}