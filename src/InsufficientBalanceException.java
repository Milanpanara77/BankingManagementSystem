/**
 * Thrown when a withdrawal or transfer amount exceeds the available balance.
 */
public class InsufficientBalanceException extends Exception {
    public InsufficientBalanceException(String message) {
        super(message);
    }
}
