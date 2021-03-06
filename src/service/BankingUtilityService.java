package service;

import model.ApplicationMessage;
import model.User;

public class BankingUtilityService {
    private final CrudUtility utility;

    public BankingUtilityService(CrudUtility utility) {
        this.utility = utility;
    }

    public ApplicationMessage createAccount(String name, String address) {
        ApplicationMessage applicationMessage = new ApplicationMessage();
        try {
            User user = new User(true);
            user.setName(name);
            user.setAddress(address);

            utility.save(user);

            applicationMessage.setMessage("Account created successfully ...");
            applicationMessage.setSuccess(true);
            applicationMessage.setUser(user);

            return applicationMessage;

        } catch (Exception e) {
            e.printStackTrace();
        }
        applicationMessage.setMessage("Error while creating account ...");
        applicationMessage.setSuccess(false);
        return applicationMessage;
    }

    public ApplicationMessage viewAccountDetails(String accountNumber) {
        ApplicationMessage applicationMessage = new ApplicationMessage();
        try {
            if (utility.searchAccount(accountNumber)) {
                User user = utility.readAccount(accountNumber);

                applicationMessage.setSuccess(true);
                applicationMessage.setUser(user);

                return applicationMessage;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        applicationMessage.setMessage("Error while viewing details ...");
        applicationMessage.setSuccess(false);
        return applicationMessage;
    }

    public User deposit(String accountNumber, double amount) {
        try {
            if (utility.searchAccount(accountNumber)) {
                User user = utility.readAccount(accountNumber);
                double newBalance = user.getBalance() + amount;
                user.setBalance(newBalance);
                utility.save(user);

                return user;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private User updateWithDrawAmount(String accountNumber, double amount) {
        try {
            User user = utility.readAccount(accountNumber);
            if (user.getBalance() >= amount) {
                double newBalance = user.getBalance() - amount;
                user.setBalance(newBalance);
                utility.save(user);
                return user;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //    For Bank
    public User withDraw(String accountNumber, double amount) {
        try {
            if (utility.searchAccount(accountNumber)) {
                return updateWithDrawAmount(accountNumber, amount);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //  For ATM
    public User withDraw(String accountNumber, int PIN, double amount) {
        try {
            if (utility.searchAccount(accountNumber)) {
                User user = utility.readAccount(accountNumber);
                if (user.getPIN() == PIN && accountNumber.equals(user.getAccountNumber()))
                    return updateWithDrawAmount(accountNumber, amount);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void changePIN(String accountNumber, int currentPIN, int newPIN, boolean flag) {
        try {
            User user = utility.readAccount(accountNumber);
            if (user.getPIN() == currentPIN && flag) {
                user.setPIN(newPIN);
                utility.save(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean checkUserCredentials(String accountNumber, int currentPIN) {
        try {
            if (utility.searchAccount(accountNumber)) {
                User user = utility.readAccount(accountNumber);
                if (accountNumber.equals(user.getAccountNumber()) && currentPIN == user.getPIN())
                    return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
