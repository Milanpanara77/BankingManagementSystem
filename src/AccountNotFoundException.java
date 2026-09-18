/**
 * Thrown when an account number does not exist in the bank's records.
 */
public class AccountNotFoundException extends Exception {
    public AccountNotFoundException(String message) {
        super(message);
    }
}
