import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents an individual deposit, withdrawal, or transfer.
 */
public class Transaction {
    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final String transactionId;
    private final String accountNumber;
    private final String type; // DEPOSIT, WITHDRAWAL, TRANSFER_OUT, TRANSFER_IN
    private final double amount;
    private final String description;
    private final String timestamp;

    public Transaction(String transactionId, String accountNumber, String type, double amount, String description) {
        this.transactionId = transactionId;
        this.accountNumber = accountNumber;
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.timestamp = LocalDateTime.now().format(FORMAT);
    }

    // Constructor used when reloading a transaction from file, where the timestamp already exists.
    public Transaction(String transactionId, String accountNumber, String type, double amount,
                        String description, String timestamp) {
        this.transactionId = transactionId;
        this.accountNumber = accountNumber;
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.timestamp = timestamp;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public String getTimestamp() {
        return timestamp;
    }

    /**
     * Serializes this transaction to a pipe-delimited line for file storage.
     * Format: transactionId|accountNumber|type|amount|description|timestamp
     */
    public String toFileString() {
        return transactionId + "|" + accountNumber + "|" + type + "|" + amount + "|" + description + "|" + timestamp;
    }

    public static Transaction fromFileString(String line) {
        String[] p = line.split("\\|", -1);
        return new Transaction(p[0], p[1], p[2], Double.parseDouble(p[3]), p[4], p[5]);
    }

    @Override
    public String toString() {
        return String.format("[%s] %s | %s | Rs.%.2f | %s | %s",
                timestamp, transactionId, type, amount, accountNumber, description);
    }
}
