/**
 * Handles account-number/PIN verification.
 */
public class Authentication {

    /**
     * Verifies that the supplied PIN matches the account's stored PIN.
     *
     * @throws InvalidPINException if the PIN does not match.
     */
    public static void verifyPin(Account account, String enteredPin) throws InvalidPINException {
        if (!account.getPin().equals(enteredPin)) {
            throw new InvalidPINException("Incorrect PIN. Access denied.");
        }
    }

    /**
     * Validates the format of a new PIN (must be exactly 4 digits).
     */
    public static void validatePinFormat(String pin) throws InvalidPINException {
        if (pin == null || !pin.matches("\\d{4}")) {
            throw new InvalidPINException("PIN must be exactly 4 digits.");
        }
    }
}
