import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ATM {
    public static void main(String[] args) {
        ATMSystem atmSystem = new ATMSystem();
        atmSystem.start();
    }
}

class ATMSystem {
    private List<User> users;
    private Scanner scanner;

    public ATMSystem() {
        users = new ArrayList<>();
        scanner = new Scanner(System.in);
        initializeUsers();
    }

    private void initializeUsers() {
        // Add some users with initial balance
        users.add(new User("user1", "1234", new Account(1000.0)));
        users.add(new User("user2", "5678", new Account(500.0)));
    }

    public void start() {
        System.out.println("Welcome to the ATM");
        User currentUser = authenticateUser();
        if (currentUser != null) {
            showMenu(currentUser);
        } else {
            System.out.println("Invalid user ID or PIN.");
        }
    }

    private User authenticateUser() {
        System.out.print("Enter user ID: ");
        String userId = scanner.nextLine();
        System.out.print("Enter PIN: ");
        String pin = scanner.nextLine();

        for (User user : users) {
            if (user.getUserId().equals(userId) && user.getPin().equals(pin)) {
                return user;
            }
        }
        return null;
    }

    private void showMenu(User user) {
        int choice;
        do {
            System.out.println("\nATM Menu:");
            System.out.println("1. Transaction History");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Transfer");
            System.out.println("5. Check Balance");
            System.out.println("6. Quit");
            System.out.print("Enter choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    user.getAccount().printTransactionHistory();
                    break;
                case 2:
                    handleWithdraw(user);
                    break;
                case 3:
                    handleDeposit(user);
                    break;
                case 4:
                    handleTransfer(user);
                    break;
                case 5:
                    System.out.println("Your balance is: " + user.getAccount().getBalance());
                    break;
                case 6:
                    System.out.println("Thank you for using the ATM. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 6);
    }

    private void handleWithdraw(User user) {
        System.out.print("Enter amount to withdraw: ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // consume newline
        if (user.getAccount().withdraw(amount)) {
            System.out.println("Withdrawal successful.");
        } else {
            System.out.println("Insufficient balance.");
        }
    }

    private void handleDeposit(User user) {
        System.out.print("Enter amount to deposit: ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // consume newline
        user.getAccount().deposit(amount);
        System.out.println("Deposit successful.");
    }

    private void handleTransfer(User user) {
        System.out.print("Enter recipient user ID: ");
        String recipientId = scanner.nextLine();
        System.out.print("Enter amount to transfer: ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // consume newline

        User recipient = findUserById(recipientId);
        if (recipient != null && user.getAccount().transfer(recipient.getAccount(), amount)) {
            System.out.println("Transfer successful.");
        } else {
            System.out.println("Transfer failed.");
        }
    }

    private User findUserById(String userId) {
        for (User user : users) {
            if (user.getUserId().equals(userId)) {
                return user;
            }
        }
        return null;
    }
}

class User {
    private String userId;
    private String pin;
    private Account account;

    public User(String userId, String pin, Account account) {
        this.userId = userId;
        this.pin = pin;
        this.account = account;
    }

    public String getUserId() {
        return userId;
    }

    public String getPin() {
        return pin;
    }

    public Account getAccount() {
        return account;
    }
}

class Account {
    private double balance;
    private List<Transaction> transactionHistory;

    public Account(double initialBalance) {
        this.balance = initialBalance;
        this.transactionHistory = new ArrayList<>();
    }

    public boolean withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            transactionHistory.add(new Transaction("Withdrawal", amount));
            return true;
        }
        return false;
    }

    public void deposit(double amount) {
        balance += amount;
        transactionHistory.add(new Transaction("Deposit", amount));
    }

    public boolean transfer(Account recipient, double amount) {
        if (amount <= balance) {
            balance -= amount;
            recipient.deposit(amount);
            transactionHistory.add(new Transaction("Transfer", amount));
            return true;
        }
        return false;
    }

    public void printTransactionHistory() {
        System.out.println("Transaction History:");
        for (Transaction transaction : transactionHistory) {
            System.out.println(transaction);
        }
    }

    public double getBalance() {
        return balance;
    }
}

class Transaction {
    private String type;
    private double amount;

    public Transaction(String type, double amount) {
        this.type = type;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return type + ": " + amount;
    }
}
