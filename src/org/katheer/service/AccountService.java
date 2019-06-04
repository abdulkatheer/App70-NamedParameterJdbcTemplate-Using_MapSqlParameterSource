package org.katheer.service;

import org.katheer.dto.Account;

public interface AccountService {
    int createAccount(Account account);
    Account getAccountDetails(int accNo);
    double depositAmount(int accNo, double amount);
    double withdrawAmount(int accNo, double amount);
}
