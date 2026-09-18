/**
 * Current account: enforces a minimum balance requirement on withdrawals.
 * Demonstrates inheritance and method overriding (polymorphism).
 */
public class CurrentAccount extends Account {

    private static final double MINIMUM_BALANCE = 1000.0;

    public CurrentAccount(String accountNumber, String pin, Customer customer, double balance) {
        super(accountNumber, pin, customer, balance);
    }

    @Override
    public String getAccountType() {
        return "CURRENT";
    }

    /**
     * Overrides withdraw() to enforce a minimum balance rule specific to current accounts.
     */
    @Override
    public void withdraw(double amount) throws InvalidAmountException, InsufficientBalanceException {
        if (amount <= 0) {
            throw new InvalidAmountException("Withdrawal amount must be greater than zero.");
        }
        if (getBalanceInternal() - amount < MINIMUM_BALANCE) {
            throw new InsufficientBalanceException(
                    "Withdrawal denied: current accounts must maintain a minimum balance of Rs."
                            + MINIMUM_BALANCE);
        }
        setBalanceInternal(getBalanceInternal() - amount);
    }
}
