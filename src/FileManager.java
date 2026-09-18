import java.io.*;
import java.util.ArrayList;

/**
 * Handles saving and loading account/transaction data to plain text files,
 * providing persistence without requiring a database (project spec section 9).
 */
public class FileManager {

    private static final String ACCOUNTS_FILE = "accounts.txt";
    private static final String TRANSACTIONS_FILE = "transactions.txt";

    /** Saves every account (and its embedded customer info) to accounts.txt. */
    public void saveAccounts(ArrayList<Account> accounts) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ACCOUNTS_FILE))) {
            for (Account acc : accounts) {
                writer.write(acc.toFileString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving accounts: " + e.getMessage());
        }
    }

    /** Saves every transaction across all accounts to transactions.txt. */
    public void saveTransactions(ArrayList<Account> accounts) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TRANSACTIONS_FILE))) {
            for (Account acc : accounts) {
                for (Transaction t : acc.getTransactions()) {
                    writer.write(t.toFileString());
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            System.out.println("Error saving transactions: " + e.getMessage());
        }
    }

    /** Loads accounts (with embedded customer data) from accounts.txt, if it exists. */
    public ArrayList<Account> loadAccounts() {
        ArrayList<Account> accounts = new ArrayList<>();
        File file = new File(ACCOUNTS_FILE);
        if (!file.exists()) {
            return accounts;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                // Format: accountNumber|pin|balance|active|accountType|name|age|phone|address
                String[] parts = line.split("\\|", -1);
                String accNo = parts[0];
                String pin = parts[1];
                double balance = Double.parseDouble(parts[2]);
                boolean active = Boolean.parseBoolean(parts[3]);
                String type = parts[4];
                String name = parts[5];
                int age = Integer.parseInt(parts[6]);
                String phone = parts[7];
                String address = parts[8];

                Customer customer = new Customer(name, age, phone, address);
                Account acc;
                if (type.equals("SAVINGS")) {
                    acc = new SavingsAccount(accNo, pin, customer, balance);
                } else {
                    acc = new CurrentAccount(accNo, pin, customer, balance);
                }
                acc.setActive(active);
                accounts.add(acc);
            }
        } catch (IOException e) {
            System.out.println("Error loading accounts: " + e.getMessage());
        }
        return accounts;
    }

    /** Loads transactions from transactions.txt and attaches each to its matching account. */
    public void loadTransactions(ArrayList<Account> accounts) {
        File file = new File(TRANSACTIONS_FILE);
        if (!file.exists()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                Transaction t = Transaction.fromFileString(line);
                for (Account acc : accounts) {
                    if (acc.getAccountNumber().equals(t.getAccountNumber())) {
                        acc.addTransaction(t);
                        break;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading transactions: " + e.getMessage());
        }
    }
}
