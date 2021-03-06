package service;

import model.User;

public interface CrudUtility {
    void save(User user);
    boolean searchAccount(String accountNumber);
    User readAccount(String accountNumber);
}
