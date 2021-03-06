package model;

import java.util.Random;

//Model:: only contains attributes with getters and setters
public class User {

    private String accountNumber;
    private String name;
    private String address;
    private double balance;
    private int PIN;

    public User(boolean newUser) {
        Random rand = new Random();
        String accountNumber = "NB";
        int PIN = 1000 + rand.nextInt(9000);
        for (int i = 0; i < 14; i++) {
            int n = rand.nextInt(10);
            accountNumber += Integer.toString(n);
        }

        if (newUser) {
            this.balance = 0.0;
            this.accountNumber = accountNumber;
            this.PIN = PIN;
        }
    }

    public User() {
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getPIN() {
        return PIN;
    }

    public void setPIN(int PIN) {
        this.PIN = PIN;
    }

    public void showUserDetail() {
        System.out.println("--------------------------");
        System.out.println("UserName : " + this.name);
        System.out.println("Address : " + this.address);
        System.out.println("Account Number : " + this.accountNumber);
        System.out.println("Balance : " + this.balance);
        System.out.println("--------------------------");
    }
}
