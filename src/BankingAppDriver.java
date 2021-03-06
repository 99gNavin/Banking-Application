import model.ApplicationMessage;
import model.User;
import service.BankingUtilityService;
import service.DbConnection;
import service.DbUtilityService;
import service.FileUtilityService;

import java.util.Scanner;

public class BankingAppDriver {
    public static void main(String[] args) {
        try {
            ApplicationMessage applicationMessage;
            BankingUtilityService service = new BankingUtilityService(new DbUtilityService());
            DbConnection.getConnection();

            System.out.println("1. FILE");
            System.out.println("2. DATABASE (default)");
            System.out.print("Where do you want to perform your operation ? ");

            Scanner scanner = new Scanner(System.in);
            int operationChoice = scanner.nextInt();

            if (operationChoice == 1)
                service = new BankingUtilityService(new FileUtilityService());

            String ch;
            do {
                welcomeBankMessage();

                int userChoice = scanner.nextInt();
                switch (userChoice) {
                    case 1:
                        System.out.println("You are about to create a new account...");
                        System.out.print("Enter your name : ");
                        String name = scanner.next();
                        System.out.print("Enter your address : ");
                        String address = scanner.next();

                        applicationMessage = service.createAccount(name, address);
                        applicationMessage.getUser().showUserDetail();
                        break;

                    case 2:
                        System.out.println("You are about to view your account details...");
                        System.out.print("Enter account Number: ");
                        String accountNumber = scanner.next();
                        applicationMessage = service.viewAccountDetails(accountNumber);
                        applicationMessage.getUser().showUserDetail();
                        break;

                    case 3:
                        System.out.println("You are about to deposit amount in your account...");
                        System.out.print("Enter account Number: ");
                        String accountNum = scanner.next();
                        System.out.println("Enter amount to be deposited : ");
                        double amount = scanner.nextDouble();
                        User amountDeposited = service.deposit(accountNum, amount);
                        amountDeposited.showUserDetail();
                        break;

                    case 4:
                        System.out.println("You are about to withdraw amount from your account...");
                        System.out.print("Enter account Number: ");
                        String accountNumb = scanner.next();
                        System.out.print("Enter amount : ");
                        double newAmount = scanner.nextDouble();
                        User withDraw = service.withDraw(accountNumb, newAmount);
                        withDraw.showUserDetail();
                        break;

                    case 5:
                        welcomeATMMessage();

                        String accountNum1;
                        int currentPIN;
                        boolean flag;
                        int choice = scanner.nextInt();
                        if (choice == 1) {
                            System.out.println("You are about to withdraw amount from your ATM...\n");
                            System.out.print("Enter account Number: ");
                            accountNum1 = scanner.next();
                            do {
                                System.out.println("Enter your PIN: ");
                                currentPIN = scanner.nextInt();
                                if (service.checkUserCredentials(accountNum1, currentPIN)) {
                                    flag = true;
                                } else {
                                    flag = false;
                                    System.out.println("Invalid PIN please try again...");
                                }
                            } while (!flag);
                            System.out.println("Enter amount to be withdrawn : ");
                            double amt = scanner.nextDouble();
                            User withDrawn = service.withDraw(accountNum1, currentPIN, amt);
                            withDrawn.showUserDetail();
                            break;

                        } else if (choice == 2) {
                            System.out.println("You are about to change your ATM PIN...\n");
                            System.out.print("Enter account Number: ");
                            accountNum1 = scanner.next();
                            do {
                                System.out.println("Enter your current PIN: ");
                                currentPIN = scanner.nextInt();
                                if (service.checkUserCredentials(accountNum1, currentPIN)) {
                                    flag = true;
                                } else {
                                    flag = false;
                                    System.out.println("Invalid PIN please try again...");
                                }
                            } while (!flag);

                            System.out.print("Are you sure to change your PIN y/Y or n/N ?");
                            String c = scanner.next();

                            if (c.equals("Y") || (c.equals("y"))) {
                                System.out.print("Enter new PIN: ");
                                int newPIN = scanner.nextInt();
                                service.changePIN(accountNum1, currentPIN, newPIN, true);
                                System.out.println("Password changed successfully ....");
                            } else {
                                service.changePIN(accountNum1, currentPIN, currentPIN, false);
                            }
                            break;
                        }

                    case 6:
                        try {
                            if (DbConnection.getConnection() != null)
                                DbConnection.getConnection().close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        System.exit(0);
                        break;

                    default:
                        System.out.println("Invalid input...");
                        break;
                }
                System.out.println("\nDo you want to continue y/Y or n/N");
                ch = scanner.next();
            } while (ch.equals("Y") || ch.equals("y"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void welcomeBankMessage() {
        System.out.println("--------------------------");
        System.out.println("Welcome To Nabil Bank");
        System.out.println("--------------------------");
        System.out.println("1: Create your account");
        System.out.println("2: View your account details");
        System.out.println("3: Deposit Amount");
        System.out.println("4: widDraw Amount");
        System.out.println("5: ATMBooth");
        System.out.println("6: EXIT");
        System.out.println("--------------------------");
        System.out.print("\nEnter your choice [1-5] : ");
    }

    public static void welcomeATMMessage() {
        System.out.println("--------------------------");
        System.out.println("Welcome To Nabil Bank ATM");
        System.out.println("--------------------------");
        System.out.println("1: WithDraw amount");
        System.out.println("2: Change PIN");
        System.out.println("--------------------------");
        System.out.print("\nEnter your choice [1 or 2] : ");
    }
}
