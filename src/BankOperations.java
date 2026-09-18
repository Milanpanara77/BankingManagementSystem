/**
 * Defines the core banking operations that every account type must support.
 * This is the abstraction layer referenced in the project spec (section 6).
 */
public interface BankOperations {
    void deposit(double amount) throws InvalidAmountException;

    void withdraw(double amount) throws InvalidAmountException, InsufficientBalanceException;

    double checkBalance();
}
