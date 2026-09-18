# Problem Statement — Banking Management System

**Project title:** Banking Management System
**Domain:** Financial services / Core Java application development
**Implementation:** Console-based application in Core Java (JDK 8+), file-based persistence
**Document version:** 1.0

---

## 1. Background

A bank branch must keep an accurate, up-to-date record of every customer, every account and
every rupee that moves between them. Historically this was done in physical ledgers: a clerk
wrote each deposit and withdrawal by hand, balances were recomputed manually, and a customer
who wanted a statement had to wait for a staff member to copy the relevant lines out by hand.

That approach fails on several fronts at once:

- **Accuracy.** Balances recalculated by hand after every transaction invite arithmetic
  errors, and a single wrong figure propagates through every later entry.
- **Speed.** Locating one account among thousands of ledger rows is a linear search performed
  by a human being.
- **Security.** A paper ledger has no concept of identity verification; anyone who can reach
  the book can read or alter it.
- **Auditability.** Retrospectively answering *"what happened to this account last Tuesday"*
  requires reading the ledger page by page.
- **Durability.** A lost, damaged or destroyed ledger means the records are simply gone.

An automated system addresses all five: arithmetic becomes deterministic, lookup becomes
instantaneous, access requires a credential, every operation is timestamped and recorded, and
the data lives in a file that can be backed up.

---

## 2. Problem Definition

> **Design and implement a console-based Banking Management System in Core Java that allows a
> customer to open a bank account, authenticate themselves using an account number and a PIN,
> and then perform the everyday banking operations of deposit, withdrawal, fund transfer,
> balance enquiry, transaction-history enquiry and account closure — with all data validated
> against banking rules, all errors reported through meaningful messages rather than program
> crashes, and all state persisted to disk so that it survives across program runs, without
> using any database server, web framework or third-party library.**

The system must demonstrate, in a non-trivial way, the core pillars of object-oriented
programming — encapsulation, inheritance, abstraction and polymorphism — together with Java
collections, custom exception handling and file I/O.

---

## 3. Objectives

1. **Model the domain with objects.** Represent customers, accounts, transactions and the bank
   itself as distinct classes with clearly separated responsibilities.
2. **Support multiple account types.** Provide at least two account types that genuinely differ
   in behaviour rather than in name only — a savings account that accrues interest and a current
   account that enforces a minimum balance.
3. **Secure every operation behind authentication.** No balance may be read and no money may be
   moved until the correct account number / PIN pair has been supplied.
4. **Enforce banking business rules.** Reject non-positive amounts, reject withdrawals that
   exceed the available balance, reject withdrawals that breach a minimum-balance rule, reject
   transfers to non-existent or closed accounts, and reject self-transfers.
5. **Fail gracefully.** Every invalid input or rule violation must raise a purpose-built checked
   exception that is caught and surfaced as a readable message; the program must never terminate
   on a stack trace during normal misuse.
6. **Maintain a complete audit trail.** Record every deposit, withdrawal and both legs of every
   transfer with a unique ID, type, amount, description and timestamp.
7. **Persist data without a database.** Save and restore the complete state of the bank using
   plain-text file I/O only.
8. **Keep the system usable.** Present a clear, numbered, menu-driven console interface that a
   first-time user can operate without instructions.

---

## 4. Scope

### 4.1 In Scope

| Area | Included |
|------|----------|
| Account management | Create account (SAVINGS / CURRENT), view details, close account |
| Customer data | Name, age, phone number, address |
| Authentication | Account-number + 4-digit-PIN login; PIN format validation on creation |
| Transactions | Deposit, withdrawal, inter-account transfer |
| Enquiry | Balance enquiry, full transaction history |
| Account rules | Interest calculation (savings), minimum balance (current), zero-balance closure rule |
| Error handling | Four custom checked exceptions plus input-format recovery |
| Persistence | Pipe-delimited text files, written after every state change and reloaded on startup |
| Interface | Text-based menu driven console |

### 4.2 Out of Scope

The following are deliberately excluded from this version and are listed instead as future
enhancements:

- Relational database storage (JDBC / MySQL)
- Graphical user interface (Swing, JavaFX) or a web front-end
- Multi-user or concurrent access, and network / client-server operation
- Cryptographic hashing or encryption of PINs (PINs are stored as plain text)
- Loans, fixed deposits, cheque handling, standing instructions, overdrafts
- Interest scheduling by calendar date (interest is a simple flat-rate method, not time-based)
- Admin / bank-employee role with privileges distinct from a customer
- Statement export to PDF or email, SMS alerts, and regulatory reporting

---

## 5. Stakeholders

| Stakeholder | Interest in the system |
|-------------|------------------------|
| Customer / account holder | Primary user — opens the account and performs all transactions. |
| Bank (as an institution) | Requires that every rupee is accounted for and every rule enforced. |
| Evaluator / examiner | Needs to build, run and assess the project from a clean machine. |
| Future developer | Needs a structure that can be extended to a database or GUI without rewriting the core. |

