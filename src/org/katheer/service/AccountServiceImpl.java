package org.katheer.service;

import org.katheer.dao.AccountDao;
import org.katheer.dto.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("accountService")
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountDao accountDao;

    @Override
    public int createAccount(Account account) {
        return accountDao.createAccount(account);
    }

    @Override
    public Account getAccountDetails(int accNo) {
        return accountDao.getAccount(accNo);
    }

    @Override
    public double depositAmount(int accNo, double amount) {
        return accountDao.deposit(accNo, amount);
    }

    @Override
    public double withdrawAmount(int accNo, double amount) {
        return accountDao.withdraw(accNo, amount);
    }

}
