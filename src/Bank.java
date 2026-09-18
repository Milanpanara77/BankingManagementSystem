import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Manages the collection of accounts and major banking operations
 * (account creation, lookup, transfers, transaction history).
 */
public class Bank {

    private final ArrayList<Account> accounts;
    private final HashMap<String, Account> accountIndex; // fast lookup by account number
    private final FileManager fileManager;
    private int transactionCounter;

    public Bank() {
        this.fileManager = new FileManager();
        this.accounts = fileManager.loadAccounts();
        fileManager.loadTransactions(accounts);

        this.accountIndex = new HashMap<>();
        for (Account acc : accounts) {
            accountIndex.put(acc.getAccountNumber(), acc);
        }
        this.transactionCounter = computeNextTransactionNumber();
    }

    /** Scans loaded transactions to continue transaction IDs (T1001, T1002, ...) without collisions. */
    private int computeNextTransactionNumber() {
        int max = 1000;
        for (Account acc : accounts) {
            for (Transaction t : acc.getTransactions()) {
                String id = t.getTransactionId().replaceAll("[^0-9]", "");
                if (!id.isEmpty()) {
                    max = Math.max(max, Integer.parseInt(id));
                }
            }
        }
        return max + 1;
    }

    private String generateAccountNumber() {
        Random rand = new Random();
        String accNo;
        do {
            accNo = "AC" + (100000 + rand.nextInt(900000));
        } while (accountIndex.containsKey(accNo));
        return accNo;
    }

    private String nextTransactionId() {
        return "T" + (transactionCounter++);
    }

    /**
     * Creates a new account, generating a unique account number.
     */
    public Account createAccount(Customer customer, String pin, double initialDeposit, String accountType)
            throws InvalidAmountException, InvalidPINException {

        if (initialDeposit < 0) {
            throw new InvalidAmountException("Initial deposit cannot be negative.");
        }
        Authentication.validatePinFormat(pin);

        String accNo = generateAccountNumber();
        Account account;
        if (accountType.equalsIgnoreCase("CURRENT")) {
            account = new CurrentAccount(accNo, pin, customer, initialDeposit);
        } else {
            account = new SavingsAccount(accNo, pin, customer, initialDeposit);
        }

        if (initialDeposit > 0) {
            Transaction t = new Transaction(nextTransactionId(), accNo, "DEPOSIT", initialDeposit, "Initial deposit");
            account.addTransaction(t);
        }

        accounts.add(account);
        accountIndex.put(accNo, account);
        saveAll();
        return account;
    }

    /** Finds an account by account number, or throws if it doesn't exist or is closed. */
    public Account findAccount(String accountNumber) throws AccountNotFoundException {
        Account acc = accountIndex.get(accountNumber);
        if (acc == null || !acc.isActive()) {
            throw new AccountNotFoundException("No active account found with number: " + accountNumber);
        }
        return acc;
    }

    /** Authenticates a customer for login using account number + PIN. */
    public Account login(String accountNumber, String pin) throws AccountNotFoundException, InvalidPINException {
        Account acc = findAccount(accountNumber);
        Authentication.verifyPin(acc, pin);
        return acc;
    }

    public void deposit(Account account, double amount) throws InvalidAmountException {
        account.deposit(amount);
        Transaction t = new Transaction(nextTransactionId(), account.getAccountNumber(),
                "DEPOSIT", amount, "Cash deposit");
        account.addTransaction(t);
        saveAll();
    }

    public void withdraw(Account account, double amount) throws InvalidAmountException, InsufficientBalanceException {
        account.withdraw(amount);
        Transaction t = new Transaction(nextTransactionId(), account.getAccountNumber(),
                "WITHDRAWAL", amount, "Cash withdrawal");
        account.addTransaction(t);
        saveAll();
    }

    /**
     * Transfers funds from one existing account to another after validating both accounts
     * and the sender's balance.
     */
    public void transfer(Account sender, String receiverAccountNumber, double amount)
            throws InvalidAmountException, InsufficientBalanceException, AccountNotFoundException {

        if (amount <= 0) {
            throw new InvalidAmountException("Transfer amount must be greater than zero.");
        }
        Account receiver = findAccount(receiverAccountNumber);
        if (receiver.getAccountNumber().equals(sender.getAccountNumber())) {
            throw new AccountNotFoundException("Cannot transfer to the same account.");
        }

        sender.withdraw(amount); // validates sender's balance / min-balance rules
        receiver.deposit(amount);

        String txId = nextTransactionId();
        sender.addTransaction(new Transaction(txId, sender.getAccountNumber(),
                "TRANSFER_OUT", amount, "Transfer to " + receiver.getAccountNumber()));
        receiver.addTransaction(new Transaction(txId, receiver.getAccountNumber(),
                "TRANSFER_IN", amount, "Transfer from " + sender.getAccountNumber()));

        saveAll();
    }

    /** Closes an account after validating that its balance is zero. */
    public void closeAccount(Account account) throws InsufficientBalanceException {
        if (account.checkBalance() > 0) {
            throw new InsufficientBalanceException(
                    "Account must have a zero balance before closing. Please withdraw remaining funds first.");
        }
        account.setActive(false);
        saveAll();
    }

    public ArrayList<Account> getAllAccounts() {
        return accounts;
    }

    private void saveAll() {
        fileManager.saveAccounts(accounts);
        fileManager.saveTransactions(accounts);
    }
}
