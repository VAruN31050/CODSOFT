import java.util.Scanner;


abstract class Transaction {
    public abstract void execute(BankAccount account, Scanner scanner);
}


class BankAccount {
    private double balance;

    public BankAccount(double initialBalance) {
        this.balance = Math.max(initialBalance, 0); 
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.printf("Deposit successful. New balance: %.2f%n", balance);
        } else {
            System.out.println("Invalid deposit amount. Please try again.");
        }
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.printf("Withdrawal successful. New balance: %.2f%n", balance);
            return true;
        }
        System.out.println("Insufficient funds or invalid withdrawal amount.");
        return false;
    }
}


class CheckBalanceTransaction extends Transaction {
    @Override
    public void execute(BankAccount account, Scanner scanner) {
        System.out.printf("Your current balance is: %.2f%n", account.getBalance());
    }
}

class DepositTransaction extends Transaction {
    @Override
    public void execute(BankAccount account, Scanner scanner) {
        double amount = promptAmount(scanner, "Enter deposit amount: ");
        account.deposit(amount);
    }

    private double promptAmount(Scanner scanner, String message) {
        System.out.print(message);
        return scanner.nextDouble();
    }
}

class WithdrawTransaction extends Transaction {
    @Override
    public void execute(BankAccount account, Scanner scanner) {
        double amount = promptAmount(scanner, "Enter withdrawal amount: ");
        if (account.withdraw(amount)) {
            System.out.println("Please take your cash.");
        } else {
            System.out.println("Transaction failed. Please try again.");
        }
    }

    private double promptAmount(Scanner scanner, String message) {
        System.out.print(message);
        return scanner.nextDouble();
    }
}


public class ATM {
    private BankAccount account;
    private Scanner scanner;

    public ATM(BankAccount account) {
        this.account = account;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        Transaction[] transactions = {
                new CheckBalanceTransaction(),
                new DepositTransaction(),
                new WithdrawTransaction()
        };

        while (true) {
            displayMenu();
            int choice = getUserChoice();
            if (choice == 4) {
                System.out.println("Thank you for using the ATM. Goodbye!");
                scanner.close();
                break;
            } else if (choice >= 1 && choice <= 3) {
                transactions[choice - 1].execute(account, scanner);
            } else {
                System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void displayMenu() {
        System.out.println("\nATM Menu:");
        System.out.println("1. Check Balance");
        System.out.println("2. Deposit");
        System.out.println("3. Withdraw");
        System.out.println("4. Exit");
    }

    private int getUserChoice() {
        System.out.print("Choose an option: ");
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a number.");
            System.out.print("Choose an option: ");
            scanner.next();
        }
        return scanner.nextInt();
    }

    public static void main(String[] args) {
        BankAccount userAccount = new BankAccount(500); // Starting balance
        ATM atm = new ATM(userAccount);
        atm.start();
    }
}

