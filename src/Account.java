import java.util.ArrayList;

/**
 * Base class containing common account properties and operations.
 * SavingsAccount and CurrentAccount extend this class (inheritance).
 * Implements BankOperations to provide the required deposit/withdraw/balance behavior (abstraction).
 */
public abstract class Account implements BankOperations {
    private final String accountNumber;
    private String pin;
    private Customer customer;
    private double balance;
    private boolean active;
    private final ArrayList<Transaction> transactions;

    public Account(String accountNumber, String pin, Customer customer, double balance) {
        this.accountNumber = accountNumber;
        this.pin = pin;
        this.customer = customer;
        this.balance = balance;
        this.active = true;
        this.transactions = new ArrayList<>();
    }

    // ---------- Encapsulated getters/setters ----------

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    protected double getBalanceInternal() {
        return balance;
    }

    protected void setBalanceInternal(double balance) {
        this.balance = balance;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    public void addTransaction(Transaction t) {
        transactions.add(t);
    }

    /** Every concrete account type must report what kind of account it is. */
    public abstract String getAccountType();

    /**
     * Subclasses can override this to apply account-specific interest logic.
     * Default implementation does nothing (e.g. current accounts do not earn interest).
     */
    public void calculateInterest() {
        // No-op by default; overridden in SavingsAccount.
    }

    // ---------- BankOperations implementation ----------

    @Override
    public void deposit(double amount) throws InvalidAmountException {
        if (amount <= 0) {
            throw new InvalidAmountException("Deposit amount must be greater than zero.");
        }
        balance += amount;
    }

    @Override
    public void withdraw(double amount) throws InvalidAmountException, InsufficientBalanceException {
        if (amount <= 0) {
            throw new InvalidAmountException("Withdrawal amount must be greater than zero.");
        }
        if (amount > balance) {
            throw new InsufficientBalanceException("Insufficient balance for this withdrawal.");
        }
        balance -= amount;
    }

    @Override
    public double checkBalance() {
        return balance;
    }

    /**
     * Serializes this account to a pipe-delimited line for file storage.
     * Format: accountNumber|pin|balance|active|accountType|name|age|phone|address
     */
    public String toFileString() {
        return accountNumber + "|" + pin + "|" + balance + "|" + active + "|" + getAccountType()
                + "|" + customer.toFileString();
    }

    @Override
    public String toString() {
        return String.format(
                "Account No: %s | Type: %s | Holder: %s | Balance: Rs.%.2f | Status: %s",
                accountNumber, getAccountType(), customer.getName(), balance, active ? "ACTIVE" : "CLOSED");
    }
}
