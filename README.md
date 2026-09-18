#  Banking Management System

A **console-based Banking Management System built using Core Java**.  
The project demonstrates important Java and Object-Oriented Programming concepts such as **classes and objects, encapsulation, inheritance, abstraction, polymorphism, interfaces, exception handling, collections, file handling, and authentication**.

---

##  Project Overview

The Banking Management System simulates basic banking activities through a menu-driven command-line interface.

A user can:

- Create a Savings or Current account
- Log in using an account number and 4-digit PIN
- Check account balance
- Deposit money
- Withdraw money
- Transfer money to another account
- View transaction history
- View customer and account details
- Close an account after clearing its balance
- Store account and transaction data permanently using files

The application automatically loads previously saved data when it starts and saves changes after banking operations.

---

##  Objectives

The main objectives of this project are:

1. To implement a practical application using **Core Java**.
2. To understand and demonstrate **OOP principles**.
3. To implement **custom exception handling** for invalid banking operations.
4. To use **Java Collections Framework** for managing accounts and transactions.
5. To implement **file handling** for data persistence.
6. To demonstrate **inheritance and method overriding** using different account types.
7. To implement simple **account authentication using account number and PIN**.

---

##  Features

### 1. Create Account

Users can create:

- **Savings Account**
- **Current Account**

During account creation, the user provides:

- Full name
- Age
- Phone number
- Address
- Account type
- Initial deposit
- 4-digit PIN

A unique account number is automatically generated.

---

### 2. Login & Authentication

Users can log in using:

```text
Account Number + PIN
```

The system verifies the account and PIN before providing access to banking operations.

---

### 3. Check Balance

The current account balance can be viewed at any time after login.

Example:

```text
Current balance: Rs.5000.00
```

---

### 4. Deposit Money

Users can deposit money into their account.

The system:

1. Validates the amount.
2. Updates the account balance.
3. Creates a transaction record.
4. Saves the updated data to files.

---

### 5. Withdraw Money

Users can withdraw money if the transaction satisfies the account's balance rules.

The system handles:

- Invalid amounts
- Insufficient balance
- Minimum-balance requirements for Current Accounts

---

### 6. Transfer Money

Users can transfer money from their account to another active account.

The system:

- Verifies the receiver account.
- Prevents transfers to the same account.
- Checks the sender's balance.
- Updates both accounts.
- Records `TRANSFER_OUT` and `TRANSFER_IN` transactions.

---

### 7. Transaction History

Each account maintains its own transaction history.

Transactions can include:

- Deposit
- Withdrawal
- Transfer In
- Transfer Out
- Initial Deposit

Example:

```text
[T1001] DEPOSIT | Rs.5000.00 | AC123456 | Initial deposit
```

---

### 8. Account Details

The user can view:

- Account number
- Account type
- Balance
- Customer information

---

### 9. Close Account

An account can only be closed when its balance is **zero**.

This prevents an account containing remaining funds from being closed accidentally.

---

##  OOP Concepts Used

This project is specifically designed to demonstrate important Object-Oriented Programming concepts.

### Encapsulation

Customer and account data are kept inside classes using private fields and controlled methods.

Example:

```java
private double balance;
```

The balance is modified through methods such as:

```java
deposit()
withdraw()
```

---

### Inheritance

`SavingsAccount` and `CurrentAccount` inherit from the abstract `Account` class.

```text
              Account
             /       \
            /         \
 SavingsAccount   CurrentAccount
```

---

### Abstraction

`Account` is an abstract class that provides common account behavior while allowing specialized account classes to implement their own behavior.

---

### Polymorphism

Different account types can be handled through an `Account` reference while their overridden methods provide account-specific behavior.

For example:

```java
Account account;
```

The actual object can be:

```java
SavingsAccount
```

or:

```java
CurrentAccount
```

---

### Interface

The `BankOperations` interface defines common banking operations:

```java
deposit()
withdraw()
checkBalance()
```

The `Account` class implements this interface.

---

##  Exception Handling

The project uses **custom checked exceptions** to handle invalid operations safely.

### Custom Exceptions

| Exception | Purpose |
|---|---|
| `InvalidAmountException` | Handles zero or negative transaction amounts |
| `InsufficientBalanceException` | Handles withdrawals/transfers exceeding allowed balance |
| `AccountNotFoundException` | Handles invalid or inactive account numbers |
| `InvalidPINException` | Handles invalid PIN format or authentication |

Example:

```java
try {
    bank.withdraw(account, amount);
} catch (InvalidAmountException | InsufficientBalanceException e) {
    System.out.println("Error: " + e.getMessage());
}
```

This prevents the application from crashing because of normal user input errors.

---

##  Collections Used

The project uses Java Collections to manage data efficiently.

### ArrayList

Used to store:

```java
ArrayList<Account>
```

and transaction history:

```java
ArrayList<Transaction>
```

### HashMap

Used for fast account lookup:

```java
HashMap<String, Account>
```

The account number is used as the key.

This allows efficient retrieval of accounts without repeatedly searching the complete account list.

---

##  File Handling

The application uses file handling to make data persistent.

The following files are automatically created:

```text
accounts.txt
transactions.txt
```

The system uses:

- `BufferedReader`
- `BufferedWriter`
- File I/O

