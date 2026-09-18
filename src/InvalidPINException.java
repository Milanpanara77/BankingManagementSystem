/**
 * Thrown when a PIN is incorrect, or in the wrong format during account creation.
 */
public class InvalidPINException extends Exception {
    public InvalidPINException(String message) {
        super(message);
    }
}
