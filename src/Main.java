import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Entry point of the program; displays menus and controls application flow.
 */
public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final Bank bank = new Bank();

    public static void main(String[] args) {
        System.out.println("=================================================");
        System.out.println("      WELCOME TO THE BANKING MANAGEMENT SYSTEM");
        System.out.println("=================================================");

        boolean running = true;
        while (running) {
            printMainMenu();
            int choice = readInt();
            switch (choice) {
                case 1:
                    handleCreateAccount();
                    break;
                case 2:
                    handleLogin();
                    break;
                case 3:
                    running = false;
                    System.out.println("Thank you for using the Banking Management System. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid menu choice. Please select 1, 2, or 3.");
            }
        }
        scanner.close();
    }

    private static void printMainMenu() {
        System.out.println("\n----------- MAIN MENU -----------");
        System.out.println("1. Create Account");
        System.out.println("2. Login");
        System.out.println("3. Exit");
        System.out.print("Enter your choice: ");
    }

    // ---------- Create Account ----------

    private static void handleCreateAccount() {
        try {
            System.out.println("\n--- Create New Account ---");
            System.out.print("Enter full name: ");
            String name = scanner.nextLine();

            System.out.print("Enter age: ");
            int age = readInt();

            System.out.print("Enter phone number: ");
            String phone = scanner.nextLine();

            System.out.print("Enter address: ");
            String address = scanner.nextLine();

            System.out.print("Enter account type (SAVINGS/CURRENT): ");
            String type = scanner.nextLine().trim();

            System.out.print("Enter initial deposit amount: ");
            double deposit = readDouble();

            System.out.print("Set a 4-digit PIN: ");
            String pin = scanner.nextLine().trim();

            Customer customer = new Customer(name, age, phone, address);
            Account account = bank.createAccount(customer, pin, deposit, type);

            System.out.println("\nAccount created successfully!");
            System.out.println("Your account number is: " + account.getAccountNumber());
            System.out.println("Please remember your account number and PIN to log in.");

        } catch (InvalidAmountException | InvalidPINException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // ---------- Login ----------

    private static void handleLogin() {
        try {
            System.out.println("\n--- Login ---");
            System.out.print("Enter account number: ");
            String accNo = scanner.nextLine().trim();

            System.out.print("Enter PIN: ");
            String pin = scanner.nextLine().trim();

            Account account = bank.login(accNo, pin);
            System.out.println("\nLogin successful. Welcome, " + account.getCustomer().getName() + "!");
            showAccountMenu(account);

        } catch (AccountNotFoundException | InvalidPINException e) {
            System.out.println("Login failed: " + e.getMessage());
        }
    }

    // ---------- Post-login menu ----------

    private static void showAccountMenu(Account account) {
        boolean loggedIn = true;
        while (loggedIn) {
            System.out.println("\n----------- ACCOUNT MENU -----------");
            System.out.println("1. Check Balance");
            System.out.println("2. Deposit Money");
            System.out.println("3. Withdraw Money");
            System.out.println("4. Transfer Money");
            System.out.println("5. View Transaction History");
            System.out.println("6. View Account Details");
            System.out.println("7. Close Account");
            System.out.println("8. Logout");
            System.out.print("Enter your choice: ");

            int choice = readInt();
            switch (choice) {
                case 1:
                    System.out.printf("Current balance: Rs.%.2f%n", account.checkBalance());
                    break;
                case 2:
                    handleDeposit(account);
                    break;
                case 3:
                    handleWithdraw(account);
                    break;
                case 4:
                    handleTransfer(account);
                    break;
                case 5:
                    showTransactionHistory(account);
                    break;
                case 6:
                    System.out.println(account);
                    System.out.println(account.getCustomer());
                    break;
                case 7:
                    if (handleCloseAccount(account)) {
                        loggedIn = false; // account closed, force logout
                    }
                    break;
                case 8:
                    loggedIn = false;
                    System.out.println("Logged out successfully.");
                    break;
                default:
                    System.out.println("Invalid menu choice. Please select an option from 1 to 8.");
            }
        }
    }

    private static void handleDeposit(Account account) {
        try {
            System.out.print("Enter amount to deposit: ");
            double amount = readDouble();
            bank.deposit(account, amount);
            System.out.printf("Deposit successful. New balance: Rs.%.2f%n", account.checkBalance());
        } catch (InvalidAmountException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void handleWithdraw(Account account) {
        try {
            System.out.print("Enter amount to withdraw: ");
            double amount = readDouble();
            bank.withdraw(account, amount);
            System.out.printf("Withdrawal successful. New balance: Rs.%.2f%n", account.checkBalance());
        } catch (InvalidAmountException | InsufficientBalanceException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void handleTransfer(Account sender) {
        try {
            System.out.print("Enter receiver's account number: ");
            String receiverAcc = scanner.nextLine().trim();
            System.out.print("Enter amount to transfer: ");
            double amount = readDouble();

            bank.transfer(sender, receiverAcc, amount);
            System.out.printf("Transfer successful. New balance: Rs.%.2f%n", sender.checkBalance());
        } catch (InvalidAmountException | InsufficientBalanceException | AccountNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void showTransactionHistory(Account account) {
        ArrayList<Transaction> history = account.getTransactions();
        if (history.isEmpty()) {
            System.out.println("No transactions found for this account.");
            return;
        }
        System.out.println("\n--- Transaction History ---");
        for (Transaction t : history) {
            System.out.println(t);
        }
    }

    private static boolean handleCloseAccount(Account account) {
        try {
            System.out.print("Are you sure you want to close this account? (yes/no): ");
            String confirm = scanner.nextLine().trim();
            if (!confirm.equalsIgnoreCase("yes")) {
                System.out.println("Account closure cancelled.");
                return false;
            }
            bank.closeAccount(account);
            System.out.println("Account closed successfully.");
            return true;
        } catch (InsufficientBalanceException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

    // ---------- Input helpers ----------

    private static int readInt() {
        while (true) {
            try {
                String line = scanner.nextLine().trim();
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a whole number: ");
            }
        }
    }

    private static double readDouble() {
        while (true) {
            try {
                String line = scanner.nextLine().trim();
                return Double.parseDouble(line);
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a valid number: ");
            }
        }
    }
}
