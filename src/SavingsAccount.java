/**
 * Savings account: earns interest, demonstrates inheritance and method overriding.
 */
public class SavingsAccount extends Account {

    private static final double INTEREST_RATE = 0.04; // 4% annual, simplified

    public SavingsAccount(String accountNumber, String pin, Customer customer, double balance) {
        super(accountNumber, pin, customer, balance);
    }

    @Override
    public String getAccountType() {
        return "SAVINGS";
    }

    /**
     * Applies a simple flat-rate interest to the current balance.
     * Overrides the no-op implementation in Account (polymorphism).
     */
    @Override
    public void calculateInterest() {
        double interest = getBalanceInternal() * INTEREST_RATE;
        setBalanceInternal(getBalanceInternal() + interest);
    }
}
