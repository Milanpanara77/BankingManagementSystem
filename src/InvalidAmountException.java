/**
 * Thrown when a deposit, withdrawal, or transfer amount is zero or negative.
 */
public class InvalidAmountException extends Exception {
    public InvalidAmountException(String message) {
        super(message);
    }
}