When the program starts:

```text
Saved data → Loaded into the program
```

When an operation is completed:

```text
Updated data → Saved to files
```

Therefore, account and transaction information remains available after restarting the program.

---

##  Project Structure

```text
BankingManagementSystem/
│
├── src/
│   ├── Account.java
│   ├── Authentication.java
│   ├── Bank.java
│   ├── BankOperations.java
│   ├── CurrentAccount.java
│   ├── Customer.java
│   ├── FileManager.java
│   ├── InsufficientBalanceException.java
│   ├── InvalidAmountException.java
│   ├── InvalidPINException.java
│   ├── AccountNotFoundException.java
│   ├── Main.java
│   ├── SavingsAccount.java
│   └── Transaction.java
│
├── accounts.txt          # Generated automatically
├── transactions.txt      # Generated automatically
└── README.md
```

---

##  Class Description

| Class / Interface | Responsibility |
|---|---|
| `Main` | Program entry point, menus, user interaction |
| `Bank` | Account creation, lookup, deposits, withdrawals, transfers and account closure |
| `Account` | Abstract base class containing common account functionality |
| `SavingsAccount` | Savings-specific behavior and interest calculation |
| `CurrentAccount` | Current-account rules including minimum balance |
| `Customer` | Stores customer information |
| `Transaction` | Represents a banking transaction |
| `BankOperations` | Defines common banking operations |
| `Authentication` | Handles PIN validation and verification |
| `FileManager` | Saves and loads account/transaction data |
| `InvalidAmountException` | Custom exception for invalid amounts |
| `InsufficientBalanceException` | Custom exception for insufficient funds |
| `AccountNotFoundException` | Custom exception for missing/inactive accounts |
| `InvalidPINException` | Custom exception for invalid PINs |


---

##  How to Run

### Prerequisites

Install:

- **Java JDK 8 or above**
- A terminal / command prompt
- Optional: VS Code, IntelliJ IDEA, or another Java IDE

Check Java installation:

```bash
java -version
javac -version
```

---

### Compile

Open the terminal inside the `src` directory:

```bash
cd src
```

Compile all Java files:

```bash
javac *.java
```

---

### Run

```bash
java Main
```

---

##  Sample Usage

### Main Menu

```text
----------- MAIN MENU -----------
1. Create Account
2. Login
3. Exit
Enter your choice:
```

### Creating an Account

```text
--- Create New Account ---
Enter full name: Rahul Sharma
Enter age: 20
Enter phone number: 9876543210
Enter address: Bhopal
Enter account type (SAVINGS/CURRENT): SAVINGS
Enter initial deposit amount: 5000
Set a 4-digit PIN: 1234

Account created successfully!
Your account number is: AC785500
```

### Account Menu

```text
----------- ACCOUNT MENU -----------
1. Check Balance
2. Deposit Money
3. Withdraw Money
4. Transfer Money
5. View Transaction History
6. View Account Details
7. Close Account
8. Logout
```

---

##  Banking Rules Implemented

### Savings Account

- Supports deposits and withdrawals.
- Supports simplified interest calculation.
- Interest rate implemented in the project: **4% annual simplified rate**.

### Current Account

- Supports deposits and withdrawals.
- Enforces a **Rs.1000 minimum balance rule** during withdrawals.

### Account Closure

An account cannot be closed while it has a positive balance.

```text
Balance > 0
     ↓
Cannot close account
     ↓
Withdraw remaining balance
     ↓
Balance = 0
     ↓
Account can be closed
```

---

##  Technologies Used

- **Language:** Java
- **Programming Style:** Object-Oriented Programming
- **Collections:** ArrayList, HashMap
- **File Handling:** BufferedReader, BufferedWriter
- **Exception Handling:** Custom checked exceptions
- **Interface:** `BankOperations`
- **Inheritance:** `SavingsAccount`, `CurrentAccount`
- **Abstraction:** Abstract `Account` class
- **IDE:** VS Code / IntelliJ IDEA / Eclipse

---

##  Future Enhancements

The current project is a Core Java console application. It can be extended into a larger banking application by adding:

- JDBC and MySQL database
- Java Swing or JavaFX GUI
- Password/PIN hashing
- Admin login and dashboard
- Customer registration system
- ATM simulation
- Interest calculation based on time
- Account statements
- Email/SMS transaction notifications
- Role-based access control
- Better input validation
- Database transactions and concurrency handling
- REST API and web-based frontend

---

##  Learning Outcomes

After completing this project, the following concepts can be understood practically:

1. Designing classes and objects.
2. Applying the four major OOP principles.
3. Creating and implementing interfaces.
4. Using abstract classes.
5. Applying inheritance and method overriding.
6. Handling errors using custom exceptions.
7. Working with Java Collections.
8. Performing file input/output.
9. Building menu-driven console applications.
10. Structuring a multi-class Java project.

---


##  Project Type

**Core Java / OOP Academic Project**


**Primary Concepts:**  
`OOP` • `Inheritance` • `Polymorphism` • `Abstraction` • `Encapsulation` • `Interfaces` • `Exception Handling` • `Collections` • `File I/O` • `Authentication`

Submitted by :

Milan Panara

VIT BHOPAL

25BAI10042