---

## 6. Functional Requirements

| ID | Requirement | Priority |
|----|-------------|----------|
| FR-01 | The system shall allow a user to create a new account by supplying name, age, phone number, address, account type, initial deposit and a PIN. | High |
| FR-02 | The system shall generate a unique account number of the form `AC` followed by six digits, and shall guarantee no collision with an existing number. | High |
| FR-03 | The system shall accept a PIN only if it consists of exactly four digits. | High |
| FR-04 | The system shall reject a negative initial deposit. | High |
| FR-05 | The system shall authenticate a user by matching the supplied account number and PIN before granting access to any account operation. | High |
| FR-06 | The system shall display the current balance of the logged-in account on request. | High |
| FR-07 | The system shall accept deposits of any amount strictly greater than zero and credit them to the balance. | High |
| FR-08 | The system shall permit a withdrawal only when the amount is greater than zero and does not exceed the available balance. | High |
| FR-09 | The system shall prevent a withdrawal from a CURRENT account that would leave the balance below Rs. 1000. | High |
| FR-10 | The system shall transfer funds between two distinct, existing, active accounts, debiting the sender and crediting the receiver atomically from the user's point of view. | High |
| FR-11 | The system shall reject a transfer to a non-existent account, a closed account, or the sender's own account. | High |
| FR-12 | The system shall record every deposit, withdrawal and transfer as a transaction carrying a unique ID, account number, type, amount, description and timestamp. | High |
| FR-13 | The system shall display the full transaction history of the logged-in account in chronological order. | High |
| FR-14 | The system shall display account details including account number, type, holder name, balance, status and customer information. | Medium |
| FR-15 | The system shall allow an account to be closed only when its balance is zero, after an explicit confirmation from the user. | Medium |
| FR-16 | The system shall treat a closed account as non-existent for login and transfer purposes. | Medium |
| FR-17 | The system shall provide a method to apply interest to a SAVINGS account at a flat rate of 4%. | Medium |
| FR-18 | The system shall save all accounts and transactions to disk after every state-changing operation. | High |
| FR-19 | The system shall reload all accounts and transactions from disk at startup, and shall start with an empty bank if no data files exist. | High |
| FR-20 | The system shall allow the user to log out and to exit the application cleanly from the menus. | Medium |

---

## 7. Non-Functional Requirements

| ID | Category | Requirement |
|----|----------|-------------|
| NFR-01 | Portability | Shall run unmodified on Windows, macOS and Linux with any JDK 8 or newer. |
| NFR-02 | Dependencies | Shall use only the Java standard library — no third-party JARs, no build tool, no database server. |
| NFR-03 | Reliability | Shall not terminate abnormally on invalid user input; all foreseeable errors shall be caught and reported. |
| NFR-04 | Usability | Every prompt shall state what is expected; every error message shall state what went wrong and, where relevant, what the rule is. |
| NFR-05 | Performance | Account lookup by account number shall be O(1) via a hash-based index; the system shall respond instantly for the data volumes expected of a mini project. |
| NFR-06 | Maintainability | Each class shall have a single, documented responsibility; business rules shall be expressed as named constants rather than literals scattered through the code. |
| NFR-07 | Extensibility | Adding a new account type shall require only a new subclass of `Account`, with no change to `Bank` or `Main`. |
| NFR-08 | Durability | No committed transaction shall be lost if the program is closed, because state is flushed to disk immediately after each operation. |
| NFR-09 | Auditability | Every balance change shall have a corresponding timestamped transaction record. |

---

## 8. Assumptions and Constraints

**Assumptions**

1. A single user operates the application at a time; there is no concurrent access.
2. The user has read/write permission in the directory from which the program is launched.
3. Customer names and addresses do not contain the pipe character `|`, which is reserved as the
   field delimiter in the data files.
4. All amounts are in Indian Rupees (Rs.); no currency conversion is required.
5. The data files are not edited by hand while the program is running.

**Constraints**

1. The implementation must use Core Java only — no frameworks, no external libraries.
2. Persistence must be file-based; a database server may not be assumed to be installed.
3. The interface must be text-based and runnable from a terminal.
4. Monetary values are held as `double`, which is adequate for a teaching project but is not
   the correct choice for production financial software (see the *Limitations* section of the
   project report).

---

## 9. Expected Outcome

A single, self-contained Java program that an evaluator can compile with `javac *.java` and run
with `java Main` on any machine with a JDK installed, and that demonstrably:

- creates accounts of two behaviourally distinct types,
- refuses access without the correct PIN,
- performs deposits, withdrawals and transfers while enforcing every stated business rule,
- produces a complete, timestamped transaction history,
- reports every error as a readable message instead of a crash, and
- retains all of the above across restarts of the program.
