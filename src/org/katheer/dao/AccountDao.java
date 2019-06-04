package org.katheer.dao;

import org.katheer.dto.Account;

public interface AccountDao {
    int createAccount(Account account);
    Account getAccount(int accNo);
    double deposit(int accNo, double amount);
    double withdraw(int accNo, double amount);
}
